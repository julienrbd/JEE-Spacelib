/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.services;

import frjulienrobardet.entities.Station;
import frjulienrobardet.entities.Transfert;
import frjulienrobardet.facades.StationFacadeLocal;
import frjulienrobardet.metier.GestionConducteurLocal;
import frjulienrobardet.metier.GestionTransfertLocal;
import frjulienrobardet.spacelibshared.exceptions.QuaiIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.TempsTrajetInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import frjulienrobardet.spacelibshared.exceptions.VoyageInconnu;
import frjulienrobardet.spacelibshared.export.StationExport;
import frjulienrobardet.spacelibshared.export.StatsStationExport;
import frjulienrobardet.spacelibshared.export.TransfertExport;
import frjulienrobardet.spacelibshared.export.TransfertNecessaireExport;
import frjulienrobardet.spacelibshared.services.ServicesConducteurRemote;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.criteria.Predicate;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class ServicesConducteur implements ServicesConducteurRemote {

    @EJB
    private StationFacadeLocal stationFacade;

    @EJB
    private GestionConducteurLocal gestionConducteur;

    @EJB
    private GestionTransfertLocal gestionTransfert;
    
    @Override
    public Long login(String login, String motdepasse) throws UtilisateurInconnu {
        return this.gestionConducteur.login(login, motdepasse);
    }

    @Override
    public void creerCompte(String nom, String prenom, String login, String motdepasse) throws UtilisateurExistant {
        this.gestionConducteur.creerCompte(nom, prenom, login, motdepasse);
    }

    @Override
    public TransfertExport reserverTransfert(Long idConducteur, Long idStationDepart, Long idStationArrivee) throws QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, UtilisateurInconnu, StationInconnu {
        Transfert transfert = this.gestionTransfert.reserverTransfert(idConducteur, idStationDepart, idStationArrivee);
        TransfertExport transfertExport = new TransfertExport(transfert.getId(), transfert.getNbPassagers(), transfert.getNavette().getId(), transfert.getStatut(), transfert.getConducteur().getId(), transfert.getDateCreation(), transfert.getDateDepart(), transfert.getDateArrivee(), transfert.getQuaiDepart().getId(), transfert.getQuaiArrivee().getId());
        return transfertExport; 
    }

    @Override
    public ArrayList<TransfertExport> obtenirTransfertsConducteur(Long idConducteur) throws UtilisateurInconnu {
        ArrayList<Transfert> transferts = this.gestionTransfert.obtenirTransfertsConducteur(idConducteur);
        ArrayList<TransfertExport> transfertExports = new ArrayList<>();
        for (Transfert transfert : transferts) {
            TransfertExport transfertExport = new TransfertExport(transfert.getId(), transfert.getNbPassagers(), transfert.getNavette().getId(), transfert.getStatut(), transfert.getConducteur().getId(), transfert.getDateCreation(), transfert.getDateDepart(), transfert.getDateArrivee(), transfert.getQuaiDepart().getId(), transfert.getQuaiArrivee().getId());
            transfertExports.add(transfertExport);
        }
        return transfertExports;
    }

    @Override
    public List<TransfertNecessaireExport> obtenirTransfertsNecessaires() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<StatsStationExport> stats() {
        List<Station> stations = stationFacade.findAll();
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 10);
        List<StatsStationExport> res = new ArrayList<>();
        for (Station station : stations) {
            StatsStationExport stat = new StatsStationExport();
            stat.nbNavettesArrimees = this.stationFacade.nbNavettes(station.getId());
            stat.nbNavettesEntrantes10jours = this.stationFacade.nbNavetteEntrantes(station.getId(), cal);
            stat.nbNavettesSortantes10jours = this.stationFacade.nbNavetteSortantes(station.getId(), cal);
            stat.station = se(station);
            res.add(stat);
        }
        return res;
    }

    @Override
    public void finaliserVoyage(Long idTransfert) throws VoyageInconnu {
        this.gestionTransfert.finaliserTransfert(idTransfert);
    }

    @Override
    public TransfertExport transfertEnCours(Long idConducteur) throws UtilisateurInconnu, VoyageInconnu {
        Transfert transfert = this.gestionTransfert.transfertEnCours(idConducteur);
        TransfertExport transfertExport = new TransfertExport();        
        transfertExport.setDateArrivee(transfert.getDateArrivee());
        transfertExport.setDateCreation(transfert.getDateCreation());
        transfertExport.setDateDepart(transfert.getDateDepart());
        transfertExport.setId(transfert.getId());
        transfertExport.setNavette(transfert.getNavette().getId());
        transfertExport.setNbPassagers(transfert.getNbPassagers());
        transfertExport.setQuaiArrivee(transfert.getQuaiArrivee().getId());
        transfertExport.setQuaiDepart(transfert.getQuaiDepart().getId());
        transfertExport.setStatut(transfert.getStatut());
        transfertExport.setConducteur(transfert.getConducteur().getId());
        return transfertExport;
    }
    
    private StationExport se(Station station) {
        StationExport stationExport = new StationExport();
        stationExport.setId(station.getId());
        stationExport.setLocalisation(station.getLocalisation());
        stationExport.setNbQuais(station.getNbQuais());
        stationExport.setNom(station.getNom());
        return stationExport;
    }
    
}
