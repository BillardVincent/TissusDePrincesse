package fr.vbillard.tissusDePrincesse.services;

import java.util.List;

import fr.vbillard.tissusDePrincesse.dao.TissuUsedDao;
import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractDao;
import fr.vbillard.tissusDePrincesse.model.Projet;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import fr.vbillard.tissusDePrincesse.model.TissuUsed;

public class TissuUsedService extends AbstractService<TissuUsed>{
	
	private TissuUsedDao dao;
	public TissuUsedService() {
		dao = new TissuUsedDao();
	}

	public List<TissuUsed> getTissuUsedByTissuRequisAndProjet(TissuRequis tr, Projet p) {
		return dao.getTissuUsedByTissuRequisAndProjet(tr, p);
	}

	public List<TissuUsed> getByTissu(Tissu t) {
		return dao.findByTissu(t);
	}

	@Override
	protected AbstractDao getDao() {
		return dao;
	}

	


}
