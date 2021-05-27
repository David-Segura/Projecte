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
public class NMTaula implements Serializable{
    private static final long serialVersionUID = 6529685098267757690L;
    int numero;
    NMComanda comanda;

    public NMTaula() {
    }

    public NMTaula(int numero, NMComanda comanda) {
        this.numero = numero;
        this.comanda = comanda;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public NMComanda getComanda() {
        return comanda;
    }

    public void setComanda(NMComanda comanda) {
        this.comanda = comanda;
    }
    
    
}
