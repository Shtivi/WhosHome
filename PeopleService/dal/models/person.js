var mongoose = require('mongoose');
var validate = require("mongoose-validator");

// Schema
var person = new mongoose.Schema({
    ID: String,
    firstname: { type: String, required: true },
    lastname: { type: String, required: true },
    facebookID: String,
    phoneNo: String,
    macAddress: String,
    insertionDate: Date
});

module.exports = person;