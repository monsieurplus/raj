/*
 * LigneEDD.java
 *
 * Created on 29 mars 2007, 8:29
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author jeremy.grabowski
 */
public class LigneEDD {
    private String nom;
    private String chemin;
    private Integer x;
    private Integer y;
    private Integer xx;
    private Integer yy;
    
    public LigneEDD() {
    }
    
    public LigneEDD(String tNom, String tChemin, Integer tX, Integer tY, Integer tXx, Integer tYy) {
        nom = new String(tNom);
       chemin = new String(tChemin);
        x = new Integer(tX);
        y =new Integer(tY);
       xx = new Integer(tXx);
        yy =new Integer(tYy);
   }
    
 /*   public void affiche() {
        System.out.println("\nReference = " +ref+", Designation  = " +des);
    }*/
  //  public int compareTo(Individu i) {
  //      return nom.compareTo(i.nom);
 //   }
    String  getNom()	{ return nom; }
    String  getChemin()	{ return chemin; }
    Integer getX()	{ return x; }
    Integer  getY() 	{ return y; }
    Integer getXx()	{ return xx; }
    Integer  getYy() 	{ return yy; }
    
    void setNom (String val) 	{nom=new String(val);}
        void setChemin(String val) 	{chemin=new String(val);}
        void setX(Integer val) 	{x=new Integer(val);}
	void setY(Integer val) {y=new Integer(val);}
        void setXx(Integer val) 	{xx=new Integer(val);}
	void setYy(Integer val) {yy=new Integer(val);}
        
    
    
}