/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.spacelibshared.exceptions;

/**
 *
 * @author JulienRobardet
 */
public class NavetteIndisponible extends Exception {

    /**
     * Creates a new instance of <code>NavetteIndsponible</code> without detail
     * message.
     */
    public NavetteIndisponible() {
    }

    /**
     * Constructs an instance of <code>NavetteIndsponible</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NavetteIndisponible(String msg) {
        super(msg);
    }
}
