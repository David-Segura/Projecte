package GestioRestaurant;

import java.io.Serializable;

public class NMLinea_Escandall implements Serializable {
    int plat;
    int num;
    int quantitat;
    int unitat;
    int ingredient;

    public NMLinea_Escandall(int plat, int num, int quantitat, int unitat, int ingredient) {
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

    public int getUnitat() {
        return unitat;
    }

    public void setUnitat(int unitat) {
        this.unitat = unitat;
    }

    public int getIngredient() {
        return ingredient;
    }

    public void setIngredient(int ingredient) {
        this.ingredient = ingredient;
    }


}
