var FCM = require('fcm-node');
var serverKey = 'AAAAZHP-YHQ:APA91bHHbcC3PYFjwNlJw8-lntCSFg1PYUBSX3RBkYTN755HTF97x3iGMor6WUF9ZW8OO9d7Z_KY6CE6b00_-7SeVrTC2GImwSZf8teYL9NiDk93aBvGuWmd2a20Yj6oK8jaKjsD-aNsysU6uBUi7d5J-nQtUvK4Hw';
var registrationTokens = '';
var fcm = new FCM(serverKey);

//Das Nachrichtenformat, das von FCM an den Client gesendet wird.
var message = {
    to: registrationTokens, 
    collapse_key: 'your_collapse_key',
    
    notification: {
        title: 'Das ist eine Push-Notification!', 
        body: 'Das ist der Inhalt der Notification!' 
    },
    
    data: { 
        my_key: 'my value',
        my_another_key: 'my another value'
    }
};

module.exports = {
    
    // Testzwecke: GET /fcm
    sendMessage: function(req, res, next) {
        fcm.send(message, function(err, response){
            if (err) {
                console.log("Something has gone wrong!");
            } else {
                console.log("Successfully sent with response: ", response);
            }
        });
        res.end();
    },
    
    // Testzwecke: POST /fcm
    getToken: function(req, res, next) {
        var data = req.body;
        registrationTokens.push(data.token);
        if(data != null) {
            res.status(400);
            res.send("Token konnte nicht hinzugefügt werden");
        }
        else {
            res.status(200);
            res.send("Token hinzugefügt.");
        }
    }
}