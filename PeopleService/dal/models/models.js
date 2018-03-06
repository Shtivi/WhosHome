var mongoose = require("mongoose");

module.exports.Person = mongoose.model("Person", require("./person"));