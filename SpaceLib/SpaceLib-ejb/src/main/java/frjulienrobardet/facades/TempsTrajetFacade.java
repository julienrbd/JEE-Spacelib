/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.facades;

import frjulienrobardet.entities.Station;
import frjulienrobardet.entities.TempsTrajet;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class TempsTrajetFacade extends AbstractFacade<TempsTrajet> implements TempsTrajetFacadeLocal {

    @PersistenceContext(unitName = "frjulienrobardet_SpaceLib-ejb_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TempsTrajetFacade() {
        super(TempsTrajet.class);
    }

    /**
     * 
     * @param sd designe la station de depart
     * @param sa designe la station d'arrivée
     * @return Le temps de trajet entre les deux stations
     */
    @Override
    public TempsTrajet findByStations(Station sd, Station sa) {
        for (TempsTrajet temps: this.findAll()){
            if (temps.getStationDepart().equals(sd) && temps.getStationArrivee().equals(sa)){
                return temps;
            }
        }
        return null;
    }
    
}
