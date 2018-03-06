var express = require('express');
var router = express.Router();

var peopleCtrl = require("./people.ctrl");

router.use('/people', peopleCtrl);

module.exports = router;