var mongoose = require("mongoose");
const Promise = require("promise");
var models = require("./models/models");

var ObjectId = mongoose.Types.ObjectId;

// API

module.exports.addPerson = (person) => {
    return new Promise((resolve, reject) => {
        var personObject = new models.Person({
            firstname: person.firstname,
            lastname: person.lastname,
            idNo: person.idNo,
            facebookID: person.facebookID,
            phoneNo: person.phoneNo,
            MAC: person.MAC
        });

        personObject.save((err, data) => {
            if (err) reject(err);
            else resolve(data.doc);
        })
    })
}

module.exports.getPeople = () => {
    return new Promise((resolve, reject) => {
        models.Person.find({}, (err, data) => {
            if (err) reject(err);
            else resolve(data);
        })
    })
}