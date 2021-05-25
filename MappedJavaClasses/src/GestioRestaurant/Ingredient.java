/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioRestaurant;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Usuari
 */

@Entity
@Access(AccessType.FIELD)
@Table(
        name = "Ingredient"
)
@NamedQueries({
    //    @NamedQuery(name = "trobaFilmsAmbIdiomaId",
    //            query = "select f from Film f where f.idioma.getCodi() = :idiomaId"),
    @NamedQuery(name = "trobaIngredients",
            query = "select i from Ingredient i"),
    @NamedQuery(name = "trobaIngredientxNom",
            query = "select i from Ingredient i where i.nom = :nom"),
    
})
public class Ingredient {
    @Id
    int codi;
    @Basic
    String nom;

    public Ingredient(int codi, String nom) {
        this.codi = codi;
        this.nom = nom;
    }

    public Ingredient() {
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


