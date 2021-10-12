package fr.vbillard.tissusDePrincesse.services;

import java.nio.channels.GatheringByteChannel;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.AbstractDao;
import fr.vbillard.tissusDePrincesse.dao.MatiereDao;
import fr.vbillard.tissusDePrincesse.dao.TypeTissuDao;
import fr.vbillard.tissusDePrincesse.model.Matiere;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextField;

public class MatiereService extends AbstractService<Matiere>{
	MatiereDao dao;
	
	public MatiereService(){
		this.dao = new MatiereDao();
	}
	
	@Override
	protected AbstractDao getDao() {
		// TODO Auto-generated method stub
		return dao;
	}
	
	public Matiere findMatiere(String value) {
		return dao.findByMatiere(value);
	}

	public ObservableList<String>getAllMatieresValues(){
		return FXCollections.observableArrayList(getAll().stream().map(m -> m.getMatiere()).collect(Collectors.toList()));
	}
	
	public boolean validate(String value) {
		return !dao.existsByMatiere(value);
	}
	
	public void delete(String value) {
		delete(findMatiere(value));
	}
	
}
