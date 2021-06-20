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
import java.util.Objects;
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

    @Override
    public Voyage findPlusProcheVoyageArriveADateEtQuai(Calendar dateDepart, Quai quai) {
        List<Voyage> listeVoyage = null;
        for(Voyage voyage : this.findAll()){
            if (voyage.getQuaiArrivee() == quai && voyage.getDateArrivee().compareTo(dateDepart)<=0){
                listeVoyage.add(voyage);
            }
        }
        if (listeVoyage.size() == 1){
            return listeVoyage.get(0);
        }
        Voyage voyageRetour = null;
        for (Voyage voyage : listeVoyage){
            if (voyageRetour == null || voyage.getDateArrivee().compareTo(voyageRetour.getDateArrivee())<0)
                voyageRetour=voyage;
        }
        return voyageRetour;
    }

    @Override
    public Voyage findPlusProcheVoyageDepartDeNavetteADateEtQuai(Calendar dateDepart, Quai quai, Navette navette) {
        List<Voyage> listeVoyage = null;
        for(Voyage voyage : this.findAll()){
            if (voyage.getQuaiDepart() == quai && voyage.getNavette() == navette && voyage.getDateDepart().compareTo(dateDepart)>=0){
                listeVoyage.add(voyage);
            }
        }
        if (listeVoyage.size() == 1){
            return listeVoyage.get(0);
        }
        Voyage voyageRetour = null;
        for (Voyage voyage : listeVoyage){
            if (voyageRetour == null || voyage.getDateArrivee().compareTo(voyageRetour.getDateArrivee())>0)
                voyageRetour=voyage;
        }
        return voyageRetour;
    }

    @Override
    public List<Voyage> findAllVoyagesPrevusByUsager(Usager usager) {
        List<Voyage> listeVoyage = null;
        for(Voyage voyage : this.findAll()){
            if (voyage.getUsager() == usager && voyage.getStatut().equals(Voyage.statutDebutVoyage)){
                listeVoyage.add(voyage);
            }
        }
        return listeVoyage;
    }

    @Override
    public Voyage findVoyageEnCoursUsager(Usager usager) {
        List<Voyage> listeVoyage = null;
        for(Voyage voyage : this.findAll()){
            if (voyage.getUsager() == usager && voyage.getStatut().equals(Voyage.statutDebutVoyage)){
                listeVoyage.add(voyage);
            }
        }
        return null;
    }

    @Override
    public boolean verifierSiAutresVoyagesPrevusSurNavette(Calendar dateDepart, Navette navette) {
        List<Voyage> listeVoyage = null;
        for(Voyage voyage : this.findAll()){
            if (voyage.getNavette() == navette && voyage.getDateDepart().compareTo(dateDepart)>=0){
                listeVoyage.add(voyage);
            }
        }
        return (!listeVoyage.isEmpty());
    }

    @Override
    public boolean verifierSiNavettePossedeDepartVoyageAvantDate(Calendar dateDepart, Navette navette) {
        List<Voyage> listeVoyage = null;
        for(Voyage voyage : this.findAll()){
            if (voyage.getNavette() == navette && voyage.getDateDepart().compareTo(dateDepart)<=0){
                listeVoyage.add(voyage);
            }
        }
        return (!listeVoyage.isEmpty());
    }

    @Override
    public Voyage findVoyageArriveeJourDateEtQuai(Calendar dateArrivee, Quai quai) {
        for(Voyage voyage : this.findAll()){
            if (voyage.getQuaiArrivee() == quai && voyage.getDateArrivee().compareTo(dateArrivee)==0){
                return voyage;
            }
        }
        return null;
    }

    @Override
    public Voyage findVoyageDepartJourDateEtQuai(Calendar dateDepart, Quai quai) {
        for(Voyage voyage : this.findAll()){
            if (voyage.getQuaiDepart() == quai && voyage.getDateDepart().compareTo(dateDepart)==0){
                return voyage;
            }
        }
        return null;
    }

    @Override
    public boolean verifierSiVoyagePasse(Long idVoyage) {
        Calendar now = Calendar.getInstance();
        for(Voyage voyage : this.findAll()){
            if (Objects.equals(voyage.getId(), idVoyage) && voyage.getDateDepart().compareTo(now)<=0){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean findVoyagesUsagerPeriode(Usager usager, Calendar depart, Calendar arrivee) {
        for(Voyage voyage : this.findAll()){
            if (voyage.getUsager().equals(usager) && voyage.getDateDepart().compareTo(arrivee)>=0 && voyage.getDateArrivee().compareTo(depart)<=0 && voyage.getStatut().equals(Voyage.statutDebutVoyage)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Voyage findSiVoyagePlanifie(Usager usager, int NbPassagers, Calendar dateDepart, Calendar dateArrivee) {
        for(Voyage voyage : this.findAll()){
            if (voyage.getUsager().equals(usager) && voyage.getDateDepart().compareTo(dateDepart)==0 && voyage.getDateArrivee().compareTo(dateArrivee)==0 && voyage.getStatut().equals(Voyage.statutDebutVoyage)){
                return voyage;
            }
        }
        return null;
    }

    
    
}
