var mongoose = require("mongoose");
const Promise = require("promise");
var models = require("./models/models");
var ObjectId = mongoose.Types.ObjectId;

// API

module.exports.addPicture = (picture) => {
    return new Promise((resolve, reject) => {
        picture.save((err, data) => {
            if (err) reject(err);
            else resolve(data);
        })
    });
}

module.exports.getPicture = (id) => {
    return new Promise((resolve, reject) => {
        models.Picture.findById(id, (err, data) => {
            if (err) reject(err);
            else if (!data) resolve(null);
            else resolve(data._doc);
        });
    });
}

module.exports.getPicturesByIds = (idsArray) => {
    return new Promise((resolve, reject) => {
        models.Picture.find({ '_id': { $in: idsArray }}, (err, data) => {
            if (err) reject(err);
            else resolve(data);
        });
    })
}

module.exports.attachFace = (pictureID, faceID, personID) => {
    return new Promise((resolve, reject) => {
        models.Picture.findById(pictureID, (err, data) => {
            if (err) reject(err);
            else {
                var pictureDoc = data._doc;
                var requestedFace = pictureDoc.faces.find(face => face._id == faceID);
                requestedFace.personID = personID;
                data.save((err, data) => {
                    if (err) reject(err);
                    else resolve(data);
                })
            }
        })
    })
}