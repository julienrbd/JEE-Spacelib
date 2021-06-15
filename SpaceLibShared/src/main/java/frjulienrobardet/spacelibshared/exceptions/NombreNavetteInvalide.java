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
public class NombreNavetteInvalide extends Exception {

    /**
     * Creates a new instance of <code>NombreNavetteInvalide</code> without
     * detail message.
     */
    public NombreNavetteInvalide() {
    }

    /**
     * Constructs an instance of <code>NombreNavetteInvalide</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NombreNavetteInvalide(String msg) {
        super(msg);
    }
}
