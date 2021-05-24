package fr.vbillard.tissusDePrincesse.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.PatronDao;
import fr.vbillard.tissusDePrincesse.dao.TissusRequisDao;
import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.mappers.PatronMapper;
import fr.vbillard.tissusDePrincesse.mappers.TissuMapper;
import fr.vbillard.tissusDePrincesse.model.FounitureRequise;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PatronService {
	
	PatronDao patronDao;
	
	public PatronService() {
		patronDao = new PatronDao();
		init();
	}

	public static ObservableList<PatronDto> patronData = FXCollections.observableArrayList();
	public static int lastPatronId;
	
	public void init() {
		patronData = FXCollections.observableArrayList(patronDao.findAll().stream().map(PatronMapper::map).collect(Collectors.toList()));

	}
	
	public ObservableList<PatronDto> getPatronData() {
		if (patronData == null || patronData.size() == 0 ) init();
		return patronData;
	}

	public PatronDto create(PatronDto patron) {
		Patron p = PatronMapper.map(patron);
		PatronDto dto = new PatronDto();
		if (p.getId() == 0) {
			dto = PatronMapper.map(patronDao.create(p ));
		}
		else {
			dto = PatronMapper.map(patronDao.update(p ));
		}
		init();
		return dto;
	}

	public boolean existByReference(String string) {
		return patronDao.existByReference(string);
	}

	public void delete(PatronDto selected) {
		TissusRequisDao tissuRequisDao = new TissusRequisDao();
		TissuRequisService tissuRequisService = new TissuRequisService();
		for (TissuRequis tr : tissuRequisDao.getAllTissuRequisByPatron(selected.getId())) {
			tissuRequisService.delete(tr);
		}
		patronDao.delete(PatronMapper.map(selected));
		init();
	}
}
