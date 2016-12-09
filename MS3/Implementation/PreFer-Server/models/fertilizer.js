var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var fertilizerSchema = new Schema({
    field_id: Number,
    nutrient: {
        nitrogen: Number,
        phosphorus: Number,
        potassium: Number
    },
    location: {
        latitude: Number,
        longitude: Number
    }                             
});

module.exports = mongoose.model('fertilizer', fertilizerSchema);