/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioRestaurant;

import java.awt.Color;

/**
 *
 * @author Usuari
 */
public class Categoria {
    int codi;
    String nom;
    Color color;

    public Categoria(int codi, String nom, Color color) {
        this.codi = codi;
        this.nom = nom;
        this.color = color;
    }

    public Categoria() {
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
    
    
}
