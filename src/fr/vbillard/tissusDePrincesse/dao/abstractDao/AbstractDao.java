package fr.vbillard.tissusDePrincesse.dao.abstractDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.AbstractEntity;
import fr.vbillard.tissusDePrincesse.model.Matiere;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public abstract class AbstractDao<T extends AbstractEntity> {

		protected static final Log log = LogFactory.getLog(AbstractDao.class);
		protected final String persistenceUnit = Constants.PERSISTENCE_UNIT;
		protected EntityManagerFactory emf = null;
		protected EntityManager em = null;
		protected EntityTransaction transaction = null;
		
		protected String tableName;
		
		
		public List<T> findAll() {
			List<T> entities = null;
			try {
				Map<String, Object> configOverrides = new HashMap<String, Object>();
			
				emf = Persistence.createEntityManagerFactory(persistenceUnit,configOverrides);
				em = emf.createEntityManager();
				entities = em.createQuery("SELECT t FROM "+ tableName +" t").getResultList();
			} catch (Exception e) {
				e.printStackTrace();

				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				throw new PersistanceException("Une erreur s'est produite lors de la recupération des Matieres.");
			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
			return entities;
		}


		public T create(T entity) {
			if (entity != null) {
				try {
					emf = Persistence.createEntityManagerFactory(persistenceUnit);
					em = emf.createEntityManager();
					transaction = em.getTransaction();
					transaction.begin();
					em.persist(entity);
					transaction.commit();
				} catch (Exception e) {
					e.printStackTrace();
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					throw new PersistanceException(
							"Une erreur s'est produite lors de la création de " +tableName + "  : [id = " + entity.getId() +"]");
				} finally {
					JPAHelper.closeEntityManagerResources(emf, em);
				}
			}
			return entity;
		}


		public T update(T entity) {
			if (entity != null) {
				try {
					emf = Persistence.createEntityManagerFactory(persistenceUnit);
					em = emf.createEntityManager();
					transaction = em.getTransaction();
					transaction.begin();
					entity = em.merge(entity);
					transaction.commit();
				} catch (Exception e) {
					log.error("Erreur lors de l'execution de la methode, Exception : " + e);
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					throw new PersistanceException(
							"Une erreur s'est produite lors de la création de la Matiere : [id = " + entity.getId() +"]");
				} finally {
					JPAHelper.closeEntityManagerResources(emf, em);
				}
			}
			return entity;
		}


		public boolean delete(T entity) {
			boolean isOk = false;
			if (entity != null) {
				try {
					emf = Persistence.createEntityManagerFactory(persistenceUnit);
					em = emf.createEntityManager();
					transaction = em.getTransaction();
					transaction.begin();
					entity = (T) em.find(entity.getClass(), entity.getId());
					em.remove(em.merge(entity));
					transaction.commit();
					isOk = em.find(entity.getClass(), entity.getId()) == null;
				} catch (Exception e) {
					log.error("Erreur lors de l'execution de la methode, Exception : " + e);
					if (transaction != null && transaction.isActive()) {
						transaction.rollback();
					}
					throw new PersistanceException(
							"Une erreur s'est produite lors de la création de la Matiere : [id = " + entity.getId() +"]");

				} finally {
					JPAHelper.closeEntityManagerResources(emf, em);
				}
			}
			return isOk;
		}


		@SuppressWarnings("unchecked")
		public T findById(Integer id) {
			T entity = null;
			try {
				emf = Persistence.createEntityManagerFactory(persistenceUnit);
				em = emf.createEntityManager();
				entity = (T) em.createQuery("SELECT t FROM "+ tableName +" t WHERE t.id=:id").setParameter("id", id).getSingleResult();
			} catch (Exception e) {
				log.error("Erreur lors de l'execution de la methode, Exception : " + e);
				throw new PersistanceException(
						"Une erreur s'est produite lors de la récupération de "+ tableName +"  : [id = " + id +"]");

			} finally {
				JPAHelper.closeEntityManagerResources(emf, em);
			}
			return entity;
		}

		
	}

	

