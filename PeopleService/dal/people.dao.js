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
            ID: person.ID,
            facebookID: person.facebookID,
            phoneNo: person.phoneNo,
            macAddress: person.macAddress
        });

        personObject.save((err, data) => {
            if (err) reject(err);
            else resolve(data._doc);
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

module.exports.getPerson = (_id) => {
    return new Promise((resolve, reject) => {
        models.Person.findById(_id, (err, data) => {
            if (err) reject(err);
            else {
                if (data) resolve(data._doc)
                else resolve(null);
            };
        })
    })
}

module.exports.deletePerson = (_id) => {
    return new Promise((resolve, reject) => {
        models.Person.findByIdAndRemove(_id, (err, data) => {
            if (err) reject(err);
            else resolve(data._doc);
        });
    })
}

module.exports.search = (params) => {
    return new Promise((resolve, reject) => {
        // Convert the params to query AND expression
        var queryParams = {};

        for (var pName in params) {
            if (params[pName]) {
                queryParams[pName] = params[pName];
            }
        }
        
        models.Person.find(queryParams).exec((err, data) => {
            if (err) reject(err);
            else resolve(data);
        })
    })
}