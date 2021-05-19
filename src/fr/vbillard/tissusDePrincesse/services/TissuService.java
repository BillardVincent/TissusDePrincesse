package fr.vbillard.tissusDePrincesse.services;

import java.util.Iterator;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.TissuDao;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.mappers.TissuMapper;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TissuService {
	
	public static ObservableList<TissuDto> tissuData = FXCollections.observableArrayList();
	private TissuDao tissuDao;
	
	
	public TissuService() {

		tissuDao = new TissuDao();
		
	}
	//public static List allTissus = new ArrayList() ;
	
	public void init() {
		
		tissuData = FXCollections.observableArrayList(tissuDao.findAll().stream().map(TissuMapper::map).collect(Collectors.toList()));
				
	}

	
	public void saveOrUpdate(TissuDto tissu) {
		
		if (tissu.getId() != 0) {
			tissuDao.update(TissuMapper.map(tissu));
			init();
		}
		else {
			tissuDao.create(TissuMapper.map(tissu));
			init();
		}
	}
	
	
	public void delete(TissuDto tissu) {
		tissuDao.delete(TissuMapper.map(tissu));
		for (Iterator<TissuDto> i = tissuData.iterator(); i.hasNext();) {
			TissuDto t = i.next();
		    if (t.getId() == tissu.getId()) {
		    	i.remove();
		    	break;
		    }  
		}
	}

	public ObservableList<TissuDto> getTissuData() {
		if (tissuData == null || tissuData.size() == 0 ) init();
		return tissuData;
	}
	

	public ObservableList<TissuDto> filter(String text) {
		ObservableList<TissuDto> result =FXCollections.observableArrayList();
		for (TissuDto t : tissuData) {
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
		for (TissuDto t : tissuData) {
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
		return tissuDao.existByReference(string);
		
	}

	public void archive(TissuDto dto) {
		Tissu t = TissuMapper.map(dto);
		t.setArchived(true);
		tissuDao.update(t);
		
	}
	

		
}
