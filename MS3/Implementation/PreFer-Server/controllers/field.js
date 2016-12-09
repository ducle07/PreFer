var fieldModel = require('../models/field');

module.exports = {
    
    // GET /field
    getAllField: function(req, res, next) {
        fieldModel.find(function(err, field) {
            res.send(field);
        });
    },
    
    // POST /field
    postField: function(req, res, next) {
        var field = new fieldModel(req.body);
        //console.log(req.body);
        field.save(function(err) {
            if(err) {
                console.log(err);
                res.status(404);
                res.send("Feld konnte nicht hinzugefügt werden!");
            }
            else {
                res.status(200);
                res.send("Feld hinzugefügt");
            }
        });
    },
    
    // GET /field/:id
    getField: function(req, res, next) {
        fieldModel.findById(req.params.id, function(err, field) {
            res.send(field);
        });
    },
    
    //PUT /field/:id
    putField: function(req, res, next) {
        fieldModel.findById(req.params.id, function(err, field) {
            if(err)
                throw err;
            
            field.name = req.body.name;
            field.size = req.body.size;
            field.outline = req.body.outline;
            field.plant_id = req.body.plant_id;
            
            field.save(function(err) {
                if(err) {
                    console.log(err);
                    res.status(404);
                    res.send("Feld mit der ID "+ req.params.id +" konnte nicht aktualisiert werden!");
                }
                else {
                    res.send("Feld mit der ID "+ req.params.id +" wurde aktualisiert!");
                }
            });
        });
    },
    
    //DELETE /field/:id
    deleteField: function(req, res, next) {
        fieldModel.findById(req.params.id, function(err, field) {
            if(err) 
                throw err;
            
            field.remove(function(err) {
                if(err) {
                    console.log(err);
                    res.status(404);
                    res.send("Feld mit der ID "+ req.params.id +" konnte nicht gefunden werden.");
                }
                else {
                    res.status(200);
                    res.send("Feld mit der ID "+ req.params.id +" wurde gelöscht.");
                }
            });
        });
    }
}