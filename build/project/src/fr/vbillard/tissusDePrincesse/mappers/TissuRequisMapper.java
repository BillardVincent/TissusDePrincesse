package fr.vbillard.tissusDePrincesse.mappers;

import java.util.List;

import fr.vbillard.tissusDePrincesse.dtosFx.TissuRequisDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuVariantDto;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import fr.vbillard.tissusDePrincesse.model.enums.GammePoids;
import fr.vbillard.tissusDePrincesse.services.TissuVariantService;

public class TissuRequisMapper {

	public static TissuRequis map(TissuRequisDto dto, Patron p) {
		TissuRequis tr = new TissuRequis();
		tr.setId(dto.getId());
		tr.setLaize(dto.getLaize());
		tr.setLongueur(dto.getLongueur());
		tr.setGammePoids(GammePoids.getEnum(dto.getGammePoids()));
		tr.setPatron(p);
		return tr;
	}

	public static TissuRequis map(TissuRequisDto dto) {
		TissuRequis tr = new TissuRequis();
		tr.setId(dto.getId());
		tr.setLaize(dto.getLaize());
		tr.setLongueur(dto.getLongueur());
		tr.setGammePoids(GammePoids.getEnum(dto.getGammePoids()));
		return tr;
	}

	public static TissuRequisDto map(TissuRequis tr) {
		TissuRequisDto dto = new TissuRequisDto();
		TissuVariantService tvs = new TissuVariantService();
		dto.setId(tr.getId());
		dto.setLaize(tr.getLaize());
		dto.setLongueur(tr.getLongueur());
		dto.setGammePoids(tr.getGammePoids().label);
		List<TissuVariantDto> variants = tvs.getVariantByTissuRequis(dto);
		dto.setVariant(variants);
		return dto;
	}

}
