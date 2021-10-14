package fr.vbillard.tissusDePrincesse.services;

import java.util.List;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.TissusRequisDao;
import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuRequisDto;
import fr.vbillard.tissusDePrincesse.mappers.PatronMapper;
import fr.vbillard.tissusDePrincesse.mappers.TissuRequisMapper;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TissuRequisService {

	private TissusRequisDao tissuRequisDao;
	private PatronMapper patronMapper;
	private TissuRequisMapper tissuRequisMapper;
	
	public TissuRequisService(){
		this.tissuRequisDao = new TissusRequisDao();
		patronMapper = new PatronMapper();
		tissuRequisMapper= new TissuRequisMapper();
	}
	
	public List<TissuRequis> getAllTissuRequisByPatron(int id){
		return tissuRequisDao.getAllTissuRequisByPatron(id);
	}
	
	public TissuRequisDto createOrUpdate(TissuRequisDto tissu, PatronDto patron) {
		TissuRequis t = TissuRequisMapper.map(tissu, patronMapper.map(patron));
		if (t.getId() == 0)
		 return tissuRequisMapper.map(tissuRequisDao.create(t));
		else return tissuRequisMapper.map(tissuRequisDao.update(t));

	}

	public TissuRequis findTissuRequis(int tissuRequisId) {
		
		return tissuRequisDao.findById(tissuRequisId);
	}

	public void delete(TissuRequisDto tissu) {
		tissuRequisDao.delete(tissuRequisMapper.map(tissu));
		
	}
	public void delete(TissuRequis tissu) {
		tissuRequisDao.delete(tissu);
		
	}

	public List<TissuRequisDto> getAsObservableAllTissuRequisByPatron(int id) {
		return tissuRequisMapper.getAsObservable(getAllTissuRequisByPatron(id));
	}
	
	
}
