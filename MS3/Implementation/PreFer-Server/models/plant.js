//Das ist der Model, wie die Pflanzen-Daten in der Datenbank gespeichert werden.

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var plantSchema = new Schema({
    id: Number,
    name: String,
    nutrient: {
        nitrogen: Number,
        phosphorus: Number,
        potassium: Number
    }
});

module.exports = mongoose.model('plant', plantSchema);