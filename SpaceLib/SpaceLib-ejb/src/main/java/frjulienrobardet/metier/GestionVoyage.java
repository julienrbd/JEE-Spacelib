/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Navette;
import frjulienrobardet.entities.Quai;
import frjulienrobardet.entities.Revision;
import frjulienrobardet.entities.Station;
import frjulienrobardet.entities.TempsTrajet;
import frjulienrobardet.entities.Usager;
import frjulienrobardet.entities.Voyage;
import frjulienrobardet.facades.NavetteFacadeLocal;
import frjulienrobardet.facades.QuaiFacadeLocal;
import frjulienrobardet.facades.RevisionFacadeLocal;
import frjulienrobardet.facades.StationFacadeLocal;
import frjulienrobardet.facades.TempsTrajetFacadeLocal;
import frjulienrobardet.facades.UsagerFacadeLocal;
import frjulienrobardet.facades.VoyageFacadeLocal;
import frjulienrobardet.spacelibshared.exceptions.QuaiIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.ReservationCloturee;
import frjulienrobardet.spacelibshared.exceptions.ReservationInconnu;
import frjulienrobardet.spacelibshared.exceptions.ReservationPassee;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.TempsTrajetInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import frjulienrobardet.spacelibshared.exceptions.VoyageInconnu;
import frjulienrobardet.spacelibshared.exceptions.NavetteIndisponible;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class GestionVoyage implements GestionVoyageLocal {

    @EJB
    private NavetteFacadeLocal navetteFacade;

    @EJB
    private UsagerFacadeLocal usagerFacade;
    
    @EJB
    private VoyageFacadeLocal voyageFacade;
    
    @EJB
    private TempsTrajetFacadeLocal tempsTrajetFacade;

    @EJB
    private StationFacadeLocal stationFacade;

    @EJB
    private QuaiFacadeLocal quaiFacade;

    @EJB
    private RevisionFacadeLocal revisionFacade;
    
    
    
    public static final String estVoyage = "Voyage";
    public static final String estTransfert = "Transfert";
    public static final String estProchaineReservation = "estProchaineReservation";
    public static final String estVoyageEnCours = "estVoyageEnCours";
    public static final String estAucun = "Aucun";

    @Override
    public Voyage reserverVoyage(Long idUsager, Long idStationDepart, Long idStationArrivee, int nbPassagers) throws QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, UtilisateurInconnu, StationInconnu, NavetteIndisponible {
        if(nbPassagers > 15){
            throw new QuaiIndisponible("Le nombre de passagers excède le nombre de places limite de nos navettes.");
        }
        
        Usager usager = this.usagerFacade.find(idUsager);
        if (usager == null) {
            Logger.getLogger(GestionVoyage.class.getName()).log(Level.SEVERE, "Ce compte d'usager n'existe pas.");
            throw new UtilisateurInconnu("Ce compte d'usager n'existe pas.");
        }
        
        // est ce que la station de départ existe ?
        Station stationDepart = this.stationFacade.find(idStationDepart);
        if (stationDepart == null) {
            throw new StationInconnu("La station de départ n'existe pas.");
        }

        // est ce que la station d'arrivée existe ?
        Station stationArrive = this.stationFacade.find(idStationArrivee);
        if (stationArrive == null) {
            throw new StationInconnu("La station d'arrivée n'existe pas.");
        }

        // est ce que le temps de trajet entre la station d'arrivée et la station de départ existe ?
        final TempsTrajet tpsTrajet = this.tempsTrajetFacade.findByStations(stationDepart, stationArrive);
        if (tpsTrajet == null) {
            throw new TempsTrajetInconnu("Le temps de trajet n'existe pas entre ces deux stations.");
        }

        // est ce que la station de départ a au moins un quai ?
        if (stationDepart.getNbQuais() <= 0) {
            throw new QuaiInexistant("Il n'existe pas de quais pour la station de départ.");
        }

        // est ce que la station d'arrivée a au moins un quai ?
        if (stationArrive.getNbQuais() <= 0) {
            throw new QuaiInexistant("Il n'existe pas de quais pour la station d'arrivée.");
        }

        
        
        Navette navetteFinale = null;
        Quai quaiDepartFinal = null;
        Quai quaiArriveFinal = null;
        //check si navette dispo
        for (Quai quai : stationDepart.getQuais()){
            if (quai.getNavette() != null && quai.getNavette().getNbPlaces() >= nbPassagers){
                navetteFinale = quai.getNavette();
                quaiDepartFinal = quai;
                break;
            }
        }
        if (navetteFinale == null){
            throw new NavetteIndisponible("Aucune navette n'est disponible dans la station de départ pour ce voyage");
        }
        if (quaiDepartFinal == null){
            throw new QuaiIndisponible("Aucun quai disponible dans la station de départ");
        }
        
        // reservation quai arrivée
        for (Quai quai : stationArrive.getQuais()){
            if (quai.getNavette() == null){
                quaiArriveFinal = quai;
                break;
            }
        }
        if (quaiArriveFinal == null){
            throw new QuaiIndisponible("Aucun quai disponible dans la station d'arrivée");
        }
        
        // création voyage initié (usager + navette)
        Calendar dateDepart = Calendar.getInstance();
        Calendar dateArrivee = (Calendar) dateDepart.clone();
        dateArrivee.add(Calendar.DATE, (tpsTrajet.getTemps()));
        Voyage voyageReserve = voyageFacade.creerVoyage(navetteFinale, usager, quaiDepartFinal, quaiArriveFinal, nbPassagers, dateDepart, dateArrivee);
        navetteFacade.addVoyage(navetteFinale, voyageReserve);
        usagerFacade.addVoyage(usager, voyageReserve);
        // log details voyage
        // TODO
        return voyageReserve;
    }

    @Override
    public void finaliserVoyage(Long idVoyage) throws VoyageInconnu {
        Voyage voyage = this.voyageFacade.find(idVoyage);
        if (voyage == null) {
            throw new VoyageInconnu("Ce voyage n'existe pas.");
        }
        
        Voyage voyageAcheve = new Voyage(voyage.getNbPassagers(), voyage.getNavette(), Voyage.statutFinVoyage, voyage.getUsager(), voyage.getDateDepart(), voyage.getDateArrivee(), voyage.getQuaiDepart(), voyage.getQuaiArrivee());
        this.voyageFacade.create(voyageAcheve);
        
        
        Navette navette = voyage.getNavette();
        Quai quaiArrivee = voyage.getQuaiArrivee();
        Quai quaiDepart = voyage.getQuaiDepart();
        
        navette = navetteFacade.setNbVoyages(navette);
        quaiArrivee = quaiFacade.setNavette(quaiArrivee, navette);
        
        if(navette.getNbVoyages() <= 0){
            Revision revisionNecessaire = new Revision(quaiArrivee, navette, Revision.statutRevisionNecessaire);
            this.revisionFacade.create(revisionNecessaire);
        }
    }

    @Override
    public Voyage voyageEnCours(Long idUsager) throws UtilisateurInconnu, VoyageInconnu {
        final Usager usager = this.usagerFacade.find(idUsager);
        if (usager == null) {
            throw new UtilisateurInconnu("Ce compte d'usager n'existe pas.");
        }
        final Voyage voy = this.voyageFacade.findVoyageEnCoursUsager(usager);     
         if (voy == null) {
            throw new VoyageInconnu("Pas de voyage en cours.");
        }
         
        if(voy.getQuaiArrivee().getNavette() != null){
            throw new VoyageInconnu("Ce voyage a déjà été finalisé.");
        }
         
         return voy; 
    }
}
