/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Conducteur;
import frjulienrobardet.facades.ConducteurFacadeLocal;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class GestionConducteur implements GestionConducteurLocal {

    @EJB
    private ConducteurFacadeLocal conducteurFacade;

    
    @Override
    public void creerCompte(String nom, String prenom, String login, String motdepasse) throws UtilisateurExistant{
        final Conducteur conducteur = this.conducteurFacade.findByPrenomAndNomAndLogin(prenom, nom, login);
        if(conducteur != null){
            throw new UtilisateurExistant("Ce compte existe déjà.");
        }
        
        final Conducteur newConducteur = new Conducteur( nom,  prenom,  login,  motdepasse);
        this.conducteurFacade.create(newConducteur);
    }

    @Override
    public Long login(String login, String motdepasse) throws UtilisateurInconnu{
        final Conducteur conducteur = this.conducteurFacade.findByLoginAndPassword(login, motdepasse);
        if(conducteur == null) {
            throw new UtilisateurInconnu("Ce compte de conducteur n'existe pas.");
        }
        return conducteur.getId();
    }

}
