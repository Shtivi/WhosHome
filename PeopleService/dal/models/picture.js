var mongoose = require('mongoose');
var ObjectId = mongoose.Schema.Types.ObjectId;
var Face = require('./face');

var picture = new mongoose.Schema({
    filename: { type: String, required: true },
    faces: [Face]
})

module.exports = picture;