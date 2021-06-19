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
public class NavetteInconnu extends Exception {

    /**
     * Creates a new instance of <code>NavetteInconnu</code> without detail
     * message.
     */
    public NavetteInconnu() {
    }

    /**
     * Constructs an instance of <code>NavetteInconnu</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NavetteInconnu(String msg) {
        super(msg);
    }
}
