package fr.vbillard.tissusDePrincesse.services;

import java.util.List;

import fr.vbillard.tissusDePrincesse.dao.PhotoDao;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.images.Photo;

public class ImageService {

	PhotoDao dao;
	public ImageService() {
		dao = new PhotoDao();
	}
	public List<Photo> getImages(Tissu tissu) {
		return dao.getByTissu(tissu);
	}

	public void save(Photo image) {
		dao.create(image);
	}
}
