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
 * @author Fitchay
 */
public abstract class  OperationExport implements Serializable{
    private Long id;
    
    private NavetteExport  navette;
    
    private Calendar dateCreation;

    private String statut;
    
    public OperationExport (){
        
    }
    
    public OperationExport (NavetteExport  n, String s){
        this.navette = n;
        this.dateCreation = Calendar.getInstance();
        this.statut = s;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public NavetteExport  getNavette() {
        return navette;
    }

    public void setNavette(NavetteExport  navette) {
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OperationExport )) {
            return false;
        }
        OperationExport  other = (OperationExport ) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.miage.spacelib.entities.Operation[ id=" + id + " ]";
    }
}
