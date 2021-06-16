/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.facades;

import frjulienrobardet.entities.Navette;
import frjulienrobardet.entities.Quai;
import frjulienrobardet.entities.Usager;
import frjulienrobardet.entities.Voyage;
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
public class VoyageFacade extends AbstractFacade<Voyage> implements VoyageFacadeLocal {

    @PersistenceContext(unitName = "frjulienrobardet_SpaceLib-ejb_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VoyageFacade() {
        super(Voyage.class);
    }

    @Override
    public Voyage creerVoyage(Navette navette, Usager usager, Quai quaiDepart, Quai quaiArrive, int NbPassagers, Calendar dateDepart, Calendar dateArrivee) {
        Voyage nouveauVoyage = new Voyage(NbPassagers, navette, Voyage.statutDebutVoyage, usager, dateDepart, dateArrivee, quaiDepart, quaiArrive);
        this.create(nouveauVoyage);
        return nouveauVoyage;
    }
    
}
