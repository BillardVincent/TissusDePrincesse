package fr.vbillard.tissusDePrincesse.services;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.MatiereDao;
import fr.vbillard.tissusDePrincesse.dao.TypeTissuDao;
import fr.vbillard.tissusDePrincesse.model.Matiere;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class MatiereService {
	MatiereDao matiereDao;
	
	public MatiereService(){
		this.matiereDao = new MatiereDao();
	}
	public static ObservableList<Matiere> allMatieres = FXCollections.observableArrayList();
	public static ObservableList<String> allMatieresValues = FXCollections.observableArrayList();
	
	public void init() {

		List<Matiere> lst = matiereDao.findAll();
		allMatieres = FXCollections.observableArrayList(lst);
		allMatieresValues = FXCollections.observableArrayList(lst.stream().map(m -> m.getMatiere()).collect(Collectors.toList()));
		
	}

	public Matiere findMatiere(String matiere) {
		
		return allMatieres.stream().filter(m-> m.getMatiere().equals(matiere)).findFirst().orElse(null);
	}

	public List<Matiere> getAll() {
		return matiereDao.findAll();
	}
	
	public ObservableList<String> getAllObs() {
		return allMatieresValues;
	}

	public void create(Matiere matiere) {
		Matiere m = matiereDao.create(matiere);
		init();
	}
	
	public void edit(Matiere matiere) {
		matiereDao.update(matiere);
		init();
}

	public boolean validate(String text) {
		return !allMatieresValues.contains(text) ;		
	}

	public void delete(String editMatiere) {
		Matiere m = allMatieres.stream().filter(t-> t.getMatiere().equals(editMatiere)).findFirst().orElse(null);
		matiereDao.delete(m);
		init();
		
	}
	
	
	
	

}
