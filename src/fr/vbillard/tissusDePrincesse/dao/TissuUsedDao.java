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
import fr.vbillard.tissusDePrincesse.model.Projet;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import fr.vbillard.tissusDePrincesse.model.TissuUsed;
import fr.vbillard.tissusDePrincesse.utils.Constants;


public class TissuUsedDao extends AbstractDao<TissuUsed>{
	
	public TissuUsedDao () {
		tableName = TissuUsed.class.getCanonicalName();
	}

	public List<TissuUsed> getTissuUsedByTissuRequis(TissuRequis tr) {
		List<TissuUsed> tissuUseds = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			// tissuUseds = em.createQuery("select tissuUsed from TissuUsed tissuUsed",
			// TissuUsed.class).getResultList();
			
			tissuUseds = em.createQuery("SELECT t FROM TissuUsed t where t.tissuRequis.id =:id ", TissuUsed.class).setParameter("id", tr.getId()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();

			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new PersistanceException("Une erreur s'est produite lors de la recupération des TissuUseds.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		log.debug("tissuUseds : " + tissuUseds);
		return tissuUseds;
	}


	public List<TissuUsed> findByTissu(Tissu t) {
		List<TissuUsed> tissuUseds = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			// tissuUseds = em.createQuery("select tissuUsed from TissuUsed tissuUsed",
			// TissuUsed.class).getResultList();
			
			tissuUseds = em.createQuery("SELECT t FROM TissuUsed t where t.tissu.id =:id ", TissuUsed.class).setParameter("id", t.getId()).getResultList();
		} catch (Exception e) {
			e.printStackTrace();

			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new PersistanceException("Une erreur s'est produite lors de la recupération des TissuUseds.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		log.debug("tissuUseds : " + tissuUseds);
		return tissuUseds;
	}

	
}
