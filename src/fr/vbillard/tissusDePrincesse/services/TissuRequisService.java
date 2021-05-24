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
	
	public TissuRequisService(){
		this.tissuRequisDao = new TissusRequisDao();
	}
	
	public List<TissuRequis> getAllTissuRequisByPatron(int id){
		return tissuRequisDao.getAllTissuRequisByPatron(id);
	}
	
	public static ObservableList<TissuRequisDto> listToFxCollection(List<TissuRequis> lst){
		return FXCollections.observableArrayList(lst.stream().map(tr -> TissuRequisMapper.map(tr)).collect(Collectors.toList()));
	}

	public TissuRequisDto createOrUpdate(TissuRequisDto tissu, PatronDto patron) {
		TissuRequis t = TissuRequisMapper.map(tissu, PatronMapper.map(patron));
		if (t.getId() == 0)
		 return TissuRequisMapper.map(tissuRequisDao.create(t));
		else return TissuRequisMapper.map(tissuRequisDao.update(t));

	}

	public TissuRequis findTissuRequis(int tissuRequisId) {
		
		return tissuRequisDao.findById(tissuRequisId);
	}

	public void delete(TissuRequisDto tissu) {
		tissuRequisDao.delete(TissuRequisMapper.map(tissu));
		
	}
	public void delete(TissuRequis tissu) {
		tissuRequisDao.delete(tissu);
		
	}
	
	
}
