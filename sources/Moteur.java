import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import java.lang.*;
import java.beans.XMLDecoder;
import java.awt.MediaTracker;

class Moteur
{
	//contient les informations spécifiques au jeu
	public Jeu jeu;

	//contient les informations sur le joueur
	public Perso pPerso;

	//différentes images du personnage
	public ImageIcon maga= new ImageIcon ();//image pour déplacement gauche
	public ImageIcon madr= new ImageIcon ();//image pour déplacement droite
	public ImageIcon maha= new ImageIcon ();//image pour déplacement haut
	public ImageIcon maba= new ImageIcon ();//image pour déplacement bas
	public ImageIcon imga= new ImageIcon ();//image pour immobile gauche
	public ImageIcon imdr= new ImageIcon ();//image pour immobile droite
	public ImageIcon imha= new ImageIcon ();//image pour immobile haut
	public ImageIcon imba= new ImageIcon ();//image pour immobile bas

	//JLabel contenant le personnage principal
	public JLabel perso;

	//fenêtre du jeu
	public JFrame frame;

	//futur conteneur du niveau
	public Niveau niveau;

	//futur conteneur des éléments de décor
	ElementDuDecor elements[];

	//futur conteneur des obstacles du décor
	Obstacle obstacles[];

	//futur conteneur des evenements
	Evenement evenements[];

	//tableau de JLabel qui contiendra tout les JLabel affichés afin
	//de pouvoir arrêter de les afficher à chaque changement de niveau
	JLabel labels[];

	//ajout d'un conteneur de type JLayeredPane
	public JLayeredPane layeredPane = new JLayeredPane();


	//booléens qui mémorisent si une touche est pressée
	public boolean dirH,dirB,dirG,dirD;
	/*	dirH : haut
		dirB : bas
		dirG : gauche
		dirD : droite
	*/
	//Thread qui gère le clavier
	Thread clavier;

	/*** affichage de JtextArea ***/
	JTextArea txt;//le JTextArea txt servira à afficher des messages ponctuels et uniques
	boolean txtBool=false;//txtBool permet de savoir si un message ponctuel est affiché

	/*** affichage d'Images ponctuelles ***/
	Thread action = new Thread();//thread pour les actions en gifs
	public JLabel image;//le JLabel image servira à afficher des gifs ponctuels
	ImageIcon icon;//le ImageIcon icon servira à recevoir le images gifs pontuelles

	public boolean imageDurBool=false;//imageBool permet de savoir si un gif ponctuel est affiché
	boolean imagePoncBool=false;




	//booleen qui spécifie si un opération de sauvegarde est en cours
	boolean operSauv=false;
	//fenêtres de Sauvegarde et de Chargement
	SauvPerso fenSauv;
	ChargPerso fenCharg;

	//booleen qui bloque le déplacement du perso
	public boolean pause = false;

	//booleen qui spécifie si l'on est en mode Développeur ou non
	public boolean modeDev = false;

	//chemin du fichier XML où sont les données du niveau
	String niv;



	//on passe au moteur en argument le chemin du fichier contenant les infos sur le jeu à exécuter
	//							  et un booleen qui précise si le jeu doit être exécuté en mode développeur ou non
	public Moteur(String fichierJeu, boolean mode)
	{

		modeDev = mode;
		//chargement du fichier XML contenant les informations du Jeu
		chargementJeu(fichierJeu);

		pPerso=jeu.persoDefaut;
		//chargement du Perso contenu dans pPerso
		chargementPerso();

		//création de la fenêtre
		JFrame frame = new JFrame();

		//configuration de la fenêtre
		frame.setSize(jeu.xResolution,jeu.yResolution);
		frame.setTitle(jeu.nomDuJeu);
		frame.setResizable(false);
		frame.setUndecorated(true);//enlève les bordures de la fenêtre

		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout());
		//fin configuration de la fenêtre

		//ajout du JLayeredPane à la JFrame
		frame.getContentPane().add(layeredPane, BorderLayout.CENTER);

		//insertion du personnage
		perso.setBounds(pPerso.x,pPerso.y,maba.getIconWidth(),maba.getIconHeight());
		layeredPane.add(perso,0);
		layeredPane.setLayer(perso,(perso.getY()+((perso.getSize()).height)));

		//création du thread qui gère les déplacements du personnage selon les touches pressées
		clavier = new Clavier(this);

		frame.setLocationRelativeTo(frame.getParent());//centre la fenêtre à la écran
		frame.setAlwaysOnTop(false);//toujours au premier plan
		frame.setVisible(true);
		//chargement de toute les données du niveau contenu dans un fichier XML au chemin niv (niv est un String)
		chargement(niv);
		//affichage des éléments chargés
		affichage();


		//***  gestion souris
		layeredPane.addMouseListener(new MouseAdapter()
		{
			String msg;
			int x,y;
			public void mousePressed(MouseEvent e)
			{
				msg=null;
				x=e.getX();
				y=e.getY();
				if(modeDev)msg=("X="+e.getX()+" Y="+e.getY());
			}

			public void mouseReleased(MouseEvent f)
			{
				if(modeDev)
				{
					//si la position de la souris a changé entre l'appui et le relachement du bouton
					//on affiche les deux
					// cela permet de tracer des carrés et d'avoir leurs coordonnées
					if(!( (x==f.getX()) && (y==f.getY()) ))
					{
						msg=msg+"\n"+("X="+f.getX()+" Y="+f.getY());
					}
					message(msg);
				}
			}

		});
		//fin MouseListener()


