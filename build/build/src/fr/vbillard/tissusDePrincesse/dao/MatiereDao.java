package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.exception.GestionMatieresException;
import fr.vbillard.tissusDePrincesse.model.Matiere;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class MatiereDao {
	private static final Log log = LogFactory.getLog(MatiereDao.class);
	private final String persistenceUnit = Constants.PERSISTENCE_UNIT;
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction transaction = null;

	
	public List<Matiere> findAll() {
		List<Matiere> matieres = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			// matieres = em.createQuery("select matiere from Matiere matiere",
			// Matiere.class).getResultList();
			matieres = em.createQuery("SELECT matiere FROM Matiere matiere") .getResultList();
		} catch (Exception e) {
			e.printStackTrace();

			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionMatieresException("Une erreur s'est produite lors de la recupération des Matieres.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		log.debug("matieres : " + matieres);
		return matieres;
	}


	public Matiere create(Matiere matiere) {
		if (matiere != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				em.persist(matiere);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionMatieresException(
						"Une erreur s'est produite lors de la création de la Matiere : [id = " + matiere.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return matiere;
	}


	public Matiere update(Matiere matiere) {
		if (matiere != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				matiere = em.merge(matiere);
				transaction.commit();
			} catch (Exception e) {
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionMatieresException(
						"Une erreur s'est produite lors de la création de la Matiere : [id = " + matiere.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return matiere;
	}


	public boolean delete(Matiere matiere) {
		boolean isOk = false;
		if (matiere != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				matiere = em.find(Matiere.class, matiere.getId());
				em.remove(em.merge(matiere));
				transaction.commit();
				isOk = em.find(Matiere.class, matiere.getId()) == null;
			} catch (Exception e) {
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new GestionMatieresException(
						"Une erreur s'est produite lors de la création de la Matiere : [id = " + matiere.getId() +"]");

			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return isOk;
	}


	public Matiere findById(Integer id) {
		Matiere matiere = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			matiere = em.find(Matiere.class, id);
		} catch (Exception e) {
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionMatieresException(
					"Une erreur s'est produite lors de la création de la Matiere : [id = " + matiere.getId() +"]");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return matiere;
	}
	
	public int count() {
		int count = 0;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			count = em.createQuery("select Count (*) FROM Matiere ") .getFirstResult();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new GestionMatieresException(
					"Ca a pas compté....");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return count;
	}

}
