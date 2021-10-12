package fr.vbillard.tissusDePrincesse.view;

import fr.vbillard.tissusDePrincesse.MainApp;
import fr.vbillard.tissusDePrincesse.model.Tissage;
import fr.vbillard.tissusDePrincesse.services.TissageService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TissageEditController implements IController{

	@FXML
	private ListView<String> listTissages;
	
	@FXML
	private TextField newTissage;
	@FXML
	private TextField editTissage;

	@FXML
	private Button ajouterButton;
	@FXML
	private Button editerButton;
	@FXML
	private Button supprimerButton;
	@FXML
	private Button fermerButton;

	    
	private Stage dialogStage;
	private boolean okClicked = false;
	
	TissageService tissageService;
	MainApp mainApp;
	
	String editedTissage;
	
	ObservableList<String> allTissages;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		listTissages.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleSelectElement(newValue));
	}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	public void setData(MainApp mainApp, TissageService tissageService) {
		this.tissageService = tissageService;
		this.mainApp = mainApp;
		allTissages = tissageService.getAllObs();
		listTissages.setItems(allTissages);
		
	}
	
	public void handleAddElement() {
		
		if (newTissage.getText().trim().equals("")) {
			 Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Pas de valeur");
	            alert.setHeaderText("Pas de valeur");
	            alert.setContentText("Veuillez remplir une valeur");

	            alert.showAndWait();
		} else if (tissageService.validate(newTissage.getText())) {
				tissageService.create(new Tissage(newTissage.getText()));
				newTissage.setText("");
				allTissages = tissageService.getAllObs();
				listTissages.setItems(allTissages);

		 } else {
			 Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Duplicat");
	            alert.setHeaderText("Tissage déja existante");
	            alert.setContentText("Ce tissage existe déjà");

	            alert.showAndWait();
		 }
	

	}
	
	public void handleSelectElement(String tissage) {
		this.editedTissage = tissage;
		this.editTissage.setText(tissage);
		this.editTissage.setDisable(false);
	}
	
public void handleEditElement() {
	if (newTissage.getText().trim().equals("")) {
		 Alert alert = new Alert(AlertType.WARNING);
           alert.initOwner(mainApp.getPrimaryStage());
           alert.setTitle("Pas de valeur");
           alert.setHeaderText("Pas de valeur");
           alert.setContentText("Veuillez remplir une valeur");

           alert.showAndWait();
	} else if (tissageService.validate(editTissage.getText())) {
			tissageService.create(new Tissage(editTissage.getText()));
			editTissage.setText("");
			allTissages = tissageService.getAllObs();
			listTissages.setItems(allTissages);

			this.editTissage.setDisable(true);

	 } else {
		 Alert alert = new Alert(AlertType.WARNING);
           alert.initOwner(mainApp.getPrimaryStage());
           alert.setTitle("Duplicat");
           alert.setHeaderText("Matière déja existante");
           alert.setContentText("Cette matière existe déjà");

           alert.showAndWait();
	 }
	}

public void handleSuppressElement() {
	
}
	
	public void handleClose() {
		 okClicked = true;
         dialogStage.close();
	}

	public boolean isOkClicked() {
		
		return okClicked;
	}


	    
}
