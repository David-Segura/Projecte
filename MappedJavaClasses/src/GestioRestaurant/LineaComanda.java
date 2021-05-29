/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioRestaurant;

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
        name = "Linea_Comanda"
)
/*@NamedQueries({
    //    @NamedQuery(name = "trobaFilmsAmbIdiomaId",
    //            query = "select f from Film f where f.idioma.getCodi() = :idiomaId"),
    @NamedQuery(name = "trobaPlats",
            query = "select p from Plat p"),
    @NamedQuery(name = "trobaPlatsPerNom",
           query = "select p from Plat p where p.nom = :nom"),
    @NamedQuery(name = "trobaPlatsPerCategoriaIDisponibilitat",
            query = "select p from Plat p where categoria.codi = :idCategoria and disponible = :disponible"),
    @NamedQuery(name = "trobaPlatsPerCategoria",
            query = "select p from Plat p where categoria.codi = :idCategoria"),
    
    
})*/
public class LineaComanda {
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "Comanda")
    @Id
    Comanda comanda;
    @Id
    int num;
    @Basic
    int quantitat;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "Plat")
    Plat item;
    @Basic
    boolean acabat;

    public LineaComanda(int num, int quantitat, Plat item, boolean acabat) {
        this.num = num;
        this.quantitat = quantitat;
        this.item = item;
        this.acabat = acabat;
    }

    public LineaComanda() {
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

    public Plat getItem() {
        return item;
    }

    public void setItem(Plat item) {
        this.item = item;
    }

    public boolean getEstat() {
        return acabat;
    }

    public void setEstat(boolean estat) {
        this.acabat = estat;
    }
   
   
}
