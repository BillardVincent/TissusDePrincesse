package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.exception.GestionTissagesException;
import fr.vbillard.tissusDePrincesse.model.Tissage;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class TissageDao {
	private static final Log log = LogFactory.getLog(TissageDao.class);
	private final String persistenceUnit = Constants.PERSISTENCE_UNIT;
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction transaction = null;

	
	public List<Tissage> findAll() {
		List<Tissage> tissages = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			// tissages = em.createQuery("select tissage from Tissage tissage",
			// Tissage.class).getResultList();
			tissages = em.createQuery("SELECT tissage FROM Tissage tissage") .getResultList();
		} catch (Exception e) {
			e.printStackTrace();

			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionTissagesException("Une erreur s'est produite lors de la recupération des Tissages.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		log.debug("tissages : " + tissages);
		return tissages;
	}


	public Tissage create(Tissage tissage) {
		if (tissage != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				em.persist(tissage);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTissagesException(
						"Une erreur s'est produite lors de la création de la Tissage : [id = " + tissage.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return tissage;
	}


	public Tissage update(Tissage tissage) {
		if (tissage != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				tissage = em.merge(tissage);
				transaction.commit();
			} catch (Exception e) {
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTissagesException(
						"Une erreur s'est produite lors de la création de la Tissage : [id = " + tissage.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return tissage;
	}


	public boolean delete(Tissage tissage) {
		boolean isOk = false;
		if (tissage != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				tissage = em.find(Tissage.class, tissage.getId());
				em.remove(em.merge(tissage));
				transaction.commit();
				isOk = em.find(Tissage.class, tissage.getId()) == null;
			} catch (Exception e) {
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTissagesException(
						"Une erreur s'est produite lors de la création de la Tissage : [id = " + tissage.getId() +"]");

			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return isOk;
	}


	public Tissage findById(Integer id) {
		Tissage tissage = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			tissage = em.find(Tissage.class, id);
		} catch (Exception e) {
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionTissagesException(
					"Une erreur s'est produite lors de la création de la Tissage : [id = " + tissage.getId() +"]");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return tissage;
	}
	
	public int count() {
		int count = 0;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			count = em.createQuery("select Count (*) FROM Tissage ") .getFirstResult();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionTissagesException(
					"Ca a pas compté....");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return count;
	}

}
