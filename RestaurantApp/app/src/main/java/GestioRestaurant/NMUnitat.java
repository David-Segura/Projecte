package GestioRestaurant;

import java.io.Serializable;

public class NMUnitat implements Serializable {
    int codi;
    String nom;

    public NMUnitat(int codi, String nom) {
        this.codi = codi;
        this.nom = nom;
    }

    public NMUnitat() {
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
