var mongoose = require('mongoose');
var ObjectId = mongoose.Schema.Types.ObjectId;

// Schema
var person = new mongoose.Schema({
    ID: String,
    firstname: { type: String, required: true },
    lastname: { type: String, required: true },
    facebookID: String,
    phoneNo: String,
    //macAddress: String,
    insertionDate: Date,
    pictures: [ObjectId]
});

module.exports = person;