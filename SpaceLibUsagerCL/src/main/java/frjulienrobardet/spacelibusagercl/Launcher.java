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
import frjulienrobardet.spacelibshared.services.ServicesAdminRemote;
import frjulienrobardet.spacelibshared.services.ServicesUsagerRemote;
import java.lang.reflect.InvocationTargetException;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author JulienRobardet
 */
public class Launcher {
    

    private static void erreur(String msg) {
        System.out.println("Erreur: " + msg);
    }

    private static void erreur(Exception ex) {
        erreur(ex.getMessage());
    }

    public static void main(String[] args){
        try {
            try {
                Context ctx = new InitialContext();
                ServicesUsagerRemote serviceUsager = (ServicesUsagerRemote) ctx.lookup("frjulienrobardet.spacelibshared.services.ServicesUsagerRemote");
                (new CLIBorne(serviceUsager)).run();
            } catch (NavetteIndisponible | UtilisateurExistant | VoyageInconnu | IllegalAccessException | InvocationTargetException | UtilisateurInconnu | QuaiInexistant | QuaiIndisponible | TempsTrajetInconnu | StationInconnu ex) {
                erreur(ex);

            }

        } catch (NamingException ex) {
            System.err.println("Erreur d'initialisation RMI : " + ex.getMessage());
            System.err.println(ex.getExplanation());
        }
    }
}
