/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.spacelibshared.services;

import frjulienrobardet.spacelibshared.exceptions.QuaiIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.TempsTrajetInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import frjulienrobardet.spacelibshared.exceptions.VoyageInconnu;
import frjulienrobardet.spacelibshared.export.StatsStationExport;
import frjulienrobardet.spacelibshared.export.TransfertExport;
import frjulienrobardet.spacelibshared.export.TransfertNecessaireExport;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author JulienRobardet
 */
@Remote
public interface ServicesConducteurRemote {
    
    public Long login(String login, String motdepasse) throws UtilisateurInconnu;
    
    public void creerCompte(String nom, String prenom, String login, String motdepasse) throws UtilisateurExistant;
    
    public TransfertExport reserverTransfert(Long idConducteur, Long idStationDepart, Long idStationArrivee) throws QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, UtilisateurInconnu, StationInconnu;
    
    public ArrayList<TransfertExport> obtenirTransfertsConducteur(Long idConducteur) throws UtilisateurInconnu;
    
    List<TransfertNecessaireExport> obtenirTransfertsNecessaires();
    
    List<StatsStationExport> stats();
    
    public void finaliserVoyage(Long idTransfert) throws VoyageInconnu;
    
    public TransfertExport transfertEnCours(Long idConducteur) throws  UtilisateurInconnu, VoyageInconnu;

}
