/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestioescandalls;

import GestioRestaurant.Categoria;
import GestioRestaurant.Linea_Escandall;
import GestioRestaurant.Plat;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.List;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuari
 */
public class GestioEscandalls {

    private static JFrame f;
    private static JTextField nom,cognom, edat;
    private static JButton add,remove,edit;
    private static JPanel esq, centre;
    private static JTable taula;
    private static String[] columnes = new String[] {"NOM","DESCRIPCIO","PREU"};
    private static DefaultTableModel model = new DefaultTableModel();
    private static JComboBox cboCat;
    private static ArrayList<Plat> llp = new ArrayList();
    private static ArrayList<Categoria> llc = new ArrayList();
    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;
    private static String up = "UP-MySQL";
        
    public static void main(String[] args) {
        comprovaEsquema();
        f= new JFrame("Gestió Escandall");
        afegirElements();
        afegirTaula();
        f.setVisible(true);
        f.pack();
        f.setResizable(false); 
        f.setLocation(10,300); 
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private static void afegirElements()
    {
        GridLayout g = new GridLayout(1,3);
        esq = new JPanel();
    
        
        centre = new JPanel();
      
        
        nom = new JTextField(12);
        cognom = new JTextField(20);
        edat = new JTextField(3);
   
        emplenarCombo();
        
      
        
        
        JPanel disponible = new JPanel(g);
        disponible.setSize(100, 1000);
        disponible.setBorder(new TitledBorder("Disponible"));
        ButtonGroup rdoDire = new ButtonGroup();
        JRadioButton btnSi = new JRadioButton("Si");
        JRadioButton btnNo = new JRadioButton("No");
        JRadioButton btnTots = new JRadioButton("Tots");
        rdoDire.add(btnSi);
        rdoDire.add(btnNo);
        rdoDire.add(btnTots);
        disponible.add(btnSi);
        disponible.add(btnNo);
        disponible.add(btnTots);
        
        esq.add(disponible);
        //esq.add(pCognom);
        //esq.add(pEdat);
        
        f.add(esq,BorderLayout.WEST);
        
        centre.add(cboCat);
//        centre.add(remove);
//        centre.add(edit);
        
        
        f.add(centre);
        
        
    }
    
    private static void emplenarCombo(){
        String [] sCarac = new String[llc.size()];
        for(int i = 0; i<llc.size(); i++){
            sCarac[i] = llc.get(i).getNom();
        }
        cboCat = new JComboBox(sCarac);
    }
    
    private static String[][] obtenirMatriu() {
        String matriuInfo[][] = new String[llp.size()][columnes.length];

        for (int i = 0; i < llp.size(); i++) {
            Plat p = llp.get(i);

            matriuInfo[i][0] = p.getNom();
            matriuInfo[i][1] = p.getDescripcioMD();
            matriuInfo[i][2] = p.getPreu()+"";
//            matriuInfo[i][0] = p.getNom(); // TODO GET FOTO
            //matriuInfo[i][3] = p.isDisponible()+"";
            //matriuInfo[i][4] = p.getCategoria()+"";
        }
        return matriuInfo;
    }
    
    private static void construirTaula() {
        
        String bdInfo[][] = obtenirMatriu();

        taula = new JTable(bdInfo,columnes){
             @Override
            public boolean isCellEditable(int row, int column)
            {
                return false;
            }
            @Override
            public Class<?> getColumnClass(int column)
            {
                Class clazz = String.class;
                switch (column)
                {
                    case 0: case 1: clazz = String.class;
                                    break;
                    case 2:         clazz = String.class;
                                    break;
                }
                return clazz;
            }
        };

    }
    
    
    private static void afegirTaula()
    {
        model = new DefaultTableModel();
        construirTaula();
        
        
        taula.getSelectionModel().addListSelectionListener(new GestioFiles());
        
        JScrollPane scroll = new JScrollPane(taula,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        
        scroll.setPreferredSize(new Dimension(400,200));
        
        Border marc = BorderFactory.createLineBorder(f.getBackground(),10);
        scroll.setBorder(marc);
        
        
        // afegir JScrollPane dins el JFrame
        f.add(scroll, BorderLayout.SOUTH);
        
        
    }
    
    private static class GestioBotons implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            String boto = e.getActionCommand();
            System.out.println("Boto premut: "+boto);
            int fila;
            switch (boto)
            {
                case "Afegir":
                    // TODO: validar dades d'entrada
                        model.addRow(new Object[]{cognom.getText(),nom.getText(),edat.getText()});
                        cognom.setText("");
                        nom.setText("");
                        edat.setText("");
                    break;
                case "Esborrar":
                    fila = taula.getSelectedRow();
                    if (fila != -1)
                    {
                        model.removeRow(fila);
                    } else
                    {
                        // TODO: avisar a l'usuari amb un JOptionPAne
                    }
                    break;
                case "Editar":
                    fila = taula.getSelectedRow();
                    if (fila != -1)
                    {
                        model.setValueAt(cognom.getText(), fila, 0);
                        model.setValueAt(nom.getText(), fila, 1);
                        model.setValueAt(edat.getText(), fila, 2);
                    } else
                    {
                         // TODO: avisar a l'usuari amb un JOptionPAne
                    }

                    break;
            }
            
        }
        
    }
    
