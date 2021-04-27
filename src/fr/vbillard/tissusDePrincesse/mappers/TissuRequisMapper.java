package fr.vbillard.tissusDePrincesse.mappers;

import fr.vbillard.tissusDePrincesse.dtosFx.TissuRequisDto;
import fr.vbillard.tissusDePrincesse.model.GammePoids;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;

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
		dto.setId(tr.getId());
		dto.setLaize(tr.getLaize());
		dto.setLongueur(tr.getLongueur());
		dto.setGammePoids(tr.getGammePoids().label);
		return dto;
	}

}
