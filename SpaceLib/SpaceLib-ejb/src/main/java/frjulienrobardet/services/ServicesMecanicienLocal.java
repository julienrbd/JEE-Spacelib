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
import frjulienrobardet.spacelibshared.exceptions.NavetteInconnu;
import frjulienrobardet.spacelibshared.exceptions.NavettePourQuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.RevisionInexistante;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JulienRobardet
 */
@Local
public interface ServicesMecanicienLocal {
    public void authentifier(String login, String motdepasse) throws UtilisateurInconnu;
    
    public void creerCompte(String nom, String prenom, String login, String motdepasse) throws UtilisateurExistant;
    
    public long renseignerStationRattachement(String nom) throws StationInconnu;
    
    public List<Navette> consulterListeNavettes(long idStation) throws StationInconnu;

    public Quai choisirNavetteDebutRevision(long idNavette, long idStation, long idMecanicien) throws NavetteInconnu, UtilisateurInconnu, QuaiInexistant;
    
    public Revision consulterRevisionEnCours(long idMecanicien, long idStation) throws NavetteInconnu, QuaiInexistant, RevisionInexistante;
    
    public void finirRevisionEnCours(long idNavette, long idStation, long idMecanicien) throws QuaiInexistant, NavetteInconnu, UtilisateurInconnu;

    public List<Station> recupererListeStations();
    
    public List<Revision> recupererListeNavettesAReviser(long idStation) throws StationInconnu, QuaiInexistant, NavettePourQuaiInexistant, RevisionInexistante;
    
    
}
