/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.facades;

import frjulienrobardet.entities.Station;
import java.util.Calendar;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author JulienRobardet
 */
@Local
public interface StationFacadeLocal {

    void create(Station station);

    void edit(Station station);

    void remove(Station station);

    Station find(Object id);

    List<Station> findAll();

    List<Station> findRange(int[] range);

    int count();
    
    /*public int nbNavetteSortantes(Long idStation, Calendar date_sup);

    public int nbNavetteEntrantes(Long idStation, Calendar date_sup);

    */
    public int nbNavettes(Long idStation);

    public int nbQuais(Long idStation);

    public int nbNavetteEntrantes(Long id, Calendar cal);

    public int nbNavetteSortantes(Long id, Calendar cal);    
}
