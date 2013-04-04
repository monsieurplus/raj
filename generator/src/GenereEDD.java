/*
 * GenreEDD.java
 *
 * Created on 29 mars 2007, 08:36
 */

/**
 *
 * @author  jeremy.grabowski
 */
import java.awt.* ;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;
import javax.swing.event.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.AbstractTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.io.*;
import java.beans.XMLEncoder;
import java.beans.XMLDecoder;
import java.util.*;


public class GenereEDD extends javax.swing.JFrame {
    
    /** Creates new form GenreEDD */
    public GenereEDD() {
        initComponents();
    }
    private MonModele monModele;
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        jLabel1.setText("G\u00e9n\u00e9ration EDD");

        jLabel2.setText("Nom Fichier");

        jTable1.setModel(new MonModele());
        monModele = (MonModele) jTable1.getModel();
        jScrollPane1.setViewportView(jTable1);

        jButton1.setText("Ajouter une image");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Supprimer une image");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("G\u00e9n\u00e9rer le fichier XML");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .add(28, 28, 28)
                .add(jLabel2)
                .add(27, 27, 27)
                .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 251, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(159, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                .add(20, 20, 20))
            .add(layout.createSequentialGroup()
                .add(21, 21, 21)
                .add(jButton1)
                .add(19, 19, 19)
                .add(jButton2)
                .add(36, 36, 36)
                .add(jButton3)
                .addContainerGap(47, Short.MAX_VALUE))
            .add(layout.createSequentialGroup()
                .add(213, 213, 213)
                .add(jLabel1)
                .addContainerGap(231, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .add(31, 31, 31)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel2)
                    .add(jTextField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(24, 24, 24)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 234, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 16, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jButton1)
                    .add(jButton2)
                    .add(jButton3))
                .addContainerGap())
        );
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
// TODO add your handling code here:
      monModele.genere();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
