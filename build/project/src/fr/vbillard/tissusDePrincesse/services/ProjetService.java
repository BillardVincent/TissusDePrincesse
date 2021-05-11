package fr.vbillard.tissusDePrincesse.services;

import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.ProjetDao;
import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.dtosFx.ProjetDto;
import fr.vbillard.tissusDePrincesse.mappers.PatronMapper;
import fr.vbillard.tissusDePrincesse.mappers.ProjetMapper;
import fr.vbillard.tissusDePrincesse.model.Projet;
import fr.vbillard.tissusDePrincesse.model.enums.ProjectStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjetService {
ProjetDao projetDao;
	
	public ProjetService() {
		projetDao = new ProjetDao();
		projetData = FXCollections.observableArrayList(projetDao.findAll().stream().map(ProjetMapper::map).collect(Collectors.toList()));
	}

	public static ObservableList<ProjetDto> projetData = FXCollections.observableArrayList();
	public static int lastProjetId;
	
	public void init() {
		projetData = FXCollections.observableArrayList(projetDao.findAll().stream().map(ProjetMapper::map).collect(Collectors.toList()));
		}
	
	public ObservableList<ProjetDto> getProjetData() {
		if (projetData == null || projetData.size() == 0 ) init();
		return projetData;
	}

	public ProjetDto create(ProjetDto projet) {
		Projet p = ProjetMapper.map(projet);
		ProjetDto dto = new ProjetDto();
		if (p.getId() == 0) {
			dto = ProjetMapper.map(projetDao.create(p ));
			projetData.add(dto);
		}
		else {
			dto = ProjetMapper.map(projetDao.update(p ));
			projetData = FXCollections.observableArrayList(projetDao.findAll().stream().map(ProjetMapper::map).collect(Collectors.toList()));
		}
		return dto;
	}

	public ProjetDto newProjetDto(PatronDto selectedPatron) {
		Projet p = new Projet();
		p.setPatron(PatronMapper.map(selectedPatron));
		
		return ProjetMapper.map(p);
	}



}
