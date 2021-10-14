package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractDao;
import fr.vbillard.tissusDePrincesse.dao.abstractDao.JPAHelper;
import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.TissuUsed;
import fr.vbillard.tissusDePrincesse.model.TissuVariant;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class TissuVariantDao extends AbstractDao<TissuVariant>{
	
	public TissuVariantDao () {
		tableName = TissuVariant.class.getCanonicalName();
	}
	
	public List<TissuVariant> getVariantByTissuRequis(int tissuRequisId) {
		List<TissuVariant> tissuVariant = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			// tissages = em.createQuery("select tissage from Tissage tissage",
			// Tissage.class).getResultList();
			tissuVariant = em.createQuery("SELECT tv FROM TissuVariant tv WHERE tv.tissuRequis.id =:id", TissuVariant.class).setParameter("id", tissuRequisId).getResultList();
		} catch (Exception e) {
			e.printStackTrace();

			
			throw new PersistanceException("Une erreur s'est produite lors de la recupération des TissuVariant.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return tissuVariant;
	}
	
	

}
