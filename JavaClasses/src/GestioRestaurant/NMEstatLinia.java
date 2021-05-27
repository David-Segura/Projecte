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
public class NMEstatLinia implements Serializable{
    byte estat;

    public NMEstatLinia(byte estat) {
        this.estat = estat;
    }

    public NMEstatLinia() {
    }

    public byte getEstat() {
        return estat;
    }

    public void setEstat(byte estat) {
        this.estat = estat;
    }
    
    
}

