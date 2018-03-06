var express = require('express');
var router = express.Router();
var peopleDao = require("../dal/people.dao");

router.get('/:id', (req, res, next) => {
  peopleDao.getPerson(req.params.id).then((person) => {
    res.send(person);
  }, (err) => {
    res.status(500).send(err);
  });
})

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

router.put('/', (req, res, next) => {
  res.send("Not implemented yet");
})

router.delete('/:id', (req, res, next) => {
  peopleDao.deletePerson(req.params.id).then((person) => {
    res.send(person);
  }, (err) => {
    res.status(500).send(err);
  });
})

router.post('/search', (req, res, next) => {
  res.send("Not implemented yet");
})

module.exports = router;
