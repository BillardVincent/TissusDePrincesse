package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.Projet;
import fr.vbillard.tissusDePrincesse.model.Tissage;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class TissageDao extends AbstractDao<Tissage>{
	
	public TissageDao () {
		tableName = Tissage.class.getCanonicalName();
	}
}
