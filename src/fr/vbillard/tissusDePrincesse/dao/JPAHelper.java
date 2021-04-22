package fr.vbillard.tissusDePrincesse.dao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.common.jdbc.ScriptRunner;

import fr.vbillard.tissusDePrincesse.utils.Constants;

public class JPAHelper {
	private static final Log log = LogFactory.getLog(JPAHelper.class);

	public static void closeEntityManagerResources(EntityManagerFactory emf, EntityManager em) {
		try {
			if (emf != null && emf.isOpen()) {
				emf.close();
			}
		} catch (Exception e) {
			log.error("Erreur lors de la fermeture de l'EntityManagerFactory , Exception :  " + e);
		}
		try {
			if (em != null && em.isOpen()) {
				em.close();
			}
		} catch (Exception e) {
			log.error("Erreur lors de la fermeture de l'EntityManager , Exception :  " + e);
		}

	}

	public static void initDataBase() {
		// Initialiser les données de la base de données
		//String scriptSqlPath = Constants.SQL_JUNIT_PATH_FILE;
		Reader reader = null;
		Connection connection = null;
		try {
			// Creer une connexion a la base de données.
			Class.forName(Constants.JDBC_DRIVER);
			connection = DriverManager.getConnection(Constants.DATABASE_URL, Constants.DATABASE_USER,
					Constants.DATABASE_PASSWORD);
			// Initialiser l'objet ScripRunner
			ScriptRunner scriptRunner = new ScriptRunner(connection, false, false);
			// Ouvrir le fichier
			//reader = new BufferedReader(new FileReader(scriptSqlPath));
			// Executer le script Sql
			//scriptRunner.runScript(reader);
		} catch (Exception e) {
			//log.error("Erreur lors de l'execution du script : " + scriptSqlPath + " , Exception : " + e);
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}

			} catch (IOException io) {
				log.error("Erreur lors de la fermiture d'un fichier dans la methode, Exception : " + io.getMessage());
			}
			try {
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException sqlEx) {
				log.error("Erreur lors de la fermiture de la connexion dans la methode, Exception : "
						+ sqlEx.getMessage());
			}
		}
	}

	public static void closeResources(Connection connection, PreparedStatement preparedStatement, ResultSet result) {
		String methodName = "closeResources";
		if (connection != null) {
			try {
				connection.close();
			} catch (Exception e) {
				System.out.println(
						"Erreur lors de l'execution de la methode " + methodName + " , Exception: " + e.getMessage());
			}
		}
		if (preparedStatement != null) {
			try {
				preparedStatement.close();
			} catch (Exception e) {
				System.out.println(
						"Erreur lors de l'execution de la methode " + methodName + " , Exception: " + e.getMessage());
			}
		}
		if (result != null) {
			try {
				result.close();
			} catch (Exception e) {
				System.out.println(
						"Erreur lors de l'execution de la methode " + methodName + " , Exception: " + e.getMessage());
			}
		}
	}
}
