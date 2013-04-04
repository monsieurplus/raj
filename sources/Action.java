import java.io.Serializable;

//classe Action
//cette classe sert à définir les actions effectuées dans le jeu liées à l'interraction du joueur
//les actions sont de types multiples, mais cette classe doit pouvoir les implémenter toutes
//la classe est donc composée d'un paramètre String nomAction et de 10 paramètres String param1~10 servant à définir l'action
public class Action implements Serializable
{
	public String nomAction;//nom de l'Action
	public String param0;//paramètres
	public String param1;//...
	public String param2;
	public String param3;
	public String param4;
	public String param5;
	public String param6;
	public String param7;
	public String param8;
	public String param9;

	//constructeur par défaut
	public Action()
	{
		nomAction=param0=param1=param2=param3=param4=param5=param6=param7=param8=param9="_";
	}

	//constructeur d'Action n'ayant pas de paramètre (juste un nom qui permet de la reconnaître)
	public Action(String telNom)
	{
		nomAction=telNom;
	}

	//constructeur d'Action n'ayant qu'un seul paramètre
	public Action(String telNom, String p0)
	{
		nomAction=telNom;
		param0=p0;
	}

	public Action(String telNom, String p0, String p1)
	{
		nomAction=telNom;
		param0=p0;
		param1=p1;
	}

	public Action(String telNom, String p0, String p1, String p2)
	{
		nomAction=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
	}

	public Action(String telNom, String p0, String p1, String p2, String p3)
	{
		nomAction=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
		param3=p3;
	}

	public Action(String telNom, String p0, String p1, String p2, String p3, String p4)
	{
		nomAction=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
		param3=p3;
		param4=p4;
	}

	public Action(String telNom, String p0, String p1, String p2, String p3, String p4, String p5)
	{
		nomAction=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
		param3=p3;
		param4=p4;
		param5=p5;
	}

	public Action(String telNom, String p0, String p1, String p2, String p3, String p4, String p5, String p6)
	{
		nomAction=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
		param3=p3;
		param4=p4;
		param5=p5;
		param6=p6;
	}

	public Action(String telNom, String p0, String p1, String p2, String p3, String p4, String p5, String p6, String p7)
	{
		nomAction=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
		param3=p3;
		param4=p4;
		param5=p5;
		param6=p6;
		param7=p7;
	}

	public Action(String telNom, String p0, String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8)
	{
		nomAction=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
		param3=p3;
		param4=p4;
		param5=p5;
		param6=p6;
		param7=p7;
		param8=p8;
	}

	//constructeur d'Action avec tout les paramètres utilisés
	public Action(String telNom, String p0, String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8, String p9)
	{
		nomAction=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
		param3=p3;
		param4=p4;
		param5=p5;
		param6=p6;
		param7=p7;
		param8=p8;
		param9=p9;
	}

	//accesseurs
	public String getNomAction(){return nomAction;}
	public String getParam0(){return param0;}
	public String getParam1(){return param1;}
	public String getParam2(){return param2;}
	public String getParam3(){return param3;}
	public String getParam4(){return param4;}
	public String getParam5(){return param5;}
	public String getParam6(){return param6;}
	public String getParam7(){return param7;}
	public String getParam8(){return param8;}
	public String getParam9(){return param9;}

	//modificateurs
	public void setNomAction(String telNom){nomAction=telNom;}
	public void setParam0(String p0){param0=p0;}
	public void setParam1(String p1){param1=p1;}
	public void setParam2(String p2){param2=p2;}
	public void setParam3(String p3){param3=p3;}
	public void setParam4(String p4){param4=p4;}
	public void setParam5(String p5){param5=p5;}
	public void setParam6(String p6){param6=p6;}
	public void setParam7(String p7){param7=p7;}
	public void setParam8(String p8){param8=p8;}
	public void setParam9(String p9){param9=p9;}

}//fin définition classe Action