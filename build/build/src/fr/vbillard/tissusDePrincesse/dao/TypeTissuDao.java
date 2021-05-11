package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.exception.GestionTissusException;
import fr.vbillard.tissusDePrincesse.exception.GestionTypeTissusException;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import fr.vbillard.tissusDePrincesse.utils.Constants;



public class TypeTissuDao {

	private static final Log log = LogFactory.getLog(TypeTissuDao.class);
	private final String persistenceUnit= Constants.PERSISTENCE_UNIT;;
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction transaction = null;

	
	public List<TypeTissu> findAll() {
		List<TypeTissu> typeTissus = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			// typeTissus = em.createQuery("select typeTissu from TypeTissu typeTissu",
			// TypeTissu.class).getResultList();
			typeTissus = em.createQuery("SELECT tt FROM TypeTissu tt") .getResultList();
		} catch (Exception e) {
			e.printStackTrace();

			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionTypeTissusException("Une erreur s'est produite lors de la recupération des TypeTissus.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		log.debug("typeTissus : " + typeTissus);
		return typeTissus;
	}


	public TypeTissu create(TypeTissu typeTissu) {
		if (typeTissu != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				em.persist(typeTissu);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTypeTissusException(
						"Une erreur s'est produite lors de la création de la TypeTissu : [id = " + typeTissu.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return typeTissu;
	}


	public TypeTissu update(TypeTissu typeTissu) {
		if (typeTissu != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				typeTissu = em.merge(typeTissu);
				transaction.commit();
			} catch (Exception e) {
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTypeTissusException(
						"Une erreur s'est produite lors de la création de la TypeTissu : [id = " + typeTissu.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return typeTissu;
	}


	public boolean delete(TypeTissu typeTissu) {
		boolean isOk = false;
		if (typeTissu != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				typeTissu = em.find(TypeTissu.class, typeTissu.getId());
				em.remove(em.merge(typeTissu));
				transaction.commit();
				isOk = em.find(TypeTissu.class, typeTissu.getId()) == null;
			} catch (Exception e) {
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTypeTissusException(
						"Une erreur s'est produite lors de la création de la TypeTissu : [id = " + typeTissu.getId() +"]");

			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return isOk;
	}


	public TypeTissu findById(Integer id) {
		TypeTissu typeTissu = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			typeTissu = em.find(TypeTissu.class, id);
		} catch (Exception e) {
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionTypeTissusException(
					"Une erreur s'est produite lors de la création de la TypeTissu : [id = " + typeTissu.getId() +"]");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return typeTissu;
	}
	
	public int count() {
		int count = 0;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			count = em.createQuery("select Count (*) FROM TypeTissu ") .getFirstResult();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionTypeTissusException(
					"Ca a pas compté....");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return count;
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
