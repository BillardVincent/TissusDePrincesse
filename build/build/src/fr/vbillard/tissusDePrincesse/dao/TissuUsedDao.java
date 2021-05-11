package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.exception.GestionTissuUsedsException;
import fr.vbillard.tissusDePrincesse.model.Projet;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import fr.vbillard.tissusDePrincesse.model.TissuUsed;
import fr.vbillard.tissusDePrincesse.utils.Constants;





public class TissuUsedDao {

	private static final Log log = LogFactory.getLog(TissuUsedDao.class);
	private final String persistenceUnit = Constants.PERSISTENCE_UNIT;
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction transaction = null;

	
	public List<TissuUsed> findAll() {
		List<TissuUsed> tissuUseds = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			// tissuUseds = em.createQuery("select tissuUsed from TissuUsed tissuUsed",
			// TissuUsed.class).getResultList();
			tissuUseds = em.createQuery("SELECT t FROM TissuUsed t ").getResultList();
		} catch (Exception e) {
			e.printStackTrace();

			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionTissuUsedsException("Une erreur s'est produite lors de la recupération des TissuUseds.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		log.debug("tissuUseds : " + tissuUseds);
		return tissuUseds;
	}


	public TissuUsed create(TissuUsed tissuUsed) {
		if (tissuUsed != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				Projet p = em.createQuery("SELECT p FROM Projet p WHERE p.id =:id", Projet.class).setParameter("id", tissuUsed.getProjet().getId()).getSingleResult();
				tissuUsed.setProjet(p);
				Tissu t = em.createQuery("SELECT t FROM Tissu t WHERE t.id =:id", Tissu.class).setParameter("id", tissuUsed.getTissu().getId()).getSingleResult();
				tissuUsed.setProjet(p);
				TissuRequis tr = em.createQuery("SELECT tr FROM TissuRequis tr WHERE tr.id =:id", TissuRequis.class).setParameter("id", tissuUsed.getTissuRequis().getId()).getSingleResult();
				tissuUsed.setProjet(p);
				em.persist(tissuUsed);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTissuUsedsException(
						"Une erreur s'est produite lors de la création de la TissuUsed : [id = " + tissuUsed.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return tissuUsed;
	}


	public TissuUsed update(TissuUsed tissuUsed) {
		if (tissuUsed != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				tissuUsed = em.merge(tissuUsed);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTissuUsedsException(
						"Une erreur s'est produite lors de la création de la TissuUsed : [id = " + tissuUsed.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return tissuUsed;
	}


	public boolean delete(TissuUsed tissuUsed) {
		boolean isOk = false;
		if (tissuUsed != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				tissuUsed = em.find(TissuUsed.class, tissuUsed.getId());
				em.remove(em.merge(tissuUsed));
				transaction.commit();
				isOk = em.find(TissuUsed.class, tissuUsed.getId()) == null;
			} catch (Exception e) {
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTissuUsedsException(
						"Une erreur s'est produite lors de la création de la TissuUsed : [id = " + tissuUsed.getId() +"]");

			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return isOk;
	}


	public TissuUsed findById(Integer id) {
		TissuUsed tissuUsed = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			tissuUsed = em.find(TissuUsed.class, id);
		} catch (Exception e) {
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionTissuUsedsException(
					"Une erreur s'est produite lors de la création de la TissuUsed : [id = " + tissuUsed.getId() +"]");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return tissuUsed;
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
			throw new GestionTissuUsedsException("Une erreur s'est produite lors de la recupération des TissuUseds.");
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
			throw new GestionTissuUsedsException("Une erreur s'est produite lors de la recupération des TissuUseds.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		log.debug("tissuUseds : " + tissuUseds);
		return tissuUseds;
	}

	
}
