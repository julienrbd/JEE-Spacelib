/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Navette;
import frjulienrobardet.facades.NavetteFacadeLocal;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class GestionNavette implements GestionNavetteLocal {

    @EJB
    private NavetteFacadeLocal navetteFacade;
    
    @Override
    public List<Navette> consulterListeNavettes(long idStation) throws StationInconnu {
        return this.navetteFacade.findAll();
    }
}
