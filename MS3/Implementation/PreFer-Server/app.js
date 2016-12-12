var express = require('express')
var bodyParser = require('body-parser');
var http = require('http');
var mongoose = require('mongoose');
var FCM = require('fcm-node');
var app = express();
var server = http.createServer(app);

app.use(bodyParser.json());
app.use(require('./routes/index'));
app.use(require('./routes/field'));
app.use(require('./routes/plant'));
app.use(require('./routes/fertilizer'));
app.use(require('./routes/soil'));

mongoose.connect('mongodb://localhost:27017/EISWS1617NguyenLe');

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  // we're connected!
    console.log("DB connected!");
});


//Test-Bereich vom Rapid Prototyping
var polySchema = new mongoose.Schema({
    name: {type: String, required: true},
    type: {type: String, default: "Polygon"},
    coordinates: [{}]
});

var Poly = mongoose.model('polygons', polySchema);

app.get('/polygon', function(req, res) {
    Poly.find(function(err, polygon) {
        res.send(polygon);
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

//FCM-Notification
var serverKey = 'AAAAZHP-YHQ:APA91bHHbcC3PYFjwNlJw8-lntCSFg1PYUBSX3RBkYTN755HTF97x3iGMor6WUF9ZW8OO9d7Z_KY6CE6b00_-7SeVrTC2GImwSZf8teYL9NiDk93aBvGuWmd2a20Yj6oK8jaKjsD-aNsysU6uBUi7d5J-nQtUvK4Hw';
var registrationTokens = '';
var fcm = new FCM(serverKey);

var message = {
    to: "fiGvHP_eFSE:APA91bFfuJb-fJ718hmEyf6eXmOSLdXUeswhPUzOKZfigIrrfD7CKK-EQd6CkMk3uK1J-u0eR4PynqELP4hA4gjxIXqMzOiz5KeLXsi8aqCUUvLaBabTv_Lr-49UOH19NRfM2HjjWJ2-", 
    notification: {
        title: 'Das ist eine Push-Notification!', 
        body: 'Das ist der Inhalt der Notification!' 
    },
    data: { 
        my_key: 'my value',
        my_another_key: 'my another value'
    }
};

app.get('/fcm', function(req, res) {
    fcm.send(message, function(err, response){
            if (err) {
                console.log(err);
            } else {
                console.log("Successfully sent with response: ", response);
            }
    });
    res.end();
});

app.post('/fcm', function(req, res) {
       var data = req.body;
        console.log(data.token);
        registrationTokens = data.token;
        if(data.token == null) {
            res.status(400);
            res.send("Token konnte nicht hinzugefügt werden");
        }
        else {
            res.status(200);
            res.send("Token hinzugefügt.");
        }
});

server.listen(3000, function() {
    console.log('Server listens on Port 3000');
});