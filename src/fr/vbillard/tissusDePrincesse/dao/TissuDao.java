package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.Tissage;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import fr.vbillard.tissusDePrincesse.utils.Constants;



public class TissuDao extends AbstractDao<Tissu>{
	
	public TissuDao () {
		tableName = Tissu.class.getCanonicalName();
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
			throw new PersistanceException(
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
