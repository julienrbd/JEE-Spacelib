/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.facades;

import frjulienrobardet.entities.Navette;
import frjulienrobardet.entities.Voyage;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class NavetteFacade extends AbstractFacade<Navette> implements NavetteFacadeLocal {

    @PersistenceContext(unitName = "frjulienrobardet_SpaceLib-ejb_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NavetteFacade() {
        super(Navette.class);
    }

    @Override
    public Navette addVoyage(Navette navette, Voyage voyage) {
        List<Voyage> listeVoyage = navette.getVoyages();
        listeVoyage.add(voyage);
        navette.setVoyages(listeVoyage);
        this.edit(navette);
        return navette;
    }

    @Override
    public Navette setNbVoyages(Navette navette) {
        int nbVoyages = navette.getNbVoyages();
        nbVoyages --;
        navette.setNbVoyages(nbVoyages);
        this.edit(navette);
        return navette;
    }
    
}
