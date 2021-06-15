/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.services;

import frjulienrobardet.facades.QuaiFacadeLocal;
import frjulienrobardet.facades.StationFacadeLocal;
import frjulienrobardet.metier.GestionStationLocal;
import frjulienrobardet.metier.GestionUsagerLocal;
import frjulienrobardet.metier.GestionVoyageLocal;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
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
    

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
