/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserver;

import GestioRestaurant.Cambrer;
import GestioRestaurant.Categoria;
import GestioRestaurant.Comanda;
import GestioRestaurant.Linea_Escandall;
import GestioRestaurant.NMCambrer;
import GestioRestaurant.NMCategoria;
import GestioRestaurant.NMComanda;
import GestioRestaurant.NMLineaComanda;
import GestioRestaurant.NMPlat;
import GestioRestaurant.NMTaula;
import GestioRestaurant.Plat;
import GestioRestaurant.Taula;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Usuari
 */
public class JavaServer {

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;
    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;
    private static String up = "UP-MySQL";
    private static Cambrer c;
    private static List<Taula> lt;
    private static List<Plat> lp;
    private static void comprovaEsquema() {
        try {
            em = null;
            emf = null;
            System.out.println("Intent amb " + up);
            emf = Persistence.createEntityManagerFactory(up);
            System.out.println("EntityManagerFactory creada");
            em = emf.createEntityManager();
            System.out.println();
            System.out.println("EntityManager creat");
            
            
            
            
            
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
            System.out.print(ex.getCause() != null ? "Caused by:" + ex.getCause().getMessage() + "\n" : "");
            System.out.println("Traça:");
            ex.printStackTrace();
        } /*finally {
            if (em != null) {
                em.close();
                System.out.println("EntityManager tancat");
            }
            if (emf != null) {
                emf.close();
                System.out.println("EntityManagerFactory tancada");
            }
        }*/
    }
    
    private static void trobaCambrer(String user){
        Query q = em.createNamedQuery("trobaCambrerPerUsuari");
        q.setParameter("user", user);
        c =  (Cambrer) q.getSingleResult();
               
    }
    
    private static void trobaTotesLesTaules(){
        Query q = em.createNamedQuery("trobaTotesLesTaules");
        
        lt =  (List<Taula>) q.getResultList();
               
    }
    
    private static void trobaTotsElsPlats(){
        Query q = em.createNamedQuery("trobaPlats");
        
        lp =  (List<Plat>) q.getResultList();
               
    }
    
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        comprovaEsquema();
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Waiting for the client request");
            trobaTotesLesTaules();
                    //System.out.println(lt.size());
            //creating socket and waiting for client connection
            Socket socket = server.accept();
            //read from socket to ObjectInputStream object
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //convert ObjectInputStream object to String
            String message = (String) ois.readObject();
            System.out.println("Message Received: " + message);
            String resp ="";
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            
                    
                    
            switch(message){
                case "1":
                    String user = (String) ois.readObject();
                    System.out.println("Usuari: "+ user);
                    String password = (String) ois.readObject();
                    System.out.println("Contrasenya: " + password);
                    
                    trobaCambrer(user);
                    if(c.getPassword().equals(password)){
                        resp= "Login correcte";
                        System.out.println("Login correcte");
                    }else{
                        resp = "Login incorrecte";
                        System.out.println("Login incorrecte");
                    }
                         oos.writeObject(resp);               
                    break;
                case "2":
                    trobaTotesLesTaules();
                    /*for(int i = 0; i<lt.size();i++){
                        System.out.println(lt.get(i).toString());
                    }*/
                    resp=lt.size()+"";
                    oos.writeObject(resp);
                    
                    for(int i = 0; i<lt.size(); i++){
                        System.out.println("Enviant taula");
                        
                        
                        
                        NMTaula t = new NMTaula();
                        t.setNumero(lt.get(i).getNumero());
                        NMComanda com = new NMComanda();
                        com.setCodi(lt.get(i).getComanda().getCodi());
                        com.setData(lt.get(i).getComanda().getData());
                        com.setTaula(t);
                        NMCambrer cambrer = new NMCambrer();
                        cambrer.setCodi(lt.get(i).getComanda().getCambrer().getCodi());
                        cambrer.setNom(lt.get(i).getComanda().getCambrer().getNom());
                        cambrer.setCognom1(lt.get(i).getComanda().getCambrer().getCognom1());
                        cambrer.setCognom2(lt.get(i).getComanda().getCambrer().getCognom2());
                        cambrer.setCognom2(lt.get(i).getComanda().getCambrer().getCognom2());
                        cambrer.setUser(lt.get(i).getComanda().getCambrer().getUser());
                        cambrer.setPassword(lt.get(i).getComanda().getCambrer().getPassword());
                        com.setCambrer(cambrer);
                        System.out.println(com.toString());
                        t.setComanda(com);
                        
                        
                        oos.writeObject(cambrer);
                        oos.writeObject(com);
                        oos.writeObject(t);
                        
                    }
                    break;
                case "3":
                    trobaTotsElsPlats();
                    resp=lp.size()+"";
                    oos.writeObject(resp);
                    
                    for(int i = 0; i<lp.size(); i++){
                        System.out.println("Enviant plat");
                        Plat pl = lp.get(i);
                        NMPlat p = new NMPlat();
                        p.setCodi(pl.getCodi());
                        p.setNom(pl.getNom());
                        p.setDescripcioMD(pl.getDescripcioMD());
                        p.setDisponible(pl.getDisponible());
                        p.setFoto(pl.getFoto());
                        p.setPreu(pl.getPreu());
                        NMCategoria categoria = new NMCategoria();
                        categoria.setCodi(pl.getCategoria().getCodi());
                        categoria.setNom(pl.getCategoria().getNom());
                        categoria.setColor(pl.getCategoria().getColor());
                        
                        p.setCategoria(categoria);
                        
                        oos.writeObject(categoria);
                        oos.writeObject(p);
                    }
                    break;
                case "5":
                    NMCambrer cambrer = (NMCambrer) ois.readObject();
                    NMComanda comanda = (NMComanda) ois.readObject();
                    NMTaula taula = (NMTaula) ois.readObject();
                    comanda.setCambrer(cambrer);
                    taula.setComanda(comanda);
                    System.out.println("Taula: "+ taula.toString());
                    int nLinies = (int) ois.readObject();
                    System.out.println("N Linies: " + nLinies);
                    List<NMLineaComanda> llc = new ArrayList<>();
                    for (int i = 0; i < nLinies; i++) {
                        NMLineaComanda lc = (NMLineaComanda)ois.readObject();
                        System.out.println("Linea comanda: "+ lc.toString());
                        llc.add(lc);
                    }
                    
                    
                    
                    Cambrer c = new Cambrer();
                    c.setCodi(cambrer.getCodi());
                    c.setNom(cambrer.getNom());
                    c.setCognom1(cambrer.getCognom1());
                    c.setCognom2(cambrer.getCognom2());
                    c.setUser(cambrer.getUser());
                    c.setPassword(cambrer.getPassword());
                    
                    
                    Comanda co = new Comanda();
                    co.setCodi(comanda.getCodi());
                    co.setData(comanda.getData());
                    co.setCambrer(c);
                    
                    
                    
                    Taula t = new Taula();
                    t.setNumero(taula.getNumero());
                    t.setComanda(co);
                    
                    co.setTaula(t);
                    
                    em.getTransaction().begin();
                    
                    em.persist(t);
                    
                    em.getTransaction().commit();
                    em.close();
                    
                    
                    
                    
                    break;
            
            }
            
            
            //create ObjectOutputStream object
            
            //write object to Socket
            //oos.writeObject("Hi Client "+message);
            
            //close resources
            ois.close();
            oos.close();
            socket.close();
            //terminate the server if client sends exit request
            if(message.equalsIgnoreCase("exit")) break;
        }
        System.out.println("Shutting down Socket server!!");
        //close the ServerSocket object
        server.close();
    }
    
    
}
