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
public class VoyageInconnu extends Exception {

    /**
     * Creates a new instance of <code>VoyageInconnu</code> without detail
     * message.
     */
    public VoyageInconnu() {
    }

    /**
     * Constructs an instance of <code>VoyageInconnu</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public VoyageInconnu(String msg) {
        super(msg);
    }
}
