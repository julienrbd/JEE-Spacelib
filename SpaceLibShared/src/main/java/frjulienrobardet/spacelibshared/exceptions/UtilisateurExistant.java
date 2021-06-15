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
public class UtilisateurExistant extends Exception {

    /**
     * Creates a new instance of <code>UtilisateurExistant</code> without detail
     * message.
     */
    public UtilisateurExistant() {
    }

    /**
     * Constructs an instance of <code>UtilisateurExistant</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UtilisateurExistant(String msg) {
        super(msg);
    }
}
