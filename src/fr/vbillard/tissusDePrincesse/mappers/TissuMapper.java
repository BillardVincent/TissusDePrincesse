package fr.vbillard.tissusDePrincesse.mappers;

import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.model.AbstractEntity;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TissuUsed;
import fr.vbillard.tissusDePrincesse.model.enums.UnitePoids;
import fr.vbillard.tissusDePrincesse.services.MatiereService;
import fr.vbillard.tissusDePrincesse.services.TissageService;
import fr.vbillard.tissusDePrincesse.services.TissuUsedService;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;

public class TissuMapper implements IMapper<Tissu, TissuDto>{

	public Tissu map(TissuDto dto) {
		TypeTissuService tts = new TypeTissuService();
		MatiereService ms = new MatiereService();
		TissageService ts = new TissageService();

		return new Tissu(
		dto.getIdProperty() == null ? 0 : dto.getId(),
		dto.getReferenceProperty() == null ? "" : dto.getReference(),
		dto.getLongueur(),
		dto.getLaize(),
		dto.getDescription(),
		ms.findMatiere(dto.getMatiere()),
		tts.findTypeTissu(dto.getType()),
		dto.getPoids(),
		UnitePoids.getEnum(dto.getUnitePoids()),
		dto.isDecati(),
		dto.getLieuAchat(),
		ts.findTissage(dto.getTissage()),
		dto.isChute());
	}
	
	public TissuDto map(Tissu t) {
	TissuUsedService tUsedService = new TissuUsedService(); 
	 TissuDto dto =  new TissuDto(t);
	 int longueurRestante = dto.getLongueur();
	 	 
	 for (TissuUsed tu : tUsedService.getByTissu(t)) {
		 longueurRestante -= tu.getLongueur();
		}
	 
	 dto.setLongueurRestante(longueurRestante);
	 
	 return dto;
	}

}
