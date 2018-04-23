var mongoose = require("mongoose");

module.exports.Person = mongoose.model("Person", require("./person"));
module.exports.Face = mongoose.model("Face", require("./face"));
module.exports.Picture = mongoose.model("Picture", require("./picture"));