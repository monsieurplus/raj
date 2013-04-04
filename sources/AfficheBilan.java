import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.beans.XMLEncoder;

class AfficheBilan
{
	//chemin du dossier contenant les sauvegardes et tableau qui servira à stocker les nom des fichiers

	String[] listeCarac;

	Perso perso;

	//éléments graphiques
	public JFrame frame = new JFrame();
	JList liste;
	JButton btnOk;


	//ActionListener des Boutons
	ActionListener action;
	boolean modeDev;


	public AfficheBilan(Perso pPerso,boolean telMode)
	{
		modeDev=telMode;
		perso=pPerso;

		action = (new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				frame.dispose();
			}
		});


		initComponents();

	}//fin SauvPerso()


	public void initComponents()
	{
		//récupération du ContentPane de la fenêtre
		Container conteneur = frame.getContentPane();

		//déclaration des JPanel
		JPanel txtPanel = new JPanel();
		JPanel lstPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		//JPanel jtxPanel = new JPanel();

		//on crée un GridLayout pour les boutons de Droite
		GridLayout layoutBoutons = new GridLayout(6,1);
		btnPanel.setLayout(layoutBoutons);


		//ajout du texte
		txtPanel.add(new JLabel("Caractéristiques de "+perso.nom));

		//ajout des boutons
		configureBouton("Ok", btnPanel, btnOk);


		//ajout de la liste
		initListe();
		liste = new JList(listeCarac);
		liste.setFixedCellWidth(300);//largeur des lignes fixée à 300
		liste.setSelectionMode(0);//on ne peut sélectionner qu'un seul élément à la fois
		lstPanel.add(liste);

		//ajout du JTextField
		//jtxNomSauvegarde=new JTextField();
		//jtxPanel.add(jtxNomSauvegarde);


		conteneur.setLayout(new BorderLayout(5,5));
		conteneur.add(txtPanel, BorderLayout.NORTH);
		conteneur.add(lstPanel, BorderLayout.CENTER);
		//conteneur.add(jtxNomSauvegarde, BorderLayout.SOUTH);
		conteneur.add(btnPanel, BorderLayout.EAST);


		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setTitle("Caractéristiques...");
		frame.pack();
		frame.setLocationRelativeTo(frame.getParent());//centre la fenêtre à la écran
		frame.setVisible(true);

	}//fin initComponents()


	public void initListe()
	{
		if(!modeDev)
		{
			listeCarac = new String[perso.nomCarac.length];
			int i;
			for(i=0;i<listeCarac.length;i++)
			{
				listeCarac[i]=perso.nomCarac[i]+"  -  "+perso.carac[i];
			}
		}
		else
		{
			listeCarac = new String[perso.carac.length];
			int nbCaracPublique=perso.nomCarac.length;
			int i;
			for(i=0;i<listeCarac.length;i++)
			{
				if(i<nbCaracPublique)
				{
					listeCarac[i]=i+"  -  "+perso.nomCarac[i]+"  -  "+perso.carac[i];
				}
				else
				{
					listeCarac[i]=i+"  -  "+perso.carac[i];
				}
			}
		}
	}


	public void configureBouton(String s, JPanel panel,JButton bouton)
	{
		bouton = new JButton(s);
		bouton.setActionCommand(s);
		bouton.addActionListener(action);
		panel.add(bouton);
	}//fin configureBouton()



	//main
	public static void main (String args[])
	{
		//SauvPerso select = new SauvPerso();
	}

}//SauvPerso