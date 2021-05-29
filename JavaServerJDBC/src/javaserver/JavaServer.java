/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaserver;


import GestioRestaurant.NMCambrer;
import GestioRestaurant.NMCategoria;
import GestioRestaurant.NMComanda;
import GestioRestaurant.NMLineaComanda;
import GestioRestaurant.NMPlat;
import GestioRestaurant.NMTaula;
import java.io.FileReader;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 *
 * @author Usuari
 */
public class JavaServer {

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 9876;
    private static Connection con = null;
    
    private static String up = "UP-MySQL";
    private static NMCambrer c = new NMCambrer();
    private static List<NMTaula> lt = new ArrayList<>();
    private static List<NMPlat> lp = new ArrayList<>();
    private static String url;
    private static String usu;
    private static String pwd;
    
    private static void comprovaEsquema() {
        Properties p = new Properties();
        try {
            p.load(new FileReader("connexioMySql.properties"));
        } catch (IOException ex) {
            System.out.println("Problemes en carregar el fitxer de configuració");
            System.out.println("Més info: "+ex.getMessage());
            System.exit(1);
        }
        // p conté les propietats necessàries per la connexió
        url = p.getProperty("url");
        usu = p.getProperty("usuari");
        pwd = p.getProperty("contrasenya");
        if (url==null || usu==null || pwd==null) {
            System.out.println("Manca alguna de les propietats: url, usuari, contrasenya");
            System.exit(1);
        }
        // Ja tenim les 3 propietats
        // Podem intentar establir connexió
        con = null;
        try {
            con = DriverManager.getConnection(url,usu,pwd);
            System.out.println("Connexió establerta");
            System.out.println("Classe que implementa Connection: ");
            System.out.println("\t"+con.getClass().getName());
            
            System.out.println("Autocommit en punt1: "+con.getAutoCommit());
            con.setAutoCommit(false);
            System.out.println("Autocommit en punt2: "+con.getAutoCommit());
        } catch (SQLException ex) {
            System.out.println("Problemes en intentar la connexió");
            System.out.println("Més info: "+ex.getMessage());
        }
    }
    
    private static void trobaCambrer(String user){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Podem intentar establir connexió
            //con = DriverManager.getConnection(url, usu, pwd);
            System.out.println("Connexió establerta");
           
           
               
            String consulta = "select c.codi as codi, c.nom as nom, c.cognom1, c.cognom2, c.user, c.password from cambrer c where c.user = ?";
           
            ps = con.prepareStatement(consulta);
            ps.setString(1, user);

            rs = ps.executeQuery();
            
           
            while (rs.next()) {
                System.out.println(rs.getInt("codi"));
                c.setCodi(rs.getInt("codi"));
                c.setNom(rs.getString("nom"));
                c.setCognom1(rs.getString("cognom1"));
                c.setCognom2(rs.getString("cognom2"));
                c.setUser(rs.getString("user"));
                c.setPassword(rs.getString("password"));  
                System.out.println(c.toString());
            } 
                
            }catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            if (ex.getCause() != null) {
                System.out.println("Cause: " + ex.getCause().getMessage());
            }
            System.exit(1);
        } /*finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.out.println("Problemes en tancar la connexió");
                    System.out.println("Més info: " + ex.getMessage());

                }
            }
        }*/
               
    }
    
    private static void trobaTotesLesTaules(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Podem intentar establir connexió
            //con = DriverManager.getConnection(url, usu, pwd);
            System.out.println("Connexió establerta");
           
           
               
            String consulta = "select t.numero, c.codi as ccodi, c.taula,ca.codi as cacodi, ca.nom from taula t\n" +
"join comanda c on t.comanda = c.codi\n" +
"join cambrer ca on c.cambrer = ca.codi;";
           
            ps = con.prepareStatement(consulta);
            

            rs = ps.executeQuery();
            
           
            while (rs.next()) {
                NMTaula t = new NMTaula();
                NMComanda co = new NMComanda();
                NMCambrer ca = new NMCambrer();
                t.setNumero(rs.getInt("numero"));
                co.setCodi(rs.getInt("ccodi"));
                co.setTaula(t);
                ca.setNom(rs.getString("nom"));
                ca.setCodi(rs.getInt("cacodi"));
                co.setCambrer(ca);
                t.setComanda(co);
                //System.out.println(rs.getInt("codi"));
                
                lt.add(t);
            } 
                
            }catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            if (ex.getCause() != null) {
                System.out.println("Cause: " + ex.getCause().getMessage());
            }
            System.exit(1);
        } /*finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex) {
                    throw new RuntimeException(ex.getMessage());
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException ex) {
                    System.out.println("Problemes en tancar la connexió");
                    System.out.println("Més info: " + ex.getMessage());

                }
            }
        }*/
        
        
               
    }
    
    private static void trobaTotsElsPlats(){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            // Podem intentar establir connexió
            //con = DriverManager.getConnection(url, usu, pwd);
            System.out.println("Connexió establerta");
           
           
               
            String consulta = "select p.codi as pcodi, p.nom as pnom, p.descripcioMD, p.preu, p.foto, p.disponible, c.codi as ccodi, c.nom as cnom, c.color from plat p join categoria c on p.categoria = c.codi";
           
            ps = con.prepareStatement(consulta);
            

            rs = ps.executeQuery();
            
           
            while (rs.next()) {
                NMPlat p = new NMPlat();
                NMCategoria ca = new NMCategoria();
                p.setCodi(rs.getInt("pcodi"));
                p.setNom(rs.getString("pnom"));
                p.setDescripcioMD(rs.getString("descripcioMD"));
                p.setPreu(rs.getFloat("preu"));
                p.setFoto(rs.getBytes("foto"));
                p.setDisponible(rs.getBoolean("disponible"));
                ca.setCodi(rs.getInt("ccodi"));
                ca.setNom(rs.getString("cnom"));
                ca.setColor(rs.getInt("color"));
                p.setCategoria(ca);
                lp.add(p);
            } 
                
            }catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            if (ex.getCause() != null) {
                System.out.println("Cause: " + ex.getCause().getMessage());
            }
            System.exit(1);
            }
               
    }
    
    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        comprovaEsquema();
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Waiting for the client request");
            //trobaTotesLesTaules();
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
                         oos.writeObject(c);
                    break;
                case "2":
                    if(lt.size()==0)
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
                    if(lp.size()==0)
                        trobaTotsElsPlats();
                    resp=lp.size()+"";
                    oos.writeObject(resp);
                    
                    for(int i = 0; i<lp.size(); i++){
                        System.out.println("Enviant plat");
                        NMPlat p = lp.get(i);
                        
                        oos.writeObject(p);
                    }
                    break;
                /*case "5":
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
            */
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
