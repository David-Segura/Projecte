package GestioRestaurant;

import java.io.Serializable;

public class NMLineaComanda implements Serializable {
    private static final long serialVersionUID = 6120463088267741090L;
    int num;
    int quantitat;
    NMPlat item;
    boolean acabat;

    public NMLineaComanda(int num, int quantitat, NMPlat item, boolean acabat) {
        this.num = num;
        this.quantitat = quantitat;
        this.item = item;
        this.acabat = acabat;
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

    public NMPlat getItem() {
        return item;
    }

    public void setItem(NMPlat item) {
        this.item = item;
    }

    public boolean getEstat() {
        return acabat;
    }

    public void setEstat(boolean acabat) {
        this.acabat = acabat;
    }

    @Override
    public String toString() {
        return "NMLineaComanda{" +
                "num=" + num +
                ", quantitat=" + quantitat +
                ", item=" + item +
                ", acabat=" + acabat +
                '}';
    }
}
