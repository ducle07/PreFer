var FCM = require('fcm-node');
var fcmModel = require('../models/fcm');
//Der Serverkey, der von Google für die Nutzung des FCM-Services erstellt wurde.
var serverKey = 'AAAAZHP-YHQ:APA91bHHbcC3PYFjwNlJw8-lntCSFg1PYUBSX3RBkYTN755HTF97x3iGMor6WUF9ZW8OO9d7Z_KY6CE6b00_-7SeVrTC2GImwSZf8teYL9NiDk93aBvGuWmd2a20Yj6oK8jaKjsD-aNsysU6uBUi7d5J-nQtUvK4Hw';
var fcm = new FCM(serverKey);

module.exports = {
    
    // Testzwecke zum manuellen Senden von Push-Notifications
    // GET /fcm
    sendMessage: function(req, res, next) {
        fcmModel.find(function(err, fcmtoken) {
            //message ist das Objekt, das die Push-Notification repräsentiert.
            var message = {
                to: fcmtoken[0].token, 
                notification: {
                    title: 'Das ist eine Push-Notification!', 
                    body: 'Das ist der Inhalt der Notification!' 
                }
            };
            
            fcm.send(message, function(err, response){
                if (err) {
                    console.log("Something has gone wrong!");
                } else {
                    console.log("Successfully sent with response: ", response);
                }
            });
            res.end();
        });
    },
    
    // Der vom Client gesendete Token für FCM muss für die spätere Nutzung gespeichert werden.
    // POST /fcm
    getToken: function(req, res, next) {
        var fcm = new fcmModel(req.body);
        //Collection 'fcms' wird geleert, bevor der neue Token hinzugefügt wird.
        //Für Testzwecke ausreichend, es wird immer nur ein Gerät für FCM eingetragen
        fcmModel.remove({}, function(err) {
            console.log("Collection geleert!");
        });
        
        //Der von Client gesendete Token wird in der Datenbank gespeichert, damit der Server später daraufzugreifen kann.
        fcm.save(function(err) {
            if(err) {
                console.log(err);
                res.status(500);
                res.send("Token konnte nicht gespeichert werden.");
            }
            else {
                console.log("Token gespeichert.");
                res.status(200);
                res.send("Token gespeichert.");
            }
        });
    }
}