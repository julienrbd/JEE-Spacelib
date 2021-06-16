/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author JulienRobardet
 */
@Entity
public class TempsTrajet implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToOne
    private Station stationDepart;
    @OneToOne
    private Station stationArrivee;
    private int temps;

    public TempsTrajet() {
    }

    public TempsTrajet(Station stationDepart, Station stationArrivee, Integer value) {
        this.stationDepart = stationDepart;
        this.stationArrivee = stationArrivee;
        this.temps = value;
        
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Station getStationDepart() {
        return stationDepart;
    }

    public void setStationDepart(Station stationDepart) {
        this.stationDepart = stationDepart;
    }

    public Station getStationArrivee() {
        return stationArrivee;
    }

    public void setStationArrivée(Station stationArrivee) {
        this.stationArrivee = stationArrivee;
    }

    public int getTemps() {
        return temps;
    }

    public void setTemps(int temps) {
        this.temps = temps;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TempsTrajet)) {
            return false;
        }
        TempsTrajet other = (TempsTrajet) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "frjulienrobardet.entities.TempsTrajet[ id=" + id + " ]";
    }
    
}
