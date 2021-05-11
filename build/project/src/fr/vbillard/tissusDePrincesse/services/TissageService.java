package fr.vbillard.tissusDePrincesse.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.TissageDao;
import fr.vbillard.tissusDePrincesse.dao.TypeTissuDao;
import fr.vbillard.tissusDePrincesse.model.Tissage;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TissageService {
	TissageDao tissageDao;
	
	public TissageService(){
		this.tissageDao = new TissageDao();
	}
	public static ObservableList<Tissage> allTissages = FXCollections.observableArrayList();
	public static ObservableList<String> allTissagesValues = FXCollections.observableArrayList();
	public static int lastTypeTissuId;
	
	//public static List allTissus = new ArrayList() ;
	
	public void init() {
		/*
		allTypeTissus = FXCollections.observableArrayList(Arrays.asList(
				new TypeTissu(0, "Chaine et trame"),
				new TypeTissu(0, "Maille")
				));
		
		lastTypeTissuId = 2;
		*/
		List<Tissage> lst = tissageDao.findAll();
		allTissages = FXCollections.observableArrayList(lst);
		allTissagesValues = FXCollections.observableArrayList(lst.stream().map(t -> t.getTissage()).collect(Collectors.toList()));
		
				
	}

	public Tissage findTissage(String tissage) {
		
		return allTissages.stream().filter(t-> t.getTissage().equals(tissage)).findFirst().orElse(null);
	}

	public List<Tissage> getAll() {
		return tissageDao.findAll();
	}
	
	public ObservableList<String> getAllObs() {
		return allTissagesValues;
	}

	public void create(Tissage tissage) {
		Tissage m = tissageDao.create(tissage);
		allTissages.add(m);
		allTissagesValues.add(m.getTissage());
	}

	public boolean validate(String text) {
		return ! allTissagesValues.contains(text) ;		
	}
	
	
	
	

}
