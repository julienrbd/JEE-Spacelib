/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.spacelibshared.services;

import frjulienrobardet.spacelibshared.exceptions.NombreNavetteInvalide;
import frjulienrobardet.spacelibshared.export.StationExport;
import java.util.ArrayList;
import java.util.Map;
import javax.ejb.Remote;

/**
 *
 * @author JulienRobardet
 */
@Remote
public interface ServicesAdminRemote {
    public Long creerStation (String localisation, String nom, Long nbquais, ArrayList<Integer> nbPlacesNavettes, Map<Long,Integer> tempsTrajets ) throws NombreNavetteInvalide;    
    public ArrayList<StationExport> obtenirStations();
    
}
