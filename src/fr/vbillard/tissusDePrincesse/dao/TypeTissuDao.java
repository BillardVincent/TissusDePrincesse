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
import fr.vbillard.tissusDePrincesse.model.TissuVariant;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import fr.vbillard.tissusDePrincesse.utils.Constants;


public class TypeTissuDao extends AbstractDao<TypeTissu>{
	
	public TypeTissuDao () {
		tableName = TypeTissu.class.getCanonicalName();
	}
	
}
