/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.spacelibshared.export;

import java.io.Serializable;

/**
 *
 * @author JulienRobardet
 */
public class StationExport implements Serializable {
   
    private Long id;
    
    private String localisation;
    
    private int nbQuais;

    private String nom;

    public StationExport() {
    }

    public StationExport(String localisation, int nbQuais, String nom) {
        this.localisation = localisation;
        this.nbQuais = nbQuais;
        this.nom = nom;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLocalisation() {
        return localisation;
    }

    public void setLocalisation(String localisation) {
        this.localisation = localisation;
    }

    public int getNbQuais() {
        return nbQuais;
    }

    public void setNbQuais(int nbQuais) {
        this.nbQuais = nbQuais;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
    
}
