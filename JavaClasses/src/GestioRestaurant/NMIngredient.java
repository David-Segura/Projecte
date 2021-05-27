/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioRestaurant;

import java.io.Serializable;

/**
 *
 * @author Usuari
 */
public class NMIngredient implements Serializable{
    int codi;
    String nom;

    public NMIngredient(int codi, String nom) {
        this.codi = codi;
        this.nom = nom;
    }

    public NMIngredient() {
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
    
    
}


