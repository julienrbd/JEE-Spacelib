/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Navette;
import frjulienrobardet.entities.Quai;
import frjulienrobardet.entities.Station;
import frjulienrobardet.entities.TempsTrajet;
import frjulienrobardet.facades.NavetteFacadeLocal;
import frjulienrobardet.facades.QuaiFacadeLocal;
import frjulienrobardet.facades.StationFacadeLocal;
import frjulienrobardet.facades.TempsTrajetFacadeLocal;
import frjulienrobardet.spacelibshared.exceptions.NombreNavetteInvalide;
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
public class GestionStation implements GestionStationLocal {

    @EJB
    private TempsTrajetFacadeLocal tempsTrajetFacade;

    @EJB
    private NavetteFacadeLocal navetteFacade;

    @EJB
    private QuaiFacadeLocal quaiFacade;

    @EJB
    private StationFacadeLocal stationFacade;

    
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<Station> recupererListeStations() {
        return this.stationFacade.findAll();
    }

    @Override
    public Long creerStation(String localisation, String nom, Long nb_quais, ArrayList<Integer> nbPlacesNavettes, Map<Long, Integer> tempsTrajets) throws NombreNavetteInvalide {
        int nb_navettes = nbPlacesNavettes.size();
        if (nb_quais < nb_navettes || nb_navettes * 2 + 1 < nb_quais) {
            throw new NombreNavetteInvalide("Nombre de quais / navettes invalide.");
        }
        Station station = new Station(localisation, (int) (long) nb_quais, nom);
        
        List<Navette> navettes = new ArrayList<>();
        for (Integer places : nbPlacesNavettes) {
            Navette navette = new Navette(places);
            navette.setNbVoyages(3);
            this.navetteFacade.create(navette);
            navettes.add(navette);
        }
        List<Quai> quais = new ArrayList<>();
        
        for (int i = 0; i < nb_quais; i++) {
            Quai quai = new Quai(station);
            quais.add(quai);
        }
        for (Navette navette : navettes){
            for (Quai quai: quais){
                    if (navette.getQuai() == null && quai.getNavette() == null){
                        quai.setNavette(navette);
                        navette.setQuai(quai);
                    }else {
                        
                    }
                }
                
        }
        for (Quai quai: quais){
            this.quaiFacade.create(quai);
        }
        station.setQuais(quais);
        this.stationFacade.create(station);

        for (Map.Entry<Long, Integer> entry : tempsTrajets.entrySet()) {
            Station station_arrivee = this.stationFacade.find(entry.getKey());
            TempsTrajet temps = new TempsTrajet(station, station_arrivee, entry.getValue());
            tempsTrajetFacade.create(temps);
            TempsTrajet temps_sym = new TempsTrajet(station_arrivee, station, entry.getValue());
            tempsTrajetFacade.create(temps_sym);
        }
        return station.getId();
    }
}
