package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.dtosFx.TissuRequisDto;
import fr.vbillard.tissusDePrincesse.exception.GestionTissagesException;
import fr.vbillard.tissusDePrincesse.exception.GestionTissusException;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.Tissage;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class TissusRequisDao {
	
	private final String persistenceUnit = Constants.PERSISTENCE_UNIT;
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction transaction = null;
	
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

			
			throw new GestionTissagesException("Une erreur s'est produite lors de la recupération des Tissages.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return tissusRequis;
	}

	public TissuRequis create(TissuRequis tissu) {
		if (tissu != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				Patron p = em.createQuery("SELECT p FROM Patron p WHERE p.id =:id", Patron.class).setParameter("id", tissu.getPatron().getId()).getSingleResult();
				tissu.setPatron(p);
				em.persist(tissu);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
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

	public TissuRequis update(TissuRequis tissu) {
		if (tissu != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				Patron p = em.createQuery("SELECT p FROM Patron p WHERE p.id =:id", Patron.class).setParameter("id", tissu.getPatron().getId()).getSingleResult();
				tissu.setPatron(p);
				tissu = em.merge(tissu);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
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
	
	public TissuRequis findById(Integer id) {
		TissuRequis tissu = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			tissu = em.find(TissuRequis.class, id);
		} catch (Exception e) {
			throw new GestionTissagesException(
					"Une erreur s'est produite lors de la création de la Tissage : [id = " + tissu.getId() +"]");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return tissu;
	}
	
	public boolean delete(TissuRequis tissu) {
		boolean isOk = false;
		if (tissu != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				for (TissuVariant tv : em.createQuery("SELECT tr FROM TissuRequis tr WHERE tr.tissuRequis.id =:id", TissuRequis.class).setParameter("id", tissu.getId()).getResultList()) {
					em.remove(tv);
				}
				tissu = em.find(TissuRequis.class, tissu.getId());
				em.remove(tissu);
				transaction.commit();
				isOk = em.find(Tissu.class, tissu.getId()) == null;
			} catch (Exception e) {
				e.printStackTrace();
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

}
