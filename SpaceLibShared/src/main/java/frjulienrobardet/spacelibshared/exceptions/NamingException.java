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
public class NamingException extends Exception {

    /**
     * Creates a new instance of <code>NamingException</code> without detail
     * message.
     */
    public NamingException() {
    }

    /**
     * Constructs an instance of <code>NamingException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public NamingException(String msg) {
        super(msg);
    }
}
