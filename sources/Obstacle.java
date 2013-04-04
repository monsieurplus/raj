import java.io.Serializable;

//classe Obstacle
public class Obstacle implements Serializable
{
	public int x1;
	public int x2;
	public int y1;
	public int y2;

	//constructeur par défaut
	public Obstacle(){x1=x2=y1=y2=0;}

	public Obstacle(int x){x1=x2=y1=y2=x;}

	public Obstacle(int x1,int x2,int y1,int y2)
	{this.x1=x1;this.x2=x2;this.y1=y1;this.y2=y2;}

	public Obstacle(Obstacle obs2)
	{x1=obs2.x1;x2=obs2.x2;y1=obs2.y1;y2=obs2.y2;}

	//accesseurs
	public int getX1(){return x1;}
	public int getX2(){return x2;}
	public int getY1(){return y1;}
	public int getY2(){return y2;}

	//modificateurs
	public void setX1(int x1){this.x1=x1;}
	public void setX2(int x2){this.x2=x2;}
	public void setY1(int y1){this.y1=y1;}
	public void setY2(int y2){this.y2=y2;}

	public void setNULL(){this.x1=0;this.x2=0;this.y1=0;this.y2=0;}

}//fin définition classe Obstacle
