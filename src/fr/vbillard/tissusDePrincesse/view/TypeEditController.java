package fr.vbillard.tissusDePrincesse.view;

import java.util.List;

import fr.vbillard.tissusDePrincesse.MainApp;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class TypeEditController {

	@FXML
	private ListView<String> listTypeTissus;
	
	@FXML
	private TextField newTypeTissu;
	@FXML
	private TextField editTypeTissu;

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
	
	TypeTissuService typeTissuService;
	MainApp mainApp;
	
	String editedTypeTissu;
	
	ObservableList<String> allTypeTissus;

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		listTypeTissus.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> handleSelectElement(newValue));
	}
	
	public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

	public void setData(MainApp mainApp, TypeTissuService typeTissuService) {
		this.typeTissuService = typeTissuService;
		this.mainApp = mainApp;
		allTypeTissus = typeTissuService.getAllObs();
		listTypeTissus.setItems(allTypeTissus);
		
	}
	
	public void handleAddElement() {
		
		if (newTypeTissu.getText().trim().equals("")) {
			 Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Pas de valeur");
	            alert.setHeaderText("Pas de valeur");
	            alert.setContentText("Veuillez remplir une valeur");

	            alert.showAndWait();
		} else if (typeTissuService.validate(newTypeTissu.getText())) {
				typeTissuService.create(new TypeTissu(newTypeTissu.getText()));
				newTypeTissu.setText("");
				allTypeTissus = typeTissuService.getAllObs();	
		 } else {
			 Alert alert = new Alert(AlertType.WARNING);
	            alert.initOwner(mainApp.getPrimaryStage());
	            alert.setTitle("Duplicat");
	            alert.setHeaderText("Matière déja existante");
	            alert.setContentText("Cette matière existe déjà");

	            alert.showAndWait();
		 }
	

	}
	
	public void handleSelectElement(String typeTissu) {
		this.editedTypeTissu = typeTissu;
		this.editTypeTissu.setText(typeTissu);
		this.editTypeTissu.setDisable(false);
	}
	
public void handleEditElement() {
	if (newTypeTissu.getText().trim().equals("")) {
		 Alert alert = new Alert(AlertType.WARNING);
           alert.initOwner(mainApp.getPrimaryStage());
           alert.setTitle("Pas de valeur");
           alert.setHeaderText("Pas de valeur");
           alert.setContentText("Veuillez remplir une valeur");

           alert.showAndWait();
	} else if (typeTissuService.validate(editTypeTissu.getText())) {
			typeTissuService.create(new TypeTissu(editTypeTissu.getText()));
			editTypeTissu.setText("");
			allTypeTissus = typeTissuService.getAllObs();
			this.editTypeTissu.setDisable(true);

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
