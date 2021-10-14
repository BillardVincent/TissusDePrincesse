package fr.vbillard.tissusDePrincesse.dao.abstractDao;

import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.AbstractSimpleValueEntity;

public class AbstractSimpleValueDao<T extends AbstractSimpleValueEntity> extends AbstractDao<T>{

	public T findByValue(String value) {
	T entity = null;
	try {
		emf = Persistence.createEntityManagerFactory(persistenceUnit);
		em = emf.createEntityManager();
		String query = "SELECT entity FROM "+ tableName + " entity WHERE  entity.value=:value";
		entity = (T) em.createQuery(query).setParameter("value", value).getSingleResult();
	} catch (Exception e) {
		e.printStackTrace();
		log.error("Erreur lors de l'execution de la methode, Exception : " + e);
		throw new PersistanceException(
				"Une erreur s'est produite lors de la récupération de : " + value +"]");

	} finally {
		JPAHelper.closeEntityManagerResources(emf, em);
	}
	return entity;		
}

public boolean existsByValue(String value) {
	boolean exists;
	try {
		emf = Persistence.createEntityManagerFactory(persistenceUnit);
		em = emf.createEntityManager();
		String query = "select case when (count(*) > 0) then true else false end from "+ tableName + " entity where entity.value=:value";
		TypedQuery<Boolean> booleanQuery = em.createQuery(query, Boolean.class).setParameter("value", value);
		exists = booleanQuery.getSingleResult();
	} catch (Exception e) {
		e.printStackTrace();
		throw new PersistanceException(
				"Une erreur s'est produite lors de la recherche de valeur");

	} finally {
		JPAHelper.closeEntityManagerResources(emf, em);
	}
	return exists;
}
}
