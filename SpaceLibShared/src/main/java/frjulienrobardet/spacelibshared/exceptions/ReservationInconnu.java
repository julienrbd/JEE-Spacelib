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
public class ReservationInconnu extends Exception {

    /**
     * Creates a new instance of <code>ReservationInconnu</code> without detail
     * message.
     */
    public ReservationInconnu() {
    }

    /**
     * Constructs an instance of <code>ReservationInconnu</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ReservationInconnu(String msg) {
        super(msg);
    }
}
