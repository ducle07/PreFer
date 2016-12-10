var router = require('express').Router();
var soilController = require('../controllers/soil');

router.route('/soil')
    .post(soilController.sendSoilData);

router.route('/soil/:fid/:id')
    .put(soilController.updateSoilData);

module.exports = router;