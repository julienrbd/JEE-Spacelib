/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.facades;

import frjulienrobardet.entities.Mecanicien;
import frjulienrobardet.entities.Navette;
import frjulienrobardet.entities.Quai;
import frjulienrobardet.entities.Revision;
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
public class RevisionFacade extends AbstractFacade<Revision> implements RevisionFacadeLocal {

    @PersistenceContext(unitName = "frjulienrobardet_SpaceLib-ejb_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public RevisionFacade() {
        super(Revision.class);
    }

    @Override
    public Revision nouveauDebutRevision(Mecanicien mecanicien, Navette navette, Quai quai) {
        String statut = Revision.statutDebutRevision;
        Revision r = new Revision(navette, statut, mecanicien, quai);
        this.create(r);
        return r;
    }

    @Override
    public Revision nouveauFinRevision(Mecanicien mecanicien, Navette navette, Quai quai) {
        String statut = Revision.statutFinRevision;
        Revision r = new Revision(navette, statut, mecanicien, quai);
        this.create(r);
        return r;
    }

    @Override
    public Revision nouveauRevisionNecessaire(Navette navette, Quai quai) {
        String statut = Revision.statutRevisionNecessaire;
        Revision r = new Revision(quai, navette, statut);
        this.create(r);
        return r;
    }

    @Override
    public Revision recupererDerniereRevisionQuai(Quai quai) {
        List<Revision> listeRevision = null;
        Revision derniereRevision = null;
        for(Revision revision : this.findAll()){
            if (revision.getQuaiNavette().equals(quai)){
                listeRevision.add(revision);
            }
        }
        for(Revision revision : listeRevision){
            if (derniereRevision==null || revision.getDateCreation().compareTo(derniereRevision.getDateCreation()) < 0){
                derniereRevision = revision;
            }
        }
        return derniereRevision;
    }

    @Override
    public Revision recupererDerniereRevisionMecanicienQuai(Quai quai, Mecanicien m) {
        List<Revision> listeRevision = null;
        Revision derniereRevision = null;
        for(Revision revision : this.findAll()){
            if (revision.getQuaiNavette().equals(quai) && revision.getMecanicien().equals(m)){
                listeRevision.add(revision);
            }
        }
        for(Revision revision : listeRevision){
            if (derniereRevision==null || revision.getDateCreation().compareTo(derniereRevision.getDateCreation()) < 0){
                derniereRevision = revision;
            }
        }
        return derniereRevision;
    }
    
}
