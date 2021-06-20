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
import java.util.ArrayList;
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
    public static final String estProchaineReservation = "estProchaineReservation";
    public static final String estVoyageEnCours = "estVoyageEnCours";
    public static final String estAucun = "Aucun";

    @Override
    public Voyage reserverVoyage(Long idUsager, Long idStationDepart, Long idStationArrivee, int nbPassagers, Calendar dateDepart) throws QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, UtilisateurInconnu, StationInconnu, NavetteIndisponible {
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
        
        List<Quai> quaisStationDepart = this.quaiFacade.getListeQuaisStation(stationDepart);
        outerloop:
        for (Quai q : quaisStationDepart) {

            Navette navette = null;
            Calendar dateArriveePrecedenteReservation = null;

            // on récupère la navette de la précédente arrivée du quai en cours
            Voyage precedentVoyage = this.voyageFacade.findPlusProcheVoyageArriveADateEtQuai(dateDepart, q);
            
            if (precedentVoyage == null) {
                    // est ce que le quai possède une navette arrimée au moment de la réservation ?
                Navette navetteArrimee = q.getNavette();
                if (navetteArrimee != null) {
                    navette = navetteArrimee;
                    dateArriveePrecedenteReservation = Calendar.getInstance();
                } else {
                    Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Il n'y a jamais eu de voyage ni de transfert sur ce quai. Il n'y a donc pas de navette disponible sur ce quai. On passe au prochain quai.");
                    continue ;
                }
            } else { 
                    navette = precedentVoyage.getNavette();
                    dateArriveePrecedenteReservation = precedentVoyage.getDateArrivee();
            }

            // est ce que cette navette a déjà un voyage prévu plus tard ?
            boolean autresVoyagesPrevus = false;
            autresVoyagesPrevus = this.voyageFacade.verifierSiAutresVoyagesPrevusSurNavette(dateArriveePrecedenteReservation, navette);
            if (autresVoyagesPrevus == false) {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Il n'y a pas de réservations prévus à cette date de départ.");
            } else {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "La navette " + navette + " a déjà des réservations prévus. On passe au prochain quai.");
                continue;
            }

            // Pour finir on vérifie que la navette remplisse les conditions nécessaires pour le voyage : nombres de places et de voyages suffisants.
            if (EstOKPassagersEtVoyagesNavette(navette, nbPassagers) == true) {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "SUCCES FINAL ! La navette a assez de voyages restants pour faire celui-ci. Quai de départ et navette validés.");
                quaiDepartFinal = q;
                navetteFinale = navette;
                break ;
            } else {
                continue ;
            }
        }

        ////////////////////////////////////////////////// CHECKPOINT ////////////////////////////////////////////////////////
        if ((quaiDepartFinal == null) || (navetteFinale == null)) {
            throw new QuaiIndisponible("Il n'y a aucune navette disponible répondant à ces critères.");
        } else {
            Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Le quai de départ choisi est le " + quaiDepartFinal);
            Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "La navette pour le voyage est la " + navetteFinale);
        }

        /*
           ALGORITHME POUR TROUVER LE QUAI D'ARRIVE
           Si il y a une navette qui arrive avant, il faut vérifier qu'elle sera parti quand la navette de l'usager arrivera !
         */
        Calendar dateArrivee = (Calendar) dateDepart.clone();
        dateArrivee.add(Calendar.DATE, (tpsTrajet.getTemps()));
        Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "DATE DE DEPART PREVU = " + dateDepart);
        Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "TEMPS TRAJET = " + tpsTrajet.getTemps());
        Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "DATE D'ARRIVEE CALCULEE = " + dateArrivee);
        
        
        
        
        // vérifier si ce voyage existe déjà dans la liste des voyages alors c'est un voyage planifié : 
        // - pas de duplication
        // - on retire la navette du quai de départ
        // - et c'est parti !
        System.out.println("usager "+ usager);
        System.out.println("NbPassagers "+ nbPassagers);
        System.out.println("dateDepart "+ dateDepart);
        System.out.println("dateArrivee "+ dateArrivee);
        Voyage voyagePlanifie = this.voyageFacade.findSiVoyagePlanifie(usager, nbPassagers, dateDepart, dateArrivee);
        
        if(voyagePlanifie != null){
            final Quai quaiDepart = voyagePlanifie.getQuaiDepart();
            quaiDepart.setNavette(null);
            return voyagePlanifie;
        } 
        
        
                
        // vérifier si pas de voyage prévu par le client sur cette période
        boolean autresVoyagesPrevusUsager = this.voyageFacade.findVoyagesUsagerPeriode(usager, dateDepart, dateArrivee);
        if(autresVoyagesPrevusUsager == true){
            throw new QuaiIndisponible("Vous avez déjà un ou plusieurs voyages prévus sur cette période.");
        } 
        
        
        List<Quai> quaisStationArrive = this.quaiFacade.getListeQuaisStation(stationArrive);
        outerloop:
        for (Quai q : quaisStationArrive) {
            Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Début d'analyse du quai de la station d'arrivée : " + q);

                        
            // est ce que la navette actuellement arrimée au quai va partir ?
            boolean prochainsVoyagesPrevus = false;
            prochainsVoyagesPrevus = this.voyageFacade.verifierSiAutresVoyagesPrevusSurNavette(Calendar.getInstance(), q.getNavette());
            if (q.getNavette() != null && prochainsVoyagesPrevus == false) {
                continue; // la navette ne repartira jamais, passons au prochain quai
            } 
            
            
            // Analyse de la précédente arrivée = Peut être avant ou le jour même. Si aucune arrivée avant, succès !
            Voyage precedentVoyage = this.voyageFacade.findPlusProcheVoyageArriveADateEtQuai(dateArrivee, q);
            Navette autreNavette = null;
            if (precedentVoyage == null){
                    Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "SUCCES ! Il n'y a pas de précédent voyage ni transfert sur le quai d'arrivée.");
                    quaiArriveFinal = q;
                    break;
            } else {
                autreNavette = precedentVoyage.getNavette();
            }
            Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "La navette " + autreNavette + " est arrivée au quai " + q);

            // On vérifie que cette navette repart STRICTEMENT avant notre arrivée
            boolean autresVoyagesPrevus = false;
            autresVoyagesPrevus = this.voyageFacade.verifierSiNavettePossedeDepartVoyageAvantDate(dateArrivee, autreNavette);
            if (autresVoyagesPrevus == false){
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "ECHEC ! La navette " + autreNavette + " sera encore arrimée au quai quand nous allons arriver.");
            } else {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "SUCCES ! La navette " + autreNavette + " ne sera plus arrimée au quai quand nous allons arriver.");
                quaiArriveFinal = q;
                break;
            }
        }

        /////////////////////////////// FINALISATION ////////////////////////////////
        Voyage voyageFinal = null;
        if ((navetteFinale != null) && (quaiDepartFinal != null) && (quaiArriveFinal != null)) {
            voyageFinal = new Voyage(nbPassagers, navetteFinale, Voyage.statutDebutVoyage, usager, dateDepart, dateArrivee, quaiDepartFinal, quaiArriveFinal);
            this.voyageFacade.create(voyageFinal);
        } else {

            if (quaiDepartFinal == null) {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.SEVERE, "ECHEC FINAL ! Il n'y a pas de quai de départ disponible.");
                throw new QuaiIndisponible("Il n'y a pas de quai de départ disponible.");
            } else if (quaiArriveFinal == null) {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.SEVERE, "ECHEC FINAL ! Il n'y a pas de quai d'arrivée disponible.");
                throw new QuaiIndisponible("Il n'y a pas de quai d'arrivée disponible.");
            } else if (navetteFinale == null) {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.SEVERE, "ECHEC FINAL ! Il n'y a pas de navette disponible.");
                throw new QuaiIndisponible("Il n'y a pas de navette disponible.");
            }
        }

        return voyageFinal;
    }
    
    private boolean EstOKPassagersEtVoyagesNavette(Navette n, int nb) {
        boolean result = false;
        System.out.println("n.getNbPlaces() = " + n.getNbPlaces());
        System.out.println("n.getNbVoyages() = " + n.getNbVoyages());
        if ((n.getNbPlaces() >= nb) && (n.getNbVoyages() > 0)) {
            result = true;
        }
        return result;
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

    @Override
    public void annulerVoyage(Long idClient, Long idReservation) throws UtilisateurInconnu, ReservationInconnu, ReservationPassee, ReservationCloturee {
        final Usager usager = this.usagerFacade.find(idClient);
        if (usager == null) {
            throw new UtilisateurInconnu("Ce compte d'usager n'existe pas.");
        }
        
        final Voyage voyage = this.voyageFacade.find(idReservation);
        if (voyage == null) {
            throw new ReservationInconnu("Ce voyage n'existe pas.");
        }
        
        final boolean voyagePasse = this.voyageFacade.verifierSiVoyagePasse(voyage.getId());
        if (voyagePasse == true) {
            throw new ReservationPassee("La date de départ de ce voyage est déjà passé.");
        }
        
        this.voyageFacade.remove(voyage);
    }

    @Override
    public ArrayList<Voyage> obtenirVoyagesPrevusUsager(Long idUsager) throws UtilisateurInconnu {
        final Usager usager = this.usagerFacade.find(idUsager);
        if (usager == null) {
            throw new UtilisateurInconnu("Ce compte d'usager n'existe pas.");
        }
        ArrayList<Voyage> voyages = new ArrayList(voyageFacade.findAllVoyagesPrevusByUsager(usager));
        return voyages;
    }
}
