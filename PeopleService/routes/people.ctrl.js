var express = require('express');
var router = express.Router();
var peopleDao = require("../dal/people.dao");

router.get('/', function(req, res, next) {
  peopleDao.getPeople().then((data) => {
    res.send(data)
  }, (err) => {
    res.status(500).send(err.message)
  });
});

module.exports = router;
