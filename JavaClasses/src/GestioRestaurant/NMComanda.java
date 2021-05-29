/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioRestaurant;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author Usuari
 */
public class NMComanda implements Serializable{
    private static final long serialVersionUID = 6529685098267741090L;
    int codi;
    Timestamp data;
    NMTaula taula;
    NMCambrer cambrer;

    public NMComanda(int codi, Timestamp data, NMTaula taula, NMCambrer cambrer) {
        this.codi = codi;
        this.data = data;
        this.taula = taula;
        this.cambrer = cambrer;
    }

    public NMComanda() {
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

    public NMTaula getTaula() {
        return taula;
    }

    public void setTaula(NMTaula taula) {
        this.taula = taula;
    }

    public NMCambrer getCambrer() {
        return cambrer;
    }

    public void setCambrer(NMCambrer cambrer) {
        this.cambrer = cambrer;
    }

    @Override
    public String toString() {
        return "NMComanda{" + "codi=" + codi + ", data=" + data + ", cambrer=" + cambrer + '}';
    }
    
    
}
