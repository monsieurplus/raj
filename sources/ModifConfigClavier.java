import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;

class ModifConfigClavier{

	//variables globales

	ConfigClavier config;

	JFrame frame = new JFrame();

	ActionListener action;
	KeyAdapter keyAdapt;

	boolean enAttente=false;
	int touchePressee=0;
	int indexSelectionne=0;

	JLabel lblDirections;
	JButton btnGauche;			JTextField jtxGauche;
	JButton btnDroite;			JTextField jtxDroite;
	JButton btnHaut;			JTextField jtxHaut;
	JButton btnBas;				JTextField jtxBas;

	JLabel lblJeu;
	JButton btnAction;			JTextField jtxAction;
	JButton btnBilan;			JTextField jtxBilan;

	JLabel lblSauvegarde;
	JButton btnSauvegarder;		JTextField jtxSauvegarder;
	JButton btnCharger;			JTextField jtxCharger;

	JLabel lblMoteur;
	//JButton btnAide;			JTextField jtxAide;
	JButton btnParametres;		JTextField jtxParametres;

	JButton btnOk;				//JButton btnAnnuler;

	JButton boutons[] = new JButton [10];
	int index = 0;
	JTextField champs[] = new JTextField [9];
	int indexJtx = 0;


	//constucteur
	public ModifConfigClavier(ConfigClavier telleConf)
	{
		config=telleConf;

		action = (new ActionListener(){
			public void actionPerformed(ActionEvent e)
			{
				if(!e.getActionCommand().equals("Ok"))
				{
					String clic=e.getActionCommand();
					indexSelectionne = nomToIndex(clic);
					enAttente=true;
					clicables(false);
				}
				else if(e.getActionCommand().equals("Ok"))
				{
					frame.dispose();
				}

			}
		});


		keyAdapt = (new KeyAdapter()
		{//écouteur de clavier
			public void keyPressed(KeyEvent e)
			{//réactions touche appuyée
				if(enAttente)
				{
					touchePressee = e.getKeyCode();
					champs[indexSelectionne].setText(e.getKeyText(touchePressee));
					clicables(true);
					setConfigClavier(indexSelectionne, touchePressee);

					//remise à l'état initial
					enAttente=false;
					touchePressee=0;
					indexSelectionne=0;
				}
			}
		});


		initComponents();
		initJtx();

		//frame.addKeyListener

	}



