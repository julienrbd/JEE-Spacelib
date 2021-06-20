/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ws;

import frjulienrobardet.entities.Navette;
import frjulienrobardet.entities.Quai;
import frjulienrobardet.entities.Revision;
import frjulienrobardet.services.ServicesMecanicienLocal;
import frjulienrobardet.spacelibshared.exceptions.NavetteInconnu;
import frjulienrobardet.spacelibshared.exceptions.NavettePourQuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.RevisionInexistante;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import frjulienrobardet.spacelibshared.export.NavetteExport;
import frjulienrobardet.spacelibshared.export.RevisionExport;
import frjulienrobardet.spacelibshared.export.StationExport;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

/**
 *
 * @author Fitchay
 */
@WebService(serviceName = "WSMecanicien")
public class WSMecanicien {

    @EJB
    private ServicesMecanicienLocal ejbRef;// Add business logic below. (Right-click in editor and choose
    // "Web Service > Add Operation"

    @WebMethod(operationName = "authentifier")
    public void authentifier(@WebParam(name = "login") String login, @WebParam(name = "motdepasse") String motdepasse) throws UtilisateurInconnu {
        ejbRef.authentifier(login, motdepasse);
    }

    @WebMethod(operationName = "creerCompte")
    public void creerCompte(@WebParam(name = "nom") String nom, @WebParam(name = "prenom") String prenom, @WebParam(name = "login") String login, @WebParam(name = "motdepasse") String motdepasse) throws UtilisateurExistant {
        ejbRef.creerCompte(nom, prenom, login, motdepasse);
    }

    @WebMethod(operationName = "renseignerStationRattachement")
    public long renseignerStationRattachement(@WebParam(name = "nom") String nom) throws StationInconnu {
        return ejbRef.renseignerStationRattachement(nom);
    }

    @WebMethod(operationName = "consulterListeNavettes")
    public List<NavetteExport> consulterListeNavettes(@WebParam(name = "idStation") long idStation) throws StationInconnu {
        return ejbRef.consulterListeNavettes(idStation);
    }

    @WebMethod(operationName = "choisirNavetteDebutRevision")
    public Quai choisirNavetteDebutRevision(@WebParam(name = "idNavette") long idNavette, @WebParam(name = "idStation") long idStation, @WebParam(name = "idMecanicien") long idMecanicien) throws NavetteInconnu, UtilisateurInconnu, QuaiInexistant {
        return ejbRef.choisirNavetteDebutRevision(idNavette, idStation, idMecanicien);
    }

    @WebMethod(operationName = "consulterRevisionEnCours")
    public RevisionExport consulterRevisionEnCours(@WebParam(name = "idMecanicien") long idMecanicien, @WebParam(name = "idStation") long idStation) throws NavetteInconnu, QuaiInexistant, RevisionInexistante {
        return ejbRef.consulterRevisionEnCours(idMecanicien, idStation);
    }

    @WebMethod(operationName = "finirRevisionEnCours")
    public void finirRevisionEnCours(@WebParam(name = "idNavette") long idNavette, @WebParam(name = "idStation") long idStation, @WebParam(name = "idMecanicien") long idMecanicien) throws QuaiInexistant, NavetteInconnu, UtilisateurInconnu {
        ejbRef.finirRevisionEnCours(idNavette, idStation, idMecanicien);
    }

    @WebMethod(operationName = "recupererListeStations")
    public ArrayList<StationExport> recupererListeStations() {
        return ejbRef.recupererListeStations();
    }

    @WebMethod(operationName = "recupererListeNavettesAReviser")
    public List<RevisionExport> recupererListeNavettesAReviser(@WebParam(name = "idStation") long idStation) throws StationInconnu, QuaiInexistant, NavettePourQuaiInexistant, RevisionInexistante {
        return ejbRef.recupererListeNavettesAReviser(idStation);
    }
    
}
