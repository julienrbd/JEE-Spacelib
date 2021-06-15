/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Navette;
import frjulienrobardet.spacelibshared.exceptions.StationInconnu;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JulienRobardet
 */
@Local
public interface GestionNavetteLocal {
    List<Navette> consulterListeNavettes(long idStation) throws StationInconnu;
}
