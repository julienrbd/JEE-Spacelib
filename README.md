# JEE-Spacelib

# Configuration 
* JEE version 7
* Netbeans 8.2
* GlassfishServer 4.1.1
* JavaDB

# Installation

##  Vérifier / Créer la BD : 
* nom = SpaceLib
* login = utilisateur
* mdp = pwd

## Clean & Build dans l'ordre
* Vérifier que votre serveur GlassFish est arreté 
* Clean & Build l'application JAVA "SpacelibShared"
* Clean & Build l'application d'entreprise "Spacelib"
* Clean & Build les clients web et lourds
* Lancer le serveur GlassFish 

## Tester
* Adresse de test pour le client web Usager
*	http://localhost:8080/SpaceLib-web/WSUsager?wsdl
*	Adresse de test pour le client web Mecanicien
* http://localhost:8080/SpaceLib-web/WSMecanicien?wsdl
