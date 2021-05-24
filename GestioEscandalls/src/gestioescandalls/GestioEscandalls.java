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
import java.awt.Color;
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
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

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
    private static JTable taulaEscandall;
    private static String[] columnes = new String[] {"NOM","DESCRIPCIO","PREU","COLOR"};
    private static String[] columnesEscandall = new String[] {"NumLinea","Quantitat","Unitat","Ingredient"};
    private static DefaultTableModel model = new DefaultTableModel();
    private static JComboBox cboCat;
    private static java.util.List<Plat> llp = new ArrayList();
    private static java.util.List<Categoria> llc = new ArrayList();
    private static java.util.List<Linea_Escandall> llesc = new ArrayList();
    private static EntityManagerFactory emf = null;
    private static EntityManager em = null;
    private static String up = "UP-MySQL";
    private static JButton btnCerca;
    private static JRadioButton btnSi = new JRadioButton("Si");
    private static JRadioButton btnNo = new JRadioButton("No");
    private static JRadioButton btnTots = new JRadioButton("Tots");
    private static JDialog subfinestra;
        
    public static void main(String[] args) {
        comprovaEsquema();
        f= new JFrame("Gestió Escandall");
        afegirElements();
        afegirTaula();
        f.setVisible(true);
        f.pack();
        //f.setResizable(false); 
        f.setLocation(10,300); 
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    
    private static void prepararSubfinestra(Plat p)
    {
        subfinestra = new JDialog(f,true);
        subfinestra.setLocation(15,300);
        // el true és per bloquejar l'accés a altres finestres mentre aquesta està activa
        // afegir elements
                
        JPanel pa2 = new JPanel(new GridLayout(6,2)); // FlowLayout
        JPanel pa1 = new JPanel(); // FlowLayout
        
        
        JLabel nomPlat = new JLabel(p.getCodi() + "    " +p.getDescripcioMD());
        JLabel preuPlat = new JLabel("Preu: " + p.getPreu()+"€");
        
        construirTaulaEscandall();
        
        pa2.add(nomPlat);
        pa2.add(preuPlat);
        pa2.add(taulaEscandall);
        JButton add = new JButton("Afegir");
        JButton delete = new JButton("Eliminar");
        
        JLabel qtat = new JLabel("Quantitat:");
        JTextField txfQuantitat = new JTextField();
        JLabel unitats = new JLabel("Unitats: ");
        JComboBox cboUni = new JComboBox();
        JLabel ingregient = new JLabel("Ingredient: ");
        JComboBox cboIng = new JComboBox();
        
        pa1.add(qtat);
        pa1.add(txfQuantitat);
        pa1.add(unitats);
        pa1.add(cboUni);
        pa1.add(ingregient);
        pa1.add(cboIng);
        
        
        pa1.add(add);
        pa1.add(delete);
        subfinestra.add(pa1,BorderLayout.SOUTH);
        subfinestra.add(pa2,BorderLayout.CENTER);
       
        
        
        
        // crear com a mínim un panell , i afegir-lo dins la subfinestra
        subfinestra.setVisible(false);
        subfinestra.setTitle("Escandall");
        subfinestra.setSize(350,300);
        //subfinestra.pack();
        subfinestra.setResizable(true);
        // centrar-lo al frame
        subfinestra.setLocationRelativeTo(f);
        subfinestra.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        
    }
    
    private static String[][] obtenirMatriuEscandall() {
        String matriuInfo[][] = new String[llesc.size()][columnesEscandall.length];

        for (int i = 0; i < llesc.size(); i++) {
            Linea_Escandall le = llesc.get(i);

            matriuInfo[i][0] = le.getNum()+"";
            matriuInfo[i][1] = le.getQuantitat()+"";
            matriuInfo[i][2] = le.getUnitat().getNom()+"";
            matriuInfo[i][3] = le.getIngredient().getNom()+"";
//            matriuInfo[i][0] = p.getNom(); // TODO GET FOTO
//            matriuInfo[i][3] = p.isDisponible()+"";
//            matriuInfo[i][4] = p.getCategoria()+"";
        }
        return matriuInfo;
    }
    
    private static void construirTaulaEscandall() {
        
        String bdInfo[][] = obtenirMatriuEscandall();
        
        DefaultTableModel modelEscandall = new DefaultTableModel();
        modelEscandall.setColumnIdentifiers(columnesEscandall);
        modelEscandall.setDataVector(bdInfo, columnesEscandall);

        taulaEscandall = new JTable(modelEscandall){
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
                    case 2: case 3: clazz = String.class;
                                    break;
                }
                return clazz;
            }
        };
        
        

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
        
        btnTots.setSelected(true);
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
        btnCerca = new JButton();
        btnCerca.setText("Cercar");
        btnCerca.addActionListener(new GestioBotons());
        centre.add(btnCerca);
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
            matriuInfo[i][3] = p.getCategoria().getColor()+""; // TODO GET FOTO
//            matriuInfo[i][3] = p.isDisponible()+"";
//            matriuInfo[i][4] = p.getCategoria()+"";
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
        taula.setDefaultRenderer(Object.class, new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table,
                    Object value, boolean isSelected, boolean hasFocus,
                    int row, int column) {
                JPanel pane = new JPanel();
                int c = (Integer.parseInt(bdInfo[row][3]));
                Color color = new Color(c);
                JLabel l = new JLabel ((String)value);
                table.setRowHeight(25);
                pane.setSize(200,500);
                pane.setBackground(color);
                pane.add(l);
                
                return pane;
            }
        });

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
            
            if(btnTots.isSelected()){
                
            }else{
                
               
                llp = buscaPlatFiltreAmbDisponibilitat();
                //obtenirMatriu();
                //construirTaula();
                 model.fireTableDataChanged();
                 taula.repaint();
                
            }
            
            
            /*int fila;
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
            }*/
            
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
                    
                    llesc = buscaEscandallPlatPerIdPlat(p.getCodi());
                    
                    prepararSubfinestra(p);
                    subfinestra.setVisible(true);
                    //System.out.println(le.toString());
                }
        }
        
    }
    
    private static java.util.List<Plat> buscaPlatFiltreAmbDisponibilitat(){
        Query q = em.createNamedQuery("trobaPlatsPerCategoriaIDisponibilitat");
        q.setParameter("idCategoria", cboCat.getSelectedIndex()+1);
        boolean disponible = btnSi.isSelected();
        q.setParameter("disponible",disponible );
        java.util.List<Plat> llp = q.getResultList();
        return llp;
        
    }
    
    private static Plat buscaPlatPerNom(String nomPlat){
        Query q = em.createNamedQuery("trobaPlatsPerNom");
        q.setParameter("nom", nomPlat);
        
        Plat p = (Plat) q.getSingleResult();
        
        return p;
    }
    
    private static java.util.List<Linea_Escandall> buscaEscandallPlatPerIdPlat(int idPlat){
        Query q = em.createNamedQuery("trobaEscandallPlatPerId");
        q.setParameter("idPlat", idPlat);
        
        java.util.List<Linea_Escandall> le = q.getResultList();
        
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
