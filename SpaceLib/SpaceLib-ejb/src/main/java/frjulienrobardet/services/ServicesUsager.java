/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.services;

import frjulienrobardet.entities.Voyage;
import frjulienrobardet.spacelibshared.services.ServicesUsagerRemote;
import frjulienrobardet.facades.QuaiFacadeLocal;
import frjulienrobardet.facades.StationFacadeLocal;
import frjulienrobardet.metier.GestionStationLocal;
import frjulienrobardet.metier.GestionUsagerLocal;
import frjulienrobardet.metier.GestionVoyageLocal;
import frjulienrobardet.spacelibshared.exceptions.NavetteIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.TempsTrajetInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import frjulienrobardet.spacelibshared.export.VoyageExport;
import java.util.Calendar;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class ServicesUsager implements ServicesUsagerRemote {
    @EJB
    private GestionStationLocal gestionStation;

    @EJB
    private GestionVoyageLocal gestionVoyage;

    @EJB
    private GestionUsagerLocal gestionUsager;
    
    @EJB
    private QuaiFacadeLocal quaiFacade;

    @EJB
    private StationFacadeLocal stationFacade;
    
    @Override
    public Long login(String login, String motdepasse) throws UtilisateurInconnu {
        System.out.println("ServicesUsager login : " +login);
        System.out.println("ServicesUsager motdepasse : " +motdepasse);
        return this.gestionUsager.authentifier(login, motdepasse);
    }
    
    @Override
    public Long creerCompte(String nom, String prenom, String login, String motdepasse) throws UtilisateurExistant{
        return this.gestionUsager.creerCompte(nom, prenom, login, motdepasse);
    }

    @Override
    public VoyageExport reserverVoyage(Long idUsager, Long idStationDepart, Long idStationArrivee, int NbPassagers, Calendar dateDepart) throws QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, UtilisateurInconnu, StationInconnu, NavetteIndisponible{
        Voyage voyage = this.gestionVoyage.reserverVoyage(idUsager, idStationDepart, idStationArrivee, NbPassagers);
        System.out.println("Voyage = " + voyage);
        VoyageExport voyageExport = new VoyageExport();

        voyageExport.setDateArrivee(voyage.getDateArrivee());
        voyageExport.setDateCreation(voyage.getDateCreation());
        voyageExport.setDateDepart(voyage.getDateDepart());
        voyageExport.setId(voyage.getId());
        voyageExport.setNavette(voyage.getNavette().getId());
        voyageExport.setNbPassagers(voyage.getNbPassagers());
        voyageExport.setQuaiArrivee(voyage.getQuaiArrivee().getId());
        voyageExport.setQuaiDepart(voyage.getQuaiDepart().getId());
        voyageExport.setStatut(voyage.getStatut());
        voyageExport.setUsager(voyage.getUsager().getId());

        System.out.println("VoyageExport = " + voyageExport);
        return voyageExport;
    }
}
