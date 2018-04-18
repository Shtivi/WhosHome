var mongoose = require("mongoose");
const Promise = require("promise");
var models = require("./models/models");
var ObjectId = mongoose.Types.ObjectId;

// API

module.exports.addPicture = (path, tagged) => {
    return new Promise((resolve, reject) => {
        reject("not implemeted");
    });
}

module.exports.getPicture = (id) => {
    return new Promise((resolve, reject) => {
        models.Picture.findById(id, (err, data) => {
            if (err) reject(err);
            else resolve(data);
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