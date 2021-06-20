/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.spacelibshared.export;

import java.io.Serializable;

/**
 *
 * @author Fitchay
 */
public class RevisionExport extends OperationExport implements Serializable{
    public static final String statutRevisionNecessaire = "révision nécessaire";
    public static final String statutDebutRevision = "début de révision";
    public static final String statutFinRevision = "fin de révision";
    
    private QuaiExport quaiNavette;
    
    private MecanicienExport mecanicien;
    
    public RevisionExport(){
        
    }
    
    public RevisionExport(NavetteExport n, String s, QuaiExport q){
        super(n, s);
        this.quaiNavette = q;
    }
    
    public RevisionExport(NavetteExport n, String s, MecanicienExport m, QuaiExport q){
        this(n, s, q);
        this.mecanicien = m;
    }  

    public QuaiExport getQuai() {
        return quaiNavette;
    }

    public void setQuai(QuaiExport quaiNavette) {
        this.quaiNavette = quaiNavette;
    }
}
