/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.spacelibshared.export;

import java.io.Serializable;
import java.util.Calendar;

/**
 *
 * @author JulienRobardet
 */
public class VoyageExport implements Serializable {
    
    public static final String statutDebutVoyage = "voyage initié";
    public static final String statutFinVoyage = "voyage achevé";
    
    private Long usager;
    
    private int nbPassagers;
    
    private Long quaiDepart;
    
    private Long quaiArrivee;
    
    private Calendar dateDepart;
    
    private Calendar dateArrivee;
    
    private Long id;
    
    private Long navette;
    
    private Calendar dateCreation;

    private String statut;

    public VoyageExport() {
    }
    
    

    public VoyageExport(Long usager, int nbPassagers, Long quaiDepart, Long quaiArrivee, Calendar dateDepart, Calendar dateArrivee, Long id, Long navette, Calendar dateCreation, String statut) {
        this.usager = usager;
        this.nbPassagers = nbPassagers;
        this.quaiDepart = quaiDepart;
        this.quaiArrivee = quaiArrivee;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
        this.id = id;
        this.navette = navette;
        this.dateCreation = dateCreation;
        this.statut = statut;
    }

    public Long getUsager() {
        return usager;
    }

    public void setUsager(Long usager) {
        this.usager = usager;
    }

    public int getNbPassagers() {
        return nbPassagers;
    }

    public void setNbPassagers(int nbPassagers) {
        this.nbPassagers = nbPassagers;
    }

    public Long getQuaiDepart() {
        return quaiDepart;
    }

    public void setQuaiDepart(Long quaiDepart) {
        this.quaiDepart = quaiDepart;
    }

    public Long getQuaiArrivee() {
        return quaiArrivee;
    }

    public void setQuaiArrivee(Long quaiArrivee) {
        this.quaiArrivee = quaiArrivee;
    }

    public Calendar getDateDepart() {
        return dateDepart;
    }

    public void setDateDepart(Calendar dateDepart) {
        this.dateDepart = dateDepart;
    }

    public Calendar getDateArrivee() {
        return dateArrivee;
    }

    public void setDateArrivee(Calendar dateArrivee) {
        this.dateArrivee = dateArrivee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNavette() {
        return navette;
    }

    public void setNavette(Long navette) {
        this.navette = navette;
    }

    public Calendar getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Calendar dateCreation) {
        this.dateCreation = dateCreation;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    
    
    
    
    
    
}
