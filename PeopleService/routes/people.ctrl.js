var express = require('express');
var router = express.Router();
var peopleDao = require("../dal/people.dao");

router.get('/', (req, res, next) => {
  peopleDao.getPeople().then((data) => {
    res.send(data)
  }, (err) => {
    res.status(500).send(err.message)
  });
});

router.post('/', (req, res, next) => {
  peopleDao.addPerson(req.body).then((data) => {
    res.send(data);
  }, (err) => {
    res.status(500).send(err.message);
  });
})

module.exports = router;
