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
    public Mecanicien authentifier(String login, String password){
        for(Mecanicien m : this.findAll()){
        if(m.getLogin().equals(login)&& m.getPassword().equals(password))
            return m;
        }
        return null;
    }
    
}
