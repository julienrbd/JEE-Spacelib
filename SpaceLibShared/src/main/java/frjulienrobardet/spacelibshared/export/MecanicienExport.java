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
class MecanicienExport extends UtilisateurExport implements Serializable {
        private List<RevisionExport> revisions;

    public MecanicienExport(){
        
    }
    
    public MecanicienExport(String n, String p, String l, String m){
        super(n, p, l, m);
    }

    public List<RevisionExport> getRevisions() {
        return revisions;
    }

    public void setRevisions(List<RevisionExport> revisions) {
        this.revisions = revisions;
    }
}
