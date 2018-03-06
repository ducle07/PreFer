# PreFer
Das Projekt "PreFer" ist ein im meinem Studium "Medieninformatik" durchgeführtes Projekt. 
Der Name "PreFer" setzt sich aus den beiden Anfangssilben der Wörter "Precision" und "Fertilizer" zusammen, worauf dieses Projekt thematisch basiert.

Im Rahmen der Veranstaltung "Entwicklungsprojekt Interaktiver Systeme" ist uns die Möglichkeit geboten worden, das in den Modulen "Mensch-Computer-Interaktion" und "Web-basierte Anwendungen 2: Verteilte Systeme" erlernte Wissen in einem praktischen Projekt einzusetzen. In Zusammenarbeit mit Thuy Trang Nguyen wurde dieses Projekt durchgeführt. Dabei fokussierte sich Thuy Trang Nguyen auf das Usability Engineering, während ich, Duc Giang Le, für das Software Engineering zuständig war. 

In diesem Modul hatten wir die Möglichkeit, die wichtigsten Methodiken der Softwareentwicklung anzuwenden und selber durchzuführen.
Im Projekt hatten wir drei große Meilensteine: Konzept (MS1), Dokumentation (MS2) und Implementierung (MS3). Zur technischen Darstellung des Projekts wird der Fokus nur auf MS3 gelegt. Hier soll die Architektur der Anwendung und die verwendeten Technologien illustiert werden. 

Quellcode: [Android-Client](https://github.com/ducle07/PreFer/tree/master/MS3/Implementation/EISWS1617PreFer) & [node.js-Server](https://github.com/ducle07/PreFer/tree/master/MS3/Implementation/PreFer-Server)

## Architektur & verwendete Technologien
![Architekturdiagramm](https://github.com/ducle07/PreFer/blob/master/Architekturdiagramm.png)

* Client-Server-Architektur nach dem **REST**-Architekturstil
* Client als mobiler Client in **Android**
  * **volley**-Library zur Kommunikation über HTTP
* Server der verteilten Anwendung realisiert mittels **node.js**
  * **Express**-Modul zur Realisierung eines Webservers
  * Datenbankanbindung zur NoSQL-Datenbank **MongoDB**
* Übertragung der Daten im Format **JSON**
* Anbindung der **Google Maps API** (client-seitig)
* **Firebase Cloud Messaging** für Push-Notifications
 
