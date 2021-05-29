package GestioRestaurant;

import android.graphics.Color;

import java.io.Serializable;
import java.util.Objects;

public class NMCategoria implements Serializable {
    private static final long serialVersionUID = 6126503098540341090L;
    int codi;
    String nom;
    int color;

    public NMCategoria(int codi, String nom, int color) {
        this.codi = codi;
        this.nom = nom;
        this.color = color;
    }

    public NMCategoria() {
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

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return "NMCategoria{" +
                "codi=" + codi +
                ", nom='" + nom + '\'' +
                ", color=" + color +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NMCategoria that = (NMCategoria) o;
        return codi == that.codi;
    }

    @Override
    public int hashCode() {
        return Objects.hash(codi);
    }
}
