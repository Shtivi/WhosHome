var mongoose = require('mongoose');
var validate = require("mongoose-validator");

// Schema
var person = new mongoose.Schema({
    firstname: { type: String, required: true },
    lastname: { type: String, required: true },
    idNo: Number,
    facebookID: String,
    phoneNo: String,
    MAC: String
});

module.exports = person;