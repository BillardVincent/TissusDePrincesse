package fr.vbillard.tissusDePrincesse.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.TissageDao;
import fr.vbillard.tissusDePrincesse.dao.TypeTissuDao;
import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractDao;
import fr.vbillard.tissusDePrincesse.model.Tissage;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TissageService extends AbstractService<Tissage>{
	TissageDao dao;
	
	public TissageService(){
		this.dao = new TissageDao();
	}

	public Tissage findTissage(String value) {
		return dao.findByValue(value);
	}
	
	public ObservableList<String> getAllObs() {
		return FXCollections.observableArrayList(getAll().stream().map(t -> t.getValue()).collect(Collectors.toList()));
	}

	public boolean validate(String value) {
		return !dao.existsByValue(value);		
	}

	@Override
	protected AbstractDao getDao() {
		return dao;
	}
	
	
	
	

}
