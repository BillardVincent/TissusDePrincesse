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
public class DaoFactory extends AbstractDaoFactory {

    private static final Log log = LogFactory.getLog(DaoFactory.class);
    private final static String className = DaoFactory.class.getSimpleName();
    private TissuDao tissuDao = null;

    private DaoFactory() {
        log.debug("--> ************ Initialisation de " + className + " ************");
        tissuDao = new TissuDao();
    }

    /**
     * Holder
     */
    private static class MySingletonHolder {

        /**
         * Instance unique non préinitialisée
         */
        private final static DaoFactory instance = new DaoFactory();
    }

    /**
     * Point d'accès pour l'instance unique du singleton
     *
     * @return
     */
    public static DaoFactory getInstance() {
        if (DaoFactory.MySingletonHolder.instance == null) {
            log.debug("--> Nouvelle Instance de " + className);
        } else {
            log.debug("--> Re-Utilisation de l'instance " + className + " dejà existante ");
        }
        return DaoFactory.MySingletonHolder.instance;
    }

    @Override
    public TissuDao getTissuDao() {
        return tissuDao;
    }
}
