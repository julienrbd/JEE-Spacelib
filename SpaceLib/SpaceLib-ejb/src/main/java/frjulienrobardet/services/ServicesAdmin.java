/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.services;

import frjulienrobardet.facades.StationFacadeLocal;
import frjulienrobardet.metier.GestionStationLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class ServicesAdmin implements ServicesAdminRemote {

    @EJB
    private StationFacadeLocal stationFacade;

    @EJB
    private GestionStationLocal gestionStation;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
