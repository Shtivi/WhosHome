const fr = require('face-recognition');
const Rect = fr.Rect;
const detector = fr.FaceDetector();
const asyncDetector = fr.AsyncFaceDetector();
const asyncRecognizer = fr.AsyncFaceRecognizer();
const Promise = require("promise");
const path = require('path');
const picturesDB = require('../dal/pictures.dao');
const peopleDB = require('../dal/people.dao');
const models = require('../dal/models/models');
const fs = require("fs");
// const image2base64 = require('image-to-base64');

var conf = {};

// Methods

function savePictureFile(dir, uploadedFile) {
    return new Promise((resolve, reject) => {
        var pictureFileName = new Date().getTime() + path.extname(uploadedFile.name);
        var fileNewPath = path.join(dir, pictureFileName);
        uploadedFile.mv(fileNewPath, (err) => {
            if (err) reject(err);
            else resolve(fileNewPath);
        });
    })
}

function rectAdapter(rect) {
    return {
        x: rect.left,
        y: rect.top,
        height: Math.round(Math.abs(rect.top - rect.bottom)),
        width: Math.round(Math.abs(rect.left - rect.right))
    }
}

function toRectAdapter(positions) {
    return new Rect(positions.x, positions.y, Math.round(Math.abs(positions.x + positions.height)), Math.round(Math.abs(positions.y + positions.width)));
}

function saveRecognitionModel() {
    console.log("Saving recognition model...");

    return new Promise((resolve, reject) => {
        var modelState = asyncRecognizer.serialize();
        
        fs.writeFile(conf.frDataFile, JSON.stringify(modelState), err => {
            if (err) reject (err);
            else {
                console.log("Recognition model saved at: " + conf.frDataFile);
                resolve();
            }
        });
    });
}

function loadRecognitionModelSync() {
    if (fs.existsSync(conf.frDataFile)) {
        var modelState = require(conf.frDataFile);
        asyncRecognizer.load(modelState);
    }
}

// API

module.exports = (opts) => {
    conf = opts;
    
    loadRecognitionModelSync();

    return module.exports;
}

module.exports.uploadPicture = (file) => {
    return new Promise((resolve, reject) => {
        savePictureFile(conf.picsDir, file).then((pictureFullPath) => {
            var img = fr.loadImage(pictureFullPath);
            var rawFaces = detector.locateFaces(img);
            var faces = rawFaces.map((face) => rectAdapter(face.rect));
            var pictureFileName = path.basename(pictureFullPath);
            
            picturesDB.addPicture(new models.Picture({
                filename: pictureFileName,
                faces: faces
            })).then((doc) => {
                resolve(doc);
            }, (err) => {
                reject(err);
            })            
        }, reject);
    })
}

module.exports.attachFaceToPerson = (pictureID, faceID, personID) => {
    return new Promise((resolve, reject) => {
        Promise.all([
            picturesDB.attachFace(pictureID, faceID, personID), 
            peopleDB.attachPicture(personID, pictureID)
        ]).then((results) => {
            var pictureDoc = results[0]._doc;
            var picturePath = path.join(conf.picsDir, pictureDoc.filename);
            var imageRGB = fr.loadImage(picturePath);
            var face = pictureDoc.faces.find(f => f._doc._id == faceID);
            var faceRGB = detector.getFacesFromLocations(imageRGB, [toRectAdapter(face._doc)]);
            asyncRecognizer.addFaces(faceRGB, personID);

            saveRecognitionModel();

            module.exports.getPicture(pictureID).then(resolve, reject);
        }, err => {
            reject(err);
        })
    })
}

module.exports.getPersonByFaceId = (pictureID, faceID) => {
    return new Promise((resolve, reject) => {
        picturesDB.getPicture(pictureID).then((pictureDoc) => {
            var face = pictureDoc.faces.find(f => f._id == faceID);
            if (!face) {
                reject("No such face");
            } else {
                peopleDB.getPerson(face._doc.personID).then(resolve, reject);
            }
        }, err => reject(err))
    })
}

module.exports.getPicture = (pictureID) => {
    return new Promise((resolve, reject) => {
        picturesDB.getPicture(pictureID).then((data) => {
            var recognizedFaces = data.faces.filter(f => {
                return f._doc.personID
            });

            Promise.all(recognizedFaces.map(f => peopleDB.getPerson(f._doc.personID))).then(results => {
                for (var i = 0; i < results.length; i++) {
                    var currentPerson = results[i]._doc;
                    var faceDoc = data.faces.find(face => face._doc.personID && face._doc.personID.equals(currentPerson._id));
                    faceDoc._doc.person = currentPerson;
                }

                resolve(data);
            }, err => reject(err));
        }, (err) => {
            reject(err);
        })
    })
}

module.exports.recognizeFace = (imageRGB, faceRect) => {
    return new Promise((resolve, reject) => {
        var faceImageRGB = detector.getFacesFromLocations(imageRGB, [faceRect]);

        asyncRecognizer.predictBest(faceImageRGB[0]).then(prediction => {
            var result = rectAdapter(faceRect);
            result.personID = prediction.className;
            result.precision = 1 - prediction.distance;

            peopleDB.getPerson(prediction.className).then(doc => {
                result.person = doc._doc;
                resolve(result);
            }, reject);
        }).catch(reject);
    });
}

module.exports.recognizePeopleInPicture = (picFile) => {
    return new Promise((resolve, reject) => {
        savePictureFile(conf.tempPicsDir, picFile).then((picPath) => {
            var imageRGB = fr.loadImage(picPath);
            var faceRects = detector.locateFaces(imageRGB);

            if (faceRects.length == 0) {
                fs.unlinkSync(picPath);
                reject("No faces found");
            } else {
                Promise.all(faceRects.map(face => module.exports.recognizeFace(imageRGB, face.rect))).then(results => {
                    resolve(results);
                }, err => {
                    reject(err);
                })
            }
        }, reject);
    })
}

module.exports.getPictureBase64 = (pictureID) => {
    return new Promise((resolve, reject) => {
        picturesDB.getPicture(pictureID).then(doc => {
            if (!doc) {
                reject("No such image")
            } else {
                var pathToPic = path.join(conf.picsDir, doc.filename);
                fs.readFile(pathToPic, (err, data) => {
                    if (err) reject(err);
                    else {
                        var buffer = Buffer.from(data);
                        var base64 = buffer.toString('base64');
                        resolve('data:image/png;base64, ' + base64);
                    }
                })
            }
        }, err => reject(err));
    })
}