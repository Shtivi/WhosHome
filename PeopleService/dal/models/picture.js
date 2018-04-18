var mongoose = require('mongoose');
var ObjectId = mongoose.Schema.Types.ObjectId;

var picture = new mongoose.Schema({
    path: { type: String, required: true },
    tags: [{ type: ObjectId }]
})

module.exports = picture;