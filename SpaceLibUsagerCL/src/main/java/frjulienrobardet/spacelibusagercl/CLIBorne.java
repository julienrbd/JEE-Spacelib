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

    public void run() throws UtilisateurInconnu, IllegalAccessException, InvocationTargetException, QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, StationInconnu, VoyageInconnu, UtilisateurExistant, NavetteIndisponible {
        ArrayList<StationExport> stations = this.serviceUsager.obtenirStations();
        Long idStationCourante = ChoisirStationCourante(stations);
        while (true) {
            System.out.println("Quel type de client êtes vous ?");
            String typeClient = utils.saisirChaine(scanner, "Type de client? ");
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
        return utils.saisirEntier(scanner, "Station courante: ", getIDsStations(stations));
    }

    private Long obtenirUsager() throws UtilisateurInconnu, UtilisateurExistant {
        System.out.println("Avez-vous un compte ?");
        boolean aUnCompte = utils.yesNoQuestion(scanner, "Avez vous un compte ?");
        if (aUnCompte) {
            return loginCompte();
        } else {
            return creerCompte();
        }

    }

    private Long loginCompte() throws UtilisateurInconnu {
        System.out.println("Veuillez saisir votre login");
        String login = utils.saisirChaine(scanner, "Login: ");
        System.out.println("Veuillez saisir votre mot de passe ");
        String mdp = utils.saisirChaine(scanner, "Mot de passe: ");
        Long usager = verifierUsager(login, mdp);
        return usager;
    }

    private Long creerCompte() throws UtilisateurExistant {
        System.out.println("veuillez saisir votre nom");
        String nom = utils.saisirChaine(scanner, "Nom: ");
        System.out.println("veuillez saisir votre prenom");
        String prenom = utils.saisirChaine(scanner, "Prenom: ");
        System.out.println("veuillez saisir votre login");
        String login = utils.saisirChaine(scanner, "Login: ");
        System.out.println("veuillez saisir votre mot de passe");
        String mdp = utils.saisirChaine(scanner, "Mot de passe: ");
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
        afficherListeStations(stationsArrivee);
        Long idStationArrivee = utils.saisirEntier(scanner, "Station d'arrivée: ", getIDsStations(stationsArrivee));
        Long nbPassagers = utils.saisirEntier(scanner, "Nombre de passagers: ", new Long(0), Long.MAX_VALUE);
        VoyageExport voyage = this.serviceUsager.reserverVoyage(usager, idStationDepart, idStationArrivee, (int) (long) nbPassagers);
        System.out.println("Réservation réussie. Rendez vous au quai " + voyage.getQuaiDepart());
    }

    private void arrivee(Long usager) throws UtilisateurInconnu, VoyageInconnu {
        VoyageExport voyageEncours = this.serviceUsager.voyageEnCours(usager);
        if (utils.yesNoQuestion(scanner, "Finaliser le voyage en cours? ")) {
            this.serviceUsager.finaliserVoyage(voyageEncours.getId());
        }
    }
}
