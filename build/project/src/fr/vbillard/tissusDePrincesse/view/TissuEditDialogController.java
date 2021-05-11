package fr.vbillard.tissusDePrincesse.view;

import java.util.stream.Collectors;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusDePrincesse.MainApp;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.model.Matiere;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.enums.UnitePoids;
import fr.vbillard.tissusDePrincesse.services.MatiereService;
import fr.vbillard.tissusDePrincesse.services.TissageService;
import fr.vbillard.tissusDePrincesse.services.TissuService;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;
import fr.vbillard.tissusDePrincesse.utils.Constants;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class TissuEditDialogController {

    @FXML
    private TextField referenceField;
    @FXML
    private Spinner<Integer> longueurField;
    private int longueur;
    @FXML
    private Spinner<Integer> laizeField;
    private int laize;
    @FXML
    private ChoiceBox<String> matiereField;
    @FXML
    private TextField descriptionField;
    @FXML
    private Spinner<Integer> poidsField;
    private int poids;
    @FXML
    private ChoiceBox<String> typeField;
    @FXML
    private TextField lieuDachatField;
    @FXML
    private ToggleButton decatiField;
    @FXML
    private ChoiceBox<String> unitePoidsField;
    @FXML
    private ChoiceBox<String> tissageField;
    @FXML
    private ToggleButton chuteField;
    
    @FXML
    private Button addTissageButton;
    @FXML
    private Button addTypeButton;
    @FXML
    private Button addMatiereButton;
    @FXML
    private Button generateReferenceButton;
   
    @FXML
    private Label errorLabel;
    
	private Stage dialogStage;
    private TissuDto tissu;
    private boolean okClicked = false;
    MaterialDesignIconView closeCircle;
    MaterialDesignIconView closeCircle1;
    MaterialDesignIconView closeCircle2;
    
    TissuService tissuService;
    MatiereService matiereService;
    TissageService tissageService;
    TypeTissuService typeTissuService;
    MainApp mainApp;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    	
    	closeCircle = new MaterialDesignIconView(MaterialDesignIcon.PLUS_CIRCLE);
    	closeCircle.setSize("1em");
    	closeCircle.setFill(Constants.colorAdd);
    	closeCircle1 = new MaterialDesignIconView(MaterialDesignIcon.PLUS_CIRCLE);
    	closeCircle1.setSize("1em");
    	closeCircle1.setFill(Constants.colorAdd);
    	closeCircle2 = new MaterialDesignIconView(MaterialDesignIcon.PLUS_CIRCLE);
    	closeCircle2.setSize("1em");
    	closeCircle2.setFill(Constants.colorAdd);
    	
    	addTissageButton.setGraphic(closeCircle);
    	addTypeButton.setGraphic(closeCircle1);
    	addMatiereButton.setGraphic(closeCircle2);
    	FontAwesomeIconView magicIcon = new FontAwesomeIconView(FontAwesomeIcon.MAGIC);
    	generateReferenceButton.setGraphic(magicIcon);
    	generateReferenceButton.setTooltip(new Tooltip("Générer une référence automatiquement"));

    	longueurField.valueProperty().addListener((obs, oldValue, newValue) -> {
        longueur = newValue;});
    	longueurField.focusedProperty().addListener((observable, oldValue, newValue) -> {
    		  if (!newValue) {
    			  longueurField.increment(0); // won't change value, but will commit editor
    			  longueur = longueurField.getValue();
    		  }
    		});
    	laizeField.valueProperty().addListener((obs, oldValue, newValue) -> 
    	laize = newValue);
    	laizeField.focusedProperty().addListener((observable, oldValue, newValue) -> {
  		  if (!newValue) {
  			laizeField.increment(0); // won't change value, but will commit editor
  			laize = laizeField.getValue();
  		  }
  		});
    	poidsField.valueProperty().addListener((obs, oldValue, newValue) -> 
    	poids = newValue);
    	poidsField.focusedProperty().addListener((observable, oldValue, newValue) -> {
  		  if (!newValue) {
  			poidsField.increment(0); // won't change value, but will commit editor
  			poids = poidsField.getValue();
  		  }
  		});
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }


    public void setTissu(TissuDto tissu, TissuService tissuService, TypeTissuService typeTissuService,  MatiereService matiereService, TissageService tissageService, MainApp mainApp) {
    	this.typeTissuService = typeTissuService;
    	this.tissuService = tissuService;
    	this.tissageService = tissageService;
    	this.matiereService = matiereService;
    	this.mainApp = mainApp;
    	
        this.tissu = tissu;
        
        if (tissu.getChuteProperty() == null) {
        	tissu = new TissuDto(new Tissu(0, "", 0, 0, "", null, TypeTissuService.allTypeTissus.get(0), 0, UnitePoids.NON_RENSEIGNE,false, "", null, false));
        } 


        longueurField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, tissu.getLongueurProperty() == null ? 0: tissu.getLongueur()));
     	laizeField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, tissu.getLaizeProperty() == null ? 0: tissu.getLaize()));
    	poidsField.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE, tissu.getPoidseProperty() == null ? 0: tissu.getPoids()));
    	referenceField.setText(tissu.getReferenceProperty() == null ? "": tissu.getReference());
    	descriptionField.setText(tissu.getDescriptionProperty() == null ? "": tissu.getDescription());
    	decatiField.setSelected(tissu.getDecatiProperty() == null ? false: tissu.isDecati());
    	lieuDachatField.setText(tissu.getLieuAchatProperty() == null ? "": tissu.getLieuAchat());
    	chuteField.setSelected(tissu.getChuteProperty() == null ? false: tissu.isChute());

    	
    	unitePoidsField.setItems(FXCollections.observableArrayList(UnitePoids.labels()));
    	unitePoidsField.setValue(tissu.getUnitePoidsProperty() == null ? UnitePoids.NON_RENSEIGNE.label :tissu.getUnitePoids());
    	
    	typeField.setItems(FXCollections.observableArrayList(typeTissuService.getAll().stream().map(tt -> tt.getType()).collect(Collectors.toList())));
    	typeField.setValue(tissu.getTypeProperty() == null ? "": tissu.getType());

    	matiereField.setItems(FXCollections.observableArrayList(matiereService.getAll().stream().map(m -> m.getMatiere()).collect(Collectors.toList())));
    	matiereField.setValue(tissu.getMatiereProperty() == null ? "": tissu.getMatiere());

    	tissageField.setItems(FXCollections.observableArrayList(tissageService.getAll().stream().map(t -> t.getTissage()).collect(Collectors.toList())));
    	tissageField.setValue(tissu.getTissageProperty() == null ? "": tissu.getTissage());
    	
    	longueur = longueurField.getValue();
    	laize = laizeField.getValue();
    	poids = poidsField.getValue();
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
        if (isInputValid()) {
    	if (tissu.getChuteProperty() == null) {
        	tissu = new TissuDto(new Tissu(0, "", 0, 0, "", null, TypeTissuService.allTypeTissus.get(0), 0, UnitePoids.NON_RENSEIGNE,false, "", null, false));
        } 
    		tissu.setReference(referenceField.getText());
        	tissu.setLongueur(longueur);
        	tissu.setLaize(laizeField.getValue());
        	tissu.setDescription(descriptionField.getText());
        	tissu.setMatiere(matiereField.getValue());
        	tissu.setType(typeField.getValue());
    		tissu.setPoids(poidsField.getValue());
    		tissu.setUnitePoids(unitePoidsField.getValue());
    		tissu.setDecati(Boolean.parseBoolean(decatiField.getText()));
    		tissu.setLieuAchat(lieuDachatField.getText());
    		tissu.setChute(Boolean.parseBoolean(chuteField.getText()));
        	tissu.setTissage(tissageField.getValue());

    		tissuService.saveOrUpdate(tissu);
            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }
    
    @FXML
    private void handleAddMatiere() {
    	mainApp.showMatiereEditDialog();
    }
    
    @FXML
    private void handleAddTissage() {
    	mainApp.showTissageEditDialog();
    }
    
    @FXML
    private void handleAddTypeTissu() {
    	mainApp.showTypeTissuEditDialog();
    }
    
    

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

    	
        if (referenceField.getText() == null || referenceField.getText().length() == 0) {
            errorMessage += "Référence non renseignée.\n";
        }
        if (matiereField.getValue() == null ) {
            errorMessage += "Matière non renseignée.\n";
        }
        if (unitePoidsField.getValue() == null ) {
            errorMessage += "Unité de poids non renseignée.\n";
        }
        if (poidsField.getValue() == null ) {
            errorMessage += "Poids non renseigné.\n";
        }
        if (tissageField.getValue() == null ) {
            errorMessage += "Poids non renseigné.\n";
        }
        
         
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
    @FXML
    private void handleGenerateReference() {
    	StringBuilder sb = new StringBuilder();
    	sb.append(typeField.getValue().trim().isEmpty() ? "X" : typeField.getValue().toUpperCase().charAt(0))
    	.append(matiereField.getValue().trim().isEmpty() ? "X" : matiereField.getValue().toUpperCase().charAt(0))
    	.append(tissageField.getValue().trim().isEmpty() ? "X" : tissageField.getValue().toUpperCase().charAt(0)).append("-");
    	if (Boolean.parseBoolean(chuteField.getText())) sb.append("cp-");
    	boolean ref = true;
    	int refNb = 0;
    	while (ref) {
    		refNb ++;
    		ref = tissuService.existByReference(sb.toString()+refNb);
    	}
    	referenceField.setText(sb.append(refNb).toString());
    }
}

