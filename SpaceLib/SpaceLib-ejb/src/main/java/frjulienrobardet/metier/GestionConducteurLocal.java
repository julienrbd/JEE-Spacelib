/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import javax.ejb.Local;

/**
 *
 * @author JulienRobardet
 */
@Local
public interface GestionConducteurLocal {

    public void creerCompte(String nom, String prenom, String login, String motdepasse) throws UtilisateurExistant;

    public Long login(String login, String motdepasse) throws UtilisateurInconnu;
    
}
