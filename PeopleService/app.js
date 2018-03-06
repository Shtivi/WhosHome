var express = require('express');
var path = require('path');
var favicon = require('serve-favicon');
var logger = require('morgan');
var cookieParser = require('cookie-parser');
var bodyParser = require('body-parser');
var mongoose = require("mongoose")

var app = express();

// Setup
app.set('port', 5020);

var api = require("./routes/api");

app.use(logger('dev'));
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: false }));
app.use(cookieParser());

// TODO: When mongo fails, shut down th application

// DB
mongoose.connect('mongodb://localhost/people').then((db) => {
  console.log("Connected to db");
}, (err) => {
  console.error(err);
  app.emit("showdown")
});

app.use('/api', api);

// catch 404 and forward to error handler
app.use(function(req, res, next) {
  res.status(404).send("404 - not found");
});

app.listen(app.get('port'));

module.exports = app;
