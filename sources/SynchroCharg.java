public class SynchroCharg extends Thread {

	Moteur moteur;
	ChargPerso charg;


    public SynchroCharg(Moteur telMoteur, ChargPerso telChargeur)
    {
		moteur=telMoteur;
		charg=telChargeur;
        this.start();
    }


    public void run()
    {
		moteur.pause=true;
		moteur.operSauv=true;
		while(charg.choix==null)
		{
			try{
				sleep(100);
			}catch(Exception ex){ex.printStackTrace();}
		}

		if (charg.choix.equals("annul"))
		{
			charg.frame.dispose();
		}
		else if(charg.choix.equals("nouvellePartie"))
		{
			moteur.pPerso=moteur.jeu.persoDefaut;
			moteur.chargementPerso();
			moteur.changementNiveau(moteur.pPerso.x,moteur.pPerso.y);
			charg.frame.dispose();
		}
		else
		{
			moteur.decodagePerso(charg.choix+".xml");
			moteur.chargementPerso();
			moteur.changementNiveau(moteur.pPerso.x,moteur.pPerso.y);
			charg.frame.dispose();
		}
		moteur.pause=false;
		moteur.operSauv=false;

	}
}