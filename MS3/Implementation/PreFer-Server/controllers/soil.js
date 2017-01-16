var FCM = require('fcm-node');
var fcmModel = require('../models/fcm');
var soilModel = require('../models/soil');
var fieldModel = require('../models/field');
var plantModel = require('../models/plant');
var fertilizerModel = require('../models/fertilizer');

//Der ServerKey, der von Google zur Verfügung gestellt wurde, damit der FCM-Service laufen kann.
var serverKey = 'AAAAZHP-YHQ:APA91bHHbcC3PYFjwNlJw8-lntCSFg1PYUBSX3RBkYTN755HTF97x3iGMor6WUF9ZW8OO9d7Z_KY6CE6b00_-7SeVrTC2GImwSZf8teYL9NiDk93aBvGuWmd2a20Yj6oK8jaKjsD-aNsysU6uBUi7d5J-nQtUvK4Hw';
var fcm = new FCM(serverKey);

module.exports = {
    
    // POST /soil
    // Bodensensoren schicken dem Server die Bodendaten an bestimmten Standorten zum ersten Mal.
    sendSoilData: function(req, res, next) {
        // Im Server zwischenspeichern
        var soilData = new soilModel(req.body);
        soilData.save(function(err) {
            if(err)
                throw err;
        });
        
        //Pflanzendaten holen und Berechnung für Beispieldaten durchführen
        fieldModel.findById(soilData.field_id, function(err, field) {
            plantModel.findById(field.plant_id, function(err, plant) {
                //Fertilizer-Objekt erstellen
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
                
                //Neue Nährstoffwerte berechen anhand der Pflanzen-Nährstoffsollwerte und den Bodendaten
                var newNitrogen = plant.nutrient.nitrogen - soilData.nutrient.nitrogen;
                var newPhosphorus = plant.nutrient.phosphorus - soilData.nutrient.phosphorus;
                var newPotassium = plant.nutrient.potassium - soilData.nutrient.potassium;
                
                //Nährstoffwerte im Fertilizer-Objekt sollen nur verändert werden,
                //wenn sie größer als 0 sind.
                if(newNitrogen > 0)
                    fertilizer.nutrient.nitrogen = newNitrogen;
                
                if(newPhosphorus > 0)
                    fertilizer.nutrient.phosphorus = newPhosphorus;
                
                if(newPotassium > 0)
                    fertilizer.nutrient.potassium = newPotassium;
                
                fertilizer.save(function(err) {
                    if(err) {
                        res.status(500);
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
    //Push-Notifications werden gesendet, wenn die neu berechneten Düngeempfehlungen einzelnd größer sind als der Schwellwert.
    
    updateSoilData: function(req, res, next) {
        // Bodendaten aktualisieren, damit die in der Datenbank gespeicherten Daten aktuell sind
        soilModel.findOne({field_id: req.params.fid, _id: req.params.id}, function(err, soilData) {
            if(err)
                throw err;
            
            soilData.nutrient.nitrogen = req.body.nutrient.nitrogen;
            soilData.nutrient.phosphorus = req.body.nutrient.phosphorus;
            soilData.nutrient.potassium = req.body.nutrient.potassium;
            
            //Die neuen Bodendaten überschreiben die alten in der Datenbank.
            soilData.save(function(err) {
                if(err) {
                    throw err;
                }
            });
            
            //Es werden mehrere Datenbanken-Abfragen gestartet:
            //fcmModel, fieldModel und plantModel
            fcmModel.find(function(err, fcmtoken) {            
                fieldModel.findById(soilData.field_id, function(err, field) {
                    plantModel.findById(field.plant_id, function(err, plant) {
                        if(err)
                            throw err;
                        
                        //message wird deklariert: Dies wird im Falle einer Push-Notification an den Client gesendet.
                        var message = {
                            to: fcmtoken[0].token, 
                            notification: {
                                title: 'Neue Düngeempfehlung!', 
                                body: 'Auf Schlag "'+field.name+'" muss gedüngt werden!' 
                            }
                        };
                        
                        //fertilizerModel: Die Datenstruktur der Düngeempfehlung.
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
                        var schwellWert = 15;
                        
                        //Befinden sich die neu berechneten Nährstoffwerte alle einzelnd über dem Schwellwert,
                        //dann wird eine Push-Notification an den Client gesendet.
                        if(newNitrogen > schwellWert && newPhosphorus > schwellWert && newPotassium > schwellWert) {
                            fcm.send(message, function(err, response){
                                if (err) {
                                    console.log("Something has gone wrong!");
                                } else {
                                    console.log("Successfully sent with response: ", response);
                                }
                            });
                        }
                        
                        if(newNitrogen > 0)
                            fertilizer.nutrient.nitrogen = newNitrogen;

                        if(newPhosphorus > 0)
                            fertilizer.nutrient.phosphorus = newPhosphorus;

                        if(newPotassium > 0)
                            fertilizer.nutrient.potassium = newPotassium;

                        //Neue Düngeempfehlung muss in der Datenbank gespeichert werden
                        fertilizer.save(function(err) {
                            if(err) {
                                console.log(err);
                                res.status(500);
                                res.send("ERROR!");
                            }
                            else {
                                res.status(200);
                                res.send("Neue Düngeempfehlung wurde berechnet.");
                            }
                        });
                    });
                });
            });
        });
    }
}