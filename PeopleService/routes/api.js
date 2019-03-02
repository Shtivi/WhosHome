var express = require('express');
var router = express.Router();
var fileUpload = require('express-fileupload');

module.exports = (app) => {
    app.use(fileUpload());

    router.use('/people', require("./people.ctrl"));
    //router.use('/pictures', require("./pictures.ctrl"));

    router.get('/_monitor/isAlive', (req, res) => {
        res.status(200).send(true);
    });

    return router;
};