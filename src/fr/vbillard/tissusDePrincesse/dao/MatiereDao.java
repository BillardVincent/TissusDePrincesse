package fr.vbillard.tissusDePrincesse.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.Matiere;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.images.Photo;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class MatiereDao extends AbstractDao<Matiere>{

	public MatiereDao() {
		tableName = Matiere.class.getCanonicalName();
	}

	public Matiere findByMatiere(String matiereName) {
		// TODO Auto-generated method stub
		Matiere matiere = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			String query = "SELECT m FROM Matiere m WHERE  m.matiere=:matiereName";
			matiere = em.createQuery(query, Matiere.class).setParameter("matiereName", matiereName).getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new PersistanceException(
					"Une erreur s'est produite lors de la récupération de la metiere : " + matiere.getMatiere() +"]");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return matiere;		
	}

	public boolean existsByMatiere(String value) {
		boolean exists;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			String query = "select case when (count(*) > 0) then true else false end from Matiere m where m.matiere=:value";
			TypedQuery<Boolean> booleanQuery = em.createQuery(query, Boolean.class).setParameter("value", value);
			exists = booleanQuery.getSingleResult();		} catch (Exception e) {
			throw new PersistanceException(
					"Une erreur s'est produite lors de la recherche de référence");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return exists;
	}

}
