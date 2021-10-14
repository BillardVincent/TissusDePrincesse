package fr.vbillard.tissusDePrincesse.services;

import fr.vbillard.tissusDePrincesse.dao.PatronDao;
import fr.vbillard.tissusDePrincesse.dao.TissusRequisDao;
import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractDao;
import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.dtosFx.ProjetDto;
import fr.vbillard.tissusDePrincesse.mappers.PatronMapper;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import javafx.collections.ObservableList;

public class PatronService extends AbstractService<Patron>{
	
	PatronDao patronDao;
	PatronMapper mapper;
	
	public PatronService() {
		patronDao = new PatronDao();
		mapper = new PatronMapper();
	}

	public PatronDto create(PatronDto patron) {
		Patron p = mapper.map(patron);
		return mapper.map(saveOrUpdate(p));
	}

	public boolean existByReference(String string) {
		return patronDao.existByReference(string);
	}

	public void delete(PatronDto selected) {
		TissusRequisDao tissuRequisDao = new TissusRequisDao();
		TissuRequisService tissuRequisService = new TissuRequisService();
		for (TissuRequis tr : tissuRequisDao.getAllTissuRequisByPatron(selected.getId())) {
			tissuRequisService.delete(tr);
		}
		delete(mapper.map(selected));
	}

	public ObservableList<PatronDto> getObservableList(){
		return mapper.getAsObservable(getDao().findAll());
	}

	@Override
	protected AbstractDao<Patron> getDao() {
		return patronDao;
	}
}
