/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Usager;
import frjulienrobardet.facades.UsagerFacadeLocal;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class GestionUsager implements GestionUsagerLocal {
    
    @EJB
    private UsagerFacadeLocal usager;

    
    public Long authentifier(String login, String password)throws UtilisateurInconnu{
        Usager u = usager.authentifier(login, password);
        if (u == null) {
            throw new UtilisateurInconnu("Ce compte d'usager n'existe pas");
        }
        return u.getId();
    }
    public Long creerCompte(String nom, String prenom, String login, String password)throws UtilisateurExistant{
        Usager u = new Usager(nom, prenom, login, password);
        if (u == null) {
            throw new UtilisateurExistant("Ce compte d'usager existe déjà");
        }
        usager.create(u);
        return u.getId();
    }
}
