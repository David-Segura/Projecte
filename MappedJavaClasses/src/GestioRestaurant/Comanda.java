/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioRestaurant;

import java.sql.Timestamp;
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
        name = "Comanda"
)
@NamedQueries({
    //    @NamedQuery(name = "trobaFilmsAmbIdiomaId",
    //            query = "select f from Film f where f.idioma.getCodi() = :idiomaId"),
    //@NamedQuery(name = "trobaTotesLesTaules",
            //query = "select t from Taula t"),
    
    
    
})
public class Comanda {
    @Id
    int codi;
    @Basic
    Timestamp data;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "Taula")
    Taula taula;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "Cambrer")
    Cambrer cambrer;

    public Comanda(int codi, Timestamp data, Taula taula, Cambrer cambrer) {
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

    public Taula getTaula() {
        return taula;
    }

    public void setTaula(Taula taula) {
        this.taula = taula;
    }

    public Cambrer getCambrer() {
        return cambrer;
    }

    public void setCambrer(Cambrer cambrer) {
        this.cambrer = cambrer;
    }

    @Override
    public String toString() {
        return "Comanda{" + "codi=" + codi + ", data=" + data + ", taula=" + taula + ", cambrer=" + cambrer + '}';
    }
    
    
}
