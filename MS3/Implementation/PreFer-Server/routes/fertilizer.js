//Das ist der Router für die Düngeempfehlungen
//Folgenden Routen sind verfügbar:
//
// /fertilizer/:fid 
// /fertilizer/:fid/:id
//
// fid = fieldID
// id = fertilizerID

var router = require('express').Router();
var fertilizerController = require('../controllers/fertilizer');

router.route('/fertilizer/:fid')
    .get(fertilizerController.getAllFertilizerFromField);

router.route('/fertilizer/:fid/:id')
    .get(fertilizerController.getOneFertilizerFromField);

module.exports = router;