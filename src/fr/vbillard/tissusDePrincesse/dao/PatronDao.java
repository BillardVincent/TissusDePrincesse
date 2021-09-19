package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;


import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class PatronDao {
	private final String persistenceUnit = Constants.PERSISTENCE_UNIT;
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction transaction = null;

	public Patron create(Patron patron) {
		if (patron != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				em.persist(patron);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new PersistanceException(
						"Une erreur s'est produite lors de la création du Patron : [id = " + patron.getId() + "]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return patron;
	}

	public boolean existByReference(String ref) {
		boolean exists;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			String query = "select case when (count(*) > 0) then true else false end from Patron p where p.reference=:ref";
			TypedQuery<Boolean> booleanQuery = em.createQuery(query, Boolean.class).setParameter("ref", ref);
			exists = booleanQuery.getSingleResult();		} catch (Exception e) {
			throw new PersistanceException(
					"Une erreur s'est produite lors de la recherche de référence");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return exists;
	}

	public List<Patron> findAll() {
		List<Patron> patrons = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();

			patrons = em.createQuery("SELECT p FROM Patron p") .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new PersistanceException("Une erreur s'est produite lors de la recupération des patrons.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return patrons;
	}
	
	public Patron update(Patron patron) {
		if (patron != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				patron = em.merge(patron);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new PersistanceException(
						"Une erreur s'est produite lors de la création du patron : [id = " + patron.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return patron;
		
	}

	public boolean delete(Patron patron) {
		boolean isOk = false;
		if (patron != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				patron = em.find(Patron.class, patron.getId());
				em.remove(em.merge(patron));
				transaction.commit();
				isOk = em.find(Patron.class, patron.getId()) == null;
			} catch (Exception e) {
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				e.printStackTrace();
				throw new PersistanceException(
						"Une erreur s'est produite lors de la suppression du patron : [id = " + patron.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return isOk;
	}

	

}
