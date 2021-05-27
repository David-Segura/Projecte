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
public class NMLinea_Escandall implements Serializable{
    int plat;
    int num;
    int quantitat;
    NMUnitat unitat;
    NMIngredient ingredient;

    public NMLinea_Escandall(int plat, int num, int quantitat, NMUnitat unitat, NMIngredient ingredient) {
        this.plat = plat;
        this.num = num;
        this.quantitat = quantitat;
        this.unitat = unitat;
        this.ingredient = ingredient;
    }

    public NMLinea_Escandall() {
    }

    public int getPlat() {
        return plat;
    }

    public void setPlat(int plat) {
        this.plat = plat;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public NMUnitat getUnitat() {
        return unitat;
    }

    public void setUnitat(NMUnitat unitat) {
        this.unitat = unitat;
    }

    public NMIngredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(NMIngredient ingredient) {
        this.ingredient = ingredient;
    }
    
    
}
