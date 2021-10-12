package fr.vbillard.tissusDePrincesse.services;

import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.AbstractDao;
import fr.vbillard.tissusDePrincesse.dao.ProjetDao;
import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.dtosFx.ProjetDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.mappers.PatronMapper;
import fr.vbillard.tissusDePrincesse.mappers.ProjetMapper;
import fr.vbillard.tissusDePrincesse.model.Projet;
import fr.vbillard.tissusDePrincesse.model.enums.ProjectStatus;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ProjetService extends AbstractService<Projet>{
ProjetMapper mapper;
ProjetDao dao;
	
	public ProjetService() {
		dao = new ProjetDao();
		mapper = new ProjetMapper();
	}
	
	@Override
	protected AbstractDao getDao() {
		return dao;
	}

	public ProjetDto saveOrUpdate(ProjetDto dto) {
		return mapper.map(saveOrUpdate(mapper.map(dto)));
	}
	
	public ObservableList<ProjetDto> getObservableList(){
		return mapper.getAsObservable(dao.findAll());
	}

	public ProjetDto newProjetDto(PatronDto selectedPatron) {
		Projet p = new Projet();
		p.setPatron(PatronMapper.map(selectedPatron));
		
		return mapper.map(p);
	}

}
