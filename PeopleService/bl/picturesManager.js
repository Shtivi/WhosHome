const fr = require('face-recognition');
const Rect = fr.Rect;
const detector = fr.FaceDetector();
const asyncDetector = fr.AsyncFaceDetector();
const recognizer = fr.FaceRecognizer();
const Promise = require("promise");
const path = require('path');
const picturesDB = require('../dal/pictures.dao');
const peopleDB = require('../dal/people.dao');
const models = require('../dal/models/models');
const fs = require("fs");

var picsDir = null;
var frDataFile = null;

// Methods

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
        var modelState = recognizer.serialize();
        
        fs.writeFile(frDataFile, JSON.stringify(modelState), err => {
            if (err) reject (err);
            else {
                console.log("Recognition model saved at: " + frDataFile);
                resolve();
            }
        });
    });
}

function loadRecognitionModelSync() {
    if (fs.existsSync(frDataFile)) {
        var modelState = require(frDataFile);
        recognizer.load(modelState);
    }
}

// API

module.exports = (picsDirPath, faceRecgonitionDataFile) => {
    picsDir = picsDirPath;
    frDataFile = faceRecgonitionDataFile;

    loadRecognitionModelSync();

    return module.exports;
};

module.exports.uploadPicture = (file) => {
    return new Promise((resolve, reject) => {
        var pictureFileName = new Date().getTime() + '.' + path.extname(file.name);
        var pictureFullPath = path.join(picsDir, pictureFileName);
        file.mv(pictureFullPath, (err) => {
            if (err) reject(err);
            else {
                var img = fr.loadImage(pictureFullPath);
                var faces = detector.locateFaces(img).map((face) => rectAdapter(face.rect));
                
                picturesDB.addPicture(new models.Picture({
                    filename: pictureFileName,
                    faces: faces
                })).then((doc) => {
                    resolve(doc);
                }, (err) => {
                    reject(err);
                })
            }
        });
    })
}

module.exports.attachFaceToPerson = (pictureID, faceID, personID) => {
    return new Promise((resolve, reject) => {
        Promise.all([
            picturesDB.attachFace(pictureID, faceID, personID), 
            peopleDB.attachPicture(personID, pictureID)
        ]).then((results) => {
            var pictureDoc = results[0]._doc;
            var picturePath = path.join(picsDir, pictureDoc.filename);
            var imageRGB = fr.loadImage(picturePath);
            var face = pictureDoc.faces.find(f => f._doc._id == faceID);
            var faceRGB = detector.getFacesFromLocations(imageRGB, [toRectAdapter(face._doc)]);
            recognizer.addFaces(faceRGB, personID);

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