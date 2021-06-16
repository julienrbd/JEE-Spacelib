/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.entities;

/**
 *
 * @author JulienRobardet
 */
import java.util.Calendar;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@MappedSuperclass
public abstract class Reservation extends Operation {

    private int nbPassagers;
    @ManyToOne
    private Quai quaiDepart;
    @ManyToOne
    private Quai quaiArrivee;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateDepart;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Calendar dateArrivee;

    public Reservation(){
        
    }

    public Reservation(int nbPassagers, Quai quaiDepart, Quai quaiArrivee, Calendar dateDepart, Calendar dateArrivee, Navette navette, String statut) {
        super(navette, statut);
        this.nbPassagers = nbPassagers;
        this.quaiDepart = quaiDepart;
        this.quaiArrivee = quaiArrivee;
        this.dateDepart = dateDepart;
        this.dateArrivee = dateArrivee;
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
    
    public int getNbPassagers() {
        return nbPassagers;
    }

    public void setNbPassagers(int nbPassagers) {
        this.nbPassagers = nbPassagers;
    }

    public Quai getQuaiDepart() {
        return quaiDepart;
    }

    public void setQuaiDepart(Quai quaiDepart) {
        this.quaiDepart = quaiDepart;
    }

    public Quai getQuaiArrivee() {
        return quaiArrivee;
    }

    public void setQuaiArrivee(Quai quaiArrivee) {
        this.quaiArrivee = quaiArrivee;
    }
    
}
