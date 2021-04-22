package fr.vbillard.tissusDePrincesse.mappers;

import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.model.Patron;

public class PatronMapper {

	public static  Patron map(PatronDto dto) {
		Patron patron = new Patron();
		patron.setId(0);
		
		return patron;
		
	}
	
	public static  PatronDto map(Patron patron) {
		PatronDto dto = new PatronDto();
		dto.setId(patron.getId());
		dto.setMarque(patron.getMarque());
		dto.setModele(patron.getModele());
		dto.setTypeTissu(patron.getTypeTissu().getType());
		dto.setTypeVetement(patron.getTypeVetement());
		dto.setFounitureRequise(patron.getFournituresRequises());
		dto.setTissusRequis(patron.getTissusRequis());
		dto.setLongueurTissuTotale(patron.getTissusRequis().stream().mapToInt(t -> t.getLongueur()).sum());
		dto.setReference(patron.getReference());
		return dto;
		
	}
}
