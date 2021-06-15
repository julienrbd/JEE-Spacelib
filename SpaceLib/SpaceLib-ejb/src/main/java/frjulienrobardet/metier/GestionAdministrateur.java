/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Administrateur;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import frjulienrobardet.facades.AdministrateurFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class GestionAdministrateur implements GestionAdministrateurLocal {

    @EJB
    private AdministrateurFacadeLocal admin;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public Long authentifier(String login, String password){
        Administrateur a = admin.authentifier(login, password);
        return a.getId();
    }
    public Long creerCompte(String nom, String prenom, String login, String password){
        Administrateur a = new Administrateur(nom, prenom, login, password);
        admin.create(a);
        return a.getId();
    }
}
