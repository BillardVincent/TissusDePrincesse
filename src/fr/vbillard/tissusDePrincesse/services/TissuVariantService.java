package fr.vbillard.tissusDePrincesse.services;

import java.util.List;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.dao.TissuVariantDao;
import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractDao;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuRequisDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuVariantDto;
import fr.vbillard.tissusDePrincesse.mappers.VariantMapper;
import fr.vbillard.tissusDePrincesse.model.TissuVariant;

public class TissuVariantService extends AbstractService<TissuVariant>{
	TissuVariantDao tissuVariantDao;
	VariantMapper mapper;
	
	public TissuVariantService(){
		tissuVariantDao = new TissuVariantDao();
		mapper = new VariantMapper();
	}

	public List<TissuVariantDto> getVariantByTissuRequis(TissuRequisDto tissu) {
		List<TissuVariant> listTv = tissuVariantDao.getVariantByTissuRequis(tissu.getId());
		return listTv.stream().map(v -> mapper.map(v)).collect(Collectors.toList());
	}

	public TissuVariantDto saveOrUpdate(TissuVariantDto variantSelected) {
		
		return mapper.map(saveOrUpdate(mapper.map(variantSelected)));
	}

	@Override
	protected AbstractDao getDao() {
		return tissuVariantDao;
	}

}
