package fr.vbillard.tissusDePrincesse.view;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Collectors;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusDePrincesse.MainApp;
import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.Projet;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.UnitePoids;
import fr.vbillard.tissusDePrincesse.services.DevInProgressService;
import fr.vbillard.tissusDePrincesse.services.PatronService;
import fr.vbillard.tissusDePrincesse.services.Serializer;
import fr.vbillard.tissusDePrincesse.services.TissuService;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;
import fr.vbillard.tissusDePrincesse.utils.Constants;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class MainOverviewController {

	@FXML
	private TableView<TissuDto> tissuTable;
	@FXML
	private TableColumn<TissuDto, String> referenceColonne;
	@FXML
	private TableColumn<TissuDto, String> descriptionColonne;
	@FXML
	private TableColumn<TissuDto, String> matiereColonne;

	@FXML
	private Label referenceLabel;
	@FXML
	private Label longueurLabel;
	@FXML
	private Label restantLabel;
	@FXML
	private Label laizeLabel;
	@FXML
	private Label matiereLabel;
	@FXML
	private Label descriptionLabel;
	@FXML
	private Label poidsLabel;
	@FXML
	private Label typeLabel;
	@FXML
	private Label lieuDachatLabel;
	@FXML
	private Label decatiLabel;
	@FXML
	private Label unitePoidsLabel;
	@FXML
	private Label tissageLabel;
	@FXML
	private Label chuteLabel;
	@FXML
	private Button filtreTissuButton;
	@FXML
	private Button filtreResetTissuButton;
	@FXML
	private TextField filtreTissuTxt;
	@FXML
	private Button addTissuButton;
	@FXML
	private Button editTissuButton;
	@FXML
	private Button deleteTissuButton;
	@FXML
	private Button generateCouponButton;
	@FXML
	private Button searchPatronButton;
	@FXML
	private Button addInProjectButton;
	@FXML
	private Button archiveTissuButton;

	@FXML
	private TableView<PatronDto> patronTable;
	@FXML
	private TableColumn<PatronDto, String> marqueColonne;
	@FXML
	private TableColumn<PatronDto, String> referencePatronColonne;
	@FXML
	private TableColumn<PatronDto, String> modeleColonne;

	@FXML
	private Label referencePatronLabel;
	@FXML
	private Label marquePatronLabel;
	@FXML
	private Label modelPatronLabel;
	@FXML
	private Label typeVetementPatronLabel;
	@FXML
	private Label typeTissuPatronLabel;
	@FXML
	private Label longueurTissuPatronLabel;
	@FXML
	private Label descriptionPatronLabel;

	@FXML
	private Button addPatronButton;
	@FXML
	private Button editPatronButton;
	@FXML
	private Button deletePatronButton;
	@FXML
	private Button createProjectButton;
	@FXML
	private Label descriptionProjetLabel;
	@FXML
	private Label marqueProjetLabel;
	@FXML
	private Label modelProjetLabel;
	@FXML
	private Label typeVetementProjetLabel;

	@FXML
	private ImageView robeImage;

	@FXML
	private Button saveAllBtn;

	@FXML
	private Tab testTab;

	// Reference to the main application.
	private TissuService tissuService;
	private PatronService patronService;
	private MainApp mainApp;

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public MainOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		// Initialize the person table with the two columns.

		descriptionColonne.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
		referenceColonne.setCellValueFactory(cellData -> cellData.getValue().getReferenceProperty());
		matiereColonne.setCellValueFactory(cellData -> cellData.getValue().getMatiereProperty());
		marqueColonne.setCellValueFactory(cellData -> cellData.getValue().getMarqueProperty());
		modeleColonne.setCellValueFactory(cellData -> cellData.getValue().getModeleProperty());
		referencePatronColonne.setCellValueFactory(cellData -> cellData.getValue().getReferenceProperty());

		// Clear person details.
		showTissuDetails(null);

		// Listen for selection changes and show the person details when changed.
		tissuTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showTissuDetails(newValue));
		patronTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPatronDetails(newValue));

		MaterialDesignIconView closeCircle = new MaterialDesignIconView(MaterialDesignIcon.PLUS_CIRCLE);
		closeCircle.setSize("1em");
		closeCircle.setFill(Constants.colorAdd);
		FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
		FontAwesomeIconView suppressIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
		suppressIcon.setFill(Constants.colorDelete);
		FontAwesomeIconView suppressIcon2 = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
		suppressIcon2.setFill(Constants.colorDelete);
		FontAwesomeIconView searchIcon = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
		FontAwesomeIconView searchIcon2 = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);

		FontAwesomeIconView cloneIcone = new FontAwesomeIconView(FontAwesomeIcon.CLONE);
		FontAwesomeIconView projectIcone = new FontAwesomeIconView(FontAwesomeIcon.TASKS);
		MaterialDesignIconView addProjectIcone = new MaterialDesignIconView(MaterialDesignIcon.PLAYLIST_PLUS);
		FontAwesomeIconView archiveIcone = new FontAwesomeIconView(FontAwesomeIcon.ARCHIVE);

		archiveTissuButton.setGraphic(archiveIcone);
		archiveTissuButton.setTooltip(new Tooltip("Archiver ce tissu. Il pourra être retrouvé"));
		generateCouponButton.setGraphic(cloneIcone);
		generateCouponButton.setTooltip(new Tooltip("Générer un coupon à partir de ce tissu"));
		searchPatronButton.setGraphic(new HBox(searchIcon2, projectIcone));
		archiveTissuButton.setTooltip(new Tooltip("Rechercher les patrons correspondant à ce tissu"));
		addInProjectButton.setGraphic(addProjectIcone);
		archiveTissuButton.setTooltip(new Tooltip("Ajouter le tissu sélectionné au projet"));
		addTissuButton.setGraphic(closeCircle);
		addTissuButton.setTooltip(new Tooltip("Ajouter un nouveau tissu"));
		editTissuButton.setGraphic(editIcon);
		editTissuButton.setTooltip(new Tooltip("Editer le tissu sélectionné"));
		deleteTissuButton.setGraphic(suppressIcon);
		deleteTissuButton.setTooltip(new Tooltip("Supprimer le tissu sélectionné. Cette opération est définitive"));
		filtreTissuButton.setGraphic(searchIcon);
		filtreResetTissuButton.setGraphic(suppressIcon2);

		// ---------------------TEST-----------------------------------

		VBox vbox = new VBox();
		for (int i = 0; i < 5; i++) {
			Label l = new Label("label " + i);
			l.setPadding(new Insets(5, 5, 5, 5));
			vbox.getChildren().add(l);
		}
		testTab.setContent(vbox);

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(TissuService tissuService, PatronService patronService, MainApp mainApp) {
		this.tissuService = tissuService;
		this.patronService = patronService;
		this.mainApp = mainApp;

		// Add observable list data to the table
		// new TypeTissuService().init();
		tissuTable.setItems(tissuService.getTissuData());
		patronTable.setItems(patronService.getPatronData());

	}

	private void showTissuDetails(TissuDto tissu) {
		if (tissu != null) {
			// Fill the labels with info from the person object.
			longueurLabel.setText(Integer.toString(tissu.getLongueur()));
			laizeLabel.setText(Integer.toString(tissu.getLaize()));
			matiereLabel.setText(tissu.getMatiere());
			descriptionLabel.setText(tissu.getDescription());
			poidsLabel.setText(Integer.toString(tissu.getPoids()));
			typeLabel.setText(tissu.getType());
			decatiLabel.setText(tissu.isDecati() ? "oui" : "non");
			lieuDachatLabel.setText(tissu.getLieuAchat());

			unitePoidsLabel.setText(tissu.getUnitePoids());
			chuteLabel.setText(tissu.isChute() ? "oui" : "non");
			tissageLabel.setText(tissu.getTissage());
			restantLabel.setText("N/A");

			// TODO: We need a way to convert the birthday into a String!
			// birthdayLabel.setText(...);
		} else {
			// Person is null, remove all the text.
			longueurLabel.setText("");
			laizeLabel.setText("");
			matiereLabel.setText("");
			descriptionLabel.setText("");
			poidsLabel.setText("");
			typeLabel.setText("");
			decatiLabel.setText("");
			lieuDachatLabel.setText("");
			unitePoidsLabel.setText("");
			chuteLabel.setText("");
			tissageLabel.setText("");
			restantLabel.setText("");
		}
	}

	private void showPatronDetails(PatronDto patron) {
		if (patron != null) {
			// Fill the labels with info from the person object.
			referencePatronLabel.setText(patron.getReference());

			marquePatronLabel.setText(patron.getMarque());
			modelPatronLabel.setText(patron.getModele());
			typeVetementPatronLabel.setText(patron.getTypeVetement());

			// TODO: We need a way to convert the birthday into a String!
			// birthdayLabel.setText(...);
		} else {
			// Person is null, remove all the text.
			referencePatronLabel.setText("");

			marquePatronLabel.setText("");
			modelPatronLabel.setText("");
			typeVetementPatronLabel.setText("");
			typeTissuPatronLabel.setText("");
			longueurTissuPatronLabel.setText("");

		}
	}

	@FXML
	private void saveAll() {
		Serializer.Serialize();
	}

	@FXML
	private void reloadAll() {
		tissuService.reload();
		tissuTable.setItems(tissuService.getTissuData());
	}

	@FXML
	private void handleDeleteTissu() {

		if (tissuTable.getSelectionModel() != null && tissuTable.getSelectionModel().getSelectedItem() != null
				&& tissuTable.getSelectionModel().getSelectedItem().getId() >= 0) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Suppression");
			alert.setHeaderText("Voulez vous vraiment supprimer");
			alert.setContentText("Supprimer le tissu :" + tissuTable.getSelectionModel().getSelectedItem());

			Optional<ButtonType> option = alert.showAndWait();

			if (option.get() == ButtonType.OK) {
				TissuDto selected = tissuTable.getSelectionModel().getSelectedItem();
				tissuService.delete(selected);
				tissuTable.setItems(tissuService.getTissuData());
			} else if (option.get() == ButtonType.CANCEL) {

			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("Pas de selection");
			alert.setHeaderText("Pas de tissu selectionné");
			alert.setContentText("Selectionnez un tissu dans la table");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleNewTissu() {
		TissuDto tempTissu = new TissuDto(new Tissu(0, "", 0, 0, "", null, null, 0,
				UnitePoids.NON_RENSEIGNE, false, "", null, false));

		boolean okClicked = mainApp.showTissuEditDialog(tempTissu);
		if (okClicked) {
		}
	}

	/**
	 * Called when the user clicks the edit button. Opens a dialog to edit details
	 * for the selected person.
	 */
	@FXML
	private void handleEditTissu() {
		TissuDto selectedTissu = tissuTable.getSelectionModel().getSelectedItem();
		System.out.println(selectedTissu);
		if (selectedTissu != null) {
			try {
				boolean okClicked = mainApp.showTissuEditDialog(selectedTissu);
				if (okClicked) {
					showTissuDetails(selectedTissu);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Tissu Selected");
			alert.setContentText("Please select a tissu in the table.");

			alert.showAndWait();
		}
	}

	@FXML
	private void handleAdvancedFilterTissu() {
		DevInProgressService.notImplemented(mainApp);
	}

	@FXML
	private void handleGenerateCoupon() {
		DevInProgressService.notImplemented(mainApp);
	}

	@FXML
	private void handleAddInProject() {
		DevInProgressService.notImplemented(mainApp);
	}

	@FXML
	private void handleArchiveTissu() {
		DevInProgressService.notImplemented(mainApp);
	}

	@FXML
	private void handleFilterTissu() {
		tissuTable.setItems(tissuService.filter(filtreTissuTxt.getText()));
	}

	@FXML
	private void handleFilterResetTissu() {
		filtreTissuTxt.setText("");
		tissuTable.setItems(tissuService.getTissuData());

	}

	public void refreshList() {
		tissuTable.setItems(tissuService.getTissuData());
		patronTable.setItems(patronService.getPatronData());

	}

	public void projetPanelConfig(Projet projet) {
		DevInProgressService.notImplemented(mainApp);

	}

	@FXML
	private void handleNewPatron() {
		PatronDto tempPatron = new PatronDto();

		boolean okClicked = mainApp.showPatronEditDialog(tempPatron);
		if (okClicked) {
			patronTable.setItems(patronService.getPatronData());
		}
	}

	@FXML
	private void handleEditPatron() {
		PatronDto selectedPatron = patronTable.getSelectionModel().getSelectedItem();
		System.out.println(selectedPatron);
		if (selectedPatron != null) {
			try {
				boolean okClicked = mainApp.showPatronEditDialog(selectedPatron);
				if (okClicked) {
					showPatronDetails(selectedPatron);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			// Nothing selected.
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No Tissu Selected");
			alert.setContentText("Please select a tissu in the table.");

			alert.showAndWait();
		}}

	@FXML
	private void handleDeletePatron() {
		DevInProgressService.notImplemented(mainApp);
	}

	@FXML
	private void handleNewProject() {
		DevInProgressService.notImplemented(mainApp);
	}

}
