package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.TissuVariant;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class TissuVariantDao {

	
	private final String persistenceUnit = Constants.PERSISTENCE_UNIT;
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction transaction = null;
	

	public TissuVariant create(TissuVariant tissu) {
		if (tissu != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				em.persist(tissu);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new PersistanceException(
						"Une erreur s'est produite lors de la création de la Tissu : [id = " + tissu.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return tissu;
	}

	public TissuVariant update(TissuVariant tissu) {
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
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new PersistanceException(
						"Une erreur s'est produite lors de la création de la Tissu : [id = " + tissu.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return tissu;
		
	}
	
	public TissuVariant findById(Integer id) {
		TissuVariant tissu = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			tissu = em.find(TissuVariant.class, id);
		} catch (Exception e) {
			throw new PersistanceException(
					"Une erreur s'est produite lors de la création du TissuVariant : [id = " + tissu.getId() +"]");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return tissu;
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
