//Module müssen importiert werden!
var express = require('express')
var bodyParser = require('body-parser');
var http = require('http');
var mongoose = require('mongoose');
var FCM = require('fcm-node');
var app = express();
var server = http.createServer(app);

//Der app.js die Routen bekannt machen
//Geschieht mithilfe der app.use Funktion
app.use(bodyParser.json());
app.use(require('./routes/index'));
app.use(require('./routes/fcm'));
app.use(require('./routes/field'));
app.use(require('./routes/plant'));
app.use(require('./routes/fertilizer'));
app.use(require('./routes/soil'));

//Die Server stellt eine Verbindung zur MongoDB-Datenbanl auf der MongoDB-url: mongodb://localhost:27017/EISWS1617NguyenLe her.
mongoose.connect('mongodb://localhost:27017/EISWS1617NguyenLe');

//Bei erfolgreicher Verbindung soll über die Konsole ausgegeben werden, 
//dass eine Verbidnugn besteht.
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function() {
    console.log("DB connected!");
});

//Port-Einstellungen
//Der Server horcht auf den Port 3000.
server.listen(3000, function() {
    console.log('Server listens on Port 3000');
});