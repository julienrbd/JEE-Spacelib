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
class QuaiExport implements Serializable{
        private Long id;
    private StationExport station;
    private NavetteExport navette;

    
    QuaiExport(StationExport station){
        this.station = station;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
}
