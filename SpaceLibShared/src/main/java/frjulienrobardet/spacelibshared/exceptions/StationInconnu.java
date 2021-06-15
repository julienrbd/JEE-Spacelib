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
public class StationInconnu extends Exception {

    /**
     * Creates a new instance of <code>StationInconnu</code> without detail
     * message.
     */
    public StationInconnu() {
    }

    /**
     * Constructs an instance of <code>StationInconnu</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public StationInconnu(String msg) {
        super(msg);
    }
}
