package GestioRestaurant;

import java.io.Serializable;

public class NMTaula implements Serializable {
    private static final long serialVersionUID = 6529685098267757690L;
    int numero;
    NMComanda NMComanda;

    public NMComanda getNMComanda() {
        return NMComanda;
    }

    public void setNMComanda(NMComanda NMComanda) {
        this.NMComanda = NMComanda;
    }

    public NMTaula() {
    }

    public NMTaula(int numero, NMComanda NMComanda) {
        this.numero = numero;
        this.NMComanda = NMComanda;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    @Override
    public String toString() {
        return "Taula{" +
                "numero=" + numero +
                ", comanda=" + NMComanda.toString() +
                '}';
    }
}
