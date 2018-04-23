const fr = require('face-recognition');
const detector = fr.FaceDetector();
const asyncDetector = fr.AsyncFaceDetector();
const Promise = require("promise");
const path = require('path');
const fs = require('fs');
const picturesDB = require('../dal/pictures.dao');
const models = require('../dal/models/models');

var picsDir = null;

// Methods

function extractExtension(filename) {
    return filename.substring(filename.lastIndexOf('.') + 1);
}

function rectAdapter(rect) {
    return {
        x: rect.left,
        y: rect.top,
        height: Math.round(Math.abs(rect.top - rect.bottom)),
        width: Math.round(Math.abs(rect.left - rect.right))
    }
}

// API

module.exports = (picsDirPath) => {
    picsDir = picsDirPath;
    return module.exports;
};

module.exports.uploadPicture = (file) => {
    return new Promise((resolve, reject) => {
        var pictureFileName = new Date().getTime() + '.' + extractExtension(file.name);
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

