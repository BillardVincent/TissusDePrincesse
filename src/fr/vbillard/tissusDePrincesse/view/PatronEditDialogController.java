package fr.vbillard.tissusDePrincesse.view;

import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.UnitePoids;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.stage.Stage;

public class PatronEditDialogController {

    @FXML
    private Spinner<Integer> longueurField;
    @FXML
    private Spinner<Integer> laizeField;
    @FXML
    private TextField matiereField;
    @FXML
    private TextField descriptionField;
    @FXML
    private Spinner<Integer> poidsField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField lieuDachatField;
    @FXML
    private ToggleButton decatiField;
    @FXML
    private ChoiceBox<UnitePoids> unitePoidsField;
    @FXML
    private TextField tissageField;
    @FXML
    private ToggleButton chuteField;
    
	private Stage dialogStage;
    private TissuDto tissu;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setTissu(TissuDto tissu) {
        this.tissu = tissu;
        
        if (tissu.getChuteProperty() == null) {
        	tissu = new TissuDto(new Tissu(0, "", 0, 0, "", null, TypeTissuService.allTypeTissus.get(0), 0, UnitePoids.GRAMME_M,false, "", null, false));
        } 


        longueurField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, tissu.getLongueurProperty() == null ? 0: tissu.getLongueur()));
     	laizeField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, tissu.getLaizeProperty() == null ? 0: tissu.getLaize()));
    	poidsField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, tissu.getPoidseProperty() == null ? 0: tissu.getPoids()));
    	matiereField.setText(tissu.getMatiereProperty() == null ? "": tissu.getMatiere());
    	descriptionField.setText(tissu.getDescriptionProperty() == null ? "": tissu.getDescription());
    	typeField.setText(tissu.getTypeProperty() == null ? "": tissu.getType());
    	decatiField.setSelected(tissu.getDecatiProperty() == null ? false: tissu.isDecati());
    	lieuDachatField.setText(tissu.getLieuAchatProperty() == null ? "": tissu.getLieuAchat());
    	chuteField.setSelected(tissu.getChuteProperty() == null ? false: tissu.isChute());
    	tissageField.setText(tissu.getTissageProperty() == null ? "": tissu.getTissage());

    	
    	unitePoidsField.setItems(FXCollections.observableArrayList(UnitePoids.values()));
    	
    	unitePoidsField.setValue( tissu.getUnitePoidsProperty() == null ? UnitePoids.GRAMME_M :UnitePoids.valueOf(tissu.getUnitePoids()));
    }

    /**
     * Returns true if the user clicked OK, false otherwise.
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        //if (isInputValid()) {
    	if (tissu.getDescriptionProperty() == null) {
        	tissu = new TissuDto(new Tissu(0, "", 0, 0, "", null, TypeTissuService.allTypeTissus.get(0), 0, UnitePoids.GRAMME_M,false, "", null, false));
        } 
        	tissu.setLongueur(longueurField.getValue());
        	tissu.setLaize(laizeField.getValue());
        	tissu.setDescription(descriptionField.getText());
        	tissu.setMatiere(matiereField.getText());
        	tissu.setType(typeField.getText());
    		tissu.setPoids(poidsField.getValue());
    		tissu.setUnitePoids(unitePoidsField.getValue());
    		tissu.setDecati(Boolean.parseBoolean(decatiField.getText()));
    		tissu.setLieuAchat(lieuDachatField.getText());
    		tissu.setChute(Boolean.parseBoolean(chuteField.getText()));
        	tissu.setTissage(tissageField.getText());

    		
            okClicked = true;
            dialogStage.close();
        //}
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";
/*
        if (firstNameField.getText() == null || firstNameField.getText().length() == 0) {
            errorMessage += "No valid first name!\n";
        }
        if (lastNameField.getText() == null || lastNameField.getText().length() == 0) {
            errorMessage += "No valid last name!\n";
        }
        if (streetField.getText() == null || streetField.getText().length() == 0) {
            errorMessage += "No valid street!\n";
        }

        if (postalCodeField.getText() == null || postalCodeField.getText().length() == 0) {
            errorMessage += "No valid postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(postalCodeField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (cityField.getText() == null || cityField.getText().length() == 0) {
            errorMessage += "No valid city!\n";
        }

        if (birthdayField.getText() == null || birthdayField.getText().length() == 0) {
            errorMessage += "No valid birthday!\n";
        } else {
            if (!DateUtil.validDate(birthdayField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }
*/
        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}

