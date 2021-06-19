/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Voyage;
import frjulienrobardet.spacelibshared.exceptions.NavetteIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiIndisponible;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.ReservationCloturee;
import frjulienrobardet.spacelibshared.exceptions.ReservationInconnu;
import frjulienrobardet.spacelibshared.exceptions.ReservationPassee;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import frjulienrobardet.spacelibshared.exceptions.TempsTrajetInconnu;
import java.util.Calendar;
import javax.ejb.Local;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import frjulienrobardet.spacelibshared.exceptions.VoyageInconnu;
import java.util.ArrayList;

/**
 *
 * @author JulienRobardet
 */
@Local
public interface GestionVoyageLocal {
    
    public Voyage reserverVoyage(Long idUsager, Long idStationDepart, Long idStationArrivee, int NbPassagers, Calendar dateDepart) throws QuaiInexistant, QuaiIndisponible, TempsTrajetInconnu, UtilisateurInconnu, StationInconnu, NavetteIndisponible;
    
    public void finaliserVoyage(Long idVoyage) throws VoyageInconnu;
    
    public Voyage voyageEnCours(Long idUsager) throws UtilisateurInconnu, VoyageInconnu ;

    public void annulerVoyage(Long idClient, Long idReservation) throws UtilisateurInconnu, ReservationInconnu, ReservationPassee, ReservationCloturee;
    
    public ArrayList<Voyage> obtenirVoyagesPrevusUsager(Long idUsager) throws UtilisateurInconnu;
    
}
