/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GestioRestaurant;

import java.awt.Image;
import java.sql.Blob;
import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
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
        name = "Plat"
)
@NamedQueries({
    //    @NamedQuery(name = "trobaFilmsAmbIdiomaId",
    //            query = "select f from Film f where f.idioma.getCodi() = :idiomaId"),
    @NamedQuery(name = "trobaPlats",
            query = "select p from Plat p"),
    @NamedQuery(name = "trobaPlatsPerNom",
           query = "select p from Plat p where p.nom = :nom"),
    @NamedQuery(name = "trobaPlatsPerCategoriaIDisponibilitat",
            query = "select p from Plat p where categoria = :idCategoria and disponible = :disponible"),
    
})
public class Plat {
    @Id
    int codi;
    @Basic
    String nom;
    @Basic
    String descripcioMD;
    @Basic
    float preu;
    @Basic
    @Column(columnDefinition = "Blob")      
    byte[] foto;
    @Basic
    boolean disponible ;
    @ManyToOne(optional = false, cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)
    @JoinColumn(name = "Categoria", insertable = false, updatable = false)
    Categoria categoria;

    public Plat(int codi, String nom, String descripcioMD, float preu, byte[] foto, boolean disponible, Categoria categoria) {
        this.codi = codi;
        this.nom = nom;
        this.descripcioMD = descripcioMD;
        this.preu = preu;
        this.foto = foto;
        this.disponible = disponible;
        this.categoria = categoria;
    }

    public Plat() {
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

    public String getDescripcioMD() {
        return descripcioMD;
    }

    public void setDescripcioMD(String descripcioMD) {
        this.descripcioMD = descripcioMD;
    }

    public float getPreu() {
        return preu;
    }

    public void setPreu(float preu) {
        this.preu = preu;
    }

    public byte[] getFoto() {
        return foto;
    }

    public void setFoto(byte[] foto) {
        this.foto = foto;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "Plat{" + "codi=" + codi + ", nom=" + nom + ", descripcioMD=" + descripcioMD + ", preu=" + preu + ", foto=" + foto + ", disponible=" + disponible + ", categoria=" + categoria + '}';
    }
    
    
}
