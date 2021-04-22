package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.exception.GestionTissusException;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;



public class TissuDao {

	private static final Log log = LogFactory.getLog(TissuDao.class);
	private final String persistenceUnit = "persistUnit";
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction transaction = null;

	
	public List<Tissu> findAll() {
		List<Tissu> tissus = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			// tissus = em.createQuery("select tissu from Tissu tissu",
			// Tissu.class).getResultList();
			tissus = em.createQuery("SELECT tissu FROM Tissu tissu WHERE tissu.archived = false").getResultList();
		} catch (Exception e) {
			e.printStackTrace();

			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionTissusException("Une erreur s'est produite lors de la recupération des Tissus.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		log.debug("tissus : " + tissus);
		return tissus;
	}


	public Tissu create(Tissu tissu) {
		if (tissu != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				TypeTissu ts = em.find(TypeTissu.class, tissu.getTypeTissu().getId());
				tissu.setTypeTissu(ts);
				em.persist(tissu);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTissusException(
						"Une erreur s'est produite lors de la création de la Tissu : [id = " + tissu.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return tissu;
	}


	public Tissu update(Tissu tissu) {
		if (tissu != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				tissu = em.merge(tissu);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTissusException(
						"Une erreur s'est produite lors de la création de la Tissu : [id = " + tissu.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return tissu;
	}


	public boolean delete(Tissu tissu) {
		boolean isOk = false;
		if (tissu != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				tissu = em.find(Tissu.class, tissu.getId());
				em.remove(em.merge(tissu));
				transaction.commit();
				isOk = em.find(Tissu.class, tissu.getId()) == null;
			} catch (Exception e) {
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTissusException(
						"Une erreur s'est produite lors de la création de la Tissu : [id = " + tissu.getId() +"]");

			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return isOk;
	}


	public Tissu findById(Integer id) {
		Tissu tissu = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			tissu = em.find(Tissu.class, id);
		} catch (Exception e) {
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionTissusException(
					"Une erreur s'est produite lors de la création de la Tissu : [id = " + tissu.getId() +"]");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return tissu;
	}


	public boolean existByReference(String ref) {
		boolean exists;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			String query = "select case when (count(*) > 0) then true else false end from Tissu t where t.reference=:ref";
			TypedQuery<Boolean> booleanQuery = em.createQuery(query, Boolean.class).setParameter("ref", ref);
			exists = booleanQuery.getSingleResult();		} catch (Exception e) {
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionTissusException(
					"Une erreur s'est produite lors de la recherche de référence");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return exists;
	}

	/*
	@Override
	public List<Tissu> findByPrenom(String prenom) {
		List<Tissu> Tissus = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			Tissus = em
					.createQuery("select tissu from Tissu tissu where tissu.prenom=:firstName", Tissu.class)
					.setParameter("firstName", prenom).getResultList();
		} catch (Exception e) {
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionTissusException(
					"Une erreur s'est produite lors de la recupération des Tissus [prenom = " + prenom + "]", 6);
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return Tissus;
	}
	*/

/*

	@Override
	public List<Tissu> createBatchTissus(List<Tissu> tissus) {
		boolean isOk = false;
		int nbTissuCreated = 0;
		log.debug("1 : tissus : " + tissus);
		if (tissus != null && !tissus.isEmpty()) {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			transaction = em.getTransaction();
			transaction.begin();
			for (Tissu tissu : tissus) {
				try {
					em.persist(tissu);
					nbTissuCreated++;
				} catch (Exception e) {
					log.error("Erreur lors de l'execution de la methode, Exception : " + e);
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					throw new GestionTissusException(
							"Une erreur s'est produite lors de la création en masse des Tissus, Exception pour la Tissu : [id = "
									+ tissu.getIdTissu() + " , prenom = " + tissu.getPrenom() + " , nom = "
									+ tissu.getNom() + "]",
							6);
				}
			}
			if (transaction != null && transaction.isActive()) {
				transaction.commit();
				JPAHelper.closeEntityManagerResources(emf, em);
			}
			log.debug("2 : tissus : " + tissus);
			if (nbTissuCreated != tissus.size()) {
				tissus = null;
			}
		}
		log.debug("isOk : " + isOk + " , tissus : " + tissus);
		return tissus;
	}


	public Boolean deleteBatchTissus(List<Tissu> tissus) {
		boolean isOk = false;
		int nbTissuDeleted = 0;
		log.debug("1 : tissus : " + tissus);
		if (tissus != null && !tissus.isEmpty()) {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			transaction = em.getTransaction();
			transaction.begin();
			for (Tissu tissu : tissus) {
				try {
					tissu = em.find(Tissu.class, tissu.getIdTissu());
					em.remove(em.merge(tissu));
					nbTissuDeleted++;
				} catch (Exception e) {
					log.error("Erreur lors de l'execution de la methode, Exception : " + e);
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					throw new GestionTissusException(
							"Une erreur s'est produite lors de la suppresion en masse des Tissus, Exception pour la Tissu : [id = "
									+ tissu.getIdTissu() + " , prenom = " + tissu.getPrenom() + " , nom = "
									+ tissu.getNom() + "]",
							7);
				}
			}
			if (transaction != null && transaction.isActive()) {
				transaction.commit();
				JPAHelper.closeEntityManagerResources(emf, em);
			}
			log.debug("2 : Tissus : " + tissus);
			if (nbTissuDeleted == tissus.size()) {
				isOk = true;
			}
		}
		log.debug("isOk : " + isOk + " , tissus : " + tissus);
		return isOk;
	}
	*/
}
