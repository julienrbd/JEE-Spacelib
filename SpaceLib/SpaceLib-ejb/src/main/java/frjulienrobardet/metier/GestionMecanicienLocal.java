/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import javax.ejb.Local;

/**
 *
 * @author JulienRobardet
 */
@Local
public interface GestionMecanicienLocal {
    
    Long authentifier(String login, String password) throws UtilisateurInconnu;
    
    Long creerCompte(String nom, String prenom, String login, String password) throws UtilisateurExistant;

    long renseignerStationRattachement(String nom);
    
    long authentifierAvecStationRattachement(String login, String motdepasse, long idStation) throws UtilisateurInconnu, StationInconnu;
}
