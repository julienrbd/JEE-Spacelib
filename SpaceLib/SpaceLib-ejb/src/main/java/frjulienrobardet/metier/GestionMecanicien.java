/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Mecanicien;
import frjulienrobardet.facades.MecanicienFacadeLocal;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurExistant;
import frjulienrobardet.spacelibshared.exceptions.UtilisateurInconnu;
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
    public Long authentifier(String login, String password)throws UtilisateurInconnu{
        Mecanicien m = mecanicien.authentifier(login, password);
        if (m == null) {
            throw new UtilisateurInconnu("Ce compte de mécanicien n'existe pas");
        }
        return m.getId();
    }
    public Long creerCompte(String nom, String prenom, String login, String password) throws UtilisateurExistant{
        Mecanicien m = new Mecanicien(nom, prenom, login, password);
        if (m == null) {
            throw new UtilisateurExistant("Ce compte de mécanicien existe deja");
        }
        mecanicien.create(m);
        return m.getId();
    }
}
