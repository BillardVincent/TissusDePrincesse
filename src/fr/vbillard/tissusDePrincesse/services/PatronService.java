package fr.vbillard.tissusDePrincesse.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.PatronDao;
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
		patronData = FXCollections.observableArrayList(patronDao.findAll().stream().map(PatronMapper::map).collect(Collectors.toList()));
	}

	public static ObservableList<PatronDto> patronData = FXCollections.observableArrayList();
	public static int lastPatronId;
	
	public void init() {
		
		//TypeTissu o = TypeTissuService.allTypeTissus.get(0);
		/*
		patronData = FXCollections.observableArrayList(Arrays.asList(
				PatronMapper.map(new Patron(1, "DRR1", "Dear & Doe", "Rose", "Robe",  TypeTissuService.allTypeTissus.get(0), 
						Arrays.asList(new TissuRequis(1, 120, 200, "bla", "bla"), new TissuRequis(2, 300, 200, "bla", "bla")), 
						new ArrayList())),
				PatronMapper.map(new Patron(2, "DMV2","Dear & Doe", "Muguet", "Veste",  TypeTissuService.allTypeTissus.get(0), 
						Arrays.asList(new TissuRequis(1, 120, 200, "bla", "bla")), 
						Arrays.asList(new FounitureRequise(), new FounitureRequise()))),
				PatronMapper.map(new Patron(3, "DPM3","Dear & Doe", "Pivoine", "Manteau",  TypeTissuService.allTypeTissus.get(1), 
						Arrays.asList(new TissuRequis(3, 20, 40, "bla", "bla"), new TissuRequis(4, 20, 20, "bla", "bla")), 
						Arrays.asList(new FounitureRequise(), new FounitureRequise()))),
				PatronMapper.map(new Patron(4, "DPR4","Dear & Doe", "Pissenlit", "Robe",  TypeTissuService.allTypeTissus.get(0), 
						Arrays.asList(new TissuRequis(5, 300, 150, "bla", "bla")), 
						Arrays.asList(new FounitureRequise(), new FounitureRequise()))),
				PatronMapper.map(new Patron(5, "DPC5","Dear & Doe", "Paquerette", "Chaussettes",  TypeTissuService.allTypeTissus.get(1), 
						Arrays.asList(new TissuRequis(6, 120, 200, "bla", "bla"), new TissuRequis(2, 300, 200, "bla", "bla")), 
						Arrays.asList(new FounitureRequise(), new FounitureRequise()))),
				PatronMapper.map(new Patron(6, "DIR6","Dear & Doe", "Iris", "Robe",  TypeTissuService.allTypeTissus.get(0), 
						Arrays.asList(new TissuRequis(1, 50, 200, "bla", "bla"), new TissuRequis(2, 50, 200, "bla", "bla")), 
						Arrays.asList(new FounitureRequise(), new FounitureRequise())))
				));
		
		lastPatronId = 6;
		*/
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
			patronData.add(dto);
		}
		else {
			dto = PatronMapper.map(patronDao.update(p ));
			patronData = FXCollections.observableArrayList(patronDao.findAll().stream().map(PatronMapper::map).collect(Collectors.toList()));
		}
		return dto;
	}

	public boolean existByReference(String string) {
		return patronDao.existByReference(string);
	}
}
