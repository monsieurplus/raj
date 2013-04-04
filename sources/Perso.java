import java.io.Serializable;

//classe ElementDuDecor
public class Perso implements Serializable
{
	public String nom;
	public int x;//position X du personnage
	public int y;//position Y du personnage
	public int vitesse;//vitesse de déplacement du perso
	public String imga,imdr,imha,imba;//chemins des images pour le perso immobile
	public String maga,madr,maha,maba;//chemins des images por le perso en mouvement

	public String cheminNiveau;//chemin du niveau dans lequel est le Perso
	public String carac[];//caractéristiques du Perso
	public String nomCarac[];//nom des caractéristiques du Perso

	//configuration du clavier pour ce perso
	public ConfigClavier touches=new ConfigClavier();

	//constructeur par défaut
	public void Perso()
	{
		x=y=vitesse=0;
		nom=new String("nom");
		imga=imdr=imha=imba=maga=madr=maha=maba=cheminNiveau=null;
		carac=nomCarac=null;
	}

	public void Perso(String nomP, int xP, int yP, int vP, String imageP, String cheminNiveauP, String[] caracP, String[] nomCaracP)
	{
		nom=nomP;
		x=xP;
		y=yP;
		vitesse=vP;
		imga=imdr=imha=imba=madr=maga=maha=maba=imageP; //dans tout ses mouvements, le Perso a la même apparance
		cheminNiveau=cheminNiveauP;
		carac=caracP;
		nomCarac=nomCaracP;
	}

	public void Perso(String nomP, int xP, int yP, int vP, String imgaP, String imdrP, String imhaP, String imbaP, String cheminNiveauP, String caracP[], String nomCaracP[])
	{
		nom=nomP;
		x=xP;
		y=yP;
		vitesse=vP;
		maga=imga=imgaP;
		madr=imdr=imdrP;
		maha=imha=imhaP;
		maba=imba=imbaP;
		cheminNiveau=cheminNiveauP;
		carac=caracP;
		nomCarac=nomCaracP;
	}

	public void Perso(String nomP, int xP, int yP, int vP, String imgaP, String imdrP, String imhaP, String imbaP, String magaP, String madrP, String mahaP, String mabaP, String cheminNiveauP, String caracP[], String nomCaracP[])
	{
		nom=nomP;
		x=xP;
		y=yP;
		vitesse=vP;
		imga=imgaP;
		imdr=imdrP;
		imha=imhaP;
		imba=imbaP;
		maga=magaP;
		madr=madrP;
		maha=mahaP;
		maba=mabaP;
		cheminNiveau=cheminNiveauP;
		carac=caracP;
		nomCarac=nomCaracP;
	}

	//accesseurs
	public String getNom(){return nom;}
	public int getX(){return x;}
	public int getY(){return y;}
	public int getVitesse(){return vitesse;}
	public String getImga(){return imga;}
	public String getImdr(){return imdr;}
	public String getImha(){return imha;}
	public String getImba(){return imba;}
	public String getMaga(){return maga;}
	public String getMadr(){return madr;}
	public String getMaha(){return maha;}
	public String getMaba(){return maba;}
	public String getCheminNiveau(){return cheminNiveau;}
	public String[] getCarac(){return carac;}
	public String[] getNomCarac(){return nomCarac;}
	public ConfigClavier getTouches(){return touches;}

	//modificateurs
	public void setNom(String nomP){nom=nomP;}
	public void setX(int xP){x=xP;}
	public void setY(int yP){y=yP;}
	public void setVitesse(int vP){vitesse=vP;}
	public void setImga(String imgaP){imga=imgaP;}
	public void setImdr(String imdrP){imdr=imdrP;}
	public void setImha(String imhaP){imha=imhaP;}
	public void setImba(String imbaP){imba=imbaP;}
	public void setMaga(String magaP){maga=magaP;}
	public void setMadr(String madrP){madr=madrP;}
	public void setMaha(String mahaP){maha=mahaP;}
	public void setMaba(String mabaP){maba=mabaP;}
	public void setCheminNiveau(String cheminNiveauP){cheminNiveau=cheminNiveauP;}
	public void setCarac(String caracP[]){carac=caracP;}
	public void setNomCarac(String nomCaracP[]){nomCarac=nomCaracP;}
	public void setTouches(ConfigClavier conf){touches=conf;}

}//fin définition classe Perso