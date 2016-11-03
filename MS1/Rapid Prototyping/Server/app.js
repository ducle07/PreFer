var express = require('express');
var bodyParser = require('body-parser');
var http = require('http');
var mongoose = require('mongoose');
var app = express();
var server = http.createServer(app);

app.use(bodyParser.json());

mongoose.connect('mongodb://localhost:27017/EISWS1617NguyenLe');

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  // we're connected!
    console.log("DB connected!");
});

var userSchema = new mongoose.Schema({
    email: String,
    password: String,
    name: {
        first: String,
        last: String
    },
    age: { type: Number, min: 0 },
    gender: String,
});

var User = mongoose.model('users', userSchema);

app.get('/users', function(req, res) {
    User.find(function(err, users) {
        res.send(users);
    });
});

app.get('/users/:id', function(req, res) {
    User.find({_id: req.params.id}, function(err, users) {
        res.send(users);
    });
});

app.post('/users', function(req, res) {
    var user = new User(req.body);
    console.log(req.body);
    user.save(function(err) {
        if(err) {
            console.log(err);
            res.status(400);
            res.send("Nutzer konnte nicht hinzugefügt werden!");
        }
        else {
            res.status(200);
            res.send("Nutzer hinzugefügt");
        }
    });
});

server.listen(3000, function() {
    console.log('Server listens on Port 3000');
});