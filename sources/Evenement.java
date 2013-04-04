import java.io.Serializable;

//classe Evenement
//Un Evenement associe à une zone(x,y,l,h), 0, une ou plusieurs Conditions qui sont contrôlées
//si ces Conditions son vérifiées, on execute 0, une ou plusieurs Action
//si elles ne le sont pas, on execute 0, une ou plusieurs Action

public class Evenement implements Serializable
{
	public int x,y,l,h;//coordonnées de l'événement
	public Condition[] conditions;//condition à vérifier(peut être vide)
	public Action[] actionNon;//Actions si la condition n'est pas vérifiée(peut être vide)
	public Action[] actionOui;//Actions si la condition est vérifiér(peut être vide)
	public int etat;//état représente l'état de l'événement,
					//certains événements peuvent être des événements à executer dès le chargement
					//on mettra leur état à 1.

	//constructeur par défaut(ne pas utiliser)
	public Evenement()
	{
		x=y=l=h=0;
		etat=0;
	}

	//constructeur avec passage des arguments
	public Evenement(int telX, int telY, int telL, int telH, Condition[] tabCond, Action[] tabActionNon, Action[] tabActionOui)
	{
		x=telX;y=telY;l=telL;h=telH;
		conditions=tabCond;
		actionNon=tabActionNon;
		actionOui=tabActionOui;
		etat=0;
	}

	//accesseurs
	public int getEtat(){return etat;}
	public int getX(){return x;}
	public int getY(){return y;}
	public int getL(){return l;}
	public int getH(){return h;}
	public Condition[] getConditions(){return conditions;}
	public Action[] getActionNon(){return actionNon;}
	public Action[] getActionOui(){return actionOui;}


	//modificateurs
	public void setEtat(int telEtat){etat=telEtat;}
	public void setX(int telX){x=telX;}
	public void setY(int telY){y=telY;}
	public void setL(int telL){l=telL;}
	public void setH(int telH){h=telH;}
	public void setConditions(Condition[] tabCond){conditions=tabCond;}
	public void setActionNon(Action[] tabActionNon){actionNon=tabActionNon;}
	public void setActionOui(Action[] tabActionOui){actionOui=tabActionOui;}


}//fin classe Evenement




