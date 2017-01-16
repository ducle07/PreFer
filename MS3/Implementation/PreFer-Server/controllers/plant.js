var plantModel = require('../models/plant');

module.exports = {
    
    // GET /plant
    getAllPlant: function(req, res, next) {
        plantModel.find(function(err, plant) {
            res.send(plant);
        });
    },
    
    // POST /plant
    //Für Simulationszwecke müssen Pflanzendaten erstellt und gespeichert werden.
    postPlant: function(req, res, next) {
        var plant = new plantModel(req.body);
        plant.save(function(err) {
            if(err) {
                res.status(500);
                res.send("Error");
            }
            else {
                res.status(200);
                res.send("Okay");
            }
        });
    },
    
    // GET /plant/:id
    getPlant: function(req, res, next) {
        plantModel.findById(req.params.id, function(err, plant) {
            res.send(plant);
        });
    }
}