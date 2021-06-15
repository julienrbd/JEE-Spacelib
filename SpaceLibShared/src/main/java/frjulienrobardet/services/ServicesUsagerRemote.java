/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.services;

import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import javax.ejb.Remote;

/**
 *
 * @author JulienRobardet
 */
@Remote
public interface ServicesUsagerRemote {
    
    Long login(String login, String motdepasse) throws UtilisateurInconnu;
    Long creerCompte(String nom, String prenom, String login, String motdepasse) throws UtilisateurExistant;
}
