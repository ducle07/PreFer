var router = require('express').Router();
var fieldController = require('../controllers/field');

router.route('/field')
    .get(fieldController.getAllField)
    .post(fieldController.postField);

router.route('/field/:id')
    .get(fieldController.getField)
    .put(fieldController.putField)
    .delete(fieldController.deleteField);

module.exports = router;