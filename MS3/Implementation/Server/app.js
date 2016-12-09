var express = require('express');
var bodyParser = require('body-parser');
var http = require('http');
var mongoose = require('mongoose');
var FCM = require('fcm-node');
var app = express();
var routes = require('./routes');
var server = http.createServer(app);

app.use(bodyParser.json());
app.use('/', routes);

mongoose.connect('mongodb://localhost:27017/EISWS1617NguyenLe');

var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
  // we're connected!
    console.log("DB connected!");
});

var serverKey = 'AAAAZHP-YHQ:APA91bHHbcC3PYFjwNlJw8-lntCSFg1PYUBSX3RBkYTN755HTF97x3iGMor6WUF9ZW8OO9d7Z_KY6CE6b00_-7SeVrTC2GImwSZf8teYL9NiDk93aBvGuWmd2a20Yj6oK8jaKjsD-aNsysU6uBUi7d5J-nQtUvK4Hw';
var registrationTokens = '';
var fcm = new FCM(serverKey);

var message = {
    to: registrationTokens, 
    collapse_key: 'your_collapse_key',
    
    notification: {
        title: 'Das ist eine Push-Notification!', 
        body: 'Das ist der Inhalt der Notification!' 
    },
    
    data: { 
        my_key: 'my value',
        my_another_key: 'my another value'
    }
};

app.post('/fcm', function(req, res) {
    var data = req.body;
    registrationTokens.push(data.token);
    if(data != null) {
        res.status(400);
        res.send("Token konnte nicht hinzugefügt werden");
    }
    else {
        res.status(200);
        res.send("Token hinzugefügt.");
    }
});

app.get('/fcm', function(req, res) {
    fcm.send(message, function(err, response){
        if (err) {
            console.log("Something has gone wrong!");
        } else {
            console.log("Successfully sent with response: ", response);
        }
    });
});

server.listen(3000, function() {
    console.log('Server listens on Port 3000');
});