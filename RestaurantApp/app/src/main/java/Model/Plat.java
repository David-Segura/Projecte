package Model;

import android.media.Image;

public class Plat {
    int codi;
    String nom;
    String descripcioMD;
    float preu;
    Image foto;
    boolean disponible ;
    int categoria;

    public Plat(int codi, String nom, String descripcioMD, float preu, Image foto, boolean disponible, int categoria) {
        this.codi = codi;
        this.nom = nom;
        this.descripcioMD = descripcioMD;
        this.preu = preu;
        this.foto = foto;
        this.disponible = disponible;
        this.categoria = categoria;
    }

    public Plat() {
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

    public Image getFoto() {
        return foto;
    }

    public void setFoto(Image foto) {
        this.foto = foto;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public int getCategoria() {
        return categoria;
    }

    public void setCategoria(int categoria) {
        this.categoria = categoria;
    }


}