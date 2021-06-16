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
import javax.persistence.ManyToOne;

/**
 *
 * @author JulienRobardet
 */
@Entity
public class Revision extends Operation implements Serializable {

    public static final String statutRevisionNecessaire = "révision nécessaire";
    public static final String statutDebutRevision = "début de révision";
    public static final String statutFinRevision = "fin de révision";
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Mecanicien mecanicien;
    @ManyToOne
    private Quai quaiNavette;

    public Revision() {
    }

    public Revision(Quai quaiNavette, Navette navette, String statut) {
        super(navette, statut);
        this.quaiNavette = quaiNavette;
    }

    public Revision(Navette navette, String statut, Mecanicien mecanicien, Quai quaiNavette) {
        super(navette, statut);
        this.mecanicien = mecanicien;
        this.quaiNavette = quaiNavette;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Mecanicien getMecanicien() {
        return mecanicien;
    }

    public void setMecanicien(Mecanicien mecanicien) {
        this.mecanicien = mecanicien;
    }

    public Quai getQuaiNavette() {
        return quaiNavette;
    }

    public void setQuaiNavette(Quai quaiNavette) {
        this.quaiNavette = quaiNavette;
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
        if (!(object instanceof Revision)) {
            return false;
        }
        Revision other = (Revision) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "frjulienrobardet.entities.Revision[ id=" + id + " ]";
    }
    
}
