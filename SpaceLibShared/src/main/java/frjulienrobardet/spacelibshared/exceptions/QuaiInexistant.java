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
public class QuaiInexistant extends Exception {

    /**
     * Creates a new instance of <code>QuaiInexistant</code> without detail
     * message.
     */
    public QuaiInexistant() {
    }

    /**
     * Constructs an instance of <code>QuaiInexistant</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public QuaiInexistant(String msg) {
        super(msg);
    }
}
