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

router.get('/limit/:limit', (req, res, next) => {
  peopleDao.getLimited(Number(req.params.limit)).then((results) => {
    res.send(results)
  }, (err) => {
    res.status(500).send(err)
  });
})

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
  peopleDao.search({
    firstname: req.body.firstname,
    lastname: req.body.lastname,
    ID: req.body.ID,
    facebookID: req.body.ID,
    phoneNo: req.body.phoneNo,
    macAddress: req.body.macAddress
  }).then((results) => {
    res.send(results);
  }, (err) => {
    res.status(500).send(err);
  })
})

module.exports = router;
