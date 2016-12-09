var router = require('express').Router();
var indexController = require('../controllers/index');

router.route('/')
    .get(indexController.getIndex);

module.exports = router;