/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Station;
import frjulienrobardet.spacelibshared.exceptions.NombreNavetteInvalide;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.Local;

/**
 *
 * @author JulienRobardet
 */
@Local
public interface GestionStationLocal {
    public List<Station> recupererListeStations();
 
    public Long creerStation (String localisation, String nom, Long nbquais, ArrayList<Integer> nbPlacesNavettes, Map<Long,Integer> tempsTrajets ) throws NombreNavetteInvalide;   

}
