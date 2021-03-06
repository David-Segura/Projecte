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
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private static List<NMLineaComanda> llc = new ArrayList<>();
    private static String url;
    private static String usu;
    private static String pwd;
    private static int maxCodiComanda;
    private static int countLinies;
    private static int liniesAcabades;
    private static int liniesPendents;
    
    private static void comprovaEsquema() {
        Properties p = new Properties();
        try {
            p.load(new FileReader("connexioMySql.properties"));
        } catch (IOException ex) {
            System.out.println("Problemes en carregar el fitxer de configuraci??");
            System.out.println("M??s info: "+ex.getMessage());
            System.exit(1);
        }
        // p cont?? les propietats necess??ries per la connexi??
        url = p.getProperty("url");
        usu = p.getProperty("usuari");
        pwd = p.getProperty("contrasenya");
        if (url==null || usu==null || pwd==null) {
            System.out.println("Manca alguna de les propietats: url, usuari, contrasenya");
            System.exit(1);
        }
        // Ja tenim les 3 propietats
        // Podem intentar establir connexi??
        con = null;
        try {
            con = DriverManager.getConnection(url,usu,pwd);
            System.out.println("Connexi?? establerta");
            con.setAutoCommit(false);
            con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException ex) {
            System.out.println("Problemes en intentar la connexi??");
            System.out.println("M??s info: "+ex.getMessage());
        }
    }
    
    private static void trobaCambrer(String user){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {   
            String consulta = "select c.codi as codi, c.nom as nom, c.cognom1, c.cognom2, c.user, c.password from cambrer c where c.user = ?";
           
            ps = con.prepareStatement(consulta);
            ps.setString(1, user);

            rs = ps.executeQuery();
            
           
            while (rs.next()) {
               
                c.setCodi(rs.getInt("codi"));
                c.setNom(rs.getString("nom"));
                c.setCognom1(rs.getString("cognom1"));
                c.setCognom2(rs.getString("cognom2"));
                c.setUser(rs.getString("user"));
                c.setPassword(rs.getString("password"));  
               
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
                    System.out.println("Problemes en tancar la connexi??");
                    System.out.println("M??s info: " + ex.getMessage());

                }
            }
        }*/
               
    }
    
    private static void trobaTotesLesTaules(){

        lt.clear();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String consulta = "select t.numero, t.comanda as ccodi, c.taula,ca.codi as cacodi, ca.nom from taula t left join comanda c on t.comanda = c.codi "
                    + "left join cambrer ca on c.cambrer = ca.codi "
                    + "order by t.numero;";
           
            ps = con.prepareStatement(consulta);
            

            rs = ps.executeQuery();
            
           
            while (rs.next()) {
                NMTaula t = new NMTaula();
                NMComanda co = new NMComanda();
                NMCambrer ca = new NMCambrer();
                t.setNumero(rs.getInt("numero"));
                contLiniesComanda(t.getNumero());
                co.setTotalLinies(countLinies);
                co.setLiniesAcabades(liniesAcabades);
                co.setLiniesPendents(liniesPendents);
                countLinies = 0;
                liniesAcabades = 0;
                liniesPendents  = 0;
                co.setCodi(rs.getInt("ccodi"));
                co.setTaula(t);
                ca.setNom(rs.getString("nom"));
                ca.setCodi(rs.getInt("cacodi"));
                co.setCambrer(ca);
               
                t.setComanda(co);
                
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
                    System.out.println("Problemes en tancar la connexi??");
                    System.out.println("M??s info: " + ex.getMessage());

                }
            }
        }*/
        
        
               
    }
    
    private static void trobaTotsElsPlats(){
        

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
           
            String consulta = "select p.codi as pcodi, p.nom as pnom, p.descripcioMD, p.preu, p.foto, p.disponible, c.codi as ccodi, c.nom as cnom, c.color from plat p join categoria c on p.categoria = c.codi order by p.categoria, p.codi";
           
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
    
     private static void trobaMaxCodiComanda(){

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
           
            String consulta = "select max(codi) from comanda";
           
            ps = con.prepareStatement(consulta);
            

            rs = ps.executeQuery();
          
           
            while (rs.next()) {
                maxCodiComanda = rs.getInt(1);
               
            } 
                
            }catch (SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
            if (ex.getCause() != null) {
                System.out.println("Cause: " + ex.getCause().getMessage());
            }
            System.exit(1);
            }
               
    }
     
     
     private static void contLiniesComanda(int taula){

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
           
            String consulta = "select t.numero, c.codi, count(le.num) as n_linies from linea_comanda le join comanda c on c.codi = le.comanda join taula t on t.comanda = c.codi where t.numero = "+taula+" group by t.numero, c.codi";
           
            ps = con.prepareStatement(consulta);
            

            rs = ps.executeQuery();
           
            
            while (rs.next()) {
                countLinies = rs.getInt(3);
                
            } 
            
            consulta = "select t.numero, c.codi, count(le.num) as n_linies from linea_comanda le join comanda c on c.codi = le.comanda join taula t on t.comanda = c.codi "
                    + "where le.acabat = true and t.numero = "+taula+"  group by t.numero, c.codi";
           
            ps = con.prepareStatement(consulta);
            

            rs = ps.executeQuery();
            
            
            while (rs.next()) {
                liniesAcabades = rs.getInt(3);
                
            } 
            
            consulta = "select t.numero, c.codi, count(le.num) as n_linies from linea_comanda le join comanda c on c.codi = le.comanda join taula t on t.comanda = c.codi "
                    + "where le.acabat = false and t.numero = "+taula+"  group by t.numero, c.codi";
           
            ps = con.prepareStatement(consulta);
            

            rs = ps.executeQuery();
            
            
            while (rs.next()) {
                liniesPendents = rs.getInt(3);
                
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
        trobaMaxCodiComanda();
        //keep listens indefinitely until receives 'exit' call or program terminates
        while(true){
            System.out.println("Waiting for the client request");
            
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
                   
                    String password = (String) ois.readObject();
                    
                    
                    trobaCambrer(user);
                    if(c.getPassword().equals(password)){
                        resp= "Login correcte";
                        
                    }else{
                        resp = "Login incorrecte";
                        
                    }
                         oos.writeObject(resp); 
                         oos.writeObject(c);
                    break;
                case "2":
                    
                    trobaTotesLesTaules();
                    
                    resp=lt.size()+"";
                    oos.writeObject(resp);
                    
                    for(int i = 0; i<lt.size(); i++){
                        System.out.println("Enviant taula");
                        
                        
                        
                        NMTaula t = new NMTaula();
                        t.setNumero(lt.get(i).getNumero());
                        NMComanda com = new NMComanda();
                        com.setCodi(lt.get(i).getComanda().getCodi());
                        com.setData(lt.get(i).getComanda().getData());
                        com.setTotalLinies(lt.get(i).getComanda().getTotalLinies());
                        com.setLiniesAcabades(lt.get(i).getComanda().getLiniesAcabades());
                        com.setLiniesPendents(lt.get(i).getComanda().getLiniesPendents());
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
                    
                case "4":
                    System.out.println("ENVIANT LES LINIES DE COMANDA");
                    int codiComanda = (int) ois.readObject();
                    buscaLinies(codiComanda);
                    
                    oos.writeObject(llc.size());
                    
                    for (int i = 0; i < llc.size(); i++) {
                        System.out.println("Enviant linea comanda");
                        oos.writeObject(llc.get(i));
                    }
                    break;
                case "5":
                    
                    NMCambrer cambrer = (NMCambrer) ois.readObject();
                    NMComanda comanda = (NMComanda) ois.readObject();
                    
                    comanda = new NMComanda();
                    comanda.setCambrer(cambrer);
                    comanda.setCodi(maxCodiComanda+1);
                    maxCodiComanda++;
                    comanda.setData(new Timestamp(System.currentTimeMillis()));
                    NMTaula taula = (NMTaula) ois.readObject();
                    comanda.setTaula(taula);
                    comanda.setCambrer(cambrer);
                    taula.setComanda(comanda);
                    
                    int nLinies = (int) ois.readObject();
                   
                    List<NMLineaComanda> llc = new ArrayList<>();
                    for (int i = 0; i < nLinies; i++) {
                        NMLineaComanda lc = (NMLineaComanda)ois.readObject();
                        lc.setComanda(comanda);
                       
                        llc.add(lc);
                    }
                   
                    
                    
                    Connection con = null;
                    
                    try {
                        con = DriverManager.getConnection(url, usu, pwd);
                       
                        con.setAutoCommit(false);  

                        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = st.executeQuery("select codi, data, taula, cambrer from comanda");
                        
                        rs.moveToInsertRow();
                        rs.updateInt("codi", comanda.getCodi());
                        rs.updateTimestamp("data", comanda.getData());
                        rs.updateInt("taula", comanda.getTaula().getNumero());
                        rs.updateInt("cambrer", comanda.getCambrer().getCodi());
                       
                        rs.insertRow();
                        
                                                
                        con.commit();
            
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        try {
                            con.rollback();
                        } catch (SQLException sss) {
                            sss.printStackTrace();
                        }
                        System.exit(-1);
                    } finally {
                        try {
                            if (con != null) {
                                con.close();
                            }
                        } catch (SQLException sqle) {
                            sqle.printStackTrace();
                        }
                    }
                    
                    
                    
                    
                    try {
                        con = DriverManager.getConnection(url, usu, pwd);
                        
                        con.setAutoCommit(false);   // Per defecte, tota connexi?? JDBC ??s amb AutoCommit(true)

                        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = st.executeQuery("select comanda, num, quantitat, item, acabat from linea_comanda");
                        
                       
                        
                        for (int i = 0; i < llc.size(); i++) {
                            NMLineaComanda lc = llc.get(i);
                            
                            rs.moveToInsertRow();
                            rs.updateInt("comanda", lc.getComanda().getCodi());
                            rs.updateInt("num", lc.getNum());
                            rs.updateInt("quantitat", lc.getQuantitat());
                            rs.updateInt("item", lc.getItem().getCodi());
                            rs.updateBoolean("acabat", lc.getEstat());
                            rs.insertRow();
                           
                            //System.out.println("Inserit la linea de comanda " + lc.getComanda().getCodi() + "? " + rs.rowInserted());
                        }
                                                
                        con.commit();
           
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        try {
                            con.rollback();
                        } catch (SQLException sss) {
                            sss.printStackTrace();
                        }
                        System.exit(-1);
                    } finally {
                        try {
                            if (con != null) {
                                con.close();
                            }
                        } catch (SQLException sqle) {
                            sqle.printStackTrace();
                        }
                    }
                    
                    try {
                        con = DriverManager.getConnection(url, usu, pwd);
                        
                        con.setAutoCommit(false);   // Per defecte, tota connexi?? JDBC ??s amb AutoCommit(true)

                        Statement st = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,
                                ResultSet.CONCUR_UPDATABLE);
                        ResultSet rs = st.executeQuery("select numero, comanda from taula where numero = "+taula.getNumero());
                        
                        
                        while (rs.next()) {
                           
                            rs.updateInt("comanda", comanda.getCodi());
                            rs.updateRow();
                        }
                                                
                        con.commit();
          
                    } catch (SQLException sqle) {
                        sqle.printStackTrace();
                        try {
                            con.rollback();
                        } catch (SQLException sss) {
                            sss.printStackTrace();
                        }
                        System.exit(-1);
                    } finally {
                        try {
                            if (con != null) {
                                con.close();
                            }
                        } catch (SQLException sqle) {
                            sqle.printStackTrace();
                        }
                    }
                    
                    
                    break;
                case "6":
                    NMTaula t = (NMTaula) ois.readObject();
                    esborraComanda(t);
                    oos.writeObject("OK");
                    break;

            
            }
            
            
            
            
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

    private static void esborraComanda(NMTaula t) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String update = "update taula set comanda = null where numero = ?";
            ps = con.prepareStatement(update);
            ps.setInt(1, t.getNumero());

            int i = ps.executeUpdate();
            con.commit();
            
            trobaTotesLesTaules();

        } catch (SQLException ex) {  
            System.out.println("Error esborrant la comanda de la taula");
        }
    }

    private static void buscaLinies(int codiComanda) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            String select = "select lc.num, lc.quantitat, lc.item, p.nom, p.preu from linea_comanda lc join plat p on lc.item = p.codi where comanda = "+codiComanda+"";
            ps = con.prepareStatement(select);
           
            

            rs = ps.executeQuery();
            llc = new ArrayList<>();
            while (rs.next()) {
                NMLineaComanda lc = new NMLineaComanda();
                lc.setNum(rs.getInt("num"));
                lc.setQuantitat(rs.getInt("quantitat"));
                NMPlat p = new NMPlat();
                p.setNom(rs.getString("nom"));
                p.setPreu(rs.getFloat("preu"));
                lc.setItem(p);
                
                llc.add(lc);
            } 

        } catch (SQLException ex) {  
            System.out.println("Error esborrant la comanda de la taula");
        }
    }
    
    
}
