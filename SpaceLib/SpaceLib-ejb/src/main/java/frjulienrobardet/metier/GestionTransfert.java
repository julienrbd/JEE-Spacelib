/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Conducteur;
import frjulienrobardet.entities.Navette;
import frjulienrobardet.entities.Quai;
import frjulienrobardet.entities.Revision;
import frjulienrobardet.entities.Station;
import frjulienrobardet.entities.TempsTrajet;
import frjulienrobardet.entities.Transfert;
import frjulienrobardet.entities.Voyage;
import frjulienrobardet.facades.ConducteurFacadeLocal;
import frjulienrobardet.facades.NavetteFacadeLocal;
import frjulienrobardet.facades.QuaiFacadeLocal;
import frjulienrobardet.facades.RevisionFacadeLocal;
import frjulienrobardet.facades.StationFacadeLocal;
import frjulienrobardet.facades.TempsTrajetFacadeLocal;
import frjulienrobardet.facades.TransfertFacadeLocal;
import frjulienrobardet.facades.UsagerFacadeLocal;
import frjulienrobardet.facades.VoyageFacadeLocal;
import frjulienrobardet.spacelibshared.exceptions.QuaiIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.TempsTrajetInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import frjulienrobardet.spacelibshared.exceptions.VoyageInconnu;
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
public class GestionTransfert implements GestionTransfertLocal {

    
    @EJB
    private UsagerFacadeLocal usagerFacade;

    @EJB
    private VoyageFacadeLocal voyageFacade;

    @EJB
    private TransfertFacadeLocal transfertFacade;

    @EJB
    private RevisionFacadeLocal revisionFacade;

    @EJB
    private QuaiFacadeLocal quaiFacade;

    @EJB
    private StationFacadeLocal stationFacade;

    @EJB
    private TempsTrajetFacadeLocal tpstrajetFacade;

    @EJB
    private NavetteFacadeLocal navetteFacade;

    @EJB
    private ConducteurFacadeLocal conducteurFacade;
    
    public static final String estVoyage = "Voyage";
    public static final String estTransfert = "Transfert";
    public static final String estProchaineReservation = "estProchaineReservation";
    public static final String estVoyageEnCours = "estVoyageEnCours";
    public static final String estAucun = "Aucun";
    
    @Override
    public Transfert reserverTransfert(Long idConducteur, Long idStationDepart, Long idStationArrivee) throws QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, UtilisateurInconnu, StationInconnu {
        Calendar dateDepart = Calendar.getInstance();
        int NbPassagers = 1;
        
        Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Param idUsager : " + idConducteur);
        Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Param idStationDepart : " + idStationDepart);
        Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Param idStationArrivee : " + idStationArrivee);

        final Conducteur conducteur = this.conducteurFacade.find(idConducteur);
        if (conducteur == null) {
            throw new UtilisateurInconnu("Ce compte de conducteur n'existe pas.");
        }

        // est ce que la station de départ existe ?
        final Station stationDepart = this.stationFacade.find(idStationDepart);
        if (stationDepart == null) {
            throw new StationInconnu("La station de départ n'existe pas.");
        }

        // est ce que la station d'arrivée existe ?
        final Station stationArrive = this.stationFacade.find(idStationArrivee);
        if (stationArrive == null) {
            throw new StationInconnu("La station d'arrivée n'existe pas.");
        }

        // est ce que le temps de trajet entre la station d'arrivée et la station de départ existe ?
        final TempsTrajet tpsTrajet = this.tpstrajetFacade.findByStations(stationDepart, stationArrive);
        if (tpsTrajet == null) {
            throw new TempsTrajetInconnu("Le temps de trajet n'existe pas entre ces deux stations.");
        }

        // est ce que la station de départ a au moins un quai ?
        List<Quai> quaisStationDepart = this.quaiFacade.recupererListeQuaisParStation(stationDepart);
        if (quaisStationDepart.size() <= 0) {
            throw new QuaiInexistant("Il n'existe pas de quais pour la station de départ.");
        }

        // est ce que la station d'arrivée a au moins un quai ?
        List<Quai> quaisStationArrive = this.quaiFacade.recupererListeQuaisParStation(stationArrive);
        if (quaisStationArrive.size() <= 0) {
            throw new QuaiInexistant("Il n'existe pas de quais pour la station d'arrivée.");
        }
        
        
        Navette navetteFinale = null;
        Quai quaiDepartFinal = null;
        Quai quaiArriveFinal = null;

        outerloop:
        for (Quai q : quaisStationDepart) {

            Navette navette = null;
            Calendar dateArriveePrecedenteReservation = null;

            // on récupère la navette de la précédente arrivée du quai en cours
            Voyage precedentVoyage = this.voyageFacade.findPlusProcheVoyageArriveADateEtQuai(dateDepart, q);
            Transfert precedentTransfert = this.transfertFacade.findPlusProcheTransfertArriveADateEtQuai(dateDepart, q);
            String lePlusTard = lequelEstLePlusTard(precedentVoyage, precedentTransfert);
            Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "lePlusTard = " +lePlusTard);
            
            switch (lePlusTard) {
                case estAucun:
                    // est ce que le quai possède une navette arrimée au moment de la réservation ?
                    Navette navetteArrimee = q.getNavette();
                    if (navetteArrimee != null) {
                        navette = navetteArrimee;
                        dateArriveePrecedenteReservation = Calendar.getInstance();
                    } else {
                        Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Il n'y a jamais eu de voyage ni de transfert sur ce quai. Il n'y a donc pas de navette disponible sur ce quai. On passe au prochain quai.");
                        continue outerloop;
                    }
                    break;
                case estVoyage:
                    navette = precedentVoyage.getNavette();
                    dateArriveePrecedenteReservation = precedentVoyage.getDateArrivee();
                    break;
                case estTransfert:
                    navette = precedentTransfert.getNavette();
                    dateArriveePrecedenteReservation = precedentTransfert.getDateArrivee();
                    break;
            }

            // est ce que cette navette a déjà un voyage ou un transfert prévu plus tard ?
            boolean autresVoyagesPrevus = false;
            boolean autresTransfertPrevus = false;
            autresVoyagesPrevus = this.voyageFacade.verifierSiAutresVoyagesPrevusSurNavette(dateArriveePrecedenteReservation, navette);
            autresTransfertPrevus = this.transfertFacade.verifierSiAutresTransfertsPrevusSurNavette(dateArriveePrecedenteReservation, navette);
            if ((autresVoyagesPrevus == false) && (autresTransfertPrevus == false)) {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Il n'y a pas de réservations prévus à cette date de départ.");
            } else {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "La navette " + navette + " a déjà des réservations prévus. On passe au prochain quai.");
                continue;
            }

