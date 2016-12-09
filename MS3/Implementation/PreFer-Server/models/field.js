var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var fieldSchema = new Schema({
    name: String,
    size: Number,
    outline: [{}],
    plant_id: Number
});

module.exports = mongoose.model('field', fieldSchema);