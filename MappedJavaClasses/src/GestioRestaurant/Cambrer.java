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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Access(AccessType.FIELD)
@Table(
        name = "Cambrer"
)
@NamedQueries({
    //    @NamedQuery(name = "trobaFilmsAmbIdiomaId",
    //            query = "select f from Film f where f.idioma.getCodi() = :idiomaId"),
    @NamedQuery(name = "trobaCambrerPerUsuari",
            query = "select c from Cambrer c where user = :user"),
    
})
public class Cambrer implements Serializable{
    @Id
    int codi;
    @Basic
    String nom;
    @Basic
    String cognom1;
    @Basic
    String cognom2;
    @Basic
    String user;
    @Basic
    String password;

    public Cambrer(int codi, String nom, String cognom1, String cognom2, String user, String password) {
        this.codi = codi;
        this.nom = nom;
        this.cognom1 = cognom1;
        this.cognom2 = cognom2;
        this.user = user;
        this.password = password;
    }

    public Cambrer() {
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

    public String getCognom1() {
        return cognom1;
    }

    public void setCognom1(String cognom1) {
        this.cognom1 = cognom1;
    }

    public String getCognom2() {
        return cognom2;
    }

    public void setCognom2(String cognom2) {
        this.cognom2 = cognom2;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Cambrer{" + "codi=" + codi + ", nom=" + nom + ", cognom1=" + cognom1 + ", cognom2=" + cognom2 + ", user=" + user + ", password=" + password + '}';
    }
    
    
    
    
}

