package fr.vbillard.tissusDePrincesse.view;

import fr.vbillard.tissusDePrincesse.MainApp;
import fr.vbillard.tissusDePrincesse.model.Matiere;
import fr.vbillard.tissusDePrincesse.services.MatiereService;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MatiereEditController implements IController {

	@FXML
	private ListView<String> listMatieres;

	@FXML
	private TextField newMatiere;
	@FXML
	private TextField editMatiere;

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

	MatiereService matiereService;
	MainApp mainApp;

	String editedMatiere;

	ObservableList<String> allMatieres;

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		listMatieres.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> handleSelectElement(newValue));

		this.editMatiere.setDisable(true);
		this.editerButton.setDisable(true);

	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setData(MainApp mainApp, MatiereService matiereService) {
		this.matiereService = matiereService;
		this.mainApp = mainApp;
		allMatieres = matiereService.getAllMatieresValues();
		listMatieres.setItems(allMatieres);

	}

	public void handleAddElement() {

		if (newMatiere.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Pas de valeur");
			alert.setHeaderText("Pas de valeur");
			alert.setContentText("Veuillez remplir une valeur");

			alert.showAndWait();
		} else if (matiereService.validate(newMatiere.getText())) {
			matiereService.saveOrUpdate(new Matiere(newMatiere.getText()));
			newMatiere.setText("");
			allMatieres = matiereService.getAllMatieresValues();
			listMatieres.setItems(allMatieres);
		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Duplicat");
			alert.setHeaderText("Mati??re d??ja existante");
			alert.setContentText("Cette mati??re existe d??j??");

			alert.showAndWait();
		}

	}

	public void handleSelectElement(String matiere) {
		this.editedMatiere = matiere;
		this.editMatiere.setText(matiere);
		this.editMatiere.setDisable(false);
		this.editerButton.setDisable(false);

	}

	public void handleEditElement() {
		if (editMatiere.getText().trim().equals("")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Pas de valeur");
			alert.setHeaderText("Pas de valeur");
			alert.setContentText("Veuillez remplir une valeur");

			alert.showAndWait();
		} else if (matiereService.validate(editMatiere.getText())) {
			Matiere m = matiereService.findMatiere(editedMatiere);
			m.setValue(editMatiere.getText());
			matiereService.saveOrUpdate(m);
			editMatiere.setText("");
			allMatieres = matiereService.getAllMatieresValues();
			listMatieres.setItems(allMatieres);
			this.editMatiere.setDisable(true);

		} else {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Duplicat");
			alert.setHeaderText("Mati??re d??ja existante");
			alert.setContentText("Cette mati??re existe d??j??");

			alert.showAndWait();
		}
	}

	public void handleSuppressElement() {
		matiereService.delete(editMatiere.getText());
		allMatieres = matiereService.getAllMatieresValues();
		listMatieres.setItems(allMatieres);
		this.editedMatiere = null;
		this.editMatiere.setText("");
		this.editMatiere.setDisable(true);
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
