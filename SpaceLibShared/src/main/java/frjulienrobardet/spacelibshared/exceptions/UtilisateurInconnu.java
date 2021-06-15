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
public class UtilisateurInconnu extends Exception {

    /**
     * Creates a new instance of <code>UtilisateurInconnu</code> without detail
     * message.
     */
    public UtilisateurInconnu() {
    }

    /**
     * Constructs an instance of <code>UtilisateurInconnu</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UtilisateurInconnu(String msg) {
        super(msg);
    }
}
