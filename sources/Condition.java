import java.io.Serializable;

//classe Condition
//cette classe sert à définir les conditions utilisées dans le jeu liées à l'interraction du joueur
//les conditions sont de types multiples, mais cette classe doit pouvoir les implémenter toutes
//la classe est donc composée d'un paramètre String nomCondition et de 10 paramètres String param1~10 servant à définir la condition
public class Condition implements Serializable
{
	public String nomCondition;//nom de l'Condition
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
	public Condition()
	{
		nomCondition=param0=param1=param2=param3=param4=param5=param6=param7=param8=param9="_";
	}

	//constructeur de Condition n'ayant pas de paramètre (juste un nom qui permet de la reconnaître)
	public Condition(String telNom)
	{
		nomCondition=telNom;
	}

	//constructeur de Condition n'ayant qu'un seul paramètre
	public Condition(String telNom, String p0)
	{
		nomCondition=telNom;
		param0=p0;
	}

	public Condition(String telNom, String p0, String p1)
	{
		nomCondition=telNom;
		param0=p0;
		param1=p1;
	}

	public Condition(String telNom, String p0, String p1, String p2)
	{
		nomCondition=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
	}

	public Condition(String telNom, String p0, String p1, String p2, String p3)
	{
		nomCondition=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
		param3=p3;
	}

	public Condition(String telNom, String p0, String p1, String p2, String p3, String p4)
	{
		nomCondition=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
		param3=p3;
		param4=p4;
	}

	public Condition(String telNom, String p0, String p1, String p2, String p3, String p4, String p5)
	{
		nomCondition=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
		param3=p3;
		param4=p4;
		param5=p5;
	}

	public Condition(String telNom, String p0, String p1, String p2, String p3, String p4, String p5, String p6)
	{
		nomCondition=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
		param3=p3;
		param4=p4;
		param5=p5;
		param6=p6;
	}

	public Condition(String telNom, String p0, String p1, String p2, String p3, String p4, String p5, String p6, String p7)
	{
		nomCondition=telNom;
		param0=p0;
		param1=p1;
		param2=p2;
		param3=p3;
		param4=p4;
		param5=p5;
		param6=p6;
		param7=p7;
	}

	public Condition(String telNom, String p0, String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8)
	{
		nomCondition=telNom;
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

	//constructeur de Condition avec tout les paramètres utilisés
	public Condition(String telNom, String p0, String p1, String p2, String p3, String p4, String p5, String p6, String p7, String p8, String p9)
	{
		nomCondition=telNom;
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
	public String getNomCondition(){return nomCondition;}
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
	public void setNomCondition(String telNom){nomCondition=telNom;}
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

}//fin définition classe Condition