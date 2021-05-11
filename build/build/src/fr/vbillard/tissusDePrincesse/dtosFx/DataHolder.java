package fr.vbillard.tissusDePrincesse.dtosFx;

import java.util.List;

import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataHolder {
	private List<Tissu> tissus;
	private int lastTissuId;
	private List<TypeTissu> typeTissus;
	private int lastTypeTissuId;
	private int lastPatronId;
	private List<Patron> patrons;
	private String filePath; 

	
}
