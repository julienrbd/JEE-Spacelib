/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package frjulienrobardet.metier;

import frjulienrobardet.entities.Usager;
import frjulienrobardet.facades.UsagerFacadeLocal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author JulienRobardet
 */
@Stateless
public class GestionUsager implements GestionUsagerLocal {
    
    @EJB
    private UsagerFacadeLocal usager;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public Long authentifier(String login, String password){
        Usager u = usager.authentifier(login, password);
        return u.getId();
    }
    public Long creerCompte(String nom, String prenom, String login, String password){
        Usager u = new Usager(nom, prenom, login, password);
        usager.create(u);
        return u.getId();
    }
}
