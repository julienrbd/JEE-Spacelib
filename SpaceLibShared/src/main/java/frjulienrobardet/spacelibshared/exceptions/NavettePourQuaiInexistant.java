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
public class NavettePourQuaiInexistant extends Exception {

    /**
     * Creates a new instance of <code>NavettePourQuaiInexistant</code> without
     * detail message.
     */
    public NavettePourQuaiInexistant() {
    }

    /**
     * Constructs an instance of <code>NavettePourQuaiInexistant</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public NavettePourQuaiInexistant(String msg) {
        super(msg);
    }
}
