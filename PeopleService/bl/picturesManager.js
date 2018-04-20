const fr = require('face-recognition');
const Promise = require("promise");
const path = require('path');
const fs = require('fs');

var picsDir = null;

// API

module.exports = (picsDirPath) => {
    picsDir = picsDirPath;
    return module.exports;
};

module.exports.uploadPicture = (file) => {
    return new Promise((resolve, reject) => {
        file.mv(path.join(picsDir, 'file.png'), (err) => {
            if (err) reject(err);
            else resolve();
        });
    })
}

