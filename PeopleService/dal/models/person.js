var mongoose = require('mongoose');

var person = new mongoose.Schema({
    firstname: { type: String, required: true },
    lastname: { type: String, required: true },
    idNo: Number,
    facebookID: String,
    phoneNo: String,
    MAC: String
})

mongoose.model = person;