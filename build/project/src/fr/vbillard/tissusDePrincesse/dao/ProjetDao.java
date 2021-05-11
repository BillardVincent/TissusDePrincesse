package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.vbillard.tissusDePrincesse.exception.GestionProjetsException;
import fr.vbillard.tissusDePrincesse.exception.GestionTissusException;
import fr.vbillard.tissusDePrincesse.exception.GestionTypeTissusException;
import fr.vbillard.tissusDePrincesse.model.Projet;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class ProjetDao {
	private final String persistenceUnit = Constants.PERSISTENCE_UNIT;
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction transaction = null;

	public Projet create(Projet projet) {
		if (projet != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				em.persist(projet);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionProjetsException(
						"Une erreur s'est produite lors de la création du Projet : [id = " + projet.getId() + "]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return projet;
	}

	

	public List<Projet> findAll() {
		List<Projet> projets = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();

			projets = em.createQuery("SELECT p FROM Projet p") .getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			throw new GestionTypeTissusException("Une erreur s'est produite lors de la recupération des projets.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return projets;
	}
	
	public Projet update(Projet projet) {
		if (projet != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				projet = em.merge(projet);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionTissusException(
						"Une erreur s'est produite lors de la création du projet : [id = " + projet.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return projet;
		
	}
}
