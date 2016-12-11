var router = require('express').Router();
var plantController = require('../controllers/plant');

//Pflanzen sollen simuliert werden, am Besten werden sie in der MongoDB-Datenbank gespeichert
router.route('/plant')
    .get(plantController.getAllPlant)
    .post(plantController.postPlant);

router.route('/plant/:id')
    .get(plantController.getPlant);

module.exports = router;