	public void initComponents()
	{
		//récupération du ContentPane de la fenêtre
		Container conteneur = frame.getContentPane();
		JLayeredPane panel = new JLayeredPane();
		frame.getContentPane().setLayout(new BorderLayout());
		frame.getContentPane().add(panel, BorderLayout.CENTER);

		//label "Directions :"
		JPanel pnlLblDirections = new JPanel();
		lblDirections = new JLabel("Directions :");
		pnlLblDirections.add(lblDirections);
		//mise en page label direction
		panel.add(pnlLblDirections,0);
			pnlLblDirections.setBounds(0,5,75,20);

		//bouton Gauche
		JPanel pnlBtnGauche = new JPanel();
		configureBtn("gauche", pnlBtnGauche, btnGauche);
		//textField Gauche
		JPanel pnlJtxGauche = new JPanel();
		jtxGauche = new JTextField();
		pnlJtxGauche.add(jtxGauche);
		configureJtx(jtxGauche);
		//mise en page gauche
		panel.add(pnlBtnGauche,0);
			pnlBtnGauche.setBounds(0,30,130,35);
		panel.add(pnlJtxGauche,0);
			pnlJtxGauche.setBounds(120,35,200,35);
				jtxGauche.setColumns(10);
				jtxGauche.setEditable(false);

		//bouton Droite
		JPanel pnlBtnDroite = new JPanel();
		configureBtn("droite", pnlBtnDroite, btnDroite);
		//textField Droite
		JPanel pnlJtxDroite = new JPanel();
		jtxDroite = new JTextField();
		pnlJtxDroite.add(jtxDroite);
		configureJtx(jtxDroite);
		//mise en page droite
		panel.add(pnlBtnDroite,0);
			pnlBtnDroite.setBounds(0,65,130,35);
		panel.add(pnlJtxDroite,0);
			pnlJtxDroite.setBounds(120,70,200,35);
				jtxDroite.setColumns(10);
				jtxDroite.setEditable(false);

		//bouton Haut
		JPanel pnlBtnHaut = new JPanel();
		configureBtn("haut", pnlBtnHaut, btnHaut);
		//textField Haut
		JPanel pnlJtxHaut = new JPanel();
		jtxHaut = new JTextField();
		pnlJtxHaut.add(jtxHaut);
		configureJtx(jtxHaut);
		//mise en page haut
		panel.add(pnlBtnHaut,0);
			pnlBtnHaut.setBounds(0,100,130,35);
		panel.add(pnlJtxHaut,0);
			pnlJtxHaut.setBounds(120,105,200,35);
				jtxHaut.setColumns(10);
				jtxHaut.setEditable(false);

		//bouton Bas
		JPanel pnlBtnBas = new JPanel();
		configureBtn("bas", pnlBtnBas, btnBas);
		//textField Bas
		JPanel pnlJtxBas = new JPanel();
		jtxBas = new JTextField();
		pnlJtxBas.add(jtxBas);
		configureJtx(jtxBas);
		//mise en page bas
		panel.add(pnlBtnBas,0);
			pnlBtnBas.setBounds(0,135,130,35);
		panel.add(pnlJtxBas,0);
			pnlJtxBas.setBounds(120,140,200,35);
				jtxBas.setColumns(10);
				jtxBas.setEditable(false);

		//label "Jeu :"
		JPanel pnlLblJeu = new JPanel();
		lblJeu = new JLabel("Jeu :");
		lblJeu.setHorizontalTextPosition(SwingConstants.LEFT);
		pnlLblJeu.add(lblJeu);
		//mise en page label direction
		panel.add(pnlLblJeu,0);
			pnlLblJeu.setBounds(0,170,35,20);

		//bouton Action
		JPanel pnlBtnAction = new JPanel();
		configureBtn("actions", pnlBtnAction, btnAction);
		//textField Action
		JPanel pnlJtxAction = new JPanel();
		jtxAction = new JTextField();
		pnlJtxAction.add(jtxAction);
		configureJtx(jtxAction);
		//mise en page action
		panel.add(pnlBtnAction,0);
			pnlBtnAction.setBounds(0,200,130,35);
		panel.add(pnlJtxAction,0);
			pnlJtxAction.setBounds(120,205,200,35);
				jtxAction.setColumns(10);
				jtxAction.setEditable(false);

		//bouton Bilan
		JPanel pnlBtnBilan = new JPanel();
		configureBtn("bilan", pnlBtnBilan, btnBilan);
		//textField Bilan
		JPanel pnlJtxBilan = new JPanel();
		jtxBilan = new JTextField();
		pnlJtxBilan.add(jtxBilan);
		configureJtx(jtxBilan);
		//mise en page action
		panel.add(pnlBtnBilan,0);
			pnlBtnBilan.setBounds(0,235,130,35);
		panel.add(pnlJtxBilan,0);
			pnlJtxBilan.setBounds(120,240,200,35);
				jtxBilan.setColumns(10);
				jtxBilan.setEditable(false);

		//label "Sauvegarde :"
		JPanel pnlLblSauvegarde = new JPanel();
		lblSauvegarde = new JLabel("Sauvegarde :");
		lblSauvegarde.setHorizontalTextPosition(SwingConstants.LEFT);
		pnlLblSauvegarde.add(lblSauvegarde);
		//mise en page label direction
		panel.add(pnlLblSauvegarde,0);
			pnlLblSauvegarde.setBounds(0,270,80,20);

		//bouton Sauvegarder
		JPanel pnlBtnSauvegarder = new JPanel();
		configureBtn("sauvegarder", pnlBtnSauvegarder, btnSauvegarder);
		//textField Sauvegarder
		JPanel pnlJtxSauvegarder = new JPanel();
		jtxSauvegarder = new JTextField();
		pnlJtxSauvegarder.add(jtxSauvegarder);
		configureJtx(jtxSauvegarder);
		//mise en page action
		panel.add(pnlBtnSauvegarder,0);
			pnlBtnSauvegarder.setBounds(0,300,130,35);
		panel.add(pnlJtxSauvegarder,0);
			pnlJtxSauvegarder.setBounds(120,305,200,35);
				jtxSauvegarder.setColumns(10);
				jtxSauvegarder.setEditable(false);

		//bouton Charger
		JPanel pnlBtnCharger = new JPanel();
		configureBtn("charger", pnlBtnCharger, btnCharger);
		//textField Charger
		JPanel pnlJtxCharger = new JPanel();
		jtxCharger = new JTextField();
		pnlJtxCharger.add(jtxCharger);
		configureJtx(jtxCharger);
		//mise en page action
		panel.add(pnlBtnCharger,0);
			pnlBtnCharger.setBounds(0,335,130,50);
		panel.add(pnlJtxCharger,0);
			pnlJtxCharger.setBounds(120,340,200,35);
				jtxCharger.setColumns(10);
				jtxCharger.setEditable(false);

		//bouton Parametres
		JPanel pnlBtnParametres = new JPanel();
		configureBtn("paramètres", pnlBtnParametres, btnParametres);
		//textField Parametres
		JPanel pnlJtxParametres = new JPanel();
		jtxParametres = new JTextField();
		pnlJtxParametres.add(jtxParametres);
		configureJtx(jtxParametres);
		//mise en page action
		panel.add(pnlBtnParametres,0);
			pnlBtnParametres.setBounds(0,370,130,50);
		panel.add(pnlJtxParametres,0);
			pnlJtxParametres.setBounds(120,375,200,35);
				jtxParametres.setColumns(10);
				jtxParametres.setEditable(false);

		//bouton Ok
		JPanel pnlBtnOk = new JPanel();
		configureBtn("Ok", pnlBtnOk, btnOk);
		//mise en page action
		panel.add(pnlBtnOk,0);
			pnlBtnOk.setBounds(180,430,130,50);


		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setTitle("Redéfinition des touches...");
		frame.setSize(300,500);
		frame.setResizable(false);
		frame.setLocationRelativeTo(frame.getParent());//centre la fenêtre à la écran
		frame.setVisible(true);
		frame.addKeyListener(keyAdapt);

	}

