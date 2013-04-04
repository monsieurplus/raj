import java.io.Serializable;

//classe ElementDuDecor
public class Jeu implements Serializable
{

	public String nomDuJeu;	//sera affiché dans le titre de la fenêtre
	public String cheminDossier;		//chemin du dossier dans lequel les fichiers du Jeu seront enregistrés
										//par exemple pour un jeu "Pong"
										//le cheminDossier sera par exemple "Donnes/Pong/"
	public int xResolution;	//taille Y de la résolution du Jeu
	public int yResolution;	//taille X de la résolution du Jeu
	public Perso persoDefaut;	//sauvegarde de Perso qui sera celui par défaut si aucune sauvegarde n'est chargée
	public String repSauv;	//répertoire de stockage des sauvegardes

	//constructeur par défaut
	public Jeu()
	{
		nomDuJeu="defaut";
		xResolution=800;
		yResolution=600;
		persoDefaut=new Perso();
		cheminDossier="Jeux/Defaut/";
		repSauv="save/";
	}

	public Jeu(String nomJ, String cheminDossierJ)
	{
		nomDuJeu=nomJ;
		cheminDossier=cheminDossierJ;
		repSauv="save/";
		xResolution=800;
		yResolution=600;
		persoDefaut=new Perso();
	}

	public Jeu(String nomJ, String cheminDossierJ, int xJ, int yJ)
	{
		nomDuJeu=nomJ;
		cheminDossier=cheminDossierJ;
		repSauv="save/";
		xResolution=xJ;
		yResolution=yJ;
		persoDefaut=new Perso();
	}


	public Jeu(String nomJ, String cheminDossierJ, int xJ, int yJ, Perso persDefJ)
	{
		nomDuJeu=nomJ;
		cheminDossier=cheminDossierJ;
		repSauv="save/";
		xResolution=xJ;
		yResolution=yJ;
		persoDefaut=persDefJ;
	}

	public Jeu(String nomJ, String cheminDossierJ, String repSauvJ, int xJ, int yJ, Perso persDefJ)
	{
		nomDuJeu=nomJ;
		cheminDossier=cheminDossierJ;
		repSauv=repSauvJ;
		repSauv=cheminDossier+"save/";
		xResolution=xJ;
		yResolution=yJ;
		persoDefaut=persDefJ;
	}

	//accesseurs
	public String getNomDuJeu(){return nomDuJeu;}
	public String getCheminDossier(){return cheminDossier;}
	public String getRepSauv(){return repSauv;}
	public int getXResolution(){return xResolution;}
	public int getYResolution(){return yResolution;}
	public Perso getPersoDefaut(){return persoDefaut;}

	//modificateurs
	public void setNomDuJeu(String nomJ){nomDuJeu=nomJ;}
	public void setCheminDossier(String cheminJ){cheminDossier=cheminJ;}
	public void setRepSauv(String repSauvJ){repSauv=repSauvJ;}
	public void setXResolution(int xJ){xResolution=xJ;}
	public void setYResolution(int yJ){yResolution=yJ;}
	public void setPersoDefaut(Perso persoDefautJ){persoDefaut=persoDefautJ;}

}//fin définition classe Jeu


