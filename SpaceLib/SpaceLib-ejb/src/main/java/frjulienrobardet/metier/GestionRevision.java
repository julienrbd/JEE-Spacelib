/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Mecanicien;
import frjulienrobardet.entities.Navette;
import frjulienrobardet.entities.Quai;
import frjulienrobardet.entities.Revision;
import frjulienrobardet.entities.Station;
import frjulienrobardet.facades.MecanicienFacadeLocal;
import frjulienrobardet.facades.NavetteFacadeLocal;
import frjulienrobardet.facades.QuaiFacadeLocal;
import frjulienrobardet.facades.RevisionFacadeLocal;
import frjulienrobardet.facades.StationFacadeLocal;
import frjulienrobardet.spacelibshared.exceptions.NavetteInconnu;
import frjulienrobardet.spacelibshared.exceptions.QuaiInexistant;
import frjulienrobardet.spacelibshared.exceptions.RevisionInexistante;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class GestionRevision implements GestionRevisionLocal {

    @EJB
    private StationFacadeLocal stationFacade;

    @EJB
    private RevisionFacadeLocal revisionFacade;

    @EJB
    private QuaiFacadeLocal quaiFacade;

    @EJB
    private NavetteFacadeLocal navetteFacade;

    @EJB
    private MecanicienFacadeLocal mecanicienFacade;

    @Override
    public Quai choisirNavetteDebutRevision(long idNavette, long idStation, long idMecanicien) throws UtilisateurInconnu, NavetteInconnu, QuaiInexistant{
        final Mecanicien mecanicien = this.mecanicienFacade.find(idMecanicien);
        if (mecanicien == null) {
            throw new UtilisateurInconnu("Ce mécanicien est inconnu du système.");
        }

        final Navette navette = this.navetteFacade.find(idNavette);
        if (navette == null) {
            throw new NavetteInconnu("Cette navette est inconnue du système.");
        }

        final Quai quai = this.quaiFacade.findByNavette(navette);
        if (quai == null) {
            throw new QuaiInexistant("Ce quai est inconnu du système.");
        }

        this.revisionFacade.nouveauDebutRevision(mecanicien, navette, quai);

        return quai;
    
    }

    @Override
    public Revision consulterRevisionEnCours(long idMecanicien, long idStation) throws QuaiInexistant {
        List<Quai> quais = new ArrayList<>();
        Revision revisionResultat = null;

        Station station = this.stationFacade.find(idStation);
        quais = this.quaiFacade.getListeQuaisStation(station);

        Mecanicien mecanicien = this.mecanicienFacade.find(idMecanicien);

        if (quais.size() > 0) {
            for (Quai q : quais) {
                // on vérifie que le quai possède une navette arrimée
                if (q.getNavette() != null) {
                    Revision revision = this.revisionFacade.recupererDerniereRevisionMecanicienQuai(q, mecanicien);
                    if (revision != null) {
                        if (new String(revision.getStatut()).equals(Revision.statutDebutRevision)) {
                            // La navette doit possèder 0 voyages
                            if (revision.getNavette().getNbVoyages() == 0) {
                                // alors c'est bien la révision en cours de réalisation par le mécanicien
                                revisionResultat = revision;
                            }
                        }
                    }
                }
            }
        } else {
            throw new QuaiInexistant("Il n'y a pas de quais dans cette station.");
        }
        if (revisionResultat != null) {
            return revisionResultat;
        } else {
            return null;
        }
    }

    @Override
    public void finirRevisionEnCours(long idNavette, long idStation, long idMecanicien) throws UtilisateurInconnu, QuaiInexistant{
        final Mecanicien mecanicien = this.mecanicienFacade.find(idMecanicien);
        if (mecanicien == null) {
            throw new UtilisateurInconnu("Ce mécanicien est inconnu du système.");
        }

        final Navette navette = this.navetteFacade.find(idNavette);
        if (navette == null) {
            throw new UtilisateurInconnu("Cette navette est inconnue du système.");
        } else {
            navette.setNbVoyages(3);
        }

        final Quai quai = this.quaiFacade.findByNavette(navette);
        if (quai == null) {
            throw new QuaiInexistant("Ce quai est inconnu du système.");
        }

        this.revisionFacade.nouveauFinRevision(mecanicien, navette, quai);
    }

    @Override
    public List<Revision> recupererListeNavettesAReviser(long idStation) throws QuaiInexistant, RevisionInexistante{
        List<Quai> quais = new ArrayList<>();
        List<Revision> revisions = new ArrayList<Revision>();

        Station station = this.stationFacade.find(idStation);

        // on récupère les quai de la station 
        quais = this.quaiFacade.getListeQuaisStation(station);

        if (quais.size() > 0) {
            for (Quai q : quais) {
                // on vérifie que le quai possède une navette en révision 
                if (q.getNavette() != null) {
                    // on récupère toutes les révisions avec le statut 'révision nécessaires' du quai
                    Revision derniereRevisionDuQuai = this.revisionFacade.recupererDerniereRevisionQuai(q);

                    if (derniereRevisionDuQuai != null) {
                        // si la dernière opération de révision est une révision nécessaire : 
                        if (new String(derniereRevisionDuQuai.getStatut()).equals(Revision.statutRevisionNecessaire)) {
                            // et si la navette possède bien 0 voyage restant
                            if (derniereRevisionDuQuai.getNavette().getNbVoyages() == 0) {
                                // alors c'est bien une révision nécessaire !
                                revisions.add(derniereRevisionDuQuai);
                            }
                        }
                    }

                }
            }
        } else {
            // Pas de quais dans cette station !
            throw new QuaiInexistant("Il n'y a pas de quais dans cette station.");
        }
        if (revisions.size() > 0) {
            return revisions;
        } else {
            throw new RevisionInexistante("Il n'y a pas de révisions nécessaires pour cette station.");
        }
    }
}
