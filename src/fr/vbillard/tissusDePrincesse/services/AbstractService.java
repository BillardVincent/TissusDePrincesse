package fr.vbillard.tissusDePrincesse.services;

import java.util.List;

import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractDao;
import fr.vbillard.tissusDePrincesse.model.AbstractEntity;

public abstract class AbstractService<T extends AbstractEntity> {

	public T saveOrUpdate(T entity) {
		if (entity.getId() != 0) {
			return (T) getDao().update(entity);
		}
		else {
			return (T) getDao().create(entity);
		}
	}

	
	public List<T> getAll(){
		return getDao().findAll();
	}
	
	public void delete(T entity) {
		getDao().delete(entity);
	}
	
	public T getById(int id) {
		return (T) getDao().findById(id);
	}
	
	protected abstract AbstractDao getDao();
}
