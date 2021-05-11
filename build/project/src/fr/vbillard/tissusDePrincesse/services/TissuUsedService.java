package fr.vbillard.tissusDePrincesse.services;

import java.util.List;

import fr.vbillard.tissusDePrincesse.dao.TissuUsedDao;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import fr.vbillard.tissusDePrincesse.model.TissuUsed;

public class TissuUsedService {
	
	private TissuUsedDao tissuUsedDao;
	public TissuUsedService() {
		tissuUsedDao = new TissuUsedDao();
	}

	public List<TissuUsed> getTissuUsedByTissuRequis(TissuRequis tr) {
		return tissuUsedDao.getTissuUsedByTissuRequis(tr);
	}
	
public void saveOrUpdate(TissuUsed tissuUsed) {
		
		if (tissuUsed.getId() != 0) {
			tissuUsedDao.update(tissuUsed);
		}
		else {
			tissuUsedDao.create(tissuUsed);
		}
	}
	
	
	public void delete(TissuUsed tissuUsed) {
		tissuUsedDao.delete(tissuUsed);
		
	}

	public TissuUsed getTissuUsedById(int id) {
		// TODO Auto-generated method stub
		return tissuUsedDao.findById(id);
	}

	public List<TissuUsed> getByTissu(Tissu t) {
		// TODO Auto-generated method stub
		return tissuUsedDao.findByTissu(t);
	}

	


}
