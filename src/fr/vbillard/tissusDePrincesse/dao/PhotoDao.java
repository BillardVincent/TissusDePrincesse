package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.images.Photo;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class PhotoDao {
	private static final Log log = LogFactory.getLog(PhotoDao.class);
	private final String persistenceUnit = Constants.PERSISTENCE_UNIT;
	private EntityManagerFactory emf = null;
	private EntityManager em = null;
	private EntityTransaction transaction = null;

	
	public List<Photo> findAll() {
		List<Photo> photos = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			// photos = em.createQuery("select photo from photo photo",
			// photo.class).getResultList();
			photos = em.createQuery("SELECT photo FROM photo photo") .getResultList();
		} catch (Exception e) {
			e.printStackTrace();

			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new PersistanceException("Une erreur s'est produite lors de la recupération des photos.");
		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		log.debug("photos : " + photos);
		return photos;
	}


	public Photo create(Photo photo) {
		if (photo != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				em.persist(photo);
				transaction.commit();
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new PersistanceException(
						"Une erreur s'est produite lors de la création de la photo : [id = " + photo.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return photo;
	}


	public Photo update(Photo photo) {
		if (photo != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				photo = em.merge(photo);
				transaction.commit();
			} catch (Exception e) {
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new PersistanceException(
						"Une erreur s'est produite lors de la création de la photo : [id = " + photo.getId() +"]");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return photo;
	}


	public boolean delete(Photo photo) {
		boolean isOk = false;
		if (photo != null) {
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				transaction = em.getTransaction();
				transaction.begin();
				photo = em.find(Photo.class, photo.getId());
				em.remove(em.merge(photo));
				transaction.commit();
				isOk = em.find(Photo.class, photo.getId()) == null;
			} catch (Exception e) {
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				if (transaction != null && transaction.isActive()) {
					transaction.rollback();
				}
				throw new PersistanceException(
						"Une erreur s'est produite lors de la création de la photo : [id = " + photo.getId() +"]");

			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
		}
		return isOk;
	}


	public Photo findById(Integer id) {
		Photo photo = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			photo = em.find(Photo.class, id);
		} catch (Exception e) {
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new PersistanceException(
					"Une erreur s'est produite lors de la création de la photo : [id = " + photo.getId() +"]");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return photo;
	}
	
	public int count() {
		int count = 0;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			count = em.createQuery("select Count (*) FROM photo ") .getFirstResult();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new PersistanceException(
					"Ca a pas compté....");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return count;
	}


	public List<Photo> getByTissu(Tissu tissu) {
		List<Photo> photos = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			em = emf.createEntityManager();
			String query = "SELECT p FROM Photo p WHERE  p.tissu=:tissu";
			photos = em.createQuery(query, Photo.class).setParameter("tissu", tissu).getResultList();
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Erreur lors de l'execution de la methode, Exception : " + e);
			throw new PersistanceException(
					"Une erreur s'est produite lors de la récupération des photos du tissu : [id = " + tissu.getId() +"]");

		} finally {
			JPAHelper.closeEntityManagerResources(emf, em);
		}
		return photos;		
	}

}
