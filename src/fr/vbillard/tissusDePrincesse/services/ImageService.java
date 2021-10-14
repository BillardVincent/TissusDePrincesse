package fr.vbillard.tissusDePrincesse.services;

import java.util.List;

import fr.vbillard.tissusDePrincesse.dao.PhotoDao;
import fr.vbillard.tissusDePrincesse.dao.abstractDao.AbstractDao;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.images.Photo;

public class ImageService extends AbstractService<Photo>{

	PhotoDao dao;
	
	
	public ImageService() {
		dao = new PhotoDao();
	}
	public List<Photo> getImages(Tissu tissu) {
		return dao.getByTissu(tissu);
	}
	
	@Override
	protected AbstractDao getDao() {
		return dao;
	}
}
