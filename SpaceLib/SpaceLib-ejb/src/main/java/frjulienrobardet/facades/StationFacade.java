/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.facades;

import frjulienrobardet.entities.Quai;
import frjulienrobardet.entities.Station;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class StationFacade extends AbstractFacade<Station> implements StationFacadeLocal {

    @PersistenceContext(unitName = "frjulienrobardet_SpaceLib-ejb_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public StationFacade() {
        super(Station.class);
    }

    /**
     * 
     * @param idStation
     * @param date_sup
     * @return Le nombre de navettes sortantes entre maintenant et date_sup
     */
    
    @Override
    public int nbNavetteSortantes(Long idStation, Calendar date_sup) {
        Station station = this.find(idStation);
        int nbNavetteSortantes = 0;
        List<Quai> listeQuai = station.getQuais();
        for (Quai quai : listeQuai){
            if (quai.getNavette()!=null){
                nbNavetteSortantes++;
            }
        }
        return nbNavetteSortantes;
        
    }

    @Override
    public int nbNavetteEntrantes(Long idStation, Calendar date_sup) {
        Station station = this.find(idStation);
        int nbNavetteSortantes = 0;
        List<Quai> listeQuai = station.getQuais();
        for (Quai quai : listeQuai){
            if (quai.getNavette()!=null){
                nbNavetteSortantes++;
            }
        }
        return nbNavetteSortantes;
    }
    @Override
    public int nbNavettes(Long idStation) {
        Station station = this.find(idStation);
        int nbNavette = 0;
        List<Quai> listeQuai = station.getQuais();
        for (Quai quai : listeQuai){
            if (quai.getNavette()!=null){
                nbNavette++;
            }
        }
        return nbNavette;
    }

    @Override
    public int nbQuais(Long idStation) {
        Station station = this.find(idStation);
        return station.getNbQuais();
    }
    
}
