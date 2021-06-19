/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Transfert;
import frjulienrobardet.spacelibshared.exceptions.QuaiIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.TempsTrajetInconnu;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import frjulienrobardet.spacelibshared.exceptions.VoyageInconnu;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author JulienRobardet
 */
@Local
public interface GestionTransfertLocal {

    public Transfert reserverTransfert(Long idConducteur, Long idStationDepart, Long idStationArrivee) throws QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, UtilisateurInconnu, StationInconnu ;

    public ArrayList<Transfert> obtenirTransfertsConducteur(Long idConducteur) throws UtilisateurInconnu;

    public void finaliserTransfert(Long idTransfert)throws VoyageInconnu;

    public Transfert transfertEnCours(Long idConducteur) throws UtilisateurInconnu, VoyageInconnu;
    
}
