import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class ChargPerso
{

	String[] listeSauv;
	public String choix;


	//éléments graphiques
	public JFrame frame = new JFrame();
	JList liste;
	JButton btnNouvellePartie;
	JButton btnCharger;
	JButton btnAnnuler;

	boolean fini=false;



	public String repSauv;
	ActionListener action;

	public ChargPerso(String chemin)
	{


		repSauv=chemin;

		action = (new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				String bouton=e.getActionCommand();
				if(bouton.equals("Annuler"))
				{
					choix="annul";
				}
				else if(bouton.equals("Charger"))
				{
					choix=(String)liste.getSelectedValue();
					if(choix==null)
					{
						if((dialogue("Aucune sauvegarde sélectionnée\nVoulez-vous commencer une nouvelle partie ?",""))==JOptionPane.YES_OPTION)
						{
							choix="nouvellePartie";
						}
					}
				}
				else if(bouton.equals("Nouvelle Partie"))
				{
					choix="NouvellePartie";
				}
			}
		});

		initComponents();

	}

	public void initComponents()
	{
		//récupération du ContentPane de la fenêtre
		Container conteneur = frame.getContentPane();

		//déclaration des JPanel
		JPanel txtPanel = new JPanel();
		JPanel listPanel = new JPanel();
		JPanel btnPanel = new JPanel();

		//on crée un GridLayout pour les boutons de Droite
		GridLayout layoutBoutons = new GridLayout(6,1);
		btnPanel.setLayout(layoutBoutons);

		//ajout du texte
		txtPanel.add(new JLabel("Selectionnez une Sauvegarde"));

		//ajout du bouton Lancer
		configureBouton("Nouvelle Partie", btnPanel, btnNouvellePartie);
		configureBouton("Charger", btnPanel, btnCharger);
		configureBouton("Annuler", btnPanel, btnAnnuler);


		//ajout de la liste
		recupSauv(new File(repSauv));
		liste = new JList(listeSauv);
		liste.setFixedCellWidth(300);//largeur des lignes fixée à 300
		liste.setSelectionMode(0);//on ne peut sélectionner qu'un seul élément à la fois
		listPanel.add(liste);



		conteneur.setLayout(new BorderLayout(5,5));
		conteneur.add(txtPanel, BorderLayout.NORTH);
		conteneur.add(listPanel, BorderLayout.WEST);
		conteneur.add(btnPanel, BorderLayout.EAST);


		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setTitle("Chargement...");
		frame.pack();
		frame.setLocationRelativeTo(frame.getParent());//centre la fenêtre à la écran
		frame.setVisible(true);

	}//fin initComponents()


	public void configureBouton(String s, JPanel panel,JButton bouton)
	{
		bouton = new JButton(s);
		bouton.setActionCommand(s);
		bouton.addActionListener(action);
		panel.add(bouton);
	}//fin configureBouton()


	//recupère la liste des fichiers finissants par l'extension
	//.xml dans le répertoire passé en paramètre
	public void recupSauv(File repertoire)
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

		listeSauv= new String [nombreFichiers];

		//on récupère la liste de fichiers qui finissent pas l'extension ext
		for(i=0;i<listeFichiers.length;i++)
		{
			if(listeFichiers[i].endsWith(ext)==true)
			{
				listeSauv[j]=listeFichiers[i].substring(0,listeFichiers[i].length()-4);
				j++;
			}
		}
	}//fin recupJeux()


	/**
	 * Ouvre une boite de dialogue avec option OUI/NON
	 * avec comme titre de fenêtre le String titre
	 * et comme texte affiché le String message
	 */
	public int dialogue(String message, String titre)
	{
		return JOptionPane.showConfirmDialog(frame,message,titre,JOptionPane.YES_NO_OPTION);
	}//fin dialogue()


	//main
	public static void main (String args[])
	{
		//ChargPerso select = new ChargPerso("./bob/save/");
	}

}//ChargPerso
