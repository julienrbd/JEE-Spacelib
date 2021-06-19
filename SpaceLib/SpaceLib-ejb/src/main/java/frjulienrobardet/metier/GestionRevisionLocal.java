/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Quai;
import frjulienrobardet.entities.Revision;
import frjulienrobardet.spacelibshared.exceptions.NavetteInconnu;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.RevisionInexistante;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JulienRobardet
 */
@Local
public interface GestionRevisionLocal {

    public Quai choisirNavetteDebutRevision(long idNavette, long idStation, long idMecanicien) throws UtilisateurInconnu, NavetteInconnu, QuaiInexistant;

    public Revision consulterRevisionEnCours(long idMecanicien, long idStation) throws QuaiInexistant;

    public void finirRevisionEnCours(long idNavette, long idStation, long idMecanicien) throws UtilisateurInconnu, QuaiInexistant;

    public List<Revision> recupererListeNavettesAReviser(long idStation) throws QuaiInexistant, RevisionInexistante;
    
}
