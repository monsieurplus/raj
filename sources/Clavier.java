public class Clavier extends Thread {

    Moteur moteur;
    boolean moveG,moveD,moveH,moveB;

    /** Création et démarrage automatique du thread */
    public Clavier(Moteur telMoteur)
    {
		moteur=telMoteur;
        this.start();
    }


    public void run()
    {
		while(true)
		{
			try
			{
				Thread.sleep(40);
			}catch(Exception ex){ex.printStackTrace();}

				//déplacement du perso
				if((moteur.dirG)&&(moteur.verifPosition(4)))
				{
					moteur.perso.setLocation((moteur.perso.getX()-moteur.pPerso.vitesse), moteur.perso.getY());
				}
				if((moteur.dirD)&&(moteur.verifPosition(6)))
				{
					moteur.perso.setLocation((moteur.perso.getX()+moteur.pPerso.vitesse), moteur.perso.getY());
				}
				if((moteur.dirH)&&(moteur.verifPosition(8)))
				{
					moteur.perso.setLocation(moteur.perso.getX(), (moteur.perso.getY()-moteur.pPerso.vitesse));
					moteur.layeredPane.setLayer(moteur.perso,(moteur.perso.getY()+((moteur.perso.getSize()).height)));
				}
				if((moteur.dirB)&&(moteur.verifPosition(2)))
				{
					moteur.perso.setLocation(moteur.perso.getX(), (moteur.perso.getY()+moteur.pPerso.vitesse));
					moteur.layeredPane.setLayer(moteur.perso,(moteur.perso.getY()+((moteur.perso.getSize()).height)));
				}


			//affichage des images correspondantes

				if(moteur.dirG)
				{
					if(moteur.dirB)
					{
						moteur.perso.setIcon(moteur.maba);
						moteur.perso.setSize(moteur.maba.getIconWidth(),moteur.maba.getIconHeight());
						moveH = moveG = moveD = false;
						moveB = true;
					}
					else if(moteur.dirH)
					{
						moteur.perso.setIcon(moteur.maha);
						moteur.perso.setSize(moteur.maha.getIconWidth(),moteur.maha.getIconHeight());
						moveB = moveG = moveD = false;
						moveH = true;
					}
					else
					{
						moteur.perso.setIcon(moteur.maga);
						moteur.perso.setSize(moteur.maga.getIconWidth(),moteur.maga.getIconHeight());
						moveB = moveH = moveD = false;
						moveG = true;
					}
				}
				else if(moteur.dirD)
				{
					if(moteur.dirB)
					{
						moteur.perso.setIcon(moteur.maba);
						moteur.perso.setSize(moteur.maba.getIconWidth(),moteur.maba.getIconHeight());
						moveH = moveG = moveD = false;
						moveB = true;
					}
					else if(moteur.dirH)
					{
						moteur.perso.setIcon(moteur.maha);
						moteur.perso.setSize(moteur.maha.getIconWidth(),moteur.maha.getIconHeight());
						moveB = moveG = moveD = false;
						moveH = true;
					}
					else
					{
						moteur.perso.setIcon(moteur.madr);
						moteur.perso.setSize(moteur.madr.getIconWidth(),moteur.madr.getIconHeight());
						moveB = moveG = moveH = false;
						moveD = true;
					}
				}

				else if(moteur.dirB)
				{
					moteur.perso.setIcon(moteur.maba);
					moteur.perso.setSize(moteur.maba.getIconWidth(),moteur.maba.getIconHeight());
					moveH = moveG = moveD = false;
					moveB = true;
				}
				else if(moteur.dirH)
				{
					moteur.perso.setIcon(moteur.maha);
					moteur.perso.setSize(moteur.maha.getIconWidth(),moteur.maha.getIconHeight());
					moveB = moveG = moveD = false;
					moveH = true;
				}

				else if(!(moteur.dirG||moteur.dirD||moteur.dirH||moteur.dirB))
				{
					if(moveG){moteur.perso.setIcon(moteur.imga);moveG=false;}
					if(moveD){moteur.perso.setIcon(moteur.imdr);moveD=false;}
					if(moveH){moteur.perso.setIcon(moteur.imha);moveH=false;}
					if(moveB){moteur.perso.setIcon(moteur.imba);moteur.perso.setSize(moteur.imba.getIconWidth(),moteur.imba.getIconHeight());moveB=false;}
				}

		}
    }
}