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
public class ReservationCloturee extends Exception {

    /**
     * Creates a new instance of <code>ReservationCloturee</code> without detail
     * message.
     */
    public ReservationCloturee() {
    }

    /**
     * Constructs an instance of <code>ReservationCloturee</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ReservationCloturee(String msg) {
        super(msg);
    }
}
