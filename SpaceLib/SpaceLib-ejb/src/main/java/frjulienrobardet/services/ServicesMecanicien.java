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
    public List<Navette> consulterListeNavettes(long idStation) throws StationInconnu {
        return this.gestionNavette.consulterListeNavettes(idStation);
    }

    @Override
    public Quai choisirNavetteDebutRevision(long idNavette, long idStation, long idMecanicien) throws NavetteInconnu, UtilisateurInconnu, QuaiInexistant {
        return this.gestionRevision.choisirNavetteDebutRevision(idNavette, idStation, idMecanicien);
    }

    @Override
    public Revision consulterRevisionEnCours(long idMecanicien, long idStation) throws NavetteInconnu, QuaiInexistant, RevisionInexistante {
        return this.gestionRevision.consulterRevisionEnCours(idMecanicien, idStation);
    }

    @Override
    public void finirRevisionEnCours(long idNavette, long idStation, long idMecanicien) throws QuaiInexistant, NavetteInconnu, UtilisateurInconnu {
        this.gestionRevision.finirRevisionEnCours(idNavette, idStation, idMecanicien);
    }

    @Override
    public List<Station> recupererListeStations() {
        return this.gestionStation.recupererListeStations();
    }

    @Override
    public List<Revision> recupererListeNavettesAReviser(long idStation) throws StationInconnu, QuaiInexistant, NavettePourQuaiInexistant, RevisionInexistante {
        return this.gestionRevision.recupererListeNavettesAReviser(idStation);
    }
}
