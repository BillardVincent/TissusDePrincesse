package fr.vbillard.tissusDePrincesse.services;

import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.TypeTissuDao;
import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractDao;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TypeTissuService extends AbstractService<TypeTissu> {
	TypeTissuDao typeTissuDao;

	public TypeTissuService() {
		this.typeTissuDao = new TypeTissuDao();
	}

	public TypeTissu findTypeTissu(String typeTissu) {
		return typeTissuDao.findByValue(typeTissu);
	}

	public ObservableList<TypeTissu> getAllAsObservable() {
		return FXCollections.observableArrayList(getAll());
	}

	public boolean validate(String text) {
		return !typeTissuDao.existsByValue(text);
	}

	public void delete(String text) {
		delete(findTypeTissu(text));

	}

	public ObservableList<String> getAllTypeTissuValues() {
		return FXCollections
				.observableArrayList(getAll().stream().map(tt -> tt.getValue()).collect(Collectors.toList()));
	}

	@Override
	protected AbstractDao getDao() {
		return typeTissuDao;
	}

}
