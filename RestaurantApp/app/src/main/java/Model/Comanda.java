package Model;

import java.sql.Timestamp;

public class Comanda {
    int codi;
    Timestamp data;
    int taula;
    Cambrer cambrer;

    public Comanda(int codi, Timestamp data, int taula, Cambrer cambrer) {
        this.codi = codi;
        this.data = data;
        this.taula = taula;
        this.cambrer = cambrer;
    }

    public Comanda() {
    }

    public int getCodi() {
        return codi;
    }

    public void setCodi(int codi) {
        this.codi = codi;
    }

    public Timestamp getData() {
        return data;
    }

    public void setData(Timestamp data) {
        this.data = data;
    }

    public int getTaula() {
        return taula;
    }

    public void setTaula(int taula) {
        this.taula = taula;
    }

    public Cambrer getCambrer() {
        return cambrer;
    }

    public void setCambrer(Cambrer cambrer) {
        this.cambrer = cambrer;
    }
}