            // Pour finir on vérifie que la navette remplisse les conditions nécessaires pour le voyage : nombres de places et de voyages suffisants.
            if (EstOKPassagersEtVoyagesNavette(navette, NbPassagers) == true) {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "SUCCES FINAL ! La navette a assez de voyages restants pour faire celui-ci. Quai de départ et navette validés.");
                quaiDepartFinal = q;
                navetteFinale = navette;
                break outerloop;
            } else {
                continue outerloop;
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
        

        outerloop:
        for (Quai q : quaisStationArrive) {
            Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Début d'analyse du quai de la station d'arrivée : " + q);

            // est ce que la navette actuellement arrimée au quai va partir ?
            boolean prochainsVoyagesPrevus = false;
            boolean prochainsTransfertPrevus = false;
            prochainsVoyagesPrevus = this.voyageFacade.verifierSiAutresVoyagesPrevusSurNavette(Calendar.getInstance(), q.getNavette());
            prochainsTransfertPrevus = this.transfertFacade.verifierSiAutresTransfertsPrevusSurNavette(Calendar.getInstance(), q.getNavette());
            if ((prochainsVoyagesPrevus == false) && (prochainsTransfertPrevus == false)) {
                continue; // la navette ne repartira jamais, passons au prochain quai
            } 
            
            
            // Analyse de la précédente arrivée = Peut être avant ou le jour même. Si aucune arrivée avant, succès !
            Voyage precedentVoyage = this.voyageFacade.findPlusProcheVoyageArriveADateEtQuai(dateArrivee, q);
            Transfert precedentTransfert = this.transfertFacade.findPlusProcheTransfertArriveADateEtQuai(dateArrivee, q);
            Navette autreNavette = null;
            String lePlusTard = lequelEstLePlusTard(precedentVoyage, precedentTransfert);
            switch (lePlusTard) {
                case estAucun:
                    Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "SUCCES ! Il n'y a pas de précédent voyage ni transfert sur le quai d'arrivée.");
                    quaiArriveFinal = q;
                    break outerloop;
                case estVoyage:
                    autreNavette = precedentVoyage.getNavette();
                    break;
                case estTransfert:
                    autreNavette = precedentTransfert.getNavette();
                    break;
            }
            Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "La navette " + autreNavette + " est arrivée au quai " + q);

            // On vérifie que cette navette repart STRICTEMENT avant notre arrivée
            boolean autresVoyagesPrevus = false;
            boolean autresTransfertPrevus = false;
            autresVoyagesPrevus = this.voyageFacade.verifierSiNavettePossedeDepartVoyageAvantDate(dateArrivee, autreNavette);
            autresTransfertPrevus = this.transfertFacade.verifierSiNavettePossedeDepartTransfertAvantDate(dateArrivee, autreNavette);
            if ((autresVoyagesPrevus == false) && (autresTransfertPrevus == false)) {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "ECHEC ! La navette " + autreNavette + " sera encore arrimée au quai quand nous allons arriver.");
                continue;
            } else {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "SUCCES ! La navette " + autreNavette + " ne sera plus arrimée au quai quand nous allons arriver.");
                quaiArriveFinal = q;
                break outerloop;
            }
        }

        /////////////////////////////// FINALISATION ////////////////////////////////
        Transfert transfertFinal = null;
        if ((navetteFinale != null) && (quaiDepartFinal != null) && (quaiArriveFinal != null)) {
            transfertFinal = new Transfert(NbPassagers, navetteFinale, Transfert.statutDebutTransfert, conducteur, dateDepart, dateArrivee, quaiDepartFinal, quaiArriveFinal);
            this.transfertFacade.create(transfertFinal);
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

        return transfertFinal;
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
    public ArrayList<Transfert> obtenirTransfertsConducteur(Long idConducteur) throws UtilisateurInconnu{
        final Conducteur conducteur = this.conducteurFacade.find(idConducteur);
        if (conducteur == null) {
            throw new UtilisateurInconnu("Ce compte de conducteur n'existe pas.");
        }
        ArrayList<Transfert> transferts = new ArrayList(transfertFacade.findAllTransfertsPrevusByConducteur(conducteur));
        return transferts;
    }

    @Override
    public void finaliserTransfert(Long idTransfert) throws VoyageInconnu {
        final Transfert transfert = this.transfertFacade.find(idTransfert);
        if (transfert == null) {
            throw new VoyageInconnu("Ce transfert n'existe pas.");
        }
        
        Transfert transfertAcheve = new Transfert(transfert.getNbPassagers(), transfert.getNavette(), Transfert.statutFinTransfert, transfert.getConducteur(), transfert.getDateDepart(), transfert.getDateArrivee(), transfert.getQuaiDepart(), transfert.getQuaiArrivee());
        this.transfertFacade.create(transfertAcheve);
        
        final Navette navette = transfert.getNavette();
        final Quai quaiArrivee = transfert.getQuaiArrivee();
        
        navette.setNbVoyages(navette.getNbVoyages() - 1);
        quaiArrivee.setNavette(navette);
        
        if(navette.getNbVoyages() <= 0){
            Revision revisionNecessaire = new Revision(navette, Revision.statutRevisionNecessaire, quaiArrivee);
            this.revisionFacade.create(revisionNecessaire);
        }

    }

    @Override
    public Transfert transfertEnCours(Long idConducteur) throws UtilisateurInconnu, VoyageInconnu {
        final Conducteur conducteur = this.conducteurFacade.find(idConducteur);
        if (conducteur == null) {
            throw new UtilisateurInconnu("Ce compte de conducteur n'existe pas.");
        }
        
        final Transfert transfert = this.transfertFacade.findTransfertEnCoursConducteur(conducteur);     
         if (transfert == null) {
            throw new VoyageInconnu("Pas de transfert en cours.");
        }
         
        if(transfert.getQuaiArrivee().getNavette() != null){
            throw new VoyageInconnu("Ce transfert a déjà été finalisé.");
        }
         
         return transfert;
    }
    
    private String lequelEstLePlusTard(Voyage voyage, Transfert transfert) {
        String resa = null;
        Calendar dateVoyage = null;
        Calendar dateTransfert = null;
        if ((voyage != null) && (transfert != null)) {
            Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Il y au moins un voyage ET un transfert prévus pour cette navette sur ce quai.");
            dateVoyage = voyage.getDateArrivee();
            dateTransfert = transfert.getDateArrivee();
            if (dateVoyage.compareTo(dateTransfert) > 0) {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "La dernière arrivée la plus récente sur ce quai est un voyage.");
                resa = estVoyage;
            } else if (dateVoyage.compareTo(dateTransfert) < 0) {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "La dernière arrivée la plus récente sur ce quai est un transfert.");
                resa = estTransfert;
            } else {
                Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Le transfert et le voyage ont la même date.");
                System.out.println("dateVoyage = " +dateVoyage.getTime());
                System.out.println("dateTransfert = " +dateTransfert.getTime());
                resa = estAucun;
            }
        } else if ((voyage == null) && (transfert == null)) {
            Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Il n'y a n'y a ni voyage ni transfert prévus prochainement pour cette navette sur ce quai.");
            resa = estAucun;
        } else if (voyage == null) {
            Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Il y a au moins un transfert pour cette navette prévus sur ce quai.");
            resa = estTransfert;
        } else if (transfert == null) {
            Logger.getLogger(GestionVoyage.class.getName()).log(Level.INFO, "Il y a au moins un voyage prévus pour cette navette sur ce quai.");
            resa = estVoyage;
        }
        return resa;
    }
    
}
