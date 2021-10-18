package fr.vbillard.tissusDePrincesse.view;

import java.io.ByteArrayInputStream;

import fr.vbillard.tissusDePrincesse.MainApp;
import fr.vbillard.tissusDePrincesse.model.Photo;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PictureExpended implements IController{

	private Stage dialogStage;
	
	@FXML
	ImageView view;
	
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(MainApp mainApp, Photo photo) {
		view.setImage(new Image(new ByteArrayInputStream(photo.getData())));
		
	}

	public boolean isOkClicked() {
		return false;
	}
	
	
}
