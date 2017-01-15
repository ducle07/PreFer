var mongoose = require('mongoose');

var Schema = mongoose.Schema;

var fcmSchema = new Schema({
    token: String
});

module.exports = mongoose.model('fcm', fcmSchema);