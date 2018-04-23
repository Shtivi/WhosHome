var mongoose = require('mongoose');
var ObjectId = mongoose.Schema.Types.ObjectId;

var face = new mongoose.Schema({
    x: { type: Number, required: true },
    y: { type: Number, required: true },
    width: { type: Number, required: true },
    height: { type: Number, required: true },
    personID: ObjectId
});

module.exports = face;