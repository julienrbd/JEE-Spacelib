/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.services;

import frjulienrobardet.entities.Navette;
import frjulienrobardet.entities.Quai;
import frjulienrobardet.entities.Revision;
import frjulienrobardet.entities.Station;
import frjulienrobardet.metier.GestionMecanicienLocal;
import frjulienrobardet.metier.GestionNavetteLocal;
import frjulienrobardet.metier.GestionRevisionLocal;
import frjulienrobardet.metier.GestionStationLocal;
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
import javax.ejb.Stateless;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class ServicesMecanicien implements ServicesMecanicienLocal {

    @EJB
    private GestionStationLocal gestionStation;

    @EJB
    private GestionRevisionLocal gestionRevision;

    @EJB
    private GestionNavetteLocal gestionNavette;

    @EJB
    private GestionMecanicienLocal gestionMecanicien;
    
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void authentifier(String login, String motdepasse) throws UtilisateurInconnu {
        this.gestionMecanicien.authentifier(login, motdepasse);
    }

    @Override
    public void creerCompte(String nom, String prenom, String login, String motdepasse) throws UtilisateurExistant {
        this.gestionMecanicien.creerCompte(nom, prenom, login, motdepasse);
    }

    @Override
    public long renseignerStationRattachement(String nom) throws StationInconnu {
        return this.gestionMecanicien.renseignerStationRattachement(nom);
    }

    @Override
    public List<NavetteExport> consulterListeNavettes(long idStation) throws StationInconnu {

        
             List<Navette> navettes = this.gestionNavette.consulterListeNavettes(idStation);
        ArrayList<NavetteExport> resultList = new ArrayList<>();
        for (Navette navette : navettes) {
            NavetteExport navetteE = new NavetteExport();
            navetteE.setId(navette.getId());
            navetteE.setNbPlaces(navette.getNbPlaces());
            navetteE.setNbVoyages(navette.getNbVoyages());
            resultList.add(navetteE);
        }
        return resultList;
    }

    @Override
    public Quai choisirNavetteDebutRevision(long idNavette, long idStation, long idMecanicien) throws NavetteInconnu, UtilisateurInconnu, QuaiInexistant {
        return this.gestionRevision.choisirNavetteDebutRevision(idNavette, idStation, idMecanicien);
    }

    @Override
    public RevisionExport consulterRevisionEnCours(long idMecanicien, long idStation) throws NavetteInconnu, QuaiInexistant, RevisionInexistante {
        RevisionExport r = new RevisionExport();
        Revision r1 = new Revision();
        r1 = this.gestionRevision.consulterRevisionEnCours(idMecanicien, idStation);
        r.setId(r1.getId());
        r.setStatut(r1.getStatut());
        r.setDateCreation(r1.getDateCreation());
        return r;
    }

    @Override
    public void finirRevisionEnCours(long idNavette, long idStation, long idMecanicien) throws QuaiInexistant, NavetteInconnu, UtilisateurInconnu {
        this.gestionRevision.finirRevisionEnCours(idNavette, idStation, idMecanicien);
    }

    @Override
    public ArrayList<StationExport> recupererListeStations() {
           List<Station> stations = this.gestionStation.recupererListeStations();
        ArrayList<StationExport> resultList = new ArrayList<>();
        for (Station station : stations) {
            StationExport stationExport = new StationExport();
            stationExport.setId(station.getId());
            stationExport.setLocalisation(station.getLocalisation());
            stationExport.setNbQuais(station.getNbQuais());
            stationExport.setNom(station.getNom());
            resultList.add(stationExport);
        }
        return resultList;
    }

    @Override
    public List<RevisionExport> recupererListeNavettesAReviser(long idStation) throws StationInconnu, QuaiInexistant, NavettePourQuaiInexistant, RevisionInexistante {
         List<RevisionExport> rExport = new ArrayList <RevisionExport>();
        List<Revision> revisions = this.gestionRevision.recupererListeNavettesAReviser(idStation);
        for (Revision r : revisions) {
            RevisionExport r1 = new RevisionExport();
           r1.setId(r.getId());
           r1.setStatut(r.getStatut());
           r1.setDateCreation(r.getDateCreation());
           rExport.add(r1);
           }
           return rExport;
    }
 
}
