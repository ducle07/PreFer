//Das ist der Router für die Plfanzen-Daten

var router = require('express').Router();
var plantController = require('../controllers/plant');

//Pflanzen sollen simuliert werden, am Besten werden sie in der MongoDB-Datenbank gespeichert
//Sie haben mit dem System an sich nicht direkt zu tun.
//Hierdurch wird es dem Server möglich, Pflanzen-Daten durch manuelle Requests anzunehmen und in der Datenbank zu speichern.
router.route('/plant')
    .get(plantController.getAllPlant)
    .post(plantController.postPlant);

router.route('/plant/:id')
    .get(plantController.getPlant);

module.exports = router;