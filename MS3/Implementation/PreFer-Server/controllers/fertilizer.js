var fertilizerModel = require('../models/fertilizer');

module.exports = {
    
    // GET /fertilizer/:fid
    getAllFertilizerFromField: function(req, res, next) {
        fertilizerModel.find({field_id: req.params.fid}, function(err, fertilizer) {
            res.send(fertilizer);
        });
    },
    
    // GET /fertilizer/:fid/:id
    getOneFertilizerFromField: function(req, res, next) {
        fetilizerModel.findOne({field_id: req.params.fid, _id: req.params.id}, function(err, fertilizer) {
            res.send(fertilizer);
        });
    }
}