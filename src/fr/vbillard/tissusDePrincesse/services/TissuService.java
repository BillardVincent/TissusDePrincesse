package fr.vbillard.tissusDePrincesse.services;

import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.TissuDao;
import fr.vbillard.tissusDePrincesse.dtosFx.DataHolder;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.mappers.TissuMapper;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.UnitePoids;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class TissuService {
	
	public static ObservableList<TissuDto> tissuData = FXCollections.observableArrayList();
	public static int lastTissuId;
	private TissuDao tissuDao;
	
	
	public TissuService() {
		tissuDao = new TissuDao();
	}
	//public static List allTissus = new ArrayList() ;
	
	public void init() {
		TypeTissuService typeTissuService = new TypeTissuService();
		typeTissuService.init();
		PatronService patronService = new PatronService();
		patronService.init();
		
		/*
		List<Tissu> tissus = Arrays.asList(
				new Tissu(0, "cc1", 200, 150, "bleu", "coton", null, 20, UnitePoids.GRAMME_M,false, "Myrtille", "tissage 1", false),
				new Tissu(0, "cc2",120, 200, "bleu à pois blancs", "coton", null, 10, UnitePoids.GRAMME_M_CARRE,false, "Myrtille", "tissage 2", false),
				new Tissu(0, "cl3",300, 200, "rouge", "laine", null, 300, UnitePoids.GRAMME_M,false, "Mamzelle Fourmi", "tissage 1", false),
				new Tissu(0, "cp4",80, 150, "rouge à rayure", "polyester", null, 20, UnitePoids.GRAMME_M_CARRE,false, "Benitex", "tissage 3", false),
				new Tissu(0, "cc5",200, 150, "bleu foncé", "coton", null, 10, UnitePoids.GRAMME_M,false, "Toto", "tissage 1", true),
				new Tissu(0, "cp6",350, 150, "bleu ciel", "popeline", null, 40, UnitePoids.GRAMME_M,false, "Myrtille", "tissage 1", true)
				);
		for (Tissu t : tissus) {
			tissuDao.create(t);
		}
		
		tissuData = FXCollections.observableArrayList(Arrays.asList(
				new TissuDto(new Tissu(1, "cc1", 200, 150, "bleu", "coton", TypeTissuService.allTypeTissus.get(0), 20, UnitePoids.GRAMME_M,false, "Myrtille", "tissage 1", false)),
				new TissuDto(new Tissu(2, "cc2",120, 200, "bleu à pois blancs", "coton", TypeTissuService.allTypeTissus.get(0), 10, UnitePoids.GRAMME_M_CARRE,false, "Myrtille", "tissage 2", false)),
				new TissuDto(new Tissu(3, "cl3",300, 200, "rouge", "laine", TypeTissuService.allTypeTissus.get(1), 300, UnitePoids.GRAMME_M,false, "Mamzelle Fourmi", "tissage 1", false)),
				new TissuDto(new Tissu(4, "cp4",80, 150, "rouge à rayure", "polyester", TypeTissuService.allTypeTissus.get(1), 20, UnitePoids.GRAMME_M_CARRE,false, "Benitex", "tissage 3", false)),
				new TissuDto(new Tissu(5, "cc5",200, 150, "bleu foncé", "coton", TypeTissuService.allTypeTissus.get(0), 10, UnitePoids.GRAMME_M,false, "Toto", "tissage 1", true)),
				new TissuDto(new Tissu(6, "cp6",350, 150, "bleu ciel", "popeline", TypeTissuService.allTypeTissus.get(0), 40, UnitePoids.GRAMME_M,false, "Myrtille", "tissage 1", true))
				));
				*/
		
		tissuData = FXCollections.observableArrayList(tissuDao.findAll().stream().map(TissuMapper::map).collect(Collectors.toList()));
		
		tissuDao.findAll().forEach(t -> System.out.println(t));;
		
		lastTissuId = 6;
		
		
				
	}
	
	public void init(DataHolder dh) {
		tissuData = FXCollections.observableArrayList(dh.getTissus().stream().map(TissuDto::new).collect(Collectors.toList()));
		lastTissuId = dh.getLastTissuId();
	}
	
	public void saveOrUpdate(TissuDto tissu) {
		
		if (tissu.getId() != 0) {
			tissuDao.update(TissuMapper.map(tissu));
			tissuData = FXCollections.observableArrayList(tissuDao.findAll().stream().map(TissuMapper::map).collect(Collectors.toList()));
		}
		else {
			tissuDao.create(TissuMapper.map(tissu));
			tissuData.add(tissu);

		}

		/*
		if (tissu.getId() == 0) {
			tissu.setId(++lastTissuId);
			tissuData.add(tissu);
		} else {
			tissuData.forEach(t -> {
		        if (t.getId() == tissu.getId()) t = tissu;
		});
		}
		*/

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
	
	public void reload() {
			Serializer.Deserialize();
		
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
	

		
}
