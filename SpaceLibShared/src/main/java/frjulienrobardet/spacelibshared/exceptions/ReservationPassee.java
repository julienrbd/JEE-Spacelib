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
public class ReservationPassee extends Exception {

    /**
     * Creates a new instance of <code>ReservationPassee</code> without detail
     * message.
     */
    public ReservationPassee() {
    }

    /**
     * Constructs an instance of <code>ReservationPassee</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ReservationPassee(String msg) {
        super(msg);
    }
}
