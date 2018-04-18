var mongoose = require("mongoose");

module.exports.Person = mongoose.model("Person", require("./person"));
module.exports.Picture = mongoose.model("Picture", require("./picture"));