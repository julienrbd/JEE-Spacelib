/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.spacelibadmincl;

import frjulienrobardet.spacelibshared.exceptions.NombreNavetteInvalide;
import frjulienrobardet.spacelibshared.export.StationExport;
import frjulienrobardet.spacelibshared.services.ServicesAdminRemote;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 *
 * @author JulienRobardet
 */
public class CLIAdmin {
    private final ServicesAdminRemote servicesAdmin;
    private final Scanner scanner = new Scanner(System.in);
    private final CLIUtils utils = new CLIUtils();

    public CLIAdmin(ServicesAdminRemote serviceAdmibn) {
        this.servicesAdmin = serviceAdmibn;
    }

    public void run() throws NombreNavetteInvalide {
        System.out.println("Création de station");
        List<StationExport> stations = this.servicesAdmin.obtenirStations();
        System.out.println("Veuillez saisir le nom de la station : ");
        String nomStation = utils.saisirChaine(scanner);
        System.out.println("Veuillez saisir la localisation de la station");
        String localisation = utils.saisirChaine(scanner);
        System.out.println("Veuillez saisir le nombre de quais de la station");        
        Long nb_quais = utils.saisirEntier(scanner, "", new Long(0), Long.MAX_VALUE);
        System.out.println("Veuillez saisir le nombre de navette de la station");        
        Long nb_navettes = utils.saisirEntier(scanner, "", new Long(0), Long.MAX_VALUE);
        ArrayList<Long> capacites_possibles = new ArrayList<>();
        capacites_possibles.add(new Long(2));
        capacites_possibles.add(new Long(5));
        capacites_possibles.add(new Long(10));
        capacites_possibles.add(new Long(15));
        ArrayList<Integer> capacites = new ArrayList<>();

        for (int i = 0; i < nb_navettes; i++) {
            int c = i+1;
            System.out.println("Veuillez saisir la capacité de la navette numéro " + c);
            capacites.add((Integer) (int) (long) utils.saisirEntier(scanner, "", capacites_possibles));
        }
        Map<Long,Integer> tempsTrajets = new HashMap<>();
        stations.forEach((station) -> {
            System.out.println("Temps du trajet vers " + station.getNom() + ": ");
            tempsTrajets.put(station.getId(), (Integer) (int) (long) utils.saisirEntier(scanner, "", new Long (0),Long.MAX_VALUE));
        });
        
        
        this.servicesAdmin.creerStation(localisation,nomStation, nb_quais, capacites,tempsTrajets);
        System.out.println("Succès.");
    }

    
    
}