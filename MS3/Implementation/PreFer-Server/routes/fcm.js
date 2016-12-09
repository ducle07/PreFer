var router = require('express').Router();
var fcmController = require('../controllers/fcm');

router.route('/fcm')
    .get(fcmController.sendMessage)
    .post(fcmController.getToken);

module.exports = router;