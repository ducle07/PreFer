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

var polySchema = new mongoose.Schema({
    name: {type: String, required: true},
    type: {type: String, default: "Polygon"},
    coordinates: [{}]
});

var User = mongoose.model('users', userSchema);
var Poly = mongoose.model('polygons', polySchema);

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

app.get('/polygon', function(req, res) {
    Poly.find(function(err, polygon) {
        res.send(polygon);
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

app.post('/polygon', function(req, res) {
    var polygon = new Poly({
        name: req.body.name,
        type: req.body.type,
        coordinates: req.body.coordinates
    });
    console.log(req.body);
    polygon.save(function(err) {
        if(err) {
            console.log(err);
            res.status(400);
            res.send("Polygon konnte nicht gespeichert werden!");
        }
        else {
            res.status(200);
            res.send("Polygon wurde gespeichert.");
        }
    });
});

server.listen(3000, function() {
    console.log('Server listens on Port 3000');
});