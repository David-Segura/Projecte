/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioRestaurant;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(
        name = "Linea_Escandall"
)
@NamedQueries({
        //@NamedQuery(name = "trobaFilmsAmbIdiomaId",
                //query = "select f from Film f where f.idioma.getCodi() = :idiomaId"),
    @NamedQuery(name = "trobaEscandallPlatPerId",
            query = "select le from Linea_Escandall le where le.plat = :idPlat"),
    @NamedQuery(name = "trobaEscandallPlatPerIdPlatINum",
            query = "select le from Linea_Escandall le where le.plat = :idPlat and le.num = :idNum"),
    //@NamedQuery(name = "maxLiniaxPlatId", 
            //query = "select max(num) from Linea_Escandall where plat = :idPlat")
    
})
public class Linea_Escandall implements Serializable{
    
    @Id
    int plat;
    @Id
    int num;
    @Basic
    int quantitat;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "Unitat")
    Unitat unitat;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient")
    Ingredient ingredient;

    public Linea_Escandall( int plat, int num, int quantitat, Unitat unitat, Ingredient ingredient) {
     
        this.plat = plat;
        this.num = num;
        this.quantitat = quantitat;
        this.unitat = unitat;
        this.ingredient = ingredient;
    }

    public Linea_Escandall() {
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

    public Unitat getUnitat() {
        return unitat;
    }

    public void setUnitat(Unitat unitat) {
        this.unitat = unitat;
    }

    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public String toString() {
        return "Linea_Escandall{" + "plat=" + plat + ", num=" + num + ", quantitat=" + quantitat + ", unitat=" + unitat + ", ingredient=" + ingredient + '}';
    }
    
    
}
