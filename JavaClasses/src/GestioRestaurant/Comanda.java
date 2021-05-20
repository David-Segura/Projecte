/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioRestaurant;

import java.sql.Timestamp;

/**
 *
 * @author Usuari
 */
public class Comanda {
    int codi;
    Timestamp data;
    int taula;
    int cambrer;

    public Comanda(int codi, Timestamp data, int taula, int cambrer) {
        this.codi = codi;
        this.data = data;
        this.taula = taula;
        this.cambrer = cambrer;
    }

    public Comanda() {
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public int getTaula() {
        return taula;
    }

    public void setTaula(int taula) {
        this.taula = taula;
    }

    public int getCambrer() {
        return cambrer;
    }

    public void setCambrer(int cambrer) {
        this.cambrer = cambrer;
    }
    
    
}
