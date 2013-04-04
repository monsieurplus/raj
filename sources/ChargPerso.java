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


	//�l�ments graphiques
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
						if((dialogue("Aucune sauvegarde s�lectionn�e\nVoulez-vous commencer une nouvelle partie ?",""))==JOptionPane.YES_OPTION)
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
		//r�cup�ration du ContentPane de la fen�tre
		Container conteneur = frame.getContentPane();

		//d�claration des JPanel
		JPanel txtPanel = new JPanel();
		JPanel listPanel = new JPanel();
		JPanel btnPanel = new JPanel();

		//on cr�e un GridLayout pour les boutons de Droite
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
		liste.setFixedCellWidth(300);//largeur des lignes fix�e � 300
		liste.setSelectionMode(0);//on ne peut s�lectionner qu'un seul �l�ment � la fois
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
		frame.setLocationRelativeTo(frame.getParent());//centre la fen�tre � la �cran
		frame.setVisible(true);

	}//fin initComponents()


	public void configureBouton(String s, JPanel panel,JButton bouton)
	{
		bouton = new JButton(s);
		bouton.setActionCommand(s);
		bouton.addActionListener(action);
		panel.add(bouton);
	}//fin configureBouton()


	//recup�re la liste des fichiers finissants par l'extension
	//.xml dans le r�pertoire pass� en param�tre
	public void recupSauv(File repertoire)
	{
		String [] listeFichiers;
		String ext=new String(".xml");
		int i;
		int nombreFichiers=0;
		int j=0;
		listeFichiers=repertoire.list();


		//on compte le nombre de fois o� on a un fichier qui fini par l'extension ext
		for(i=0;i<listeFichiers.length;i++)
		{
			if(listeFichiers[i].endsWith(ext)==true)
			{
				nombreFichiers++;
			}
		}

		listeSauv= new String [nombreFichiers];

		//on r�cup�re la liste de fichiers qui finissent pas l'extension ext
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
	 * avec comme titre de fen�tre le String titre
	 * et comme texte affich� le String message
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
