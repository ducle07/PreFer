# PreFer

**english version below!**

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
  * **volley**-Library zur Kommunikation über **HTTP**
* Server der verteilten Anwendung realisiert mittels **node.js**
  * **Express**-Modul zur Realisierung eines Webservers
  * Datenbankanbindung zur NoSQL-Datenbank **MongoDB** mit dem **mongoose**-Modul
* Übertragung der Daten im Format **JSON**
* Anbindung der **Google Maps API** (client-seitig)
* **Firebase Cloud Messaging** für Push-Notifications

# English version

The project "PreFer" is carried out in my study "media computer science".
The name "PreFer" is composed of the two syllables of the words "Precision" and "Fertilizer", on which this project is thematically based on.

As part of the module "Interactive Systems Development Project", we were given the oppurtunity to use the knowledge learned in the modules "Human Computer Interaction" and "Web-based Applications 2: Distributed Systems" in a practical project. This project is carried out in collaboration with Thuy Trang Nguyen. While she focused on usability engineering, I, Duc Giang Le, was responsible for the software engineering.
 
In this module, we could use the main methods of software development to develop an interactive system based on an own project idea.
During the project, we had three major milestones: Concept (MS1), Documentation (MS2) and Implementation (MS3). For the technical presentation of the project, the focus will be on MS3 only. The architecture of the application and technologies used will be illustated.

Source Code: [Android-Client](https://github.com/ducle07/PreFer/tree/master/MS3/Implementation/EISWS1617PreFer) & [node.js-Server](https://github.com/ducle07/PreFer/tree/master/MS3/Implementation/PreFer-Server)

## Architecture and used technologies
![architecture](https://github.com/ducle07/PreFer/blob/master/Architekturdiagramm.png)

* Client-server architecture based on the **REST** architecture style
* Client as a mobile client in **Android**
 * **volley**-library for communication over **HTTP**
* Server of the distributed application realized with **node.js**
 * **Express**-module for the realization of the web server
 * Database connection to the NoSQL database **MongoDB** with the **mongoose**-module
* Data transfer in the format **JSON**
* Use of the **Google Maps API** (client-side)
* **Firebase Cloud Messaging** for push notifications
