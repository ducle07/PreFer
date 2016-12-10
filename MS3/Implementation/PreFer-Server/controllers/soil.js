var soilModel = require('../models/soil');
var fieldModel = require('../models/field');
var plantModel = require('../models/plant');

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
            /*if(err) {
                console.log(err);
                res.status(404);
                res.send("Es konnten keine Bodendaten empfangen werden.");
            }
            else {
                res.status(200);
                res.send("Es konnten Bodendaten emfpangen werden");
            }*/
        });
        
        //Pflanzendaten holen
        fieldModel.findById(soilData.field_id, function(err, field) {
            res.send(field);
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