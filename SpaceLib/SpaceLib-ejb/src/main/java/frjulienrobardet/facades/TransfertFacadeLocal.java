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
import java.util.Calendar;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JulienRobardet
 */
@Local
public interface TransfertFacadeLocal {

    void create(Transfert transfert);

    void edit(Transfert transfert);

    void remove(Transfert transfert);

    Transfert find(Object id);

    List<Transfert> findAll();

    List<Transfert> findRange(int[] range);

    int count();

    Transfert findPlusProcheTransfertArriveADateEtQuai(Calendar dateDepart,Quai q);
    
    Transfert findPlusProcheTransfertDepartDeNavetteADateEtQuai(Calendar dateDepart, Quai q, Navette n);
    
    boolean verifierSiAutresTransfertsPrevusSurNavette(Calendar dateDepart, Navette n);
    
    boolean verifierSiNavettePossedeDepartTransfertAvantDate(Calendar Cdate, Navette n);
    
    Transfert findTransfertArriveeJourDateEtQuai(Calendar dateDepart, Quai q);
    
    Transfert findTransfertDepartJourDateEtQuai(Calendar dateDepart, Quai q);
    
    List<Transfert> findAllTransfertsPrevusByConducteur(Conducteur conducteur);
    
    Transfert findTransfertEnCoursConducteur(Conducteur conducteur);
    
    
}
