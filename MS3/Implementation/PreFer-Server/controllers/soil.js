var soilModel = require('../models/soil');
var fieldModel = require('../models/field');
var plantModel = require('../models/plant');
var fertilizerModel = require('../models/fertilizer');

module.exports = {
    
    // POST /soil
    // Bodensensoren schicken dem Server die Bodendaten an bestimmten Standorten zum ersten Mal.
    sendSoilData: function(req, res, next) {
        // Im Server zwischenspeichern
        var soilData = new soilModel(req.body);
        //console.log(req.body);
        soilData.save(function(err) {
            if(err)
                throw err;
        });
        
        //Pflanzendaten holen und Berechnung für Beispieldaten durchführen
        //mögliche weitere neue Modellierung in Betracht ziehen: Nährstoffgehaltsklassen, Standortniveau und Ertragsniveau
        fieldModel.findById(soilData.field_id, function(err, field) {
            plantModel.findById(field.plant_id, function(err, plant) {
                var fertilizer = new fertilizerModel({
                    field_id: field._id,
                    nutrient: {
                        nitrogen: 0,
                        phosphorus: 0,
                        potassium: 0
                    },
                    location: {
                        latitude: soilData.location.latitude,
                        longitude: soilData.location.longitude
                    }
                });
                
                var newNitrogen = plant.nutrient.nitrogen - soilData.nutrient.nitrogen;
                var newPhosphorus = plant.nutrient.phosphorus - soilData.nutrient.phosphorus;
                var newPotassium = plant.nutrient.potassium - soilData.nutrient.potassium;
                
                if(newNitrogen > 0)
                    fertilizer.nutrient.nitrogen = newNitrogen;
                
                if(newPhosphorus > 0)
                    fertilizer.nutrient.phosphorus = newPhosphorus;
                
                if(newPotassium > 0)
                    fertilizer.nutrient.potassium = newPotassium;
                
                fertilizer.save(function(err) {
                    if(err) {
                        console.log(err);
                        res.status(400);
                        res.send("Düngeempfehlung konnte nicht berechnet werden.");
                    }
                    else {
                        res.status(200);
                        res.send("Düngeempfehlung wurde berechnet.");
                    }
                });
            });
        });
    },
    
    //PUT /soil/:fid/:id
    //Bodensensoren aktualisieren die geschickten Bodendaten.
    
    updateSoilData: function(req, res, next) {
        // Bodendaten aktualisieren, damit die in der Datenbank gespeicherten Daten aktuell sind
        soilModel.findOne({field_id: req.params.fid, _id: req.params.id}, function(err, soilData) {
            if(err)
                throw err;
            
            soilData.nutrient.nitrogen = req.body.nutrient.nitrogen;
            soilData.nutrient.phosphorus = req.body.nutrient.phosphorus;
            soilData.nutrient.potassium = req.body.nutrient.potassium;
            
            soilData.save(function(err) {
                if(err) {
                    console.log(err);
                    res.status(404);
                    res.send("Bodendaten konnten nicht aktualisiert werden.");
                }
                else {
                    res.send("Bodendaten wurden aktualisiert.");
                }
            });
        });
    }
}