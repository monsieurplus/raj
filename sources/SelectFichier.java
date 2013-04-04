import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class SelectFichier implements ActionListener
{
	String [] listeJeux;
	String selected;

	//elements graphiques
	public JFrame frame = new JFrame();
	JComboBox combo;
	JButton bouton;
	JCheckBox checkDev;

	public SelectFichier()
	{

		recupJeux(new File("."));

		if(listeJeux.length==0){message("Aucun jeu n'a été détecté");System.exit(0);}
		else
		{
			initComponents();
		}
	}



	public String getChemin()
	{
		return selected;
	}//fin exec()

	public boolean getDev()
	{
		return checkDev.isSelected();
	}

	public void initComponents()
	{
		Container conteneur = frame.getContentPane();

		//déclaration des JPanel
		JPanel txtPanel = new JPanel();
		JPanel comboPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		JPanel cbxPanel = new JPanel();

		//ajout du texte
		txtPanel.add(new JLabel("Selectionnez un Jeu"));

		//ajout du bouton Lancer
		configureBouton("Lancer", btnPanel);

		//ajout du menu déroulant
		combo = new JComboBox();
		configureCombo();
		comboPanel.add(combo);

		//ajout de la checkBox
		checkDev = new JCheckBox("mode Développeur");
		cbxPanel.add(checkDev);

		conteneur.setLayout(new BorderLayout(5,5));
		conteneur.add(txtPanel, BorderLayout.NORTH);
		conteneur.add(btnPanel, BorderLayout.EAST);
		conteneur.add(comboPanel, BorderLayout.WEST);
		conteneur.add(cbxPanel, BorderLayout.SOUTH);


		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(frame.getParent());//centre la fenêtre à la écran
        frame.setVisible(true);

	}//fin initComponents()



	public void configureCombo()
	{
		int i=0;

		while(i<listeJeux.length)
		{
			combo.addItem(listeJeux[i].substring(0,listeJeux[i].length()-4));//on affiche le nom des fichiers xml sans leur extension ".xml"
			i++;
		}
	}//fin configureCombo()





	//cette fonction ajoute un bouton contenant le texte s, dans le JPanel panel
	//et lui imlémente l'ActionListener de la classe
	public void configureBouton(String s, JPanel panel)
	{
		bouton = new JButton(s);
		bouton.setActionCommand(s);
		bouton.addActionListener(this);
		panel.add(bouton);
	}//fin configureBouton()


	public void actionPerformed(ActionEvent e)
	{
		String nom = e.getActionCommand();

		if (nom.equals("Lancer"))
		{
			String choix;
			choix=(String)combo.getSelectedItem();
			selected=new String(choix+".xml");
		}
	}//fin actionPerformed()



	//recupère la liste des fichiers finissants par l'extension
	//.xml dans le répertoire passé en paramètre
	public void recupJeux(File repertoire)
	{
		String [] listeFichiers;
		String ext=new String(".xml");
		int i;
		int nombreFichiers=0;
		int j=0;
		listeFichiers=repertoire.list();


		//on compte le nombre de fois où on a un fichier qui fini par l'extension ext
		for(i=0;i<listeFichiers.length;i++)
		{
			if(listeFichiers[i].endsWith(ext)==true)
			{
				nombreFichiers++;
			}
		}

		listeJeux= new String [nombreFichiers];

		//on récupère la liste de fichiers qui finissent pas l'extension ext
		for(i=0;i<listeFichiers.length;i++)
		{
			if(listeFichiers[i].endsWith(ext)==true)
			{
				listeJeux[j]=listeFichiers[i];
				j++;
			}
		}
	}//fin recupJeux()


	/**
	 * Ouvre une boite de dialogue simple
	 * qui affiche le String message et un bouton OK
	 */
	public void message(String message)
	{
		JOptionPane.showMessageDialog(frame, message);
	}//fin message()



	//main
	public static void main (String args[])
	{
		SelectFichier select = new SelectFichier();
	}

}