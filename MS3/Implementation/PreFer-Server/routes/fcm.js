//Das ist der Router für die Route /fcm
//Der Router ruft die definierten Funktionen aus den Controller-Klassen auf.
//
//fcmController.sendMessage
//fcmController.getToken

var router = require('express').Router();
var fcmController = require('../controllers/fcm');

router.route('/fcm')
    .get(fcmController.sendMessage)
    .post(fcmController.getToken);

module.exports = router;