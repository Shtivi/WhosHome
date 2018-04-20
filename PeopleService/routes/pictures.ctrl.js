var express = require('express');
var router = express.Router();
var path = require('path')
var picturesBL = require("../bl/picturesManager")(path.join(__dirname, '../', '/pics'));

router.post('/upload', (req, res, next) => {
    if (!req.files) {
        res.status(400).send("No files uploaded");
    } else {
        picturesBL.uploadPicture(req.files.file).then(() => {
            res.send();
        }, (err) => {
            res.status(500).send(err);
        })
    }
})

module.exports = router;