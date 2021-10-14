package fr.vbillard.tissusDePrincesse.services;

import java.util.Iterator;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.TissuDao;
import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractDao;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.mappers.TissuMapper;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TissuService extends AbstractService<Tissu>{
	
	private TissuMapper mapper;
	TissuDao dao;

	public TissuService() {
		mapper = new TissuMapper();
		dao = new TissuDao();
		
	}

	public ObservableList<TissuDto> getObservableList(){
		return mapper.getAsObservable(dao.findAll());
	}

	public ObservableList<TissuDto> filter(String text) {
		ObservableList<TissuDto> result =FXCollections.observableArrayList();
		for (TissuDto t : mapper.getAsObservable(getAll())) {
			if (t.getDescription().contains(text) ||
					t.getLieuAchat().contains(text) ||
					t.getMatiere().contains(text) ||
					t.getTissage().contains(text) ||
					t.getType().contains(text)					
					) result.add(t);
		}
		return result;
	}
	public ObservableList<TissuDto> filter(TissuDto tissuDto) {
		ObservableList<TissuDto> result =FXCollections.observableArrayList();
		for (TissuDto t : mapper.getAsObservable(getAll())) {
			if (t.getDescription().contains(tissuDto.getDescription()) ||
					t.getLieuAchat().contains(tissuDto.getLieuAchat()) ||
					t.getMatiere().contains(tissuDto.getMatiere()) ||
					t.getTissage().contains(tissuDto.getMatiere()) ||
					t.getType().contains(tissuDto.getMatiere())					
					) result.add(t);
		}
		return result;
	}

	public boolean existByReference(String string) {
		return dao.existByReference(string);
		
	}

	public void archive(TissuDto dto) {
		Tissu t = mapper.map(dto);
		t.setArchived(true);
		dao.update(t);
		
	}
	
	public void delete(TissuDto dto) {
		delete(mapper.map(dto));
	}
	
	public void saveOrUpdate(TissuDto dto) {
		saveOrUpdate(mapper.map(dto));
	}
	
	@Override
	protected AbstractDao getDao() {
		return dao;
	}
	

		
}
