var express = require('express');
var router = express.Router();

router.use('/people', require("./people.ctrl"));
router.use('/pictures', require("./pictures.ctrl"));

module.exports = router;