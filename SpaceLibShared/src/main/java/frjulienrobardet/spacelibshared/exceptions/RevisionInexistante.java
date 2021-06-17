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
public class RevisionInexistante extends Exception {

    /**
     * Creates a new instance of <code>RevisionInexistante</code> without detail
     * message.
     */
    public RevisionInexistante() {
    }

    /**
     * Constructs an instance of <code>RevisionInexistante</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public RevisionInexistante(String msg) {
        super(msg);
    }
}
