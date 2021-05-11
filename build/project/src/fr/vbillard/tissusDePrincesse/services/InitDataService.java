package fr.vbillard.tissusDePrincesse.services;

import fr.vbillard.tissusDePrincesse.dao.TissuDao;
import fr.vbillard.tissusDePrincesse.dao.TypeTissuDao;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;

public class InitDataService {

	private TissuDao tissuDao;
	private TypeTissuDao typeTissuDao;
	
	
	public InitDataService(){
		tissuDao = new TissuDao();
		typeTissuDao = new TypeTissuDao();
	}
	public void init() {
		/*
		if (typeTissuDao.count() == 0) {
			
			typeTissuDao.create(new TypeTissu(1, "Chaine et trame"));
			typeTissuDao.create(new TypeTissu(2, "maille"));
		}
		*/
	}
}
