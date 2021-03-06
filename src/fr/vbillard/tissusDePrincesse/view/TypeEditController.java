package fr.vbillard.tissusDePrincesse.view;

import java.util.List;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.MainApp;
import fr.vbillard.tissusDePrincesse.model.TypeTissu;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TypeEditController implements IController {

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
	TypeTissu typeTissu;

	TypeTissu editedTypeTissu;

	ObservableList<String> allTypeTissus;

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		listTypeTissus.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> handleSelectElement(newValue));
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(MainApp mainApp, TypeTissuService typeTissuService) {
		this.typeTissuService = typeTissuService;
		this.mainApp = mainApp;
		allTypeTissus = typeTissuService.getAllTypeTissuValues();
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
			typeTissuService.saveOrUpdate(new TypeTissu(newTypeTissu.getText()));
			newTypeTissu.setText("");
			allTypeTissus = typeTissuService.getAllTypeTissuValues();
			listTypeTissus.setItems(allTypeTissus);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Duplicat");
			alert.setHeaderText("Type d??ja existante");
			alert.setContentText("Ce type existe d??j??");

			alert.showAndWait();
		}
	}

	public void handleSelectElement(String typeTissu) {
		this.editedTypeTissu = typeTissuService.findTypeTissu(typeTissu);
		this.editTypeTissu.setText(typeTissu);
		this.editTypeTissu.setDisable(false);
	}

	public void handleEditElement() {
		if (editTypeTissu.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Pas de valeur");
			alert.setHeaderText("Pas de valeur");
			alert.setContentText("Veuillez remplir une valeur");

			alert.showAndWait();
		} else if (typeTissuService.validate(editTypeTissu.getText())) {
			editedTypeTissu.setValue(editTypeTissu.getText());
			typeTissuService.saveOrUpdate(editedTypeTissu);
			editTypeTissu.setText("");
			allTypeTissus = typeTissuService.getAllTypeTissuValues();
			listTypeTissus.setItems(allTypeTissus);
			this.editTypeTissu.setDisable(true);

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Duplicat");
			alert.setHeaderText("Type d??ja existante");
			alert.setContentText("Ce type existe d??j??");

			alert.showAndWait();
		}
	}

	public void handleSuppressElement() {
		typeTissuService.delete(editTypeTissu.getText());
		allTypeTissus = typeTissuService.getAllTypeTissuValues();
		listTypeTissus.setItems(allTypeTissus);
		this.editedTypeTissu = null;
		this.editTypeTissu.setText("");
		this.editTypeTissu.setDisable(true);
		this.editerButton.setDisable(true);
	}

	public void handleClose() {
		okClicked = true;
		dialogStage.close();
	}

	public boolean isOkClicked() {

		return okClicked;
	}

}
