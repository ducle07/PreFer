var router = require('express').Router();
var fertilizerController = require('../controllers/fertilizer');

router.route('/fertilizer/:fid')
    .get(fertilizerController.getAllFertilizerFromField);

router.route('/fertilizer/:fid/:id')
    .get(fertilizerController.getOneFertilizerFromField);

module.exports = router;