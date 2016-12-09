var express = require('express')
var bodyParser = require('body-parser');
var http = require('http');
var mongoose = require('mongoose');
var app = express();
var server = http.createServer(app);

app.use(bodyParser.json());
app.use(require('./routes/index'));
app.use(require('./routes/field'));
app.use(require('./routes/fcm'));

mongoose.connect('mongodb://localhost:27017/EISWS1617NguyenLe');

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  // we're connected!
    console.log("DB connected!");
});

server.listen(3000, function() {
    console.log('Server listens on Port 3000');
});