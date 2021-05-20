/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gestioescandalls;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        
    public static void main(String[] args) {
        f= new JFrame("Gestió Escandall");
        afegirElements();
        afegirTaula();
        f.setVisible(true);
        f.pack();
        f.setResizable(false); // no permetre modificar la mida de la finestra
        f.setLocation(10,300); // ubicar l'aplicació al monitor tant pixels x,y respecte el punt 0,0, superior,esquerra
        // +x, més a la dreta
        // +y, més avall
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private static void afegirElements()
    {
        GridLayout g = new GridLayout(1,3);
        esq = new JPanel();
        // quin LayoutManager ha de serguir?
        //  GridLayout d'una sola columna i 3 files
        // cada fila serà un JPanel individual amb FlowLayout
        
        
        centre = new JPanel();
        // quina LayoutManager ha de seguir?
        
        nom = new JTextField(12);
        cognom = new JTextField(20);
        edat = new JTextField(3);
        
        /*add = new JButton("Afegir");
        remove = new JButton("Esborrar");
        edit = new JButton("Editar");
        GestioBotons gestor = new GestioBotons();
        add.addActionListener(gestor);
        remove.addActionListener(gestor);
        edit.addActionListener(gestor);*/
        
        cboCat = new JComboBox();
        
        /*JPanel pNom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pCognom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JPanel pEdat = new JPanel(new FlowLayout(FlowLayout.LEFT));
        pNom.add(new JLabel("Nom: "));
        pNom.add(nom);
        pNom.setAlignmentX(Component.LEFT_ALIGNMENT);
        pCognom.add(new JLabel("Cognom: "));
        pCognom.add(cognom);
        pEdat.add(new JLabel("Edat: "));
        pEdat.add(edat);*/
        
        
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
    
    private static void afegirTaula()
    {
        model = new DefaultTableModel();
        taula = new JTable(model) {
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
                    case 2:         clazz = Integer.class;
                                    break;
                }
                return clazz;
            }
        }; // definició / construcció de la taula
        // afegir la fila amb els titols
        
        for (int i=0;i<columnes.length;i++)
        {
            model.addColumn(columnes[i]);
        }
        
        taula.getColumnModel().getColumn(0).setPreferredWidth(170);
        taula.getColumnModel().getColumn(1).setPreferredWidth(130);
        taula.getColumnModel().getColumn(2).setPreferredWidth(50);
        
        
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
                    
                    String n = (String) taula.getValueAt(fila, 1);
                    String c = (String) taula.getValueAt(fila, 0);
                    String ed =(String) taula.getValueAt(fila, 2);
                    edat.setText(ed);
                    nom.setText(n);
                    cognom.setText(c);
                }
        }
        
    }
    
}
