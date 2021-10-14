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
import fr.vbillard.tissusDePrincesse.dtosFx.TissuRequisDto;
import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.Tissage;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import fr.vbillard.tissusDePrincesse.model.TissuVariant;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class TissusRequisDao extends AbstractDao<TissuRequis>{
	
	public TissusRequisDao () {
		tableName = TissuRequis.class.getCanonicalName();
	}
	
	public List<TissuRequis> getAllTissuRequisByPatron(int id) {
		List<TissuRequis> tissusRequis = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			// tissages = em.createQuery("select tissage from Tissage tissage",
			// Tissage.class).getResultList();
			tissusRequis = em.createQuery("SELECT tr FROM TissuRequis tr WHERE tr.patron.id =:id", TissuRequis.class).setParameter("id", id).getResultList();
		} catch (Exception e) {
			e.printStackTrace();

			
			throw new PersistanceException("Une erreur s'est produite lors de la recupération des Tissages.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return tissusRequis;
	}

}
