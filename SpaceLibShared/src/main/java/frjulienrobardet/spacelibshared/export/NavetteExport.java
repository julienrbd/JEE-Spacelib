/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.spacelibshared.export;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Fitchay
 */
public class NavetteExport implements Serializable{
    private Long id;
    
    private QuaiExport quai;
    
    // 2, 5, 10 ou 15 places
    private int nbPlaces;
    
    private int nbVoyages;
   
    private List<VoyageExport> voyages;
 
    private List<TransfertExport> transferts;
    
    private List<RevisionExport> revisions;
    
    public NavetteExport(){
        
    }
    
    public NavetteExport(int nb, String s){
        this.nbPlaces = nb;
        this.nbVoyages = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNbPlaces() {
        return nbPlaces;
    }

    public void setNbPlaces(int nbPlaces) {
        this.nbPlaces = nbPlaces;
    }

    public int getNbVoyages() {
        return nbVoyages;
    }

    public void setNbVoyages(int nbVoyages) {
        this.nbVoyages = nbVoyages;
    }

    public QuaiExport getQuai() {
        return quai;
    }

    public void setQuai(QuaiExport quai) {
        this.quai = quai;
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
        if (!(object instanceof NavetteExport)) {
            return false;
        }
        NavetteExport other = (NavetteExport) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.miage.spacelib.entities.Navette[ id=" + id + " ]";
    }
}
