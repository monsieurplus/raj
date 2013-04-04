import java.io.Serializable;
import java.awt.event.KeyEvent;

public class ConfigClavier implements Serializable
{
	//différentes touches
	public int haut;
	public int bas;
	public int gauche;
	public int droite;
	public int action;
	public int sauvegarder;
	public int charger;
	public int aide;
	public int options;
	public int parametres;
	public int inventaire;
	public int bilanPerso;

	//constructeur par défaut
	public ConfigClavier()
	{
		haut=KeyEvent.VK_UP;
		bas=KeyEvent.VK_DOWN;
		gauche=KeyEvent.VK_LEFT;
		droite=KeyEvent.VK_RIGHT;

		action=KeyEvent.VK_SPACE;
		inventaire=KeyEvent.VK_SHIFT;
		bilanPerso=KeyEvent.VK_F2;

		sauvegarder=KeyEvent.VK_F6;
		charger=KeyEvent.VK_F7;

		parametres=KeyEvent.VK_F3;
		options=KeyEvent.VK_F4;
		aide=KeyEvent.VK_F1;

	}

	//accesseurs
	public int getHaut(){return new Integer(haut);}
	public int getBas(){return bas;}
	public int getGauche(){return gauche;}
	public int getDroite(){return droite;}
	public int getAction(){return action;}
	public int getSauvegarder(){return sauvegarder;}
	public int getCharger(){return charger;}
	public int getAide(){return aide;}
	public int getInventaire(){return inventaire;}
	public int getBilanPerso(){return bilanPerso;}
	public int getOptions(){return options;}
	public int getParametres(){return parametres;}

	//modificateurs
	public void setHaut(int e){haut=e;}
	public void setBas(int e){bas=e;}
	public void setGauche(int e){gauche=e;}
	public void setDroite(int e){droite=e;}
	public void setAction(int e){action=e;}
	public void setSauvegarder(int e){sauvegarder=e;}
	public void setCharger(int e){charger=e;}
	public void setAide(int e){aide=e;}
	public void setInventaire(int e){inventaire=e;}
	public void setBilanPerso(int e){bilanPerso=e;}
	public void setOptions(int e){options=e;}
	public void setParametres(int e){parametres=e;}

}