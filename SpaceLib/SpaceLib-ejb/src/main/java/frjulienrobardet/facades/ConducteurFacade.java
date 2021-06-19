/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.facades;

import frjulienrobardet.entities.Conducteur;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class ConducteurFacade extends AbstractFacade<Conducteur> implements ConducteurFacadeLocal {

    @PersistenceContext(unitName = "frjulienrobardet_SpaceLib-ejb_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ConducteurFacade() {
        super(Conducteur.class);
    }

    @Override
    public Conducteur findByLoginAndPassword(String login, String motdepasse) {
        for (Conducteur conducteur : this.findAll()){
            if (conducteur.getLogin().equals(login) && conducteur.getPassword().equals(motdepasse)){
                return conducteur;
            }
        }
        return null;
    }

    @Override
    public Conducteur findByPrenomAndNomAndLogin(String prenom, String nom, String login) {
        for (Conducteur conducteur : this.findAll()){
            if (conducteur.getLogin().equals(login) && conducteur.getNom().equals(nom) && conducteur.getPrenom().equals(prenom)){
                return conducteur;
            }
        }
        return null;
    }
    
}
