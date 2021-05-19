package fr.vbillard.tissusDePrincesse.view;
import fr.vbillard.tissusDePrincesse.MainApp;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.services.PatronService;
import fr.vbillard.tissusDePrincesse.services.TissuService;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 * The controller for the root layout. The root layout provides the basic
 * application layout containing a menu bar and space where other JavaFX
 * elements can be placed.
 * 
 * @author Marco Jakob
 */
public class RootLayoutController {

    // Reference to the main application
    private MainApp mainApp;
    private TissuService tissuService;
    private PatronService patronService;
    private MainOverviewController tissuOverviewController;
    

    /**
     * Is called by the main application to give a reference back to itself.
     * @param tissuOverviewController 
     * 
     * @param mainApp
     */
    public void setMainApp(TissuService tissuService, PatronService patronService, MainOverviewController tissuOverviewController, MainApp mainApp) {
        this.tissuService = tissuService;
        this.patronService = patronService;
        this.mainApp = mainApp;
        this.tissuOverviewController = tissuOverviewController;
    }


   
    @FXML
    private void handleSave() {
    	/*
        File tissuFile = Serializer.getFilePath();
        if (tissuFile != null) {
            Serializer.serialize(tissuFile);
        } else {
            handleSaveAs();
        }
        */
    }

    /**
     * Opens a FileChooser to let the user select a file to save to.
     */
    @FXML
    private void handleSaveAs() {
    	/*
		FileChooser fileChooser = new FileChooser();

		// Set extension filter
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				 "JSON files (*.json)", "*.json");
		fileChooser.getExtensionFilters().add(extFilter);

		// Show save file dialog
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (file != null) {
			// Make sure it has the correct extension
			if (!file.getPath().endsWith(".json")) {
				file = new File(file.getPath() + ".json");
			}
			Serializer.serialize(file);
		}
		*/
	}
    
    @FXML
    private void handleOpen() {
    	/*
        FileChooser fileChooser = new FileChooser();

        // Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "JSON files (*.json)", "*.json");
        fileChooser.getExtensionFilters().add(extFilter);

        // Show save file dialog
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
           Serializer.Deserialize(file);
           tissuOverviewController.refreshList();
        }
        */
    }

    /**
     * Opens an about dialog.
     */
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("Les Tissus de Princesse");
    	alert.setHeaderText("A Propos");
    	alert.setContentText("Développé par Vincent Billard");

    	alert.showAndWait();
    }
    
    @FXML
    private void handleNewMatiere() {
    	mainApp.showMatiereEditDialog();
    }
    
    @FXML
    private void handleNewTissu() {
    	mainApp.showTissuEditDialog(new TissuDto());
    }
    
    @FXML
    private void handleNewTissage() {
    	mainApp.showTissageEditDialog();
    }
    
    @FXML
    private void handleNewTypeTissu() {
    	mainApp.showTypeTissuEditDialog();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}