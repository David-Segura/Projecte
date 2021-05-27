package Model;

public class Unitat {
    int codi;
    String nom;

    public Unitat(int codi, String nom) {
        this.codi = codi;
        this.nom = nom;
    }

    public Unitat() {
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


}
