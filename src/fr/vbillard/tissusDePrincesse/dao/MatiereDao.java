package fr.vbillard.tissusDePrincesse.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractDao;
import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractSimpleValueDao;
import fr.vbillard.tissusDePrincesse.dao.abstractDao.JPAHelper;
import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.Matiere;
import fr.vbillard.tissusDePrincesse.model.Photo;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class MatiereDao extends AbstractSimpleValueDao<Matiere>{

	public MatiereDao() {
		tableName = Matiere.class.getCanonicalName();
	}

}