	public void initJtx()
	{
		jtxGauche.setText(KeyEvent.getKeyText(config.gauche));
		jtxDroite.setText(KeyEvent.getKeyText(config.droite));
		jtxHaut.setText(KeyEvent.getKeyText(config.haut));
		jtxBas.setText(KeyEvent.getKeyText(config.bas));

		jtxAction.setText(KeyEvent.getKeyText(config.action));
		jtxBilan.setText(KeyEvent.getKeyText(config.bilanPerso));

		jtxSauvegarder.setText(KeyEvent.getKeyText(config.sauvegarder));
		jtxCharger.setText(KeyEvent.getKeyText(config.charger));

		jtxParametres.setText(KeyEvent.getKeyText(config.parametres));
	}

	//à partir d'un index du tableau
	public int nomToIndex(String nom)
	{
		int i=0;
		while (!boutons[i].getText().equals(nom))
		{i++;}
		return i;
	}

	public void selectUnSeul(int iB)
	{
		int i;
		for(i=0;i<boutons.length;i++)
		{
			if(i!=iB)
			{
				boutons[i].setEnabled(false);
			}
		}
	}

	public void clicables(boolean etat)
	{
		int i;
		for(i=0;i<boutons.length;i++)
		{
			boutons[i].setEnabled(etat);
		}
	}

	public void setConfigClavier(int indexSelec, int valeurTouche)
	{
		switch(indexSelec)
		{
			case 0:
				config.gauche=valeurTouche;
				break;
			case 1:
				config.droite=valeurTouche;
				break;
			case 2:
				config.haut=valeurTouche;
				break;
			case 3:
				config.bas=valeurTouche;
				break;
			case 4:
				config.action=valeurTouche;
				break;
			case 5:
				config.bilanPerso=valeurTouche;
				break;
			case 6:
				config.sauvegarder=valeurTouche;
				break;
			case 7:
				config.charger=valeurTouche;
				break;
			case 8:
				config.parametres=valeurTouche;
			default:
				break;
		}

	}

	public void configureBtn(String s, JPanel panel,JButton bouton)
	{
		bouton = new JButton(s);
		bouton.setActionCommand(s);
		bouton.addActionListener(action);
		bouton.addKeyListener(keyAdapt);
		panel.add(bouton);
		boutons[index]=bouton;
		index++;
	}//fin configureBouton()

	public void configureJtx(JTextField jtx)
	{
		jtx.addKeyListener(keyAdapt);
		champs[indexJtx]=jtx;
		indexJtx++;
	}




	//main
	public static void main (String args[])
	{
		ModifConfigClavier select = new ModifConfigClavier(new ConfigClavier());
	}
}
