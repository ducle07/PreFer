//Hier wird der Model definiert, wie der Token von fcm in der Datenbank gespeichert wird.

var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var fcmSchema = new Schema({
    token: String
});

module.exports = mongoose.model('fcm', fcmSchema);