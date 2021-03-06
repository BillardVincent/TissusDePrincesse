package fr.vbillard.tissusDePrincesse.view;

import java.util.ArrayList;
import java.util.stream.Collectors;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import fr.vbillard.tissusDePrincesse.MainApp;
import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuRequisDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuVariantDto;
import fr.vbillard.tissusDePrincesse.mappers.PatronMapper;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.enums.GammePoids;
import fr.vbillard.tissusDePrincesse.services.MatiereService;
import fr.vbillard.tissusDePrincesse.services.PatronService;
import fr.vbillard.tissusDePrincesse.services.TissageService;
import fr.vbillard.tissusDePrincesse.services.TissuRequisService;
import fr.vbillard.tissusDePrincesse.services.TissuVariantService;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;
import fr.vbillard.tissusDePrincesse.utils.Constants;
import fr.vbillard.tissusDePrincesse.utils.DevInProgressService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class PatronEditDialogController implements IController{

	@FXML
	private TextField marqueField;
	@FXML
	private TextField modeleField;
	@FXML
	private TextField typeVetementField;
	@FXML
	private Button addTissuButton;
	@FXML
	private Button addFournitureButton;
	@FXML
	private VBox rightContainer;
	@FXML
	private GridPane tissusPatronListGrid;
	@FXML
	private Button generateReferenceButton;
	@FXML
	private TextField referenceField;

	private Stage dialogStage;
	private PatronDto patron;
	//private TissuRequisDto tissuRequisDto;
	private TissuRequisService tissuRequisService;
	private TissuVariantService tissuVariantService;
	private MatiereService matiereService;
	private TissageService tissageService;
	private TypeTissuService typeTissuService;
	private PatronMapper patronMapper;
	private boolean okClicked = false;
	private MainApp mainApp;
	//private ObservableList<TissuRequisDto> listTissuRequis;
	private boolean unregistredPatron;
	private PatronService patronService;
	private int longueur;
	private int laize;
	//private HBox tissuRequisDisplayHbox;
	private TissuVariantDto variantSelected;
	boolean editingVariant = false;
	private ObservableList<TissuVariantDto> tvList;
	private VBox bottomRightVbox;

	public PatronEditDialogController() {
		patronMapper = new PatronMapper();
	}
	
	@FXML
	private void initialize() {
		FontAwesomeIconView addIcon1 = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
		addIcon1.setFill(Constants.colorAdd);
		addTissuButton.setGraphic(addIcon1);

		FontAwesomeIconView addIcon2 = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
		addIcon2.setFill(Constants.colorAdd);
		addFournitureButton.setGraphic(addIcon2);

		FontAwesomeIconView magicIcon = new FontAwesomeIconView(FontAwesomeIcon.MAGIC);
		generateReferenceButton.setGraphic(magicIcon);
		generateReferenceButton.setTooltip(new Tooltip("G??n??rer une r??f??rence automatiquement"));
	}

	private void setDisabledButton() {
		unregistredPatron = (patron == null || patron.getIdProperty() == null || patron.getId() == 0);
		addTissuButton.setDisable(unregistredPatron);
		addFournitureButton.setDisable(unregistredPatron);
	}

	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}

	public void setPatron(PatronDto patron, MainApp mainApp, PatronService patronService,
			TissuRequisService tissuRequisService, TypeTissuService typeTissuService, TissageService tissageService,
			MatiereService matiereService) {
		this.patronService = patronService;
		this.tissuRequisService = tissuRequisService;
		this.typeTissuService = typeTissuService;
		this.tissageService = tissageService;
		this.matiereService = matiereService;
		this.tissuVariantService = new TissuVariantService();

		this.patron = patron;
		this.mainApp = mainApp;

		if (patron.getMarque() == null) {
			patron = patronMapper.map(new Patron("", "", "", "", "", null));
		}

		setDisabledButton();

		// patron.setTissusRequis(tissuRequisService.getAllTissuRequisByPatron(patron.getId()));

		referenceField.setText(patron.getReferenceProperty() == null ? "" : patron.getReference());
		marqueField.setText(patron.getMarqueProperty() == null ? "" : patron.getMarque());
		modeleField.setText(patron.getModeleProperty() == null ? "" : patron.getModele());
		typeVetementField.setText(patron.getTypeVetementProperty() == null ? "" : patron.getTypeVetement());

		loadTissuRequisForPatron();

	}

	/**
	 * Charge les tissusRequis, en fonction du patron s??lectionn??.
	 * tableau sous le patron : tissusRequis.toString() - boutons
	 */
	private void loadTissuRequisForPatron() {
		tissusPatronListGrid.getChildren().clear();
		patron.setTissusRequis(tissuRequisService.getAsObservableAllTissuRequisByPatron(patron.getId()));

		if (patron.getTissusRequisProperty() != null && patron.getTissusRequis() != null) {

			for (int i = 0; i < patron.getTissusRequis().size(); i++) {

				TissuRequisDto tissu = patron.getTissusRequis().get(i);

				Button editButton = new Button();
				FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
				editButton.setGraphic(editIcon);
				editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						displayTissuRequis(tissu);
					}
				});

				Button deleteButton = new Button();
				FontAwesomeIconView suppressIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
				suppressIcon.setFill(Constants.colorDelete);
				deleteButton.setGraphic(suppressIcon);
				deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						deleteTissuRequis(tissu);
					}
				});

				HBox hbox = new HBox(editButton, deleteButton);
				hbox.setSpacing(10);
				hbox.setAlignment(Pos.CENTER_LEFT);
				tissusPatronListGrid.add(new Label(tissu.toString()), 0, i);
				tissusPatronListGrid.add(hbox, 1, i);
			}
		}
	}

	/**
	 * D??tails d'un tissu requis, pour ??dition
	 * 
	 * @param tissu
	 */
	private void displayTissuRequis(TissuRequisDto tissu) {
		variantSelected = new TissuVariantDto();
		longueur = tissu.getLongueur();
		laize = tissu.getLaize();
		/*
		 * if (tissu == null) { tissu = new TissuRequisDto(); }
		 */
		rightContainer.getChildren().clear();
		tvList = FXCollections.observableArrayList(new ArrayList<TissuVariantDto>());
		bottomRightVbox = new VBox();

		Label titre = new Label("Tissus recommend??s : ");

		GridPane topGrid = new GridPane();
		topGrid.setVgap(10);
		topGrid.setHgap(20);

		topGrid.setPadding(new Insets(10, 0, 10, 0));
		topGrid.add(new Label("Longeur"), 0, 0);
		topGrid.add(new Label("Laize"), 0, 1);
		topGrid.add(new Label("Gamme de poids"), 0, 2);

		Spinner<Integer> longueurSpinner = new Spinner<Integer>();
		longueurSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE,
				tissu.getLongueurProperty() == null ? 0 : tissu.getLongueur()));
		longueurSpinner.setEditable(true);
		longueurSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
			longueur = newValue;
		});
		longueurSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				longueurSpinner.increment(0); // won't change value, but will commit editor
				longueur = longueurSpinner.getValue();
			}
		});

		topGrid.add(longueurSpinner, 1, 0);

		Spinner<Integer> laizeSpinner = new Spinner<Integer>();
		laizeSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE,
				tissu.getLaizeProperty() == null ? 0 : tissu.getLaize()));
		laizeSpinner.setEditable(true);
		laizeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> laize = newValue);
		laizeSpinner.focusedProperty().addListener((observable, oldValue, newValue) -> {
			if (!newValue) {
				laizeSpinner.increment(0); // won't change value, but will commit editor
				laize = laizeSpinner.getValue();
			}
		});
		topGrid.add(laizeSpinner, 1, 1);

		longueurSpinner.valueProperty().addListener((obs, oldValue, newValue) -> {
			longueur = newValue;
		});
		laizeSpinner.valueProperty().addListener((obs, oldValue, newValue) -> laize = newValue);

		ChoiceBox<String> gammePoidsChBx = new ChoiceBox<String>();
		gammePoidsChBx.setItems(FXCollections.observableArrayList(GammePoids.labels()));
		gammePoidsChBx.setValue(tissu.getGammePoidsProperty() == null || tissu.getGammePoids() == null
				|| tissu.getGammePoids().equals("") ? GammePoids.NON_RENSEIGNE.label : tissu.getGammePoids());
		topGrid.add(gammePoidsChBx, 1, 2);

		Button validateBtn = new Button("Valider");
		validateBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {

				tissu.setGammePoids(gammePoidsChBx.getValue());
				tissu.setLaize(laize);
				tissu.setLongueur(longueur);
				tissu.setGammePoids(gammePoidsChBx.getValue());
				saveTissuRequis(tissu);

			}
		});

		Button cancelBtn = new Button("Annuler");
		cancelBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				rightContainer.getChildren().clear();
			}
		});

		Button deleteBtn = new Button("Supprimer");
		deleteBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				deleteTissuRequis(tissu);
			}
		});

		HBox hboxBtn = new HBox(validateBtn, cancelBtn, deleteBtn);
		hboxBtn.setSpacing(10);
		hboxBtn.setAlignment(Pos.CENTER);
		hboxBtn.setPadding(new Insets(20, 20, 20, 20));

		rightContainer.getChildren().addAll(titre, topGrid, hboxBtn, bottomRightVbox);

		if (tissu != null) {
			tvList = FXCollections.observableArrayList(tissuVariantService.getVariantByTissuRequis(tissu));
		}

		loadBottomRightVbox(tissu);

		// if (tissuget.)

	}

	private void loadBottomRightVbox(TissuRequisDto tissu) {
		bottomRightVbox.getChildren().clear();

		bottomRightVbox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), new Label("Possibilit??s :"));

		if (tvList != null && tvList.size() > 0) {
			GridPane bottomGrid = new GridPane();
			bottomGrid.setPadding(new Insets(5, 0, 5, 0));
			bottomRightVbox.getChildren().add(bottomGrid);
			// bottomGrid.setGridLinesVisible( true );
			bottomGrid.getColumnConstraints().addAll(new ColumnConstraints(300), new ColumnConstraints(100));

			for (int i = 0; i < tvList.size(); i++) {
				TissuVariantDto tv = tvList.get(i);
				Button editButton = new Button();
				FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
				editButton.setGraphic(editIcon);
				editButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						editingVariant = true;
						DevInProgressService.notImplemented(mainApp);
					}
				});

				Button deleteButton = new Button();
				FontAwesomeIconView suppressIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
				suppressIcon.setFill(Constants.colorDelete);
				deleteButton.setGraphic(suppressIcon);
				deleteButton.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent e) {
						DevInProgressService.notImplemented(mainApp);
					}
				});
				HBox btns = new HBox(editButton, deleteButton);
				btns.setAlignment(Pos.CENTER_RIGHT);
				btns.setSpacing(10);

				bottomGrid.add(new Label(tv.getTypeTissu() + " " + tv.getMatiere() + " " + tv.getTissage()), 0, i * 2);
				bottomGrid.add(btns, 1, i * 2);

				if (i != tvList.size() - 1) {
					HBox hbox = new HBox(new Label("-------------   OU   --------------  "));
					hbox.setAlignment(Pos.CENTER);
					bottomGrid.add(hbox, 0, i * 2 + 1, 2, 1);
					// displayTissuRequis(tissu);
				}
			}
		}

		if (tissu != null && tissu.getId() != 0) {

			ChoiceBox<String> typeField = new ChoiceBox<String>();
			typeField.setItems(FXCollections.observableArrayList(
					typeTissuService.getAll().stream().map(tt -> tt.getValue()).collect(Collectors.toList())));
			typeField.setValue(variantSelected.getTypeTissuProperty() == null ? "" : variantSelected.getTypeTissu());

			ChoiceBox<String> matiereField = new ChoiceBox<String>();
			matiereField.setItems(FXCollections.observableArrayList(
					matiereService.getAll().stream().map(m -> m.getValue()).collect(Collectors.toList())));
			matiereField.setValue(variantSelected.getMatiereProperty() == null ? "" : variantSelected.getMatiere());

			ChoiceBox<String> tissageField = new ChoiceBox<String>();
			tissageField.setItems(FXCollections.observableArrayList(
					tissageService.getAll().stream().map(t -> t.getValue()).collect(Collectors.toList())));
			tissageField.setValue(variantSelected.getTissageProperty() == null ? "" : variantSelected.getTissage());

			Button addTvBtn = new Button();
			FontAwesomeIconView addIcon = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
			addIcon.setFill(Constants.colorAdd);
			addTvBtn.setGraphic(addIcon);
			addTvBtn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent e) {
					variantSelected.setMatiere(matiereField.getValue());
					variantSelected.setTissage(tissageField.getValue());
					variantSelected.setType(typeField.getValue());
					variantSelected.setTissuRequisId(tissu.getId());
					variantSelected = tissuVariantService.saveOrUpdate(variantSelected);
					if (variantSelected.getId() == 0) {
						tvList.add(variantSelected);
					}
					editingVariant = false;
					loadTissuRequisForPatron();
					displayTissuRequis(tissu);
				}
			});
			HBox hboxTissuVariant = new HBox(typeField, matiereField, tissageField, addTvBtn);
			hboxTissuVariant.setSpacing(10);
			hboxTissuVariant.setAlignment(Pos.CENTER);
			hboxTissuVariant.setPadding(new Insets(20, 20, 20, 20));
			bottomRightVbox.getChildren().addAll(new Separator(Orientation.HORIZONTAL), hboxTissuVariant);
		}
	}

	public void saveTissuRequis(TissuRequisDto tissu) {
		boolean edit = false;
		if (tissu.getId() != 0)
			edit = true;
		TissuRequisDto tissuReturned = tissuRequisService.createOrUpdate(tissu, patron);
		if (!edit) {
			patron.getTissusRequis().add(tissu);
		}
		loadTissuRequisForPatron();
		displayTissuRequis(tissuReturned);

		// DevInProgressService.notImplemented(mainApp);
	}

	private void deleteTissuRequis(TissuRequisDto tissu) {
		tissuRequisService.delete(tissu);
		loadTissuRequisForPatron();
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
	private void handleSavePatron() {
		if (isInputValid()) {

		patron.setReference(referenceField.getText());
		patron.setMarque(marqueField.getText());
		patron.setModele(modeleField.getText());
		patron.setTypeVetement(typeVetementField.getText());

		patron = patronService.create(patron);
		setDisabledButton();

		okClicked = true;
		}
	}

	@FXML
	private void handleSaveAndQuitPatron() {
		if (isInputValid()) {

		handleSavePatron();
		dialogStage.close();
		}
	}

	@FXML
	private void handleCancel() {
		dialogStage.close();
	}

	@FXML
	private void handleGenerateReference() {
		StringBuilder sb = new StringBuilder();
		String bla = marqueField.getText();
		sb.append(marqueField.getText().trim().isEmpty() ? "X" : marqueField.getText().toUpperCase().charAt(0))
				.append(modeleField.getText().trim().isEmpty() ? "X" : modeleField.getText().toUpperCase().charAt(0))
				.append(typeVetementField.getText().trim().isEmpty() ? "X"
						: typeVetementField.getText().toUpperCase().charAt(0))
				.append("-");
		boolean ref = true;
		int refNb = 0;
		while (ref) {
			refNb++;
			ref = patronService.existByReference(sb.toString() + refNb);
		}
		referenceField.setText(sb.append(refNb).toString());
	}

	@FXML
	private void handleTissuListedit() {

		displayTissuRequis(new TissuRequisDto());
	}

	@FXML
	private void handleFournitureListedit() {
		DevInProgressService.notImplemented(mainApp);

	}

	/**
	 * Validates the user input in the text fields.
	 *
	 * @return true if the input is valid
	 */
	private boolean isInputValid() {
		String errorMessage = "";
		
		if (referenceField.getText() == null || referenceField.getText().length() == 0) {
			errorMessage += "R??f??rence non renseign??e.\n";
		}
		if (marqueField.getText() == null || marqueField.getText().length() == 0) {
			errorMessage += "Marque non renseign??e.\n";
		}
		if (modeleField.getText() == null || modeleField.getText().length() == 0) {
			errorMessage += "Mod??le non renseign??.\n";
		}
		if (typeVetementField.getText() == null || typeVetementField.getText().length() == 0) {
			errorMessage += "Type non renseign??.\n";
		}

		if (errorMessage.length() == 0) {
			return true;
		} else {
			// Show the error message.
			Alert alert = new Alert(AlertType.ERROR);
			alert.initOwner(dialogStage);
			alert.setTitle("Champ(s) invalide(s)");
			alert.setHeaderText("Merci de corriger :");
			alert.setContentText(errorMessage);

			alert.showAndWait();

			return false;
		}
	}
}
