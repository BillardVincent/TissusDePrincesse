/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.vbillard.tissusDePrincesse.dao.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import fr.vbillard.tissusDePrincesse.dao.TissuDao;

/**
 *
 * @author ElHadji
 */
public abstract class AbstractDaoFactory {

	public static String className = AbstractDaoFactory.class.getName();
	private static final Log log = LogFactory.getLog(AbstractDaoFactory.class);

	public enum FactoryDaoType {

		JDBC_DAO_FACTORY, JPA_DAO_FACTORY;
	}

	public abstract TissuDao getTissuDao();

	/**
	 * Méthode pour récupérer une factory de DAO
	 *
	 * @param daoType
	 * @return AbstractDaoFactory
	 */
	public static AbstractDaoFactory getFactory(FactoryDaoType daoType) {
		AbstractDaoFactory factory = null;
		log.debug("daoType: " + daoType);
		switch (daoType) {
		case JPA_DAO_FACTORY:
		default:
			factory = DaoFactory.getInstance();
			break;
		}
		return factory;
	}
}
