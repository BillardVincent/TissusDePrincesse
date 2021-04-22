package fr.vbillard.tissusDePrincesse.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import fr.vbillard.tissusDePrincesse.dao.TypeTissuDao;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TypeTissuService {
	TypeTissuDao typeTissuDao;
	
	public TypeTissuService(){
		this.typeTissuDao = new TypeTissuDao();
	}
	public static ObservableList<TypeTissu> allTypeTissus = FXCollections.observableArrayList();
	public static ObservableList<String> allTypeTissusValues = FXCollections.observableArrayList();

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
		allTypeTissus = FXCollections.observableArrayList(typeTissuDao.findAll());
		
		if (allTypeTissus.size() == 0 ) {
			typeTissuDao.create(new TypeTissu(0, "Chaine et trame"));
			typeTissuDao.create(new TypeTissu(0, "Maille"));
			allTypeTissus = FXCollections.observableArrayList(typeTissuDao.findAll());

		}
		
				
	}

public TypeTissu findTypeTissu(String typeTissu) {
		
		return allTypeTissus.stream().filter(t-> t.getType().equals(typeTissu)).findFirst().orElse(null);
	}

	public List<TypeTissu> getAll() {
		return typeTissuDao.findAll();
	}
	
	public ObservableList<String> getAllObs() {
		return allTypeTissusValues;
	}

	public void create(TypeTissu typeTissu) {
		TypeTissu tt = typeTissuDao.create(typeTissu);
		allTypeTissus.add(tt);
		allTypeTissusValues.add(tt.getType());
	}

	public boolean validate(String text) {
		return ! allTypeTissusValues.contains(text) ;		
	}
	
	
	

}
