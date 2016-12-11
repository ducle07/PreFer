var mongoose = require('mongoose');
var fieldModel = require('./field');
var Schema = mongoose.Schema;

var soilSchema = new Schema({
    field_id: { type: Schema.Types.ObjectId, ref: 'field' },
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

module.exports = mongoose.model('soil', soilSchema);