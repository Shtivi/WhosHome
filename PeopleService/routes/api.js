var express = require('express');
var router = express.Router();
var fileUpload = require('express-fileupload');

module.exports = (app) => {
    app.use(fileUpload());

    router.use('/people', require("./people.ctrl"));
    //router.use('/pictures', require("./pictures.ctrl"));

    return router;
};