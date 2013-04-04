import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.beans.XMLEncoder;

class SauvPerso
{
	//chemin du dossier contenant les sauvegardes et tableau qui servira � stocker les nom des fichiers
	public String repSauv;
	String[] listeSauv;

	String nomFichier;
	Perso aSauver;

	//�l�ments graphiques
	public JFrame frame = new JFrame();
	JList liste;
	JButton btnSauvegarder;
	JButton btnAnnuler;
	JTextField jtxNomSauvegarde;

	boolean fini=false;

	//ActionListener des Boutons
	ActionListener action;
	ListSelectionListener actionList;



	public SauvPerso(String chemin, Perso pPerso)
	{
		repSauv=chemin;
		aSauver=pPerso;

		actionList = (new ListSelectionListener(){

			public void valueChanged(ListSelectionEvent e)
			{
				jtxNomSauvegarde.setText((String)liste.getSelectedValue());
			}
		});

		action = (new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(e.getActionCommand().equals("Sauvegarder"))
				{
					nomFichier=(String)jtxNomSauvegarde.getText();
					if(nomFichier.equals("null"))
					{
						message("Vous devez sp�cifier un nom de sauvegarde");
					}
					else
					{
						if((nomFichier.equals("annul"))||(nomFichier.equals("nouvellePartie")))
						{
							message("Ce nom de sauvegarde est r�serv�\nessayez un autre nom");
						}
						else
						{
							encodeXML(repSauv+nomFichier+".xml");
							fini=true;
							frame.dispose();
						}
					}
				}
				else if(e.getActionCommand().equals("Annuler"))
				{
					fini=true;
					frame.dispose();
				}
			}
		});

		initComponents();

	}//fin SauvPerso()


	public void initComponents()
	{
		//r�cup�ration du ContentPane de la fen�tre
		Container conteneur = frame.getContentPane();

		//d�claration des JPanel
		JPanel txtPanel = new JPanel();
		JPanel lstPanel = new JPanel();
		JPanel btnPanel = new JPanel();
		JPanel jtxPanel = new JPanel();

		//on cr�e un GridLayout pour les boutons de Droite
		GridLayout layoutBoutons = new GridLayout(6,1);
		btnPanel.setLayout(layoutBoutons);


		//ajout du texte
		txtPanel.add(new JLabel("Selectionnez une Sauvegarde ou sp�cifiez un nom pour une nouvelle Sauvegarde"));

		//ajout des boutons
		configureBouton("Sauvegarder", btnPanel, btnSauvegarder);
		configureBouton("Annuler", btnPanel, btnAnnuler);


		//ajout de la liste
		recupSauv(new File(repSauv));
		liste = new JList(listeSauv);
		liste.setFixedCellWidth(300);//largeur des lignes fix�e � 300
		liste.setSelectionMode(0);//on ne peut s�lectionner qu'un seul �l�ment � la fois
		liste.addListSelectionListener(actionList);
		lstPanel.add(liste);

		//ajout du JTextField
		jtxNomSauvegarde=new JTextField();
		jtxPanel.add(jtxNomSauvegarde);


		conteneur.setLayout(new BorderLayout(5,5));
		conteneur.add(txtPanel, BorderLayout.NORTH);
		conteneur.add(lstPanel, BorderLayout.CENTER);
		conteneur.add(jtxNomSauvegarde, BorderLayout.SOUTH);
		conteneur.add(btnPanel, BorderLayout.EAST);


		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.setTitle("Sauvegarder...");
		frame.pack();
		frame.setLocationRelativeTo(frame.getParent());//centre la fen�tre � la �cran
		frame.setVisible(true);

	}//fin initComponents()


	public boolean fini()
	{
		while(!fini)
		{}
		return fini;
	}


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


	public void encodeXML(String nomDuFichier)
	{
		try
		{
			XMLEncoder en = new XMLEncoder(new BufferedOutputStream(new FileOutputStream(nomDuFichier)));
			en.writeObject(aSauver);
			en.close();
		}
	   	catch(Exception ex1)
	   	{
			message("Erreur de Sauvegarde");
			ex1.printStackTrace();
			System.out.println(ex1.getMessage());
		}
		message("Sauvegarde r�ussie");
	}


	/**
	 * Ouvre une boite de dialogue simple
	 * qui affiche le String message et un bouton OK
	 */
	public void message(String message)
	{
		JOptionPane.showMessageDialog(frame, message);
	}//fin message()



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
		//SauvPerso select = new SauvPerso();
	}

}//SauvPerso