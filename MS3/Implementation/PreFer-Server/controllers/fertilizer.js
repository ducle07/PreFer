var fertilizerModel = require('../models/fertilizer');

module.exports = {
    
    // GET /fertilizer/:fid
    // Alle Düngeempfehlungen werden anhand der fieldID zurückgegeben
    getAllFertilizerFromField: function(req, res, next) {
        fertilizerModel.find({field_id: req.params.fid}, function(err, fertilizer) {
            res.send(fertilizer);
        });
    },
    
    // GET /fertilizer/:fid/:id
    // Eine bestimmte Düngeempfehlung wird anhand der fieldID und der fertilizerID zurückgegeben
    getOneFertilizerFromField: function(req, res, next) {
        fertilizerModel.findOne({field_id: req.params.fid, _id: req.params.id}, function(err, fertilizer) {
            res.send(fertilizer);
        });
    }
}