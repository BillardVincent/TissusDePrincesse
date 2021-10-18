package fr.vbillard.tissusDePrincesse.services;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.vbillard.tissusDePrincesse.dtosFx.DataHolder;
import fr.vbillard.tissusDePrincesse.mappers.TissuMapper;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import javafx.collections.FXCollections;

public class Serializer {
	
    private static String filePath;
    private final static String defaultFilePath = "autoSave.json";

    
private static TissuService tissuService = new TissuService();

	public static void Serialize()  {
		DataHolder dh = new DataHolder();
		List<Tissu> allTissus = tissuService
				.getTissuData()
				.stream()
				.map(TissuMapper::map)
				.collect(Collectors.toList());
		dh.setTissus(allTissus);
		dh.setTypeTissus(TypeTissuService.allTypeTissus);
		dh.setLastTypeTissuId(TypeTissuService.lastTypeTissuId);
		dh.setLastTissuId(tissuService.lastTissuId);
		dh.setFilePath(filePath);
		
	
		ObjectMapper objectMapper = new ObjectMapper();

		try {
			objectMapper.writeValue(new File(filePath), dh);
			objectMapper.writeValue(new File(defaultFilePath), dh);

		} catch (JsonGenerationException e) {
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void Deserialize()  {
		ObjectMapper objectMapper = new ObjectMapper();
		DataHolder dh;
		try {
			dh = objectMapper.readValue(new File(filePath), DataHolder.class);
		
		tissuService.tissuData = FXCollections.observableArrayList(dh.getTissus().stream().map(TissuMapper::map).collect(Collectors.toList()));
		tissuService.lastTissuId = dh.getLastTissuId();
		TypeTissuService.allTypeTissus = FXCollections.observableArrayList(dh.getTypeTissus());
		TypeTissuService.lastTypeTissuId = dh.getLastTypeTissuId();
		PatronService.lastPatronId = dh.getLastPatronId();
		filePath =dh.getFilePath();
		} catch (JsonParseException e) {	
			e.printStackTrace();
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		 
	}
	public static void setFilePath(String path) {
		if (path == null || path.trim().isEmpty()) filePath = defaultFilePath;
		else filePath = path;
	}

	public static File getFilePath() {

		return filePath == null ?  null : new File(filePath);
	}

	public static void serialize(File file) {
		setFilePath(file.getPath());
		Serialize();
	}

	public static void Deserialize(File file) {
		setFilePath(file.getPath());
		Deserialize();		
	}

}
