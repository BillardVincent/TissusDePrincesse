package fr.vbillard.tissusDePrincesse.services;

import java.util.List;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.TissuVariantDao;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuRequisDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuVariantDto;
import fr.vbillard.tissusDePrincesse.mappers.VariantMapper;
import fr.vbillard.tissusDePrincesse.model.TissuVariant;

public class TissuVariantService {
	TissuVariantDao tissuVariantDao;
	public TissuVariantService(){
		tissuVariantDao = new TissuVariantDao();
	}

	public List<TissuVariantDto> getVariantByTissuRequis(TissuRequisDto tissu) {
		List<TissuVariant> listTv = tissuVariantDao.getVariantByTissuRequis(tissu.getId());
		return listTv.stream().map(v -> VariantMapper.map(v)).collect(Collectors.toList());
	}

	public TissuVariantDto saveOrUpdate(TissuVariantDto variantSelected) {
		if (variantSelected.getId() == 0) return VariantMapper.map(tissuVariantDao.create(VariantMapper.map(variantSelected)));
		else return VariantMapper.map(tissuVariantDao.update(VariantMapper.map(variantSelected)));
	}

}
