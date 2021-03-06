var mongoose = require("mongoose");
const Promise = require("promise");
var models = require("./models/models");

var ObjectId = mongoose.Types.ObjectId;

// API

module.exports.addPerson = (person) => {
    return new Promise((resolve, reject) => {
        // Validate
        if (!person.firstname || !person.lastname) {
            reject({ message: "A person must have a first name and a last name" });
        } else {
            var personObject = new models.Person({
                firstname: person.firstname,
                lastname: person.lastname,
                ID: person.ID,
                facebookID: person.facebookID,
                phoneNo: person.phoneNo,
                macAddress: person.macAddress,
                insertionDate: new Date().getTime(),
                pictures: []
            });
    
    
            personObject.save((err, data) => {
                if (err) reject(err);
                else resolve(data._doc);
            })
        }
    })
}

module.exports.getAllPeople = () => {
    return new Promise((resolve, reject) => {
        models.Person.find({}, (err, data) => {
            if (err) reject(err);
            else resolve(data);
        })
    })
}

module.exports.getAllPeopleMinified = () => {
    return new Promise((resolve, reject) => {
        models.Person
            .find({})
            .select({
                "firstname": 1,
                "lastname": 1
            })
            .exec((err, data) => {
                if (err) reject(err);
                else resolve(data);
            })
    })
}

module.exports.getPerson = (_id) => {
    return new Promise((resolve, reject) => {
        if (!ObjectId.isValid(_id)) {
            reject({message: "Not a valid id"});
        }

        models.Person.findById(_id, (err, data) => {
            if (err) reject(err);
            else resolve(data);
        })
    })
}

module.exports.deletePerson = (_id) => {
    return new Promise((resolve, reject) => {
        if (!ObjectId.isValid(_id)) {
            reject({message: "Not a valid id"});
        }

        models.Person.findByIdAndRemove(_id, (err, data) => {
            if (err) reject(err);
            else resolve(data._doc);
        });
    })
}

module.exports.findPeople = (params) => {
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

module.exports.getPeopleByIds = (idsList) => {
    return new Promise((resolve, reject) => {
        models.Person.find({'_id': {$in: idsList}}, (err, data) => {
            if (err) reject(err);
            else resolve(data);
        })
    });
}

module.exports.search = (params) => {
    return new Promise((resolve, reject) => {
        // Convert the params to OR experssions
        var query = [];
        
        for (var paramName in params) {
            if (paramName == 'ID' && !ObjectId.isValid[params.ID]) {
                continue;
            }

            var currentField = {};
            currentField[paramName] = {
                $regex: new RegExp(params[paramName], 'i')
            }

            // Add the current field to the query
            query.push(currentField);
        }

        // Execute query
        models.Person.find().or(query).select({'firstname': 1, 'lastname': 1}).exec((err, data) => {
            if (err) reject(err);
            else resolve(data);
        })
    });
}

module.exports.getLimited = (limit) => {
    return new Promise((resolve, reject) => {
        models.Person.find({}).limit(limit).exec((err, data) => {
            if (err) reject(err);
            else resolve(data);
        })
    })
}

module.exports.updatePerson = (personData) => {
    return new Promise((resolve, reject) => {
        models.Person.findByIdAndUpdate(personData._id, personData, {upsert: true}, (err, data) => {
            if (err) reject(err);
            else resolve(data);
        })
    })
}

module.exports.attachPicture = (personID, pictureID) => {
    return new Promise((resolve, reject) => {
        models.Person.findById(personID, (err, personDoc) => {
            if (err) reject(err);
            else {
                personDoc._doc.pictures.push(pictureID);
                personDoc.save((err, data) => {
                    if (err) reject(err);
                    else resolve(data._doc);
                })
            }
        });
    })
}