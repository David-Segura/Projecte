package GestioRestaurant;

import android.media.Image;

import java.io.Serializable;
import java.util.Arrays;

public class NMPlat implements Serializable {
    private static final long serialVersionUID = 6120365098540341090L;
    int codi;
    String nom;
    String descripcioMD;
    float preu;
    byte[] foto;
    boolean disponible ;
    NMCategoria categoria;

    public NMPlat(int codi, String nom, String descripcioMD, float preu, byte[] foto, boolean disponible, NMCategoria categoria) {
        this.codi = codi;
        this.nom = nom;
        this.descripcioMD = descripcioMD;
        this.preu = preu;
        this.foto = foto;
        this.disponible = disponible;
        this.categoria = categoria;
    }

    public NMPlat() {
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

    public String getDescripcioMD() {
        return descripcioMD;
    }

    public void setDescripcioMD(String descripcioMD) {
        this.descripcioMD = descripcioMD;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public NMCategoria getCategoria() {
        return categoria;
    }

    public void setCategoria(NMCategoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "NMPlat{" +
                "codi=" + codi +
                ", nom='" + nom + '\'' +

                '}';
    }


}