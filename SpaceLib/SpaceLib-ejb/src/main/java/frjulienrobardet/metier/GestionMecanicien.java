/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Mecanicien;
import frjulienrobardet.facades.MecanicienFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class GestionMecanicien implements GestionMecanicienLocal {

    @EJB
    private MecanicienFacadeLocal mecanicien;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public Long authentifier(String login, String password){
        Mecanicien m = mecanicien.authentifier(login, password);
        return m.getId();
    }
    public Long creerCompte(String nom, String prenom, String login, String password){
        Mecanicien m = new Mecanicien(nom, prenom, login, password);
        mecanicien.create(m);
        return m.getId();
    }
}
