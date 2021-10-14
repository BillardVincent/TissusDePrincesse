package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.Persistence;

import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractDao;
import fr.vbillard.tissusDePrincesse.dao.abstractDao.JPAHelper;
import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.Projet;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import fr.vbillard.tissusDePrincesse.model.TissuUsed;

public class TissuUsedDao extends AbstractDao<TissuUsed> {

	public TissuUsedDao() {
		tableName = TissuUsed.class.getCanonicalName();
	}

	public List<TissuUsed> findByTissu(Tissu t) {
		List<TissuUsed> tissuUseds = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			tissuUseds = em.createQuery("SELECT t FROM TissuUsed t where t.tissu.id =:id ", TissuUsed.class)
					.setParameter("id", t.getId()).getResultList();
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

	public List<TissuUsed> getTissuUsedByTissuRequisAndProjet(TissuRequis tr, Projet p) {
		List<TissuUsed> tissuUseds = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			tissuUseds = em
					.createQuery("SELECT t FROM TissuUsed t where t.tissuRequis.id =:tr_id  AND t.projet.id=:p_id",
							TissuUsed.class)
					.setParameter("tr_id", tr.getId()).setParameter("p_id", p.getId()).getResultList();
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
