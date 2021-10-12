package fr.vbillard.tissusDePrincesse.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.TypeTissuDao;
import fr.vbillard.tissusDePrincesse.model.Matiere;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TypeTissuService {
	TypeTissuDao typeTissuDao;
	
	public TypeTissuService(){
		this.typeTissuDao = new TypeTissuDao();
		init();
		}
	public static ObservableList<TypeTissu> allTypeTissus = FXCollections.observableArrayList();
	public static ObservableList<String> allTypeTissusValues = FXCollections.observableArrayList();	
	
	public void init() {

		List<TypeTissu> lst = typeTissuDao.findAll();
		allTypeTissus = FXCollections.observableArrayList(lst);
		allTypeTissusValues = FXCollections.observableArrayList(lst.stream().map(m -> m.getType()).collect(Collectors.toList()));
		
				
	}

public TypeTissu findTypeTissu(String typeTissu) {
		return allTypeTissus.stream().filter(t-> t.getType().equals(typeTissu)).findFirst().orElse(null);
	}

	public List<TypeTissu> getAll() {
		return typeTissuDao.findAll();
	}

	public void create(TypeTissu typeTissu) {
		TypeTissu tt = typeTissuDao.create(typeTissu);
		init();
	}

	public boolean validate(String text) {
		return ! allTypeTissusValues.contains(text) ;		
	}
	public void update(TypeTissu typeTissu) {
		typeTissuDao.update(typeTissu);
	}
	
	

}
