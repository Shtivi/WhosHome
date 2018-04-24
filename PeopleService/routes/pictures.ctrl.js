var express = require('express');
var router = express.Router();
var path = require('path')
var picturesBL = require("../bl/picturesManager")(path.join(__dirname, '../', '/pics'), 'face-recognition.data.json');

router.post('/upload', (req, res, next) => {
    if (!req.files) {
        res.status(400).send("No files uploaded");
    } else {
        picturesBL.uploadPicture(req.files.file).then((pictureData) => {
            res.send(pictureData);
        }, (err) => {
            res.status(500).send(err);
        })
    }
})

router.get('/:pictureID', (req, res, next) => {
    picturesBL.getPicture(req.params.pictureID).then(data => {
        res.send(data);
    }, err => {
        res.status(500).send(data);
    })
})

router.post('/faces', (req, res, next) => {
    picturesBL.attachFaceToPerson(req.body.pictureID, req.body.faceID, req.body.personID).then((data) => {
        res.send(data);
    }, (err) => {
        res.status(500).send(data);
    })
})

module.exports = router;