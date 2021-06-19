/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.facades;

import frjulienrobardet.entities.Conducteur;
import frjulienrobardet.entities.Navette;
import frjulienrobardet.entities.Quai;
import frjulienrobardet.entities.Transfert;
import java.util.ArrayList;
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
public class TransfertFacade extends AbstractFacade<Transfert> implements TransfertFacadeLocal {

    @PersistenceContext(unitName = "frjulienrobardet_SpaceLib-ejb_ejb_1.0PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TransfertFacade() {
        super(Transfert.class);
    }

    @Override
    public Transfert findPlusProcheTransfertArriveADateEtQuai(Calendar dateDepart, Quai quai) {
        List<Transfert> listeTransfert = new ArrayList<>();
        for (Transfert transfert : this.findAll()){
            if (transfert.getQuaiArrivee() == quai && transfert.getDateArrivee().compareTo(dateDepart)<= 0){
                listeTransfert.add(transfert);
            }
        }
        Transfert res = null;
        if (listeTransfert.isEmpty()){
            return null;
        }else if (listeTransfert.size() == 1){
            return listeTransfert.get(0);
        } else {
            for (Transfert transfert : listeTransfert){
                if (res == null || transfert.getDateArrivee().compareTo(res.getDateArrivee())<= 0){
                    res = transfert;
                }
            }
            return res;
        }
    }

    @Override
    public boolean verifierSiAutresTransfertsPrevusSurNavette(Calendar dateArrivee, Navette navette) {
        List<Transfert> listeTransfert = new ArrayList<>();
        for (Transfert transfert : this.findAll()){
            if (transfert.getNavette() == navette && transfert.getDateDepart().compareTo(dateArrivee)>= 0){
                listeTransfert.add(transfert);
            }
        }
        return listeTransfert.size()>0;
    }

    @Override
    public boolean verifierSiNavettePossedeDepartTransfertAvantDate(Calendar dateArrivee, Navette navette) {
        List<Transfert> listeTransfert = new ArrayList<>();
        for (Transfert transfert : this.findAll()){
            if (transfert.getNavette() == navette && transfert.getDateDepart().compareTo(dateArrivee)<= 0){
                listeTransfert.add(transfert);
            }
        }
        return listeTransfert.size()>0;
    }

    @Override
    public List<Transfert> findAllTransfertsPrevusByConducteur(Conducteur conducteur) {
        List<Transfert> listeTransfert = new ArrayList<>();
        for (Transfert transfert : this.findAll()){
            if (transfert.getConducteur() == conducteur && transfert.getStatut().equals(Transfert.statutDebutTransfert)){
                listeTransfert.add(transfert);
            }
        }
        return listeTransfert;
    }

    @Override
    public Transfert findTransfertEnCoursConducteur(Conducteur conducteur) {
        Calendar date = Calendar.getInstance();
        for (Transfert transfert : this.findAll()){
            if (transfert.getConducteur() == conducteur && transfert.getStatut().equals(Transfert.statutDebutTransfert) && transfert.getDateDepart().compareTo(date)<=0 && transfert.getDateArrivee().compareTo(date)>=0){
                return transfert;
            }
        }
        return null;
    }

    @Override
    public Transfert findPlusProcheTransfertDepartDeNavetteADateEtQuai(Calendar dateDepart, Quai quai, Navette navette) {
       List<Transfert> listeTransfert = new ArrayList<>();
        for (Transfert transfert : this.findAll()){
            if (transfert.getNavette() == navette && transfert.getDateDepart().compareTo(dateDepart)>= 0 && transfert.getDateArrivee().compareTo(dateDepart)>= 0 && transfert.getQuaiDepart().equals(quai)){
                return transfert;
            }
        }
        return null;
    }

    @Override
    public Transfert findTransfertArriveeJourDateEtQuai(Calendar dateDepart, Quai quai) {
        List<Transfert> listeTransfert = new ArrayList<>();
        for (Transfert transfert : this.findAll()){
            if (transfert.getQuaiDepart().equals(quai) && transfert.getDateArrivee().compareTo(dateDepart)>= 0 && transfert.getDateArrivee().compareTo(dateDepart)>= 0 && transfert.getQuaiDepart().equals(quai)){
                return transfert;
            }
        }
        return null;
    }

    @Override
    public Transfert findTransfertDepartJourDateEtQuai(Calendar dateDepart, Quai quai) {
        List<Transfert> listeTransfert = new ArrayList<>();
        for (Transfert transfert : this.findAll()){
            if (transfert.getQuaiDepart().equals(quai) && transfert.getDateDepart().compareTo(dateDepart)>= 0 && transfert.getDateDepart().compareTo(dateDepart)>= 0 && transfert.getQuaiDepart().equals(quai)){
                return transfert;
            }
        }
        return null;
    }
    
}
