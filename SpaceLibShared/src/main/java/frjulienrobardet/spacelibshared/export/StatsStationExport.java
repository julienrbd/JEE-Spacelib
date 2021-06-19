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
public class StatsStationExport implements Serializable{
    
    public StationExport station;
    public int nbNavettesArrimees;
    public int nbNavettesSortantes10jours;
    public int nbNavettesEntrantes10jours;
}