// TODO add your handling code here:
         String[] choix={"Oui","Non","Annuler"};
           int reponse = JOptionPane.showOptionDialog(this,
				"Etes-vous s�r de vouloir supprimer cette image ?", "Oui",0,
				JOptionPane.PLAIN_MESSAGE,null,choix,choix[0]);
           if(reponse == 0) {
          int [] lesLignes = jTable1.getSelectedRows();
				monModele.suppLigne(lesLignes);}
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
// TODO add your handling code here:
         LigneEDD e = new LigneEDD("","",0,0,0,0);
				monModele.ajoutLigne(e);
    }//GEN-LAST:event_jButton1ActionPerformed
    
    /**
     * @param args the command line arguments
     */
    
        // classe interne qui d�crit le mod�le de table
    class MonModele extends AbstractTableModel {
        private String[] nomsColonnes = {"Nom Image","Chemin Image","Position X", "Position Y", "Taille X", "Taille Y"};
        private ArrayList <LigneEDD> donnees;
        
        public MonModele(){
            donnees = new ArrayList <LigneEDD>();

            
        /* // ajout dun �couteur perso au mod�le
          this.addTableModelListener(new MonEcouteurTable());*/
        }
        public int getColumnCount() {
            return nomsColonnes.length;
        }
        public int getRowCount() {
            return donnees.size();
        }
        public Object getValueAt(int row, int col) {
            if (col==0)  return donnees.get(row).getNom();
            if (col==1)  return donnees.get(row).getChemin();
            else if (col==2) return donnees.get(row).getX();
            else if (col==3) return donnees.get(row).getY();
             else if (col==4) return donnees.get(row).getXx();
            else return donnees.get(row).getYy();
              }
        
        public String getColumnName(int col) {
            return col >=0 ? nomsColonnes[col] : null;
        }
        public Class getColumnClass(int c) {
            return getValueAt(0, c).getClass();
        }
        
           public boolean isCellEditable(int row, int col) {
            return true;
        }
           
           public void ajoutLigne(LigneEDD emp) {
			donnees.add(emp);
			int ligne=donnees.size()-1;
                      //  ajouts.add(emp);
			fireTableChanged(new TableModelEvent(
				this,ligne,ligne,TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT)
			);
		}
           
           public void setValueAt(Object value, int row, int col) {
			if (col==0)
				donnees.get(row).setNom((String)value);
                        if (col==1)
				donnees.get(row).setChemin((String)value);
			else if (col ==2)
				donnees.get(row).setX((Integer)value);
                        else if (col ==3)
				donnees.get(row).setY((Integer)value);
                        else if (col ==4)
				donnees.get(row).setXx((Integer)value);
                        else if (col ==5)
				donnees.get(row).setYy((Integer)value);
           fireTableCellUpdated(row, col);
		}
           
           public void suppLigne(int [] lesLignes) {
			int nb = lesLignes.length;
			if (nb!=0) {
                                
                            ArrayList <LigneEDD> donneesSupprimees = new ArrayList <LigneEDD>();
				for (int i=0;i<nb;i++)
					donneesSupprimees.add(donnees.get(lesLignes[i]));
				donnees.removeAll(donneesSupprimees);
				fireTableRowsDeleted(lesLignes[0],lesLignes[nb-1]);
			}
		}
           
           public void genere(){
               String imgnom = new String("");
               String imgch = new String ("");
               Integer imgx=0;
               Integer imgxx=0;
               Integer imgy=0;
               Integer imgyy=0;
                String nomXML= new String("");
               nomXML=jTextField1.getText();
                int i=0;
                ElementDuDecor elements[] = new ElementDuDecor[20];
                while (i<donnees.size())
                {
                    imgnom = ((LigneEDD)donnees.get(i)).getNom();
                    imgch = ((LigneEDD)donnees.get(i)).getChemin();
                    imgx = ((LigneEDD)donnees.get(i)).getX();
                    imgxx = ((LigneEDD)donnees.get(i)).getXx();
                    imgy = ((LigneEDD)donnees.get(i)).getY();
                    imgyy = ((LigneEDD)donnees.get(i)).getYy();
                   // img = new ElementDuDecor(imgnom,imgch,imgx,imgy,imgxx,imgyy);
                    elements[i]=new ElementDuDecor(imgnom,imgch,imgx,imgy,imgxx,imgyy);
                    i++;
                }
                
                try
		{
			XMLEncoder en = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(nomXML)));
			en.writeObject(elements);
			en.close();

		}

	   	catch(Exception ex1)
	   	{
			ex1.printStackTrace();
			System.out.println(ex1.getMessage());
		}

		System.out.println("**********************");
		System.out.println("*  Fichier XML cree  *");
		System.out.println("**********************");
		System.out.println(nomXML);
               
               
                
                
                
           }
           
        
        
        /*
        // seules les cellules des colonnes 3 et 4 sont modifiables
     
        public void setValueAt(Object value, int row, int col) {
            if (col==3)
                donnees.get(row).setSport((String)value);
            else
                donnees.get(row).setRedouble((Boolean)value);
            fireTableCellUpdated(row, col);
        }
        // insertion en fin de table
        public void ajoutLigne(Individu ind) {
            donnees.add(ind);
            int ligne=donnees.size()-1;
            fireTableChanged(new TableModelEvent(
                    this,ligne,ligne,TableModelEvent.ALL_COLUMNS,TableModelEvent.INSERT)
                    );
        }
        // suppression multiple selon la s�lection
        public void suppLigne(int [] lesLignes) {
            int nb = lesLignes.length;
            if (nb!=0) {
                ArrayList <Individu> donneesSupprimees = new ArrayList <Individu>();
                for (int i=0;i<nb;i++)
                    donneesSupprimees.add(donnees.get(lesLignes[i]));
                donnees.removeAll(donneesSupprimees);
                fireTableRowsDeleted(lesLignes[0],lesLignes[nb-1]);
            }
        } */
    } // fin classe interne MonModele
    
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GenereEDD().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
    
}