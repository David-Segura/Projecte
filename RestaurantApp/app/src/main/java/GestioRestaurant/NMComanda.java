package GestioRestaurant;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class NMComanda implements Serializable {
    private static final long serialVersionUID = 6529685098267741090L;
    int codi;
    Timestamp data;
    NMTaula taula;
    NMCambrer NMCambrer;

    int totalLinies;
    int liniesAcabades;
    int liniesPendents;


    public NMComanda(int codi, Timestamp data, NMTaula taula, NMCambrer NMCambrer) {
        this.codi = codi;
        this.data = data;
        this.taula = taula;
        this.NMCambrer = NMCambrer;
    }

    public NMComanda() {
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

    public NMTaula getTaula() {
        return taula;
    }

    public void setTaula(NMTaula taula) {
        this.taula = taula;
    }

    public NMCambrer getNMCambrer() {
        return NMCambrer;
    }

    public void setNMCambrer(NMCambrer NMCambrer) {
        this.NMCambrer = NMCambrer;
    }

    public int getTotalLinies() {
        return totalLinies;
    }

    public void setTotalLinies(int totalLinies) {
        this.totalLinies = totalLinies;
    }

    public int getLiniesAcabades() {
        return liniesAcabades;
    }

    public void setLiniesAcabades(int liniesAcabades) {
        this.liniesAcabades = liniesAcabades;
    }

    public int getLiniesPendents() {
        return liniesPendents;
    }

    public void setLiniesPendents(int liniesPendents) {
        this.liniesPendents = liniesPendents;
    }

    @Override
    public String toString() {
        return "NMComanda{" +
                "codi=" + codi +
                //", data=" + data.toString() +
                ", NMCambrer=" + NMCambrer.toString() +
                '}';
    }
}
