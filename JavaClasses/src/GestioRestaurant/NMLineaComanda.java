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
public class NMLineaComanda implements Serializable{
   NMComanda comanda;
   int num;
   int quantitat;
   int item;
   int estat;

    public NMLineaComanda(NMComanda comanda, int num, int quantitat, int item, int estat) {
        this.comanda = comanda;
        this.num = num;
        this.quantitat = quantitat;
        this.item = item;
        this.estat = estat;
    }

    public NMLineaComanda() {
    }

    public NMComanda getComanda() {
        return comanda;
    }

    public void setComanda(NMComanda comanda) {
        this.comanda = comanda;
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

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    public int getEstat() {
        return estat;
    }

    public void setEstat(int estat) {
        this.estat = estat;
    }
   
   
}
