package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractDao;
import fr.vbillard.tissusDePrincesse.dao.abstractDao.JPAHelper;
import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.Photo;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class PhotoDao extends AbstractDao<Photo>{

	public PhotoDao() {
		tableName = Photo.class.getCanonicalName();
	}

	public List<Photo> getByTissu(Tissu tissu) {
		List<Photo> photos = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			String query = "SELECT p FROM Photo p WHERE  p.tissu=:tissu";
			photos = em.createQuery(query, Photo.class).setParameter("tissu", tissu).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new PersistanceException(
					"Une erreur s'est produite lors de la récupération des photos du tissu : [id = " + tissu.getId() +"]");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return photos;		
	}

}
