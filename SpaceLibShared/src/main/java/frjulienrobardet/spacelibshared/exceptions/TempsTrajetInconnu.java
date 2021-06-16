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
public class TempsTrajetInconnu extends Exception {

    /**
     * Creates a new instance of <code>TempsTrajetInconnu</code> without detail
     * message.
     */
    public TempsTrajetInconnu() {
    }

    /**
     * Constructs an instance of <code>TempsTrajetInconnu</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public TempsTrajetInconnu(String msg) {
        super(msg);
    }
}