		//***	gestion clavier
		frame.addKeyListener(new KeyAdapter()
		{//écouteur de clavier
			public void keyPressed(KeyEvent e)
			{//réactions touche appuyée

				if (e.getKeyCode()==pPerso.touches.gauche)//touche : gauche
				{
					dirG = pause ? false : true;
				}
				if (e.getKeyCode()==pPerso.touches.droite)//touche : droite
				{
					dirD = pause ? false : true;
				}
				if (e.getKeyCode()==pPerso.touches.haut)//touche : haut
				{
					dirH = pause ? false : true;
				}
				if (e.getKeyCode()==pPerso.touches.bas)//touche : bas
				{
					dirB = pause ? false : true;
				}
				if (e.getKeyCode()==pPerso.touches.action)//touche : action
				{
					if(evenements!=null)verifEVT();
				}
				if (e.getKeyCode()==pPerso.touches.bilanPerso)//touche : bilan
				{
					AfficheBilan fen = new AfficheBilan (pPerso,modeDev);
				}
				if (e.getKeyCode()==pPerso.touches.parametres)//touche : parametres (configuration du clavier)
				{
					pause=true;
					ModifConfigClavier modif = new ModifConfigClavier(pPerso.touches);
					pause=false;
				}
				if (e.getKeyCode()==KeyEvent.VK_ESCAPE)//touche : échap
				{
					dirH=dirB=dirG=dirD=false;
					if(dialogue("Voulez-vous vraiment quitter ?","Quitter?")==JOptionPane.YES_OPTION)System.exit(0);
				}
				if (e.getKeyCode()==pPerso.touches.charger)
				{
					if(!operSauv)
					{
						operSauv=true;
						chargementSauvegarde();
						operSauv=false;
					}
				}
				if (e.getKeyCode()==pPerso.touches.sauvegarder)
				{
					if(!operSauv)
					{
						operSauv=true;
						pPerso.x=perso.getX();
						pPerso.y=perso.getY();
						fenSauv = new SauvPerso(jeu.cheminDossier+jeu.repSauv, pPerso);
						operSauv=false;
					}
				}


			}

			public void keyReleased(KeyEvent f)
			{//réactions touche relachée
				if (f.getKeyCode()==pPerso.touches.gauche)//touche : gauche
				{
					dirG=false;
				}
				if (f.getKeyCode()==pPerso.touches.droite)//touche : droite
				{
					dirD=false;
				}
				if (f.getKeyCode()==pPerso.touches.haut)//touche : haut
				{
					dirH=false;
				}
				if (f.getKeyCode()==pPerso.touches.bas)//touche : bas
				{
					dirB=false;
				}
				//*** gestion des événements et de leur affichage ***//
				if(txtBool){layeredPane.remove(txt);txtBool=false;affichage();}
				if(imagePoncBool){layeredPane.remove(image);imagePoncBool=false;affichage();}

				//fin gestion des événements et de leur affichage
			}


		});//fin KeyListener()

	}//fin Jeu()


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
	 * avec comme titre de fenêtre le String titre
	 * et comme texte affiché le String message
	 */
	public int dialogue(String message, String titre)
	{
		return JOptionPane.showConfirmDialog(frame,message,titre,JOptionPane.YES_NO_OPTION);
	}//fin dialogue()


	/**
	 * Charge les paramètres du Jeu lancé à partir du fichier XML
	 */
	public void chargementJeu(String cheminJeu)
	{
		try
		{
			XMLDecoder de = new XMLDecoder(new BufferedInputStream(new FileInputStream(cheminJeu)));
			jeu=(Jeu) de.readObject();
			de.close();
		}
		catch (Exception ex2)
		{
			if(modeDev)message("XML: "+cheminJeu+"\nSynthaxe incorrecte\n Exception :\n"+ex2.getMessage());
		}//fin de la dé-sérialisation XML du Jeu
	}//fin chargementJeu()


	/**
	 * Charge les image qui représentent le Perso dans ces différents états
	 * récupère le chemin du Niveau dans lequel se trouve le perso
	 * initialise le perso dans la position de face
	 */
	public void chargementPerso()
	{
		//récupération des images représentant le personnage
		maga= new ImageIcon (jeu.cheminDossier+pPerso.maga);
		madr= new ImageIcon (jeu.cheminDossier+pPerso.madr);
		maha= new ImageIcon (jeu.cheminDossier+pPerso.maha);
		maba= new ImageIcon (jeu.cheminDossier+pPerso.maba);
		imga= new ImageIcon (jeu.cheminDossier+pPerso.imga);
		imdr= new ImageIcon (jeu.cheminDossier+pPerso.imdr);
		imha= new ImageIcon (jeu.cheminDossier+pPerso.imha);
		imba= new ImageIcon (jeu.cheminDossier+pPerso.imba);

		//récupération du chemin du niveau dans lequel est le perso
		niv=pPerso.cheminNiveau;

		//par défaut au début du jeu le perso apparait immobiile de face
		perso=new JLabel(imba);

	}//fin chargementPerso()


	public void chargementSauvegarde()
	{
		fenCharg=new ChargPerso(jeu.cheminDossier+jeu.repSauv);
		SynchroCharg synchro = new SynchroCharg(this,fenCharg);
	}//fin chargementSauvegarde()



	public void decodagePerso(String cheminPerso)
	{
		try
		{
			XMLDecoder de = new XMLDecoder(new BufferedInputStream(new FileInputStream(jeu.cheminDossier+jeu.repSauv+cheminPerso)));
			pPerso=(Perso) de.readObject();
			de.close();
		}
		catch (Exception ex2)
		{
			if(modeDev)message("XML: "+jeu.cheminDossier+jeu.repSauv+cheminPerso+"\nSynthaxe incorrecte\n Exception :\n"+ex2.getMessage());
		}//fin de la dé-sérialisation XML du niveau
	}



	/**
	 * Lance les procédures de :
	 *	vidage() du Niveau en cours
	 *	chargement() du prochain du prochain Niveau
	 *	affichage() des elements chargés
	 */
	public void changementNiveau()
	{
		String niv=pPerso.cheminNiveau;
		vidage();
		chargement(niv);
		affichage();
		if(evenements!=null)niveauEVT();
	}//fin changementNiveau()


	/**
	 * Lance les procédures de :
	 *	vidage() du Niveau en cours
	 *	chargement() du prochain du prochain Niveau
	 *	affichage() des elements chargés
	 */
	public void changementNiveau(int xNouvellePos, int yNouvellePos)
	{
		String niv=pPerso.cheminNiveau;
		perso.setLocation(pPerso.x,pPerso.y);
		vidage();
		chargement(niv);
		affichage();
		perso.setLocation(xNouvellePos,yNouvellePos);
		if(evenements!=null)niveauEVT();
	}//fin changementNiveau()


	/**
	 * chargement le Niveau à partir u fichier XML niv
	 *	puis charge les Obstacles, Evenements, ElementsDuDecor
	 *	si leur adresse est spécifiée dans le fichier XML
	 */
	public void chargement(String niv)
	{
		try
		{
			XMLDecoder de = new XMLDecoder(new BufferedInputStream(new FileInputStream(jeu.cheminDossier+niv)));
			niveau=(Niveau) de.readObject();
			de.close();
		}
		catch (Exception ex2)
		{
			if(modeDev)message("XML: "+jeu.cheminDossier+niv+"\nSynthaxe incorrecte\n Exception :\n"+ex2.getMessage());
		}//fin de la dé-sérialisation XML du niveau

		if (niveau.cheminEDD!=null)//si un fichier d'Elements Du Decor a été spécifié
		{
			//dé-sérialisation du fichier XML contenant le décor
			try
			{
				XMLDecoder de = new XMLDecoder(new BufferedInputStream(new FileInputStream(jeu.cheminDossier+niveau.cheminEDD)));
				elements=(ElementDuDecor[]) de.readObject();
				de.close();
			}
			catch (Exception ex2)
			{
				if(modeDev)message("XML: "+jeu.cheminDossier+niveau.cheminEDD+"\nSynthaxe incorrecte\n Exception :\n"+ex2.getMessage());
			}//fin de la dé-sérialisation XML du décor
		}

		if (niveau.cheminOBS!=null)//si un fichier d'Obstacles a été spécifié
		{
			//dé-sérialisation du fichier XML contenant les obstacles
			try
			{
				XMLDecoder de = new XMLDecoder(new BufferedInputStream(new FileInputStream(jeu.cheminDossier+niveau.cheminOBS)));
				obstacles=(Obstacle[]) de.readObject();
				de.close();
			}
			catch (Exception ex2)
			{
				if(modeDev)message("XML: "+jeu.cheminDossier+niveau.cheminOBS+"\nSynthaxe incorrecte\n Exception :\n"+ex2.getMessage());
			}//fin de la dé-sérialisation des obstacles
		}

		if (niveau.cheminEVT!=null)//si un fichier d'Evenements a été spécifié
		{
			//dé-sérialisation du fichier XML contenant les événements
			try
			{
				XMLDecoder de = new XMLDecoder(new BufferedInputStream(new FileInputStream(jeu.cheminDossier+niveau.cheminEVT)));
				evenements=(Evenement[]) de.readObject();
				de.close();
			}
			catch (Exception ex2)
			{
				if(modeDev)message("XML: "+jeu.cheminDossier+niveau.cheminEVT+"\nSynthaxe incorrecte\n Exception :\n"+ex2.getMessage());
			}//fin de la dé-sérialisation des événements
		}
	}//fin de chargement()


	/**
	 * cette fonction vide le layeredPane
	 * initialise tout les éléments chargés à partir du xml
	 *	niveau
	 *	elements
	 *	obstacles
	 *	evenements
	 */
	public void vidage()
	{
		//reset des Composants du layeredPane sauf le perso
		layeredPane.removeAll();
		//tout les composant du LayeredPane ont été vidés, même le perso, on le réinsère donc
		layeredPane.add(perso);

		//reset des Element
		elements=null;
		//reset du Niveau
		niveau=null;
		//reset des Obstacle
		obstacles=null;
		//reset des Evenement
		evenements=null;
	}//fin vidage()


	/**
	 * cette fonction insère tout les éléments du décor tu tableau elements
	 * en leur donnant le bon layer sur le layeredPane
	 */
	public void affichage()
	{
		//on règle la taille du tableau labels[] selon le nombre d'éléments
		labels=new JLabel[elements.length];

		//tempLabel,tempElement: éléments temporaires servant dans la boucle d'insertion des éléments
		JLabel tempLabel;
		ElementDuDecor tempElement;


		//insertion du fond
		tempElement=elements[elements.length-1];
		tempLabel=new JLabel(new ImageIcon(jeu.cheminDossier+tempElement.nomDuFichierImage));
		layeredPane.add(tempLabel,0);
		tempLabel.setBounds(tempElement.x,tempElement.y,tempElement.largeur,tempElement.hauteur);
		labels[elements.length-1]=tempLabel;

		//insertion des éléments du décor
		//i: itérateur
		int i=0;
		while(i<(elements.length-1))
		{
			tempElement=elements[i];
			tempLabel=new JLabel(new ImageIcon(jeu.cheminDossier+tempElement.nomDuFichierImage));
			layeredPane.add(tempLabel,0);
			tempLabel.setBounds(tempElement.x,tempElement.y,tempElement.largeur,tempElement.hauteur);
			layeredPane.setLayer(tempLabel,(tempLabel.getY()+((tempLabel.getSize()).height)));
			labels[i]=tempLabel;
			i++;
		}//fin insertion des éléments du décor

		layeredPane.setLayer(perso,(perso.getY()+((perso.getSize()).height)));
	}//fin affichage()


	/**
	 * verifPosition : gestionnaire d'obstacles
	 * retourne		vrai si déplacement possible
	 * 				faux si déplacement impossible
	 * int direction : 2=bas		4=gauche	6=droite	8=haut
	 */
	public boolean verifPosition(int direction)
	{
		//si le jeu est en pause, verifPosition empêche tout mouvement
		if(pause){return false;}

		int i=0;

		Obstacle tempObstacle = new Obstacle(0,0,0,0);

		int x,y,l,h;//on va utiliser ces variables pour mémoriser toutes les coordonnées de perso après déplacement
		int x1,x2,y1,y2;//pour simplifier l'écriture, on utilisera ces variable pour mémoriser les coordonnées de tempObstacle

		//***vers la gauche
		if(direction==4)
		{
			x=perso.getX()-pPerso.vitesse;
			y=perso.getY();
			l=(perso.getSize()).width;
			h=(perso.getSize()).height;

			//contrôle si le personnage sort de l'écran
			// si oui, charge un nouveau niveau
			if((x+l)<0)
			{
				perso.setLocation(jeu.xResolution,y);
				pPerso.cheminNiveau=niveau.sortieG;
				changementNiveau();
				return true;
			}
			while (i<(obstacles.length))
			{
				tempObstacle=obstacles[i];
				x1=tempObstacle.x1;
				x2=tempObstacle.x2;
				y1=tempObstacle.y1;
				y2=tempObstacle.y2;
				if((x2>x)&&(x>x1))//x2 > x > x1
				{
					if((y2>(y+h))&&((y+h)>y1)) // y2 > (y+h) > y1
					{return false;}
				}
				i++;
			}
			return true;
		}

		//***vers la droite
		else if(direction==6)
		{
			x=perso.getX()+pPerso.vitesse;
			y=perso.getY();
			l=(perso.getSize()).width;
			h=(perso.getSize()).height;

			//contrôle si le personnage sort de l'écran
			// si oui, charge un nouveau niveau
			if(x>jeu.xResolution)
			{
				perso.setLocation(0,y);
				pPerso.cheminNiveau=niveau.sortieD;
				changementNiveau();
				return true;
			}

			while (i<(obstacles.length))
			{
				tempObstacle=obstacles[i];
				x1=tempObstacle.x1;
				x2=tempObstacle.x2;
				y1=tempObstacle.y1;
				y2=tempObstacle.y2;
				if((x2>(x+l))&&((x+l)>x1))// x2 > (x+l) > x1
				{
					if((y2>(y+h))&&((y+h)>y1))//y2 > (y+h) > y1
					{return false;}
				}
				i++;
			}
			return true;
		}

		//***vers le bas
		else if(direction==2)
		{
			x=perso.getX();
			y=perso.getY()+pPerso.vitesse;
			l=(perso.getSize()).width;
			h=(perso.getSize()).height;

			//contrôle si le personnage sort de l'écran
			// si oui, charge un nouveau niveau
			if(y>jeu.yResolution)
			{
				perso.setLocation(x, 0);
				pPerso.cheminNiveau=niveau.sortieB;
				changementNiveau();
				return true;
			}

			while (i<(obstacles.length))
			{
				tempObstacle=obstacles[i];
				x1=tempObstacle.x1;
				x2=tempObstacle.x2;
				y1=tempObstacle.y1;
				y2=tempObstacle.y2;
				if((y1<(y+h))&&((y+h)<y2))//y1 < (y+h) < y2
				{
					if (((x<x1)&&(x1<x+l)) || ((x<x2) && (x2<x+l))  ||  ((x1<x)&&(x<x2))  ||  ((x1<x+l) && (x+l<x2)))
					{return false;}
				}
				i++;
			}
			return true;
		}

		//***vers le haut
		else if(direction==8)
		{
			x=perso.getX();
			y=perso.getY()-pPerso.vitesse;
			l=(perso.getSize()).width;
			h=(perso.getSize()).height;


			//contrôle si le personnage sort de l'écran
			// si oui, charge un nouveau niveau
			if(y+h<0)
			{
				perso.setLocation(x, jeu.yResolution);
				pPerso.cheminNiveau=niveau.sortieH;
				changementNiveau();
				return true;
			}

			while (i<(obstacles.length))
			{
				tempObstacle=obstacles[i];
				x1=tempObstacle.x1;
				x2=tempObstacle.x2;
				y1=tempObstacle.y1;
				y2=tempObstacle.y2;
				if(((y+h)<(y2+1))&&((y+h)>(y1+1)
				))
				{
					if (((x<x1)&&(x1<x+l)) || ((x<x2) && (x2<x+l))  ||  ((x1<x)&&(x<x2))  ||  ((x1<x+l) && (x+l<x2)))
					{return false;}
				}
				i++;
			}
			return true;
		}
		else return true;

	}//fin verifPosition()

	/**
	 * fonction appelée au chargement d'un niveau pour savoir s'il y a une
	 * action à executer selon une condition juste après le chargement
	 * d'un niveau (modifier les éléments affichés,...)
	 */
	void niveauEVT()
	{
		Evenement tempEVT=new Evenement();
		int i=0;
		while(i<(evenements.length))
		{
			tempEVT=evenements[i];
			if(tempEVT.etat==1)
			{execEVT(i);}
			i++;
		}
	}


	/**
	 * fonction appelée par l'appui sur la touche espace(pour l'instant)
	 * vérifie si le personnage est dans une zone d'événement
	 * et si c'est le cas, l'execute
	 */
	void verifEVT()
	{
		//on mémorise les coordonnées de perso
		int xP=perso.getX();
		int yP=perso.getY();
		int lP=(perso.getSize()).width;
		int hP=(perso.getSize()).height;

		//variables qui mémorisent l'emplacement de tempEVT
		int xE,yE,lE,hE;

		Evenement tempEVT;
		int i=0;
		while(i<(evenements.length))
		{
			tempEVT=evenements[i];
			xE=tempEVT.x;
			yE=tempEVT.y;
			lE=tempEVT.l;
			hE=tempEVT.h;
			if((appartient(xP,yP,xE,yE,lE,hE))||
			(appartient(xP+lP,yP,xE,yE,lE,hE))||
			(appartient(xP,yP+hP,xE,yE,lE,hE))||
			(appartient(xP+lP,yP+hP,xE,yE,lE,hE))||
			(appartient(xE+lE,yE,xP,yP,lP,hP))||
			(appartient(xE+lE,yE,xP,yP,lP,hP))||
			(appartient(xE+lE,yE,xP,yP,lP,hP))||
			(appartient(xE+lE,yE,xP,yP,lP,hP)))
			{
				if(tempEVT.etat==0)execEVT(i);
			}
			i++;
		}
	}//fin verifEVT()


	/**
	 * Cette fonction "execute" un Evenement situé à l'index index du tableau evenements
	 * correspondant aux evenements du tableau.
	 *
	 *
	 * @param index index de l'Evenement dans le tableau evenements qui est chargé à partir de XML
	 * @see Evenement
	 *
	 */
	void execEVT(int index)//l'index de l'événement à executer est passé en paramètre
	{
		//déclaration des variables
		Evenement tempEVT;
		Condition tabCond[];
		Condition tempCond;
		Action tabACTO[];
		Action tabACTN[];

		//récupération des données
		tempEVT=evenements[index];
		tabCond=tempEVT.conditions;
		tabACTO=tempEVT.actionOui;
		tabACTN=tempEVT.actionNon;

		/* On vérifie dans une boucle les conditions, si une n'est pas
		 * vérifiée, on sort de la boucle.
		 * les tableau de condition est en fait considéré comme cette unique condition :
		 * cond[1] && cond[2] && cond[3] && ...
		 */
		boolean verifiee=true;
		int i=0;
		while(i<(tabCond.length))
		{
			if(!verifCond(tabCond[i]))//verifCond vérifie une condition
			{
				verifiee=false;
				break;
			}
			i++;
		}
		//on n'execute pas la même Action si la condition a été vérifiée ou non
		if(verifiee) execTACT(tabACTO);
		else execTACT(tabACTN);
	}//fin execEVT()


	/**
	 * Cette fonction vérifie une Condition qui lui est passée en
	 * paramètre
	 * chaque Condition possède un membre nomCondition qui spécifie
	 * quelle vérification il faudra faire.
	 *
	 * @param	cond	une Condition à vérifier
	 * @return	un booléen
	 *			true si la condition est vérifiée
     * 			false si elle ne l'est pas
     * @see Condition
     */
	boolean verifCond(Condition cond)
	{
		if((cond.nomCondition.equals("toujours"))||cond.nomCondition.equals("!jamais")){return true;}
		else if((cond.nomCondition.equals("jamais"))||(cond.nomCondition.equals("toujours")))return false;
		else if(cond.nomCondition.equals("persoDansZone"))
		{
			return persoDansZone(cond.param0,cond.param1,cond.param2,cond.param3);
		}
		else if(cond.nomCondition.equals("!persoDansZone"))
		{

			return !persoDansZone(cond.param0,cond.param1,cond.param2,cond.param3);
		}
		else if(cond.nomCondition.equals("compCaracVal"))
		{
			return compCaracVal(cond.param0, cond.param1, cond.param2);
		}
		else if(cond.nomCondition.equals("!compCaracVal"))
		{
			return !compCaracVal(cond.param0, cond.param1, cond.param2);
		}
		else if(cond.nomCondition.equals("compCaracCarac"))
		{
			return compCaracCarac(cond.param0, cond.param1, cond.param2);
		}
		else if(cond.nomCondition.equals("!compCaracCarac"))
		{
			return !compCaracCarac(cond.param0, cond.param1, cond.param2);
		}
		else if(cond.nomCondition.equals("persoDansImg"))
		{
			return persoDansImg(cond.param0);
		}
		else if(cond.nomCondition.equals("!persoDansImg"))
		{
			return !persoDansImg(cond.param0);
		}
		else
		{
			if(modeDev){message("nomCondition :"+cond.nomCondition+"\nnon-valide");}
			return false;
		}
	}//fin verifCond()


	/**
	 * la fonction appartient défini si un point appartient à une zone
	 * xP et yP sont les coordonnées du point à tester
	 * xZ, yZ, lZ et hZ sont les coordonnées de la zone
	 */
	boolean appartient(int xP,int yP,int xZ,int yZ,int lZ,int hZ)
	{
		if((xZ<=xP)&&(xP<=(xZ+hZ))&&(yZ<=yP)&&(yP<=(yZ+hZ)))
		{return true;}
		else return false;
	}//fin appartient()


	/**		persoDansZone
	 * permet de savoir si le perso est dans une zone définie par :
	 *	param0 : coordonnée X de la zone
	 *	param1 : coordonnée Y de la zone
	 *	param2 : largeur de la zone
	 *	param3 : hauteur de la zone
	 */
	boolean persoDansZone(String param0, String param1, String param2, String param3)
	{
		//variables locales
		int xP=perso.getX();
		int yP=perso.getY();
		int lP=perso.getSize().width;
		int hP=perso.getSize().height;
		int xZ=0;
		int yZ=0;
		int lZ=0;
		int hZ=0;

		//vérification des paramètres
		try{
			xZ=Integer.parseInt(param0);
		}catch(Exception ex){if(modeDev)message("Condition: persoDansZone(param0) :\ncoordonnée X non-valide");return false;}
		try{
			yZ=Integer.parseInt(param1);
		}catch(Exception ex){if(modeDev)message("Condition: persoDansZone(param1) :\ncoordonnée Y non-valide");return false;}
		try{
			lZ=Integer.parseInt(param2);
		}catch(Exception ex){if(modeDev)message("Condition: persoDansZone(param2) :\ncoordonnée L(largeur) non-valide");return false;}
		try{
			hZ=Integer.parseInt(param3);
		}catch(Exception ex){if(modeDev)message("Condition: persoDansZone(param3) :\ncoordonnée H(hauteur) non-valide");return false;}

		//calculs
		//on vérifie si :
		//	le coin sup. gauche du perso est dans la zone
		if (appartient(xP,yP,xZ,yZ,lZ,hZ))return true;
		//	le coin sup. droit du perso est dans la zone
		else if (appartient(xP+lP,yP,xZ,yZ,lZ,hZ))return true;
		//	le coin inf. gauche du perso est dans la zone
		else if (appartient(xP,yP+hP,xZ,yZ,lZ,hZ))return true;
		//	le coin inf. droit du perso est dans la zone
		else if (appartient(xP+lP,yP+hP,xZ,yZ,lZ,hZ))return true;
		//	le coin sup. gauche de la zone est dans le perso
		else if (appartient(xZ,yZ,xP,yP,lP,hP))return true;
		//	le coin sup. droit de la zone est dans le perso
		else if (appartient(xZ,yZ+lZ,xP,yP,lP,hP))return true;
		//	le coin inf. gauche de la zone est dans le perso
		else if (appartient(xZ,yZ+hZ,xP,yP,lP,hP))return true;
		//	le coin inf. droit de la zone est dans le perso
		else if (appartient(xZ+lZ,yZ+hZ,xP,yP,lP,hP))return true;
		//sinon c'est que le perso n'est pas dans la zone
		else return false;
	}//fin persoDansZone()


	/**		persoDansImg
	 * permet de savoir si le perso est dans une zone occupée par une Image :
	 *	param0 : index de l'image dans le tableau de JLabel
	 */
	boolean persoDansImg(String param0)
	{
		//variables locales
		int xP=perso.getX();
		int yP=perso.getY();
		int lP=perso.getSize().width;
		int hP=perso.getSize().height;

		int xZ=0;
		int yZ=0;
		int lZ=0;
		int hZ=0;

		int index=0;


		//vérification des paramètres
		try{
			index=Integer.parseInt(param0);
		}catch(Exception ex){if(modeDev)message("Condition: persoDansImg(param0) :\nindex non-valide\nentier requis");return false;}
		try{
			xZ=labels[index].getX();
			yZ=labels[index].getY();
			lZ=labels[index].getSize().width;
			hZ=labels[index].getSize().height;
		}catch(Exception ex){if(modeDev)message("Condition: persoDansImg :\nindex non-valide\nhors du tableau");return false;}


		//calculs
		//on vérifie si :
		//	le coin sup. gauche du perso est dans la zone
		if (appartient(xP,yP,xZ,yZ,lZ,hZ))return true;
		//	le coin sup. droit du perso est dans la zone
		else if (appartient(xP+lP,yP,xZ,yZ,lZ,hZ))return true;
		//	le coin inf. gauche du perso est dans la zone
		else if (appartient(xP,yP+hP,xZ,yZ,lZ,hZ))return true;
		//	le coin inf. droit du perso est dans la zone
		else if (appartient(xP+lP,yP+hP,xZ,yZ,lZ,hZ))return true;
		//	le coin sup. gauche de la zone est dans le perso
		else if (appartient(xZ,yZ,xP,yP,lP,hP))return true;
		//	le coin sup. droit de la zone est dans le perso
		else if (appartient(xZ,yZ+lZ,xP,yP,lP,hP))return true;
		//	le coin inf. gauche de la zone est dans le perso
		else if (appartient(xZ,yZ+hZ,xP,yP,lP,hP))return true;
		//	le coin inf. droit de la zone est dans le perso
		else if (appartient(xZ+lZ,yZ+hZ,xP,yP,lP,hP))return true;
		//sinon c'est que le perso n'est pas dans la zone
		else return false;
	}//fin persoDansImg()


	/**		compCaracVal
	 * permet de comparer la valeur d'une caractéristique du perso à une valeur donnée :
	 *	param0 : valeur à comparer
	 *	param1 : opérateur de comparaison (<,>,<=,>=,!=,=,==), un opérateur différent de ceux-la sera interprêté comme un opérateur d'égalité
	 *	param2 : index dans le tableau de carac de la caractéristique à comparer
	 *
	 */
	boolean compCaracVal(String param0, String param1, String param2)
	{
		//variables locales
		int valeur=0;
		int index=0;
		String recup="";
		int carac=0;

		//vérification des paramètres
		// on ne vérifie pas le param1, car si sa valeur n'est ni <,ni >, ni >=, ni <=, ni =, ni ==, ni !=, on considère qu'il représente l'égalité
		try{
			valeur=Integer.parseInt(param0);
		}catch(Exception ex){if(modeDev)message("Condition: compCarac(param0) :\nvaleur non-valide\nentier requis");return false;}
		try{
			index=Integer.parseInt(param2);
		}catch(Exception ex){if(modeDev)message("Condition: compCarac(param2) :\nindex non-valide\nentier requis");return false;}
		try{
			recup = pPerso.carac[index];
		}catch(Exception ex){if(modeDev)message("Condition: compCarac :\nindex non-valide\nhors du tableau");return false;}
		try{
			carac = Integer.parseInt(recup);
		}catch(Exception ex){if(modeDev)message("Condition: compCarac :\nCaractéristique du perso non-valide\nentier requis");return false;}

		//comparaison de la valeur donnée et de la Caractéristique du perso
		//	valeur (opérateur) carac
		if(param1.equals("<"))return (valeur < carac);
		else if(param1.equals(">"))return (valeur > carac);
		else if(param1.equals("<="))return (valeur <= carac);
		else if(param1.equals(">="))return (valeur >= carac);
		else if(param1.equals("!="))return (valeur != carac);
		else return (valeur == carac);
	}//fin compCaracVal()


	/**		compCaracCarac
	 * permet de comparer la valeur d'une caractéristique du perso à une valeur donnée :
	 *	param0 : index dans le tableau de première carac de la caractéristique à comparer
	 *	param1 : opérateur de comparaison (<,>,<=,>=,!=,=,==), un opérateur différent de ceux-la sera interprêté comme un opérateur d'égalité
	 *	param2 : index dans le tableau de deuxième carac de la caractéristique à comparer
	 *
	 */
	boolean compCaracCarac(String param0, String param1, String param2)
	{
		//variables locales
		int index1=0;
		int index2=0;
		String recup1="";
		String recup2="";
		int val1=0;
		int val2=0;

		//vérification des paramètres
		// on ne vérifie pas le param1, car si sa valeur n'est ni <,ni >, ni >=, ni <=, ni =, ni ==, ni !=, on considère qu'il représente l'égalité
		try{
			index1=Integer.parseInt(param0);
		}catch(Exception ex){if(modeDev)message("Condition: compCarac(param0) :\nindex1 non-valide\nentier requis");return false;}

		try{
			index2=Integer.parseInt(param2);
		}catch(Exception ex){if(modeDev)message("Condition: compCarac(param2) :\nindex2 non-valide\nentier requis");return false;}

		try{
			recup1 = pPerso.carac[index1];
		}catch(Exception ex){if(modeDev)message("Condition: compCarac :\nindex 1 non-valide\nhors du tableau");return false;}

		try{
			val1 = Integer.parseInt(recup1);
		}catch(Exception ex){if(modeDev)message("Condition: compCarac :\nCaractéristique 1 du perso non-valide\nentier requis");return false;}

		try{
			recup2 = pPerso.carac[index2];
		}catch(Exception ex){if(modeDev)message("Condition: compCarac :\nindex 2 non-valide\nhors du tableau");return false;}

		try{
			val2 = Integer.parseInt(recup2);
		}catch(Exception ex){if(modeDev)message("Condition: compCarac :\nCaractéristique 2 du perso non-valide\nentier requis");return false;}

		//comparaison de la valeur donnée et de la Caractéristique du perso
		//	valeur (opérateur) carac
		if(param1.equals("<"))return (val1 < val2);
		else if(param1.equals(">"))return (val1 > val2);
		else if(param1.equals("<="))return (val1 <= val2);
		else if(param1.equals(">="))return (val1 >= val2);
		else if(param1.equals("!="))return (val1 != val2);
		else return (val1 == val2);
	}//fin compCaracVal()


	/**
	 * Cette fonction permet d'executer un tableau d'Action passé en paramètre
	 *
	 * @param actions[]	une tableau d'Action à executer
	 * @see execACT, Action
	 */
	void execTACT(Action actions[])
	{
		int i=0;
		while (i<(actions.length))
		{
			execACT(actions[i]);
			i++;
		}
	}//fin execTACT()


	/**
	 * Cette fonction execute une Action qui lui est passée en paramètre
	 * comme pour Condition, chaque Action possède un membre nomAction
	 * qui spécifie quelle action il faudra executer
	 *
	 * @param	 action	une Action à executer
	 * @return			une entier qui n'est pas récupérer mais qui permet, une fois
	 *					l'Action executer de sortir de la fonction
	 * @see Action
	 */
	int execACT(Action action)
	{
		if(action.nomAction.equals("println"))
		{
			//action Println: affiche le texte param0 dans la console
			System.out.println(action.param0);
			return 0;
		}
		else if(action.nomAction.equals("affTxt"))
		{
			//action affTxt: affiche le texte param0 à un endroit de l'écran déterminé par le param1
			return affTxt(action.param0,action.param1,action.param2,action.param3,action.param4,action.param5);
		}
		else if((action.nomAction.equals("affGif"))||action.nomAction.equals("affImg"))
		{
			//action affImg: affiche une image
			return affImg(action.param0,action.param1, action.param2, action.param3, action.param4, action.param5);
		}
		else if(action.nomAction.equals("remplaceImg"))
		{
			//action remplaceImg : remplace une Image affichée par une autre
			return remplaceImg(action.param0,action.param1,action.param2,action.param3);
		}
		else if(action.nomAction.equals("deplacePerso"))
		{
			return deplacePerso(action.param0,action.param1,action.param2,action.param3);
		}
		else if(action.nomAction.equals("modifImgPerso"))
		{
			return modifImgPerso(action.param0,action.param1, action.param2, action.param3, action.param4, action.param5, action.param6, action.param7);
		}
		else if(action.nomAction.equals("modifCaracPerso"))
		{
			return modifCaracPerso(action.param0,action.param1, action.param2);
		}
		else if(action.nomAction.equals("deplaceObs"))
		{
			return deplaceObs(action.param0,action.param1,action.param2,action.param3);
		}
		else if(action.nomAction.equals("changNiv"))
		{
			return changNiv(action.param0,action.param1,action.param2);
		}
		else if(action.nomAction.equals("rien"))
		{
			return 0;
		}
		message("Action: "+action.nomAction+"\nAction non-valide");
		return 1;
	}//fin execACT()


	/**		affTxt
	 *
	 * param0 : le texte à afficher (peut contenir du html ! (à tester)
	 * param1 : String qui définira où le texte s'affichera
	 * param2 : largeur du JTextArea qui contiendra le texte
	 * param3 : hauteur du JTextArea qui contiendra le texte
	 * param4 : coordonnée x si en mode absolu
	 * param5 : coordonnée y si en mode absolu
	 */
	int affTxt(String param0, String param1, String param2, String param3, String param4, String param5)
	{
		if(txtBool){return 1;}
		int l = 0;
		int h = 0;

		//Integer.parseInt(monString) permet de récupérer l'entier contenu par monString
		try{
		h = Integer.parseInt(param3);
		}catch(Exception ex){if(modeDev)message("Action: affTxt :\nhauteur non-valide");return 1;}
		try{
		l = Integer.parseInt(param2);
		}catch(Exception ex){if(modeDev)message("Action: affTxt :\nlargeur non-valide");return 1;}

		try{
		txt=new JTextArea(param0);
		}catch(Exception ex){if(modeDev)message("Action: affTxt :\ntexte non-valide");return 1;}

		txt.setLineWrap(true);//retour à la ligne automatique...
		txt.setWrapStyleWord(true);//...en gardant les mots entiers
		layeredPane.add(txt,0);

		//le texte apparaît à la gauche du perso
		if(param1.equals("persoG"))
		{
			//setBounds(X,Y,L,H)
			txt.setBounds
			(
				perso.getX()-l,
				perso.getY()-((h-(perso.getSize()).height)/2),
				l,
				h
			);
			layeredPane.setLayer(txt,800);
			txtBool=true;////on met txtBool à true
		}
		//le texte apparaît à la droite du perso
		else if(param1.equals("persoD"))
		{
			//setBounds(X,Y,L,H)
			txt.setBounds
			(
				perso.getX()+(perso.getSize()).width,
				perso.getY()-((h-(perso.getSize()).height)/2),
				l,
				h
			);
			layeredPane.setLayer(txt,800);
			txtBool=true;////on met txtBool à true
		}
		//le texte apparaît au dessus du perso
		else if(param1.equals("persoH"))
		{
			//setBounds(X,Y,L,H)
			txt.setBounds
			(
				perso.getX()-((l-(perso.getSize()).width)/2),
				perso.getY()-h,
				l,
				h
			);
			layeredPane.setLayer(txt,jeu.yResolution);
			txtBool=true;////on met txtBool à true
		}
		//le texte apparaît en dessous du perso
		else if(param1.equals("persoB"))
		{
			//setBounds(X,Y,L,H)
			txt.setBounds
			(
				perso.getX()-((l-(perso.getSize()).width)/2),
				perso.getY()+(perso.getSize()).height,
				l,
				h
			);
			layeredPane.setLayer(txt,800);
			txtBool=true;////on met txtBool à true
		}
		else if(param1.equals("absolu"))
		{
			int x=0;
			int y=0;
			try{
			x = Integer.parseInt(param4);
			}catch(Exception ex){if(modeDev)message("Action: affTxt :\nx non-valide");return 1;}
			try{
			y = Integer.parseInt(param5);
			}catch(Exception ex){if(modeDev)message("Action: affTxt :\ny non-valide");return 1;}

			//setBounds(X,Y,L,H)
			txt.setBounds(x,y,l,h);

			layeredPane.setLayer(txt,800);
			txtBool=true;////on met txtBool à true
		}
		return 0;
	}//fin affTxt()


	/**		affImg
	 *
	 * param0 : String qui définira où l'image s'affichera
	 * param1 : le chemin d'accès au gif
	 * param2 : le type d'affichage
	 * param3 : dans le cas d'un affichage "action", param3 donne la durée d'affichage de l'image
	 * param4 : dans le cas d'un positionnement absolu, param 4 est le x de la position
	 * param5 : dans le cas d'un positionnement absolu, param 5 est le y de la position
	 */
	int affImg(String param0, String param1, String param2, String param3, String param4, String param5)
	{
		//si une image est déjà affichée ponctuellement ou avec une durée déterminée
		//on ne peut pas utiliser le label image pour en afficher une autre
		//alors on retourne 1 pour sortir de la fonction
		if((imageDurBool)||(imagePoncBool))return 1;



		icon = new ImageIcon (jeu.cheminDossier+param1);
		image = new JLabel(icon);

		if(MediaTracker.ERRORED==icon.getImageLoadStatus())
		{
			if(modeDev)message("Action: affImg :\nImage ou chemin Image non-valide");
			return 1;
		}


		int l, h, x, y;

		l = icon.getIconWidth();
		h = icon.getIconHeight();

		//positionnement à Gauche du PERSO
	 	if(param0.equals("persoG"))
		{
			x = perso.getX()-l;
			y = perso.getY()-((h-(perso.getSize()).height)/2);
		}
		//positionnement à Droite du PERSO
		else if(param0.equals("persoD"))
		{
			x = perso.getX()+perso.getSize().width;
			y = perso.getY()-((h-(perso.getSize()).height)/2);
		}
		//positionnement en Haut du PERSO
		else if(param0.equals("persoH"))
		{
			x = perso.getX()-((l-(perso.getSize()).width)/2);
			y = perso.getY()-h;
		}
		//positionnement en Bas du PERSO
		else if(param0.equals("persoB"))
		{
			x = perso.getX()-((l-(perso.getSize()).width)/2);
			y = perso.getY()+perso.getSize().height;
		}
		//positionnement absolu
		else
		{
			x = 0;
			y = 0;
			try{
			x = Integer.parseInt(param4);
			}catch(Exception ex){if(modeDev)message("Action: affImg :\ncoordonnee X non-valide");return 1;}
			try{
			y = Integer.parseInt(param5);
			}catch(Exception ex){if(modeDev)message("Action: affImg :\ncoordonnee Y non-valide");return 1;}
		}
		layeredPane.add(image,0);
		image.setBounds(x,y,l,h);

		//on règle le label sur la couche supérieure
		layeredPane.setLayer(image,jeu.yResolution);

		//***  action selon le param2 ***//

		//permet au perso de se déplacer pendant l'affichage
		//de l'image durant la durée param3 (en millisecondes)
		if(param2!=null)
		{
			if(param2.equals("action"))
			{
				int duree=0;
				try{
				duree = Integer.parseInt(param3);
				}catch(Exception ex){if(modeDev)message("Action: affImg :\nduree non-valide");return 1;}
				action = new Dors(duree,this);
				//le booleen d'image qui "dure" passe à vrai
				//c'est le Thread qui le remettra à faux
				imageDurBool = true;
			}
			else if(param2.equals("cine"))
			{
				int duree=0;
				try{
				duree = Integer.parseInt(param3);
				}catch(Exception ex){if(modeDev)message("Action: affImg :\nduree non-valide");return 1;}
				action = new Dors(duree,this);
				pause = true;
				//le booleen d'image qui "dure" passe à vrai
				//c'est le Thread qui le remettra à faux
				imageDurBool = true;
			}
			//affichage simple pendant l'appui sur la touche d'action
			else if(param2.equals("simple"))
			{
				//le booleen d'image ponctuelle passe à vrai
				//l'image sera affichée sur appui et enlevée sur relachement de touche
				imagePoncBool =true;
			}
			//idem
			else
			{
				//le booleen d'image ponctuelle passe à vrai
				//l'image sera affichée sur appui et enlevée sur relachement de touche
				imagePoncBool = true;
			}
		}
		return 0;
	}//fin affGif()


	/**		remplaceImg
	 *
	 * param0 : index dans le tableau de JLabel labels
	 * param1 : chemin d'acces de l'image remplaçante
	 * param2,param3 : prennent une valeur différente de null si on veut placer l'image remplaçante à un endroit précis
	 *				  dans ce cas :	param2 : x (abscisse de la nouvelle image)
	 *								param3 : y (ordonnee de la nouvelle image)
	 */
	int remplaceImg(String param0, String param1, String param2, String param3)
	{
		int index = 0;
		try{
		index = Integer.parseInt(param0);
		}catch(Exception ex){if(modeDev)message("Action: remplaceImg :\nindex de l'image non-valide");return 1;}
		ImageIcon image = new ImageIcon(jeu.cheminDossier+param1);
		int x,y;

		x=labels[index].getX();
		y=labels[index].getY();

		ImageIcon icon = new ImageIcon(jeu.cheminDossier+param1);

		if(MediaTracker.ERRORED==icon.getImageLoadStatus())
		{
			if(modeDev)message("Action: remplaceImg :\nImage ou chemin Image non-valide");
			return 1;
		}

		labels[index].setIcon(new ImageIcon(jeu.cheminDossier+param1));


		if((param2!=null)&&(param3!=null))
		{
			try{
			x = Integer.parseInt(param2);
			}catch(Exception ex){if(modeDev)message("Action: remplaceImg :\ncoordonnee X non-valide");}
			try{
			y = Integer.parseInt(param3);
			}catch(Exception ex){if(modeDev)message("Action: remplaceImg :\ncoordonnee X non-valide");}

		}
		labels[index].setBounds(x,y,image.getIconWidth(),image.getIconHeight());
		return 0;
	}//fin remplaceImg()


	/**		deplaceImg
	 *
	 * permet de déplacer une image n'importe où dans le décor
	 *	param0 : index de l'image dans le tableau de labels
	 *	param1 : operateur sur la position de l'image (+,- ou =)
	 *	param2 : x de la nouvelle position
	 *	param3 : y de la nouvelle position
	 */
	int deplaceImg(String param0,String param1, String param2, String param3)
	{
		int index = 0;
		try{
		index = Integer.parseInt(param0);
		}catch(Exception ex){if(modeDev){message("Action: deplaceImg :\nindex de l'image non-valide");}return 1;}
		if(param1.equals("+"))
		{
			int oldX = labels[index].getX();
			int oldY = labels[index].getY();
			int newX = 0;
			int newY = 0;
			try{
			newX = Integer.parseInt(param2);
			}catch(Exception ex){if(modeDev)message("Action: deplaceImg '+' :\ncoordonnee X non-valide");return 1;}
			try{
			newY = Integer.parseInt(param3);
			}catch(Exception ex){if(modeDev)message("Action: deplaceImg '+' :\ncoordonnee Y non-valide");return 1;}
			labels[index].setLocation(newX+oldX,newY+oldY);
		}
		if(param1.equals("-"))
		{
			int oldX = labels[index].getX();
			int oldY = labels[index].getY();
			int newX = 0;
			int newY = 0;
			try{
			newX = Integer.parseInt(param2);
			}catch(Exception ex){if(modeDev)message("Action: deplaceImg '-' :\ncoordonnee X non-valide");return 1;}
			try{
			newY = Integer.parseInt(param3);
			}catch(Exception ex){if(modeDev)message("Action: deplaceImg '-' :\ncoordonnee Y non-valide");return 1;}
			labels[index].setLocation(newX-oldX,newY-oldY);
		}
		else
		{
			int newX = 0;
			int newY = 0;
			try{
			newX = Integer.parseInt(param2);
			}catch(Exception ex){if(modeDev)message("Action: deplaceImg '=' :\ncoordonnee X non-valide");return 1;}
			try{
			newY = Integer.parseInt(param3);
			}catch(Exception ex){if(modeDev)message("Action: deplaceImg '=' :\ncoordonnee Y non-valide");return 1;}
			labels[index].setLocation(newX,newY);
		}
		return 0;
	}//fin deplaceImg()


	/**		deplacePerso
	 *
	 * permet de déplacer le perso n'importe où dans le décor
	 *	param0 : operateur sur la position du perso (+,- ou =)
	 *	param1 : x de la nouvelle position
	 *	param2 : y de la nouvelle position
	 *	param3 : nom de l'ImageIcon
	 *		maba : MArche vers le BAs
	 *		maha : MArche vers le HAut
	 *		madr : MArche vers la DRoite
	 *		maga : MArche vers la Gauche
	 *		imba : IMmobile tourné vers le BAs
	 *		imha : IMmobile tourné vers le HAut
	 *		imdr : IMmobile tourné vers la DRoite
	 *		imga : IMmobile tourné vers la GAuche
	 */
	int deplacePerso(String param0, String param1, String param2, String param3)
	{
		int newX=0;
		int newY=0;
		int oldX=perso.getX();
		int oldY=perso.getY();
		if(param0.equals("+"))
		{
			try{
				newX=Integer.parseInt(param2);
			}catch(Exception ex){if(modeDev)message("Action: deplacePerso '+' :\ncoordonnee X non-valide");return 1;}
			try{
				newY=Integer.parseInt(param3);
			}catch(Exception ex){if(modeDev)message("Action: deplacePerso '+' :\ncoordonnee Y non-valide");return 1;}
			perso.setLocation(newX+oldX,newY+oldY);
		}
		if(param0.equals("-"))
		{
			try{
				newX=Integer.parseInt(param2);
			}catch(Exception ex){if(modeDev)message("Action: deplacePerso '-' :\ncoordonnee X non-valide");return 1;}
			try{
				newY=Integer.parseInt(param3);
			}catch(Exception ex){if(modeDev)message("Action: deplacePerso '-' :\ncoordonnee Y non-valide");return 1;}
			perso.setLocation(newX-oldX,newY-oldY);
		}
		else
		{
			try{
				newX=Integer.parseInt(param2);
			}catch(Exception ex){if(modeDev)message("Action: deplacePerso '=' :\ncoordonnee X non-valide");return 1;}
			try{
				newY=Integer.parseInt(param3);
			}catch(Exception ex){if(modeDev)message("Action: deplacePerso '=' :\ncoordonnee Y non-valide");return 1;}
			perso.setLocation(newX,newY);
		}


		if(param3!=null)
		{
			if((param3.equals("maba"))||(param3.equals("MABA")))
			{perso.setIcon(maba);perso.setSize(maba.getIconWidth(),maba.getIconHeight());}
			else if((param3.equals("maha"))||(param3.equals("MAHA")))
			{perso.setIcon(maha);perso.setSize(maha.getIconWidth(),maha.getIconHeight());}
			else if((param3.equals("madr"))||(param3.equals("MADR")))
			{perso.setIcon(madr);perso.setSize(madr.getIconWidth(),madr.getIconHeight());}
			else if((param3.equals("maga"))||(param3.equals("MAGA")))
			{perso.setIcon(maga);perso.setSize(maga.getIconWidth(),maga.getIconHeight());}
			else if((param3.equals("imba"))||(param3.equals("IMBA")))
			{perso.setIcon(imba);perso.setSize(imba.getIconWidth(),imba.getIconHeight());}
			else if((param3.equals("imha"))||(param3.equals("IMHA")))
			{perso.setIcon(imha);perso.setSize(imha.getIconWidth(),imha.getIconHeight());}
			else if((param3.equals("imdr"))||(param3.equals("IMDR")))
			{perso.setIcon(imdr);perso.setSize(imdr.getIconWidth(),imdr.getIconHeight());}
			else if((param3.equals("imga"))||(param3.equals("IMGA")))
			{perso.setIcon(imga);perso.setSize(imga.getIconWidth(),imga.getIconHeight());}
			else
			{perso.setIcon(maba);perso.setSize(maba.getIconWidth(),maba.getIconHeight());}
		}
		else
		{
			perso.setIcon(maba);perso.setSize(maba.getIconWidth(),maba.getIconHeight());
		}
		return 0;
	}

	/**		modifImgPerso
	 *
	 * permet de modifier les images qui représentent le Perso
	 *	param0 : maga
	 *	param1 : madr
	 *	param2 : maha
	 *	param3 : maba
	 *	param4 : imga
	 *	param5 : imdr
	 *	param6 : imha
	 *	param7 : imba
	 *	De plus, si l'on a qu'une image (en param0), elle sera appliquées à toutes les représentations du perso
	 *	Si on en a 4 (param0 ~ param3) elle seront appliquées pour les 4 directions sans distinction de mobilité
	 *	Enfin, si on a 8 (param0 ~ param7) toutes les images sont appliquées normalement
	 */
	int modifImgPerso(String param0, String param1, String param2, String param3, String param4, String param5, String param6, String param7)
	{
		//cas dans lequel le perso a la même image quel que soit son mouvement
		if(param1==null)
		{
			//chargement de l'image
			ImageIcon ico=new ImageIcon(jeu.cheminDossier+param0);

			//vérification du chargement de l'image
			if(MediaTracker.ERRORED==ico.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image unique non-valide");
				return 1;
			}

			//changement effectif des ImageIcon
			maga=madr=maha=maba=imga=imdr=imha=imba=ico;

			//changement dans les données du Perso
			pPerso.maga=pPerso.madr=pPerso.maha=pPerso.maba=pPerso.imga=pPerso.imdr=pPerso.imha=pPerso.imba=param0;
			return 0;
		}
		//cas dans lequel le perso a 4 images correspondantes au 4 directions
		else if(param4==null)
		{
			//chargement des images
			ImageIcon icoGa=new ImageIcon(jeu.cheminDossier+param0);
			ImageIcon icoDr=new ImageIcon(jeu.cheminDossier+param1);
			ImageIcon icoHa=new ImageIcon(jeu.cheminDossier+param2);
			ImageIcon icoBa=new ImageIcon(jeu.cheminDossier+param3);

			//vérification du chargement des images
			if(MediaTracker.ERRORED==icoGa.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image 'Gauche' non-valide");
				return 1;
			}
			if(MediaTracker.ERRORED==icoDr.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image 'Droite' non-valide");
				return 1;
			}
			if(MediaTracker.ERRORED==icoHa.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image 'Haut' non-valide");
				return 1;
			}
			if(MediaTracker.ERRORED==icoBa.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image 'Bas' non-valide");
				return 1;
			}

			//changement effectif des ImageIcon
			maga=imga=icoGa;
			madr=imdr=icoDr;
			maha=imha=icoHa;
			maba=imba=icoBa;

			//changement effectif des données du perso
			pPerso.maga=pPerso.imga=param0;
			pPerso.madr=pPerso.imdr=param1;
			pPerso.maha=pPerso.imha=param2;
			pPerso.maba=pPerso.imba=param3;

			return 0;
		}
		//cas dans lequel toutes les images sont instanciées
		else
		{
			//chargement des images
			ImageIcon icoMaGa=new ImageIcon(jeu.cheminDossier+param0);
			ImageIcon icoMaDr=new ImageIcon(jeu.cheminDossier+param1);
			ImageIcon icoMaHa=new ImageIcon(jeu.cheminDossier+param2);
			ImageIcon icoMaBa=new ImageIcon(jeu.cheminDossier+param3);

			ImageIcon icoImGa=new ImageIcon(jeu.cheminDossier+param4);
			ImageIcon icoImDr=new ImageIcon(jeu.cheminDossier+param5);
			ImageIcon icoImHa=new ImageIcon(jeu.cheminDossier+param6);
			ImageIcon icoImBa=new ImageIcon(jeu.cheminDossier+param7);


			//vérification du chargement des images
			if(MediaTracker.ERRORED==icoMaGa.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image 'Mouvement Gauche' non-valide");
				return 1;
			}
			if(MediaTracker.ERRORED==icoMaDr.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image 'Mouvement Droite' non-valide");
				return 1;
			}
			if(MediaTracker.ERRORED==icoMaHa.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image 'Mouvement Haut' non-valide");
				return 1;
			}
			if(MediaTracker.ERRORED==icoMaBa.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image 'Mouvement Bas' non-valide");
				return 1;
			}

			if(MediaTracker.ERRORED==icoImGa.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image 'Immobile Gauche' non-valide");
				return 1;
			}
			if(MediaTracker.ERRORED==icoImDr.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image 'Immobile Droite' non-valide");
				return 1;
			}
			if(MediaTracker.ERRORED==icoImHa.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image 'Immobile Haut' non-valide");
				return 1;
			}
			if(MediaTracker.ERRORED==icoImBa.getImageLoadStatus())
			{
				if(modeDev)message("Action: modifImgPerso :\nImage ou chemin Image 'Immobile Bas' non-valide");
				return 1;
			}

			//changement effectif des ImageIcon
			maga=icoMaGa;
			madr=icoMaDr;
			maha=icoMaHa;
			maba=icoMaBa;

			imga=icoImGa;
			imdr=icoImDr;
			imha=icoImHa;
			imba=icoImBa;

			//changement effectif des données du perso
			pPerso.maga=param0;
			pPerso.madr=param1;
			pPerso.maha=param2;
			pPerso.maba=param3;

			pPerso.imga=param4;
			pPerso.imdr=param5;
			pPerso.imha=param6;
			pPerso.imba=param7;

		}
		return 0;
	}

	/**		modifCaracPerso
	 *
	 * permet de modifier les caractéristiques du perso
	 *	param0 : index de la caractéristique dans le table carac de Perso
	 *	param1 : symbole (+,-,=) de l'opération à effectuer sur la caractéristique
	 *	param2 : valeur à ajouter,retrancher, donner à la caractéristique
	 */
	int modifCaracPerso(String param0, String param1, String param2)
	{
		int index=0;
		int newVal=0;
		String oldStr=null;
		int oldVal=0;
		//vérification des valeur fournie dans le XML
		try{
			index = Integer.parseInt(param0);
		}catch(Exception ex){if(modeDev)message("Action: modifCaracPerso :\nindex non valide : Entier demandé");return 1;}
		try{
			newVal = Integer.parseInt(param2);
		}catch(Exception ex){if(modeDev)message("Action: modifCaracPerso :\nvaleur non valide : Entier demandé");return 1;}
		try{
			oldStr = pPerso.carac[index];
		}catch(Exception ex){if(modeDev)message("Action: modifCaracPerso :\nindex non valide : Hors du tableau");return 1;}
		try{
			oldVal = Integer.parseInt(oldStr);
		}catch(Exception ex){if(modeDev)message("Action: modifCaracPerso :\nles Valeur dans le tableau de Caractéristiques\n du Perso doivent être des entiers pour\npouvoir être modifées avec modifCaracPerso");return 1;}

		if(param1.equals("+"))
		{
			pPerso.carac[index]=String.valueOf(oldVal+newVal);
		}
		else if(param1.equals("-"))
		{
			pPerso.carac[index]=String.valueOf(oldVal-newVal);
		}
		else
		{
			pPerso.carac[index]=String.valueOf(newVal);
		}
		return 0;
	}//fin modifCaracPerso()


	/**		deplaceObs
	 *
	 * permet de déplacer un obstacle sans changer sa taille
	 *	param0 : index de l'obstacle à modifier dans le tableau d'obstacles (doit correspondre au numéro dans le fichier XML)
	 *	param1 : symbole (+,-,=) de l'opération à effectuer sur les coordonnees de l'obstacle
	 *	param2 : coordonnées x
	 *	param3 : coordonnées y
	 */
	int deplaceObs(String param0, String param1, String param2, String param3)
	{
		//déclaration et initialisation des variables
		int index = 0;
		int newX = 0;
		int newY = 0;
		Obstacle obs=null;

		//vérification des paramètres
		try{
			index = Integer.parseInt(param0);
		}catch(Exception ex){if(modeDev)message("Action: deplaceObs :\nindex non-valide\nentier requis");return 1;}
		try{
			newX = Integer.parseInt(param2);
		}catch(Exception ex){if(modeDev)message("Action: deplaceObs :\ncoordonnées x non-valide\nentier requis");return 1;}
		try{
			newY = Integer.parseInt(param3);
		}catch(Exception ex){if(modeDev)message("Action: deplaceObs :\ncoordonnées y non-valide\nentier requis");return 1;}
		try{
			obs=obstacles[index];
		}catch(Exception ex){if(modeDev)message("Action: deplaceObs :\nindex non-valide\nhors du tableau");return 1;}

		//calculs sur l'obstacle local
		if(param1.equals("+"))
		{
			obs.x1+=newX;
			obs.x2+=newX;
			obs.y1+=newY;
			obs.y2+=newY;
		}
		else if(param2.equals("-"))
		{
			obs.x1-=newX;
			obs.x2-=newX;
			obs.y1-=newY;
			obs.y2-=newY;
		}
		else
		{
			int l,h;
			l=obs.x1-obs.x2;
			h=obs.y1-obs.y2;
			obs.x1=newX;
			obs.x2=newX+l;
			obs.y1=newY;
			obs.y2=newY+h;
		}

		//transfert de l'obstacle local à l'obstacle à modifier
		obstacles[index]=obs;
		return 0;
	}//fin deplaceOsb()


	/**		modifObs
	 *
	 * permet de modifier toutes les coordonnées d'un obstacle
	 *	param0 : index de l'obstacle à modifier dans le tableau d'obstacles (correspond surement au numéro dans le fichier XML)
	 *	param1 : coordonnées x1
	 *	param2 : coordonnées x2
	 *	param3 : coordonnées y1
	 *	param4 : coordonnées y2
	 */
	int modifObs(String param0, String param1, String param2, String param3, String param4)
	{
		//variables locales
		int index=0;
		int newX1=0;
		int newX2=0;
		int newY1=0;
		int newY2=0;
		Obstacle obs=null;

		//vérification sur les paramètres
		try{
		index=Integer.parseInt(param0);
		}catch(Exception ex){if(modeDev)message("Action: modifObs :\nindex non-valide\nentier requis");return 1;}
		try{
		newX1=Integer.parseInt(param1);
		}catch(Exception ex){if(modeDev)message("Action: modifObs :\nX1 non-valide\nentier requis");return 1;}
		try{
		newX2=Integer.parseInt(param2);
		}catch(Exception ex){if(modeDev)message("Action: modifObs :\nX2 non-valide\nentier requis");return 1;}
		try{
		newY1=Integer.parseInt(param3);
		}catch(Exception ex){if(modeDev)message("Action: modifObs :\nY1 non-valide\nentier requis");return 1;}
		try{
		newY2=Integer.parseInt(param4);
		}catch(Exception ex){if(modeDev)message("Action: modifObs :\nY2 non-valide\nentier requis");return 1;}
		try{
		obs=obstacles[index];
		}catch(Exception ex){if(modeDev)message("Action: modifObs :\nindex non-valide\nhors du tableau");return 1;}

		//modification en local
		obs.x1=newX1;
		obs.x2=newX2;
		obs.y1=newY1;
		obs.y2=newY2;

		//insertion dans le tableau
		obstacles[index]=obs;

		return 0;
	}//fin modifObs()


	/**		ajoutObs
	 *
	 * permet d'ajouter une obstacle à la scène
	 *
	 *	param0 : coordonnées x1 du nouvel obstacle
	 *	param1 : coordonnées x2 du nouvel obstacle
	 *	param2 : coordonnées y1 du nouvel obstacle
	 *	param3 : coordonnées y2 du nouvel obstacle
	 */
	int ajoutObs(String param0, String param1, String param2, String param3)
	{
		//variables locales
		Obstacle tabObs[];
		Obstacle newTabObs[];
		int newX1=0;
		int newX2=0;
		int newY1=0;
		int newY2=0;

		try{
		newX1=Integer.parseInt(param0);
		}catch(Exception ex){if(modeDev)message("Action: modifObs :\nX1 non-valide\nentier requis");return 1;}
		try{
		newX2=Integer.parseInt(param1);
		}catch(Exception ex){if(modeDev)message("Action: modifObs :\nX2 non-valide\nentier requis");return 1;}
		try{
		newY1=Integer.parseInt(param2);
		}catch(Exception ex){if(modeDev)message("Action: modifObs :\nY1 non-valide\nentier requis");return 1;}
		try{
		newY2=Integer.parseInt(param3);
		}catch(Exception ex){if(modeDev)message("Action: modifObs :\nY2 non-valide\nentier requis");return 1;}

		tabObs=obstacles;
		newTabObs=new Obstacle[obstacles.length+1];
		newTabObs=tabObs;
		newTabObs[newTabObs.length-1]=new Obstacle(newX1,newX2, newY1, newY2);
		obstacles=newTabObs;
		return 0;
	}//fin ajoutObs()


	/**		changNiv
	 *
	 * permet de charger un niveau différent
	 *
	 *	param0 : chemin du fichier du niveau à charger
	 *	param1 : coordonnée x du perso dans le nouveau niveau (peut ne pas avoir de valeur)
	 *	param2 : coordonnée y du perso dans le nouveau niveau (peut ne pas avoir de valeur)
	 */
	int changNiv(String param0, String param1, String param2)
	{
		pPerso.cheminNiveau=param0;
		changementNiveau();
		if(param1!=null)
		{
			int newX=0;
			try{
				newX=Integer.parseInt(param1);
			}catch(Exception ex){if(modeDev)message("Action: changNivObs :\nX position perso non-valide\nentier requis");return 1;}
			perso.setLocation(newX,perso.getY());
		}
		if(param2!=null)
		{
			int newY=0;
			try{
				newY=Integer.parseInt(param2);
			}catch(Exception ex){if(modeDev)message("Action: changNivObs :\nX position perso non-valide\nentier requis");return 1;}
			perso.setLocation(perso.getX(),newY);
		}
		return 0;
	}//fin changNiv()


	/**		ajoutImg
	 *
	 * permet d'ajouter un element au niveau et au tableau de Labels
	 *
	 *	param0 : chemin du fichier de l'image à ajouter
	 *	param1 : coordonnée x de la nouvelle image (si non spécifiée, x=0)
	 *	param2 : coordonnée y de la nouvelle image (si non spécifiée, y=0)
	 */
	int ajoutImg(String param0, String param1, String param2)
	{
		//variables locales
		int x=0;
		int y=0;
		ImageIcon icon = new ImageIcon(jeu.cheminDossier+param0);
		JLabel lbl;
		JLabel newTabLbl[];

		//vérification des paramètres
		if(MediaTracker.ERRORED==icon.getImageLoadStatus())
		{
			if(modeDev)message("Action: ajoutImg :\nImage ou chemin Image non-valide");
			return 1;
		}
		if(param1!=null)
		{
			try
			{
				x=Integer.parseInt(param1);
			}catch(Exception ex){if(modeDev)message("Action: ajoutImg :\ncoordonnées X non-valide\nentier requis");}
		}
		if(param2!=null)
		{
			try
			{
				y=Integer.parseInt(param2);
			}catch(Exception ex){if(modeDev)message("Action: ajoutImg :\ncoordonnées Y non-valide\nentier requis");}
		}

		//insertion effective de la nouvelle image
		newTabLbl=new JLabel [labels.length+1];
		newTabLbl=labels;
		lbl=new JLabel(icon);

		newTabLbl[newTabLbl.length]=lbl;
		lbl.setBounds(x,y,icon.getIconWidth(),icon.getIconHeight());

		affichage();
		return 0;
	}

	//main
	public static void main (String args[])
	{
		boolean modeDev=false;
		String fichier=null;
		SelectFichier select=new SelectFichier();

		//on fait "dormir" le Thread pendant 0.5 secondes pour qu'il laisse le selecteur se charger
		try
		{
			Thread.sleep(500);
		}catch(Exception e){e.printStackTrace();}

		//tant que le selecteur n'a pas de valeur de jeu, on ne lance pas le moteur
		while(fichier==null)
		{
			try{
				Thread.sleep(100);
			}catch(Exception ex){ex.printStackTrace();}
			fichier=select.getChemin();
		}
		modeDev=select.getDev();

		select.frame.dispose();

		Moteur moteur = new Moteur(fichier,modeDev);
	}
}