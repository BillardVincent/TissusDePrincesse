package fr.vbillard.tissusDePrincesse.dao.abstractDao;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ibatis.common.jdbc.ScriptRunner;

import fr.vbillard.tissusDePrincesse.services.PreferenceService;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class JPAHelper {
	private static final Log log = LogFactory.getLog(JPAHelper.class);
	private static final String persistenceUnit = Constants.PERSISTENCE_UNIT;
	private static PreferenceService preferenceService = new PreferenceService();

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
