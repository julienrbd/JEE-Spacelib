/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.facades;

import frjulienrobardet.entities.Navette;
import frjulienrobardet.entities.Quai;
import frjulienrobardet.entities.Station;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class QuaiFacade extends AbstractFacade<Quai> implements QuaiFacadeLocal {

    @PersistenceContext(unitName = "frjulienrobardet_SpaceLib-ejb_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public QuaiFacade() {
        super(Quai.class);
    }

    @Override
    public Quai findByNavette(Navette navette) {
        for(Quai quai : this.findAll()){
        if(quai.getNavette().equals(navette))
            return quai;
        }
        return null;
    }

    @Override
    public List<Quai> getListeQuaisStation(Station station) {
        List<Quai> listeQuais = null;
        for(Quai quai : this.findAll()){
        if(quai.getStation().equals(station))
            listeQuais.add(quai);
        }
        return listeQuais;
    }
    
}
