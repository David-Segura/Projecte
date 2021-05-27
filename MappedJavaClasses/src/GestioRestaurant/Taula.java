/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioRestaurant;

import java.io.Serializable;
import javax.persistence.Access;
import javax.persistence.AccessType;
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
        name = "Taula"
)
@NamedQueries({
    //    @NamedQuery(name = "trobaFilmsAmbIdiomaId",
    //            query = "select f from Film f where f.idioma.getCodi() = :idiomaId"),
    @NamedQuery(name = "trobaTotesLesTaules",
            query = "select t from Taula t"),
    
    
    
})
public class Taula implements Serializable{
    @Id
    int numero;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "Comanda")
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

    @Override
    public String toString() {
        return "Taula{" + "numero=" + numero + ", comanda=" + comanda + '}';
    }
    
    
    
}
