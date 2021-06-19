/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.services;

import frjulienrobardet.entities.Station;
import frjulienrobardet.spacelibshared.services.ServicesAdminRemote;
import frjulienrobardet.facades.StationFacadeLocal;
import frjulienrobardet.metier.GestionStationLocal;
import frjulienrobardet.spacelibshared.exceptions.NombreNavetteInvalide;
import frjulienrobardet.spacelibshared.export.StationExport;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class ServicesAdmin implements ServicesAdminRemote {

    @EJB
    private GestionStationLocal gestionStation;

    @Override
    public Long creerStation(String localisation, String nom, Long nbquais, ArrayList<Integer> nbPlacesNavettes, Map<Long, Integer> tempsTrajets) throws NombreNavetteInvalide {
        return this.gestionStation.creerStation(localisation, nom, nbquais, nbPlacesNavettes, tempsTrajets);
    }

    @Override
    public ArrayList<StationExport> obtenirStations() {
        List<Station> stations = this.gestionStation.recupererListeStations();
        ArrayList<StationExport> resultList = new ArrayList<>();
        for (Station station : stations) {
            resultList.add(se(station));
        }
        return resultList;
    }
    
    private StationExport se(Station station) {
        StationExport stationExport = new StationExport();
        stationExport.setId(station.getId());
        stationExport.setLocalisation(station.getLocalisation());
        stationExport.setNbQuais(station.getNbQuais());
        stationExport.setNom(station.getNom());
        return stationExport;
    }
}
