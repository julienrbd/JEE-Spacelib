/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.spacelibshared.services;

import frjulienrobardet.spacelibshared.exceptions.NavetteIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.TempsTrajetInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import frjulienrobardet.spacelibshared.export.VoyageExport;
import java.util.Calendar;
import javax.ejb.Remote;

/**
 *
 * @author JulienRobardet
 */
@Remote
public interface ServicesUsagerRemote {
    
    Long login(String login, String motdepasse) throws UtilisateurInconnu;
    Long creerCompte(String nom, String prenom, String login, String motdepasse) throws UtilisateurExistant;
    VoyageExport reserverVoyage(Long idUsager, Long idStationDepart, Long idStationArrivee, int NbPassagers, Calendar dateDepart) throws QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, UtilisateurInconnu, StationInconnu, NavetteIndisponible;
}