    private static class GestioFiles implements ListSelectionListener
    {

        @Override
        public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting())
                {
                    int fila = taula.getSelectedRow();
                    
                    String n = (String) taula.getValueAt(fila, 0);
                    
                    Plat p = buscaPlatPerNom(n);
                    System.out.println(p.toString());
                    
                    Linea_Escandall le = buscaEscandallPlatPerIdPlat(p.getCodi());
                    System.out.println(le.toString());
                }
        }
        
    }
    
    private static Plat buscaPlatPerNom(String nomPlat){
        Query q = em.createNamedQuery("trobaPlatsPerNom");
        q.setParameter("nom", nomPlat);
        
        Plat p = (Plat) q.getSingleResult();
        
        return p;
    }
    
    private static Linea_Escandall buscaEscandallPlatPerIdPlat(int idPlat){
        Query q = em.createNamedQuery("trobaEscandallPlatPerId");
        q.setParameter("idPlat", idPlat);
        
        Linea_Escandall le = (Linea_Escandall) q.getSingleResult();
        
        return le;
    }
    
    
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
            
            
            
            Query q = em.createNamedQuery("trobaPlats");
                
                
                java.util.List<Plat> ll = (java.util.List<Plat>)q.getResultList();
                for(Plat p : ll){
                    llp.add(p);
                    
                    model.addRow(new Object[]{p.getNom(),p.getDescripcioMD(),p.getPreu()});
                      
                    
                    
                    System.out.println(p.toString());
                }
                
              Query q2 = em.createNamedQuery("trobaCategories");
                
                
                java.util.List<Categoria> lll = (java.util.List<Categoria>)q2.getResultList();
                for(Categoria c : lll){
                    llc.add(c);
                    
                    
                      
                    
                    
                    System.out.println(c.toString());
                }  
                
            Categoria c = null;
            c = em.find(Categoria.class, 1);
            System.out.println(c.toString());
            
            String cad = "select col from Linea_Escandall col";
            Query q3 = em.createQuery(cad);
            java.util.List<Linea_Escandall> lllllll = q3.getResultList();
            if (lllllll.size() == 0) {
                System.out.println("No hi ha cap color");
            } else {
                for (Linea_Escandall col : lllllll) {
                    System.out.println(col);
                }
            }      
            
            
//            Linea_Escandall le = null;
//            le = em.find(Linea_Escandall.class, 1);
//            System.out.println(le.toString());
            /*Query q3 = em.createNamedQuery("trobaEscandallPlatPerId");
            q3.setParameter("idPlat", 1);
                
                
                java.util.List<Linea_Escandall> llll = (java.util.List<Linea_Escandall>)q3.getResultList();
                for(Linea_Escandall le : llll){
                    
                    
                    
                      
                    
                    
                    System.out.println(le.toString());
                }  
                */
            
            
            
            
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
    
}
