package fr.vbillard.tissusDePrincesse.mappers;

import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.model.Patron;

public class PatronMapper {

	
	
	public static  Patron map(PatronDto dto) {
		Patron patron = new Patron();
		patron.setId(dto.getId());
		patron.setMarque(dto.getMarque());
		patron.setModele(dto.getModele());
		patron.setTypeVetement(dto.getTypeVetement());
		patron.setReference(dto.getReference());		
		return patron;
		
	}
	
	public static  PatronDto map(Patron patron) {
		PatronDto dto = new PatronDto();
		dto.setId(patron.getId());
		dto.setMarque(patron.getMarque());
		dto.setModele(patron.getModele());
		dto.setTypeVetement(patron.getTypeVetement());
		dto.setReference(patron.getReference());
		return dto;
		
	}
}
