/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.facades;

import frjulienrobardet.entities.Mecanicien;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class MecanicienFacade extends AbstractFacade<Mecanicien> implements MecanicienFacadeLocal {

    @PersistenceContext(unitName = "frjulienrobardet_SpaceLib-ejb_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MecanicienFacade() {
        super(Mecanicien.class);
    }
    

    @Override
    public Mecanicien findByLoginAndPassword(String login, String motdepasse) {
        for (Mecanicien mecanicien : findAll()){
            if (mecanicien.getLogin().equals(login) && mecanicien.getPassword().equals(motdepasse)){
                return mecanicien;
            }
        }
        return null;
    }

    @Override
    public Mecanicien findByPrenomAndNom(String prenom, String nom) {
        for (Mecanicien mecanicien : findAll()){
            if (mecanicien.getPrenom().equals(prenom) && mecanicien.getNom().equals(nom)){
                return mecanicien;
            }
        }
        return null;
    }

    @Override
    public Mecanicien findByPrenomAndNomAndLogin(String prenom, String nom, String login) {
        for (Mecanicien mecanicien : findAll()){
            if (mecanicien.getPrenom().equals(prenom) && mecanicien.getNom().equals(nom) && mecanicien.getLogin().equals(login)){
                return mecanicien;
            }
        }
        return null;
    }

    @Override
    public Mecanicien creerMecanicienSiInexistant(String prenom, String nom, String login, String motdepasse) {
        if (this.findByPrenomAndNomAndLogin(prenom, nom, login) == null){
            Mecanicien m = new Mecanicien(nom, prenom, login, motdepasse);
            this.create(m);
            return m;
        }
        return this.findByPrenomAndNomAndLogin(prenom, nom, login);
    }
    
}
