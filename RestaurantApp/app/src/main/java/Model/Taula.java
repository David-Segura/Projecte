package Model;

public class Taula {
    int numero;
    Comanda comanda;

    public Comanda getComanda() {
        return comanda;
    }

    public void setComanda(Comanda comanda) {
        this.comanda = comanda;
    }

    public Taula() {
    }

    public Taula(int numero,Comanda comanda) {
        this.numero = numero;
        this.comanda = comanda;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

}
