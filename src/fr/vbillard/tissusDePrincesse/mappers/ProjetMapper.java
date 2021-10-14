package fr.vbillard.tissusDePrincesse.mappers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dtosFx.ProjetDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuRequisDto;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.Projet;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import fr.vbillard.tissusDePrincesse.model.TissuUsed;
import fr.vbillard.tissusDePrincesse.model.enums.ProjectStatus;
import fr.vbillard.tissusDePrincesse.services.MatiereService;
import fr.vbillard.tissusDePrincesse.services.TissageService;
import fr.vbillard.tissusDePrincesse.services.TissuRequisService;
import fr.vbillard.tissusDePrincesse.services.TissuUsedService;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;

public class ProjetMapper implements IMapper<Projet, ProjetDto>{
	
	PatronMapper patronMapper;
	TissuRequisMapper tissuRequisMapper;
	public ProjetMapper() {
		patronMapper = new PatronMapper();
		tissuRequisMapper = new TissuRequisMapper();
	}
	
	public Projet map(ProjetDto dto) {
		Projet projet = new Projet();
		projet.setId(dto.getId());
		projet.setDescription(dto.getDescription());
		projet.setStatus(dto.getProjectStatusProperty() == null || dto.getProjectStatus() == "" ? ProjectStatus.BROUILLON : ProjectStatus.getEnum(dto.getProjectStatus()));
		projet.setPatron(dto.getPatron() == null ? new Patron() : patronMapper.map(dto.getPatron()));
		return projet;
	}
	
	public ProjetDto map(Projet projet) {
		TissuRequisService trs = new TissuRequisService();
		TissuUsedService tus = new TissuUsedService();
		ProjetDto dto = new ProjetDto();
		dto.setId(projet.getId());
		dto.setDescription(projet.getDescription());
		dto.setPatron(patronMapper.map(projet.getPatron()));
		dto.setProjectStatus(projet.getStatus());
		Map<TissuRequisDto, List<Integer>> tuMap = new HashMap<TissuRequisDto, List<Integer>>();
		List<TissuRequis> trLst = trs.getAllTissuRequisByPatron(projet.getPatron().getId());
		for (TissuRequis tr : trLst) {
			List<TissuUsed> tu = tus.getTissuUsedByTissuRequisAndProjet(tr, projet);
			tuMap.put(tissuRequisMapper.map(tr), tu == null ? new ArrayList<Integer>() : tu.stream().map(t -> t.getId()).collect(Collectors.toList()));
		}
		
		dto.setTissuUsed(tuMap);
		
	 return dto;
	}
	
	
}
