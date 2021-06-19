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
public class TransfertNecessaireExport implements Serializable{
    private Long stationDepart;

    private Long stationArrivee;
    
    private String nomStationDepart;

    private String nomStationArrivee;

    public TransfertNecessaireExport() {
    }

    public TransfertNecessaireExport(Long stationDepart, Long stationArrivee, String nomStationDepart, String nomStationArrivee) {
        this.stationDepart = stationDepart;
        this.stationArrivee = stationArrivee;
        this.nomStationDepart = nomStationDepart;
        this.nomStationArrivee = nomStationArrivee;
    }

    public Long getStationDepart() {
        return stationDepart;
    }

    public void setStationDepart(Long stationDepart) {
        this.stationDepart = stationDepart;
    }

    public Long getStationArrivee() {
        return stationArrivee;
    }

    public void setStationArrivee(Long stationArrivee) {
        this.stationArrivee = stationArrivee;
    }

    public String getNomStationDepart() {
        return nomStationDepart;
    }

    public void setNomStationDepart(String nomStationDepart) {
        this.nomStationDepart = nomStationDepart;
    }

    public String getNomStationArrivee() {
        return nomStationArrivee;
    }

    public void setNomStationArrivee(String nomStationArrivee) {
        this.nomStationArrivee = nomStationArrivee;
    }
    
}
