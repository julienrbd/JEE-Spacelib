/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import frjulienrobardet.spacelibshared.exceptions.NavetteIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.ReservationCloturee;
import frjulienrobardet.spacelibshared.exceptions.ReservationInconnu;
import frjulienrobardet.spacelibshared.exceptions.ReservationPassee;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.TempsTrajetInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import frjulienrobardet.spacelibshared.exceptions.VoyageInconnu;
import frjulienrobardet.spacelibshared.export.StationExport;
import frjulienrobardet.spacelibshared.export.VoyageExport;
import frjulienrobardet.spacelibshared.services.ServicesUsagerRemote;
import java.util.ArrayList;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Fitchay
 */
@WebService(serviceName = "WSUsager")
public class WSUsager {

    @EJB
    private ServicesUsagerRemote ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "login")
    public Long login(@WebParam(name = "login") String login, @WebParam(name = "motdepasse") String motdepasse) throws UtilisateurInconnu {
        return ejbRef.login(login, motdepasse);
    }

    @WebMethod(operationName = "creerCompte")
    public Long creerCompte(@WebParam(name = "nom") String nom, @WebParam(name = "prenom") String prenom, @WebParam(name = "login") String login, @WebParam(name = "motdepasse") String motdepasse) throws UtilisateurExistant {
        return ejbRef.creerCompte(nom, prenom, login, motdepasse);
    }

    @WebMethod(operationName = "reserverVoyage")
    public VoyageExport reserverVoyage(@WebParam(name = "idUsager") Long idUsager, @WebParam(name = "idStationDepart") Long idStationDepart, @WebParam(name = "idStationArrivee") Long idStationArrivee, @WebParam(name = "NbPassagers") int NbPassagers, @WebParam(name = "dateDepart") Calendar dateDepart) throws QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, UtilisateurInconnu, StationInconnu, NavetteIndisponible {
        return ejbRef.reserverVoyage(idUsager, idStationDepart, idStationArrivee, NbPassagers, dateDepart);
    }

    @WebMethod(operationName = "voyageEnCours")
    public VoyageExport voyageEnCours(@WebParam(name = "idUsager") Long idUsager) throws UtilisateurInconnu, VoyageInconnu {
        return ejbRef.voyageEnCours(idUsager);
    }

    @WebMethod(operationName = "finaliserVoyage")
    public void finaliserVoyage(@WebParam(name = "idVoyage") Long idVoyage) throws VoyageInconnu {
        ejbRef.finaliserVoyage(idVoyage);
    }

    @WebMethod(operationName = "obtenirStations")
    public ArrayList<StationExport> obtenirStations() {
        return ejbRef.obtenirStations();
    }

    @WebMethod(operationName = "obtenirVoyagesPrevusUsager")
    public ArrayList<VoyageExport> obtenirVoyagesPrevusUsager(@WebParam(name = "idUsager") Long idUsager) throws UtilisateurInconnu {
        return ejbRef.obtenirVoyagesPrevusUsager(idUsager);
    }

    @WebMethod(operationName = "annulerVoyage")
    public void annulerVoyage(@WebParam(name = "idUsager") Long idUsager, @WebParam(name = "idVoyage") Long idVoyage) throws UtilisateurInconnu, ReservationInconnu, ReservationPassee, ReservationCloturee {
        ejbRef.annulerVoyage(idUsager, idVoyage);
    }

    @WebMethod(operationName = "obtenirStationParIdQuai")
    public StationExport obtenirStationParIdQuai(@WebParam(name = "idQuai") Long idQuai) throws StationInconnu, QuaiInexistant {
        return ejbRef.obtenirStationParIdQuai(idQuai);
    }
    
}
