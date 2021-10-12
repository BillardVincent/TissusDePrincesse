package fr.vbillard.tissusDePrincesse.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import fr.vbillard.tissusDePrincesse.exception.PersistanceException;
import fr.vbillard.tissusDePrincesse.model.Projet;
import fr.vbillard.tissusDePrincesse.model.images.Photo;
import fr.vbillard.tissusDePrincesse.utils.Constants;

public class ProjetDao  extends AbstractDao<Projet>{

	public ProjetDao() {
		tableName = Projet.class.getCanonicalName();
	}
	
}
