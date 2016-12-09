var plantModel = require('../models/plant');

module.exports = {
    
    // GET /plant
    getAllPlant: function(req, res, next) {
        plantModel.find(function(err, plant) {
            res.send(plant);
        });
    },
    
    // GET /plant/:id
    getPlant: function(req, res, next) {
        plantModel.findById(req.params.id, function(err, plant) {
            res.send(plant);
        });
    }
}