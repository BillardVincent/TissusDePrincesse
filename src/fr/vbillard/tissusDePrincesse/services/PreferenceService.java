package fr.vbillard.tissusDePrincesse.services;

import javax.swing.filechooser.FileSystemView;

import fr.vbillard.tissusDePrincesse.dao.PreferenceDao;
import fr.vbillard.tissusDePrincesse.model.Preference;

public class PreferenceService {

	private PreferenceDao dao = new PreferenceDao();
	
	public Preference getPreferences() {
		return dao.getPreference();
	}
	
	public void savePreferences(Preference pref) {
		pref.setDataBasePath(normalizePath(pref.getDataBasePath()));
		pref.setPictureLastUploadPath(normalizePath(pref.getPictureLastUploadPath()));
		dao.savePreferences(pref);
	}
	
	private String normalizePath(String path) {
		if (!path.equals(FileSystemView.getFileSystemView().getDefaultDirectory().getPath()) &&
				path.contains("\\") &&
				!path.contains("\\\\")) {
			path =  path.substring(0, path.lastIndexOf("\\")).replace("\\", "\\\\");
		}
		return path;
	}
}
