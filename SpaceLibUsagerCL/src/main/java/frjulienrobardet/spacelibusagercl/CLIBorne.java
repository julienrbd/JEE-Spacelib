/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.spacelibusagercl;

import frjulienrobardet.spacelibshared.exceptions.NavetteIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.StationInsuffisante;
import frjulienrobardet.spacelibshared.exceptions.TempsTrajetInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import frjulienrobardet.spacelibshared.exceptions.VoyageInconnu;
import frjulienrobardet.spacelibshared.export.StationExport;
import frjulienrobardet.spacelibshared.export.VoyageExport;
import frjulienrobardet.spacelibshared.services.ServicesUsagerRemote;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author JulienRobardet
 */
public class CLIBorne {
    

    private enum CHOIX_PROCESS {
        DEPART, ARRIVEE
    };

    private final ServicesUsagerRemote serviceUsager;
    private final Scanner scanner = new Scanner(System.in);
    private final CLIUtils utils = new CLIUtils();

    public CLIBorne(ServicesUsagerRemote serviceUsager) {
        this.serviceUsager = serviceUsager;
    }

    public void run() throws UtilisateurInconnu, IllegalAccessException, InvocationTargetException, QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, StationInconnu, VoyageInconnu, UtilisateurExistant, NavetteIndisponible, StationInsuffisante {
        ArrayList<StationExport> stations = this.serviceUsager.obtenirStations();
        if(stations.size() < 2 ){
            throw new StationInsuffisante("Il n'existe pas assez de station pour faire fonctionner l'application");
        } 
        
        System.out.println("Voici la liste des stations : ");
        Long idStationCourante = ChoisirStationCourante(stations);
        System.out.println("Veuillez choisir votre station de départ : ");
        while (true) {
            System.out.println("Quel type de client êtes vous ? (Conducteur / Usager)");
            String typeClient = utils.saisirChaine(scanner);
            try {
                if (typeClient.equalsIgnoreCase("Conducteur")) {
                } else if (typeClient.equalsIgnoreCase("Usager")) {
                    Long idUsager = obtenirUsager();
                    runUsager(idStationCourante, idUsager, stations);
                } else {
                    System.out.println("Aucun utilisateur reconnu. Veuillez reessayer svp.");
                }
            } catch (UtilisateurInconnu ex) {
                System.out.println("Aucun utilisateur reconnu. Veuillez reessayer.");
            }

        }
    }

    private void runUsager(Long idStationCourante, Long idUsager, ArrayList<StationExport> stations) throws UtilisateurInconnu, QuaiInexistant, VoyageInconnu, QuaiIndisponible, TempsTrajetInconnu, StationInconnu, NavetteIndisponible {
        CHOIX_PROCESS process = obtenirProcess(idUsager);
        if (process == CHOIX_PROCESS.DEPART) {
            depart(idUsager, idStationCourante, stationsArrivee(stations, idStationCourante));
        } else {
            arrivee(idUsager);
        }
    }

    private ArrayList<StationExport> stationsArrivee(ArrayList<StationExport> stations, Long idStationDepart) {
        ArrayList<StationExport> stationsDepart = new ArrayList(stations);
        stationsDepart.removeIf((StationExport station) -> Objects.equals(station.getId(), idStationDepart));
        return stationsDepart;
    }

    private void afficherListeStations(ArrayList<StationExport> stations) {
        stations.forEach((station) -> {
            System.out.println(station.getId() + ". " + station.getNom());
        });
    }

    private ArrayList<Long> getIDsStations(ArrayList<StationExport> stations) {
        ArrayList<Long> ids = new ArrayList<>();
        stations.forEach((station) -> {
            ids.add(station.getId());
        });
        return ids;
    }

    private Long verifierUsager(String login, String mdp) throws UtilisateurInconnu {
        Long idUsager = this.serviceUsager.login(login, mdp);
        return idUsager;
    }

    private Long ChoisirStationCourante(ArrayList<StationExport> stations) throws IllegalAccessException, InvocationTargetException {
        afficherListeStations(stations);
        
        System.out.println("Veuillez choisir votre station de départ : ");
        //"Station courante: ",
        return utils.saisirEntier(scanner,  getIDsStations(stations));
    }

    private Long obtenirUsager() throws UtilisateurInconnu, UtilisateurExistant {
        System.out.println("Avez-vous un compte ?");
        boolean aUnCompte = utils.yesNoQuestion(scanner);
        if (aUnCompte) {
            return loginCompte();
        } else {
            return creerCompte();
        }

    }

    private Long loginCompte() throws UtilisateurInconnu {
        System.out.println("Login: ");
        String login = utils.saisirChaine(scanner);
        System.out.println("Mot de passe: ");
        String mdp = utils.saisirChaine(scanner);
        Long usager = verifierUsager(login, mdp);
        return usager;
    }

    private Long creerCompte() throws UtilisateurExistant {
        System.out.println("Nom: ");
        String nom = utils.saisirChaine(scanner);
        System.out.println("Prenom: ");
        String prenom = utils.saisirChaine(scanner);
        System.out.println("Login: ");
        String login = utils.saisirChaine(scanner);
        System.out.println("Mot de passe: ");
        String mdp = utils.saisirChaine(scanner);
        return this.serviceUsager.creerCompte(nom, prenom, login, mdp);
    }

    private CHOIX_PROCESS obtenirProcess(Long idUsager) throws UtilisateurInconnu {
        try {
            VoyageExport voyage = this.serviceUsager.voyageEnCours(idUsager);
            if (voyage == null) {
                return CHOIX_PROCESS.DEPART;
            } else {
                return CHOIX_PROCESS.ARRIVEE;
            }
        } catch (VoyageInconnu ex) {
            return CHOIX_PROCESS.DEPART;
        }
    }

    private void depart(Long usager, Long idStationDepart, ArrayList<StationExport> stationsArrivee) throws QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, UtilisateurInconnu, StationInconnu, NavetteIndisponible {
        System.out.println("Voici la liste des stations : ");
        afficherListeStations(stationsArrivee);
        System.out.println("Indiquer le numero de votre destination : ");
        //, "Station d'arrivée: "
        Long idStationArrivee = utils.saisirEntier(scanner, getIDsStations(stationsArrivee));
        
        
        System.out.println("Indiquer le nombre de passager : ");
        // "Nombre de passagers: ",
        Long nbPassagers = utils.saisirEntier(scanner, new Long(0), Long.MAX_VALUE);
        VoyageExport voyage = this.serviceUsager.reserverVoyage(usager, idStationDepart, idStationArrivee, (int) (long) nbPassagers);
        System.out.println("Réservation réussie. Rendez vous au quai " + voyage.getQuaiDepart());
    }

    private void arrivee(Long usager) throws UtilisateurInconnu, VoyageInconnu {
        VoyageExport voyageEncours = this.serviceUsager.voyageEnCours(usager);
        //, "Finaliser le voyage en cours? "
        System.out.println("finaliser? : ");
        if (utils.yesNoQuestion(scanner)) {
            this.serviceUsager.finaliserVoyage(voyageEncours.getId());
        }
    }
}
