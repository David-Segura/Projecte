package GestioRestaurant;

import java.io.Serializable;

public class NMLineaComanda implements Serializable {
    int num;
    int quantitat;
    int item;
    int estat;

    public NMLineaComanda(int num, int quantitat, int item, int estat) {
        this.num = num;
        this.quantitat = quantitat;
        this.item = item;
        this.estat = estat;
    }

    public NMLineaComanda() {
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
