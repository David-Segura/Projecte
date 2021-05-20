/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioRestaurant;

import java.awt.Color;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;


@Entity
@Access(AccessType.FIELD)
@Table(
        name = "Categoria"
)
@NamedQueries({
    //    @NamedQuery(name = "trobaFilmsAmbIdiomaId",
    //            query = "select f from Film f where f.idioma.getCodi() = :idiomaId"),
    @NamedQuery(name = "trobaCategories",
            query = "select c from Categoria c"),
    
})
public class Categoria {
    @Id
    int codi;
    @Basic
    String nom;
    @Basic
    int color;

    public Categoria(int codi, String nom, int color) {
        this.codi = codi;
        this.nom = nom;
        this.color = color;
    }

    public Categoria() {
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
        return "Categoria{" + "codi=" + codi + ", nom=" + nom + ", color=" + color + '}';
    }
    
    
    
}
