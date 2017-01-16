//Das ist der Router für die Felder/Schläge
//
//Folgende Routen sind verfügbar:
//
// /field
// /field/:id

var router = require('express').Router();
var fieldController = require('../controllers/field');

//Hier werden die HTTP-Methoden definiert, die auf die Ressourcen anwendbar sind.
router.route('/field')
    .get(fieldController.getAllField)
    .post(fieldController.postField);

router.route('/field/:id')
    .get(fieldController.getFieldById)
    .put(fieldController.putField)
    .delete(fieldController.deleteField);

module.exports = router;