import java.io.Serializable;

//classe ElementDuDecor
public class ElementDuDecor implements Serializable
{

	public String nomDuFichierImage;//JLabel de l'élément
	public boolean affichage;//la définition de l'élément s'affiche quand on clique dessus
	public String nomDeLElement;
	public String description;//description de l'élément
	public int x,y,largeur,hauteur;//coordonnée où devra être placé l'élément

	//constructeur par défaut
	public ElementDuDecor()
	{
		nomDuFichierImage="_.gif";
		affichage=false;
		nomDeLElement="_";
		description="_";
		x=y=largeur=hauteur=0;
	}

	public ElementDuDecor(String chemin)
	{
		nomDuFichierImage=chemin;
		affichage=false;
		nomDeLElement="_";
		description="_";
		x=y=largeur=hauteur=0;
	}

	public ElementDuDecor(String chemin, int x, int y, int l, int h)
	{
		nomDuFichierImage=chemin;
		affichage=false;
		nomDeLElement="_";
		description="_";
		this.x=x;
		this.y=y;
		largeur=l;
		hauteur=h;
	}

	public ElementDuDecor(String chemin,String nom, int x, int y, int l, int h)
	{
		nomDuFichierImage=chemin;
		affichage=false;
		nomDeLElement=nom;
		description="_";
		this.x=x;
		this.y=y;
		largeur=l;
		hauteur=h;
	}

	public ElementDuDecor(String chemin,String nom)
	{
		nomDuFichierImage=chemin;
		affichage=false;
		nomDeLElement=nom;
		description="_";
		x=y=largeur=hauteur=0;
	}

	public ElementDuDecor(String chemin,String nom, String comm)
	{
		nomDuFichierImage=chemin;
		affichage=true;
		nomDeLElement=nom;
		description=comm;
		x=y=largeur=hauteur=0;
	}

	public ElementDuDecor(String chemin,String nom, String comm, int x, int y, int l, int h)
	{
		nomDuFichierImage=chemin;
		affichage=true;
		nomDeLElement=nom;
		description=comm;
		this.x=x;
		this.y=y;
		largeur=l;
		hauteur=h;
	}

	public ElementDuDecor(ElementDuDecor element)
	{
		nomDuFichierImage=element.nomDuFichierImage;
		affichage=element.affichage;
		nomDeLElement=element.nomDeLElement;
		description=element.description;
		x=element.x;
		y=element.y;
		largeur=element.largeur;
		hauteur=element.hauteur;
	}

	//accesseurs
	public String getNomDuFichierImage(){return nomDuFichierImage;}
	public boolean getAffichage(){return affichage;}
	public String getNomDeLElement(){return nomDeLElement;}
	public String getDescription(){return description;}
	public int getX(){return x;}
	public int getY(){return y;}
	public int getLargeur(){return largeur;}
	public int getHauteur(){return hauteur;}

	//modificateurs
	public void setNomDuFichierImage(String chemin){nomDuFichierImage=chemin;}
	public void setAffichage(boolean ouinon){affichage=ouinon;}
	public void setNomDeLElement(String nom){nomDeLElement=nom;}
	public void setDescription(String descri){description=descri;}
	public void setX(int x){this.x=x;}
	public void setY(int y){this.y=y;}
	public void setLargeur(int l){largeur=l;}
	public void setHauteur(int h){hauteur=h;}

}//fin définition classe ElementDuDecor