public class Dors extends Thread {
    /** Un attribut propre à chaque thread */
    private int temps;
    Moteur moteur;

    /** Création et démarrage automatique du thread */
    public Dors(int telTemps, Moteur telMoteur)
    {
		moteur = telMoteur;
        temps = telTemps;
        start();
    }

    /** Le but d'un tel thread est d'afficher 500 fois
     *  son attribut threadName. Notons que la méthode
     *  <I>sleep</I> peut déclancher des exceptions.
     */
    public void run() {
        try{
                Thread.sleep(this.temps);
        } catch (InterruptedException exc) {exc.printStackTrace();}
		moteur.layeredPane.remove(moteur.image);
		moteur.imageDurBool=false;
		moteur.affichage();
		moteur.pause = false;
    }
}