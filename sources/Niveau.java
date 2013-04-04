import java.io.Serializable;

//classe Niveau
public class Niveau implements Serializable
{
	//chemin des composants du niveau :
	public String cheminEDD;//ElementDuDecor
	public String cheminOBS;//Obstacle
	public String cheminEVT;//Evenement

	//nom du niveau
	public String nomDuNiveau;

	//chemins des Niveaux selon la direction de sortie
	public String sortieG,sortieD,sortieH,sortieB;

	//constructeur par défaut
	public Niveau()
	{
		cheminEDD=cheminOBS=cheminEVT=nomDuNiveau=sortieG=sortieD=sortieH=sortieB="_";
	}


	public Niveau(String cheminEDD, String cheminOBS, String cheminEVT, String nomDuNiveau, String sortieG, String sortieD, String sortieH, String sortieB)
	{
		this.cheminEDD=cheminEDD;
		this.cheminOBS=cheminOBS;
		this.cheminEVT=cheminEVT;
		this.nomDuNiveau=nomDuNiveau;
		this.sortieG=sortieG;
		this.sortieD=sortieD;
		this.sortieH=sortieH;
		this.sortieB=sortieB;
	}


	//accesseurs
	public String getCheminEDD(){return cheminEDD;}
	public String getCheminOBS(){return cheminOBS;}
	public String getCheminEVT(){return cheminEVT;}
	public String getNomDuNiveau(){return nomDuNiveau;}
	public String getSortieG(){return sortieG;}
	public String getSortieD(){return sortieD;}
	public String getSortieH(){return sortieH;}
	public String getSortieB(){return sortieB;}

	//modificateurs
	public void setCheminEDD(String cheminEDD){this.cheminEDD=cheminEDD;}
	public void setCheminOBS(String cheminOBS){this.cheminOBS=cheminOBS;}
	public void setCheminEVT(String cheminEVT){this.cheminEVT=cheminEVT;}
	public void setNomDuNiveau(String nomDuNiveau){this.nomDuNiveau=nomDuNiveau;}
	public void setSortieG(String sortieG){this.sortieG=sortieG;}
	public void setSortieD(String sortieD){this.sortieD=sortieD;}
	public void setSortieH(String sortieH){this.sortieH=sortieH;}
	public void setSortieB(String sortieB){this.sortieB=sortieB;}

}//fin définition classe Niveau