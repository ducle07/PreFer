var router = require('express').Router();
var plantController = require('../controllers/plant');

router.route('/plant')
    .get(plantController.getAllPlant);

router.route('/plant/:id')
    .get(plantController.getPlant);

module.exports = router;