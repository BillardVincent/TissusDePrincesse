package fr.vbillard.tissusDePrincesse.mappers;

import fr.vbillard.tissusDePrincesse.dtosFx.TissuVariantDto;
import fr.vbillard.tissusDePrincesse.model.TissuVariant;
import fr.vbillard.tissusDePrincesse.services.MatiereService;
import fr.vbillard.tissusDePrincesse.services.TissageService;
import fr.vbillard.tissusDePrincesse.services.TissuRequisService;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;

public class VariantMapper {

	public static TissuVariantDto map(TissuVariant v) {
		TissuVariantDto dto = new TissuVariantDto();
		dto.setId(v.getId());
		dto.setMatiere(v.getMatiere() == null ? "" : v.getMatiere().getMatiere());
		dto.setTissage(v.getTissage() == null ? "" :  v.getTissage().getTissage());
		dto.setTissuRequisId(v.getTissuRequis());
		dto.setType(v.getTypeTissu() == null ? "" :  v.getTypeTissu().getType());
		return dto;
	}
	
	public static TissuVariant map(TissuVariantDto dto) {
		TissuRequisService trs = new TissuRequisService();
		TypeTissuService tts = new TypeTissuService();
		MatiereService ms = new MatiereService();
		TissageService ts = new TissageService();
		
		TissuVariant v = new TissuVariant();
		v.setId(dto.getId());
		
		
		v.setMatiere(ms.findMatiere(dto.getMatiere()));
		v.setTypeTissu(tts.findTypeTissu(dto.getTypeTissu()));
		v.setTissage(ts.findTissage(dto.getTissage()));
		v.setTissuRequis(trs.findTissuRequis(dto.getTissuRequisId()));
		return v;
	}

}
