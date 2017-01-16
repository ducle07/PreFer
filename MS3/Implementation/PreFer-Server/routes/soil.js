//Das ist der Router, der für das Erhalten und Aktualisieren der Bodendaten zuständig ist.

var router = require('express').Router();
var soilController = require('../controllers/soil');

//Boden-Daten werden dem Server gesendet.
router.route('/soil')
    .post(soilController.sendSoilData);

//Neue Boden-Daten werden dem Server gesendet und aktualisiert.
router.route('/soil/:fid/:id')
    .put(soilController.updateSoilData);

module.exports = router;