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
import frjulienrobardet.spacelibshared.exceptions.QuaiIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.ReservationCloturee;
import frjulienrobardet.spacelibshared.exceptions.ReservationInconnu;
import frjulienrobardet.spacelibshared.exceptions.ReservationPassee;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.TempsTrajetInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JulienRobardet
 */
@Local
public interface VoyageFacadeLocal {

    void create(Voyage voyage);

    void edit(Voyage voyage);

    void remove(Voyage voyage);

    Voyage find(Object id);

    List<Voyage> findAll();

    List<Voyage> findRange(int[] range);

    int count();
    
    public Voyage creerVoyage(Navette navette, Usager usager, Quai quaiDepart, Quai quaiArrive, int NbPassagers, Calendar dateDepart, Calendar dateArrivee);

    public Voyage findPlusProcheVoyageArriveADateEtQuai(Calendar dateDepart, Quai q);

    public Voyage findPlusProcheVoyageDepartDeNavetteADateEtQuai(Calendar dateDepart, Quai q, Navette n);

    public List<Voyage> findAllVoyagesPrevusByUsager(Usager usager);

    public Voyage findVoyageEnCoursUsager(Usager usager);

    public boolean verifierSiAutresVoyagesPrevusSurNavette(Calendar dateDepart, Navette n);

    public boolean verifierSiNavettePossedeDepartVoyageAvantDate(Calendar Cdate, Navette n);

    public Voyage findVoyageArriveeJourDateEtQuai(Calendar dateDepart, Quai q);

    public Voyage findVoyageDepartJourDateEtQuai(Calendar dateDepart, Quai q);
    
    public boolean verifierSiVoyagePasse(Long IdVoyage);
    
    public boolean findVoyagesUsagerPeriode(Usager usager, Calendar depart, Calendar arrivee);
    
    public Voyage findSiVoyagePlanifie(Usager usager, int NbPassagers, Calendar depart, Calendar arrivee);

}
