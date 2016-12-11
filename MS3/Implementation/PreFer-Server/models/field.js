var mongoose = require('mongoose');
var plantModel = require('./plant');

var Schema = mongoose.Schema;

var fieldSchema = new Schema({
    name: String,
    size: Number,
    outline: [{}],
    plant_id: { type: Schema.Types.ObjectId, ref: 'plant'}
});

module.exports = mongoose.model('field', fieldSchema);