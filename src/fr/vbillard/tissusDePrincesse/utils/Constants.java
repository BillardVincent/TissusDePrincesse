package fr.vbillard.tissusDePrincesse.utils;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class Constants {

	public static final int EXCEPTION_CODE_PERSON_ALREADY_EXIST = -1;
	public static final String EXCEPTION_MESSAGE_PERSON_ALREADY_EXIST = "Attention une personne avec le même prenom+nom existe déja";

	// Url de connexion en base de donnée
	// MYSQL 5 :
	// jdbc:mysql://localhost:3306/base_personnes?serverTimezone=UTC&useSSL=false
	// MYSQL 8 : jdbc:mysql://localhost:3308/base_personnes?serverTimezone=UTC
	public static String DATABASE_URL = "jdbc:h2:file:/data/demo";

	// Utilisateur de la base de données
	public static String DATABASE_USER = "sa";

	// Mot de passe de la base de données
	public static String DATABASE_PASSWORD = "";

	public static String DATABASE_NAME = "base_personnes";
	// Drivers Jdbc
	// MYSQL 5 : com.mysql.jdbc.Driver
	// MYSQL 8 : com.mysql.cj.jdbc.Driver
	public static String JDBC_DRIVER = "org.h2.Driver";
	
	public final static String PERSISTENCE_UNIT = "persistUnit";

	// Junit
	public static String SQL_JUNIT_PATH_FILE = "script_test_junit_base_personnes.sql";

	public static String DB_UNIT_PATH_FILE = "datas_personnes.xml";
	
	public static Paint colorAdd = Color.GREEN;
	public static Paint colorDelete = Color.RED;

}