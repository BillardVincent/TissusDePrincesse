package fr.vbillard.tissusDePrincesse.view;

import java.util.Optional;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import fr.vbillard.tissusDePrincesse.MainApp;
import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.dtosFx.ProjetDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuRequisDto;
import fr.vbillard.tissusDePrincesse.fxCustomElements.TissuRequisToggleButton;
import fr.vbillard.tissusDePrincesse.model.Projet;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.TissuUsed;
import fr.vbillard.tissusDePrincesse.model.enums.ProjectStatus;
import fr.vbillard.tissusDePrincesse.model.enums.UnitePoids;
import fr.vbillard.tissusDePrincesse.services.DevInProgressService;
import fr.vbillard.tissusDePrincesse.services.PatronService;
import fr.vbillard.tissusDePrincesse.services.ProjetService;
import fr.vbillard.tissusDePrincesse.services.Serializer;
import fr.vbillard.tissusDePrincesse.services.TissuService;
import fr.vbillard.tissusDePrincesse.services.TissuUsedService;
import fr.vbillard.tissusDePrincesse.utils.Constants;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Tooltip;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class MainOverviewController {

	// ----------- FXML Tissu Liste -------------

	@FXML
	private TableView<TissuDto> tissuTable;
	@FXML
	private TableColumn<TissuDto, String> referenceColonne;
	@FXML
	private TableColumn<TissuDto, String> descriptionColonne;
	@FXML
	private TableColumn<TissuDto, String> matiereColonne;

	// ----------- FXML Tissu d??tails -------------

	@FXML
	private Label referenceLabel;
	@FXML
	private Label longueurLabel;
	@FXML
	private Label longueurRestanteLabel;

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

	// ----------- FXML Patron Liste -------------

	@FXML
	private TableView<PatronDto> patronTable;
	@FXML
	private TableColumn<PatronDto, String> marqueColonne;
	@FXML
	private TableColumn<PatronDto, String> referencePatronColonne;
	@FXML
	private TableColumn<PatronDto, String> modeleColonne;

	// ----------- FXML Patron d??tails -------------

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
	private Button filtrePatronButton;
	@FXML
	private Button filtreResetPatronButton;

	// ----------- FXML Projet Main View-------------
	@FXML
	private Pane projetPanel;
	@FXML
	private Label descriptionProjetLabel;
	@FXML
	private Label marqueProjetLabel;
	@FXML
	private Label modelProjetLabel;
	@FXML
	private Label typeVetementProjetLabel;
	@FXML
	private Label projetStatusLabel;
	@FXML
	private VBox projetTissusUsedPanel;
	@FXML
	private Button editProjetDescription;
	@FXML
	private Button editProjetStatus;
	@FXML
	private Button deselectProjetBtn;
	@FXML
	private Label warningUnregistredLabel;

	// ----------- FXML Projet Liste -------------

	@FXML
	private TableView<ProjetDto> projetTable;
	@FXML
	private TableColumn<ProjetDto, String> marqueProjetColonne;
	@FXML
	private TableColumn<ProjetDto, String> statutProjetColonne;
	@FXML
	private TableColumn<ProjetDto, String> modeleProjetColonne;

	// ------------ FXML Projet Details --------------

	@FXML
	private Label descriptionProjetPanLabel;
	@FXML
	private Label marqueProjetPanLabel;
	@FXML
	private Label modelProjePantLabel;
	@FXML
	private Label typeVetementPanProjetLabel;
	@FXML
	private Label projetStatusPanLabel;
	@FXML
	private Button selectProjetPanButton;
	@FXML
	private Button deleteProjetPanButton;
	@FXML
	private Button filtrePatronPanButton;
	@FXML
	private Button filtreResetPatronPanButton;

	// ------------ FXML others
	@FXML
	private ImageView robeImage;

	@FXML
	private Button saveAllBtn;

	// References to the main application and services.
	private TissuService tissuService;
	private PatronService patronService;
	private MainApp mainApp;
	private ProjetDto projetSelected;
	private ProjetDto projetPanSelected;
	private PatronDto patronSelected;
	private TissuDto tissuSelected;
	private TissuRequisDto tissuRequisSelected;
	private TissuUsedService tissuUsedService;
	private ProjetService projetService;
	private final ToggleGroup group = new ToggleGroup();

	/**
	 * The constructor. The constructor is called before the initialize() method.
	 */
	public MainOverviewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called after
	 * the fxml file has been loaded.
	 * 
	 * Chargement des listes (tissus, patron, ...) Initialisation des boutons
	 * (icones, tooltips)
	 * 
	 */
	@FXML
	private void initialize() {
		tissuUsedService = new TissuUsedService();
		String iconeSize = "1.5em";
		// Initialize the person table with the two columns.

		descriptionColonne.setCellValueFactory(cellData -> cellData.getValue().getDescriptionProperty());
		referenceColonne.setCellValueFactory(cellData -> cellData.getValue().getReferenceProperty());
		matiereColonne.setCellValueFactory(cellData -> cellData.getValue().getMatiereProperty());
		marqueColonne.setCellValueFactory(cellData -> cellData.getValue().getMarqueProperty());
		modeleColonne.setCellValueFactory(cellData -> cellData.getValue().getModeleProperty());
		referencePatronColonne.setCellValueFactory(cellData -> cellData.getValue().getReferenceProperty());
		marqueProjetColonne.setCellValueFactory(cellData -> cellData.getValue().getPatron().getMarqueProperty());
		statutProjetColonne.setCellValueFactory(cellData -> cellData.getValue().getProjectStatusProperty());
		modeleProjetColonne.setCellValueFactory(cellData -> cellData.getValue().getPatron().getModeleProperty());

		// Clear tissu details.
		showTissuDetails(null);
		showPatronDetails(null);
		showProjetDetails(null);
		showProjetPanDetails(null);

		// Listen for selection changes and show the person details when changed.
		tissuTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showTissuDetails(newValue));
		patronTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showPatronDetails(newValue));
		projetTable.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> showProjetPanDetails(newValue));

		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
			public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {
				if (new_toggle != null) {

					tissuRequisSelected = (TissuRequisDto) group.getSelectedToggle().getUserData();
				} else {
					System.out.println("deselect");
					tissuRequisSelected = null;
				}
			}
		});

		MaterialDesignIconView plusCircle = new MaterialDesignIconView(MaterialDesignIcon.PLUS_CIRCLE);
		plusCircle.setSize(iconeSize);
		plusCircle.setFill(Constants.colorAdd);
		MaterialDesignIconView plusCircle2 = new MaterialDesignIconView(MaterialDesignIcon.PLUS_CIRCLE);
		plusCircle2.setFill(Constants.colorAdd);
		plusCircle2.setSize(iconeSize);
		FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
		editIcon.setSize(iconeSize);
		FontAwesomeIconView editIcon2 = new FontAwesomeIconView(FontAwesomeIcon.EDIT);
		editIcon2.setSize(iconeSize);
		FontAwesomeIconView suppressIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
		suppressIcon.setFill(Constants.colorDelete);
		suppressIcon.setSize(iconeSize);
		FontAwesomeIconView suppressIcon2 = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
		suppressIcon2.setFill(Constants.colorDelete);
		suppressIcon2.setSize("1em");
		FontAwesomeIconView suppressIcon3 = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
		suppressIcon3.setFill(Constants.colorDelete);
		suppressIcon3.setSize(iconeSize);
		FontAwesomeIconView suppressIcon4 = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
		suppressIcon4.setFill(Constants.colorDelete);
		suppressIcon4.setSize("1em");
		FontAwesomeIconView suppressIcon5 = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
		suppressIcon5.setFill(Constants.colorDelete);
		suppressIcon5.setSize(iconeSize);
		FontAwesomeIconView suppressIcon6 = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
		suppressIcon6.setFill(Constants.colorDelete);
		suppressIcon6.setSize("1em");
		FontAwesomeIconView suppressIcon7 = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
		suppressIcon7.setFill(Constants.colorDelete);
		suppressIcon7.setSize(iconeSize);
		FontAwesomeIconView searchIcon = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
		searchIcon.setSize("1em");
		FontAwesomeIconView searchIcon2 = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
		searchIcon2.setSize(iconeSize);
		FontAwesomeIconView searchIcon3 = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
		searchIcon3.setSize("1em");
		FontAwesomeIconView searchIcon4 = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
		searchIcon4.setSize("1em");
		FontAwesomeIconView cloneIcone = new FontAwesomeIconView(FontAwesomeIcon.CLONE);
		cloneIcone.setSize(iconeSize);
		FontAwesomeIconView projectIcone = new FontAwesomeIconView(FontAwesomeIcon.TASKS);
		projectIcone.setSize(iconeSize);
		FontAwesomeIconView addProjectIcone = new FontAwesomeIconView(FontAwesomeIcon.PLAY_CIRCLE);
		addProjectIcone.setSize(iconeSize);
		addProjectIcone.setFill(Constants.colorAccent);
		addProjectIcone.setDisable(true);
		FontAwesomeIconView selectProjectIcone = new FontAwesomeIconView(FontAwesomeIcon.PLAY_CIRCLE);
		selectProjectIcone.setSize(iconeSize);
		selectProjectIcone.setFill(Constants.colorAccent);
		selectProjectIcone.setDisable(true);
		FontAwesomeIconView createProjectIcone = new FontAwesomeIconView(FontAwesomeIcon.PLAY_CIRCLE);
		createProjectIcone.setSize(iconeSize);
		createProjectIcone.setFill(Constants.colorAccent);
		FontAwesomeIconView archiveIcone = new FontAwesomeIconView(FontAwesomeIcon.ARCHIVE);
		archiveIcone.setSize(iconeSize);

		FontAwesomeIconView warningIcone = new FontAwesomeIconView(FontAwesomeIcon.EXCLAMATION_TRIANGLE);
		warningIcone.setSize("2em");
		warningIcone.setFill(Constants.colorWarning);

		selectProjetPanButton.setGraphic(selectProjectIcone);
		deleteProjetPanButton.setGraphic(suppressIcon7);
		filtrePatronPanButton.setGraphic(searchIcon4);
		filtreResetPatronPanButton.setGraphic(suppressIcon6);
		;

		addPatronButton.setGraphic(plusCircle2);
		editPatronButton.setGraphic(editIcon2);
		deletePatronButton.setGraphic(suppressIcon3);
		createProjectButton.setGraphic(createProjectIcone);
		filtrePatronButton.setGraphic(searchIcon3);
		filtreResetPatronButton.setGraphic(suppressIcon4);

		archiveTissuButton.setGraphic(archiveIcone);
		archiveTissuButton.setTooltip(new Tooltip("Archiver ce tissu. Il pourra ??tre retrouv??"));
		generateCouponButton.setGraphic(cloneIcone);
		generateCouponButton.setTooltip(new Tooltip("G??n??rer un coupon ?? partir de ce tissu"));
		searchPatronButton.setGraphic(new HBox(searchIcon2, projectIcone));
		searchPatronButton.setTooltip(new Tooltip("Rechercher les patrons correspondant ?? ce tissu"));
		addInProjectButton.setGraphic(addProjectIcone);
		addInProjectButton.setTooltip(new Tooltip("Ajouter le tissu s??lectionn?? au projet"));
		addTissuButton.setGraphic(plusCircle);
		addTissuButton.setTooltip(new Tooltip("Ajouter un nouveau tissu"));
		editTissuButton.setGraphic(editIcon);
		editTissuButton.setTooltip(new Tooltip("Editer le tissu s??lectionn??"));
		deleteTissuButton.setGraphic(suppressIcon);
		deleteTissuButton.setTooltip(new Tooltip("Supprimer le tissu s??lectionn??. Cette op??ration est d??finitive"));
		filtreTissuButton.setGraphic(searchIcon);
		filtreResetTissuButton.setGraphic(suppressIcon2);
		editProjetDescription.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));
		editProjetStatus.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.EDIT));
		deselectProjetBtn.setGraphic(suppressIcon5);
		warningUnregistredLabel.setGraphic(warningIcone);
		setButtons();

	}

	private void showProjetPanDetails(ProjetDto projetDto) {
		if (projetDto != null) {
			projetPanSelected = projetDto;
			descriptionProjetPanLabel.setText(projetDto.getDescription());
			marqueProjetPanLabel.setText(projetDto.getPatron().getMarque());
			modelProjePantLabel.setText(projetDto.getPatron().getModele());
			typeVetementPanProjetLabel.setText(projetDto.getPatron().getTypeVetement());
			projetStatusPanLabel.setText(projetDto.getProjectStatus());

		} else {
			descriptionProjetPanLabel.setText("");
			marqueProjetPanLabel.setText("");
			modelProjePantLabel.setText("");
			typeVetementPanProjetLabel.setText("");
			projetStatusPanLabel.setText("");
		}
		setButtons();

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(TissuService tissuService, PatronService patronService, ProjetService projetService,
			MainApp mainApp) {
		this.tissuService = tissuService;
		this.patronService = patronService;
		this.projetService = projetService;
		this.mainApp = mainApp;
		tissuTable.setItems(tissuService.getTissuData());
		patronTable.setItems(patronService.getPatronData());
		projetTable.setItems(projetService.getProjetData());
		setButtons();

	}

	private void showTissuDetails(TissuDto tissu) {
		if (tissu != null) {
			tissuSelected = tissu;
			referenceLabel.setText(tissu.getReference());
			longueurLabel.setText(Integer.toString(tissu.getLongueur()));
			longueurRestanteLabel.setText(Integer.toString(tissu.getLongueurRestante()));
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

		} else {
			referenceLabel.setText("");
			longueurLabel.setText("");
			longueurRestanteLabel.setText("");
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
		}
		setButtons();

	}

	private void showPatronDetails(PatronDto patron) {
		patronSelected = patron;
		if (patron != null) {
			referencePatronLabel.setText(patron.getReference());
			marquePatronLabel.setText(patron.getMarque());
			modelPatronLabel.setText(patron.getModele());
			typeVetementPatronLabel.setText(patron.getTypeVetement());

		} else {
			referencePatronLabel.setText("");

			marquePatronLabel.setText("");
			modelPatronLabel.setText("");
			typeVetementPatronLabel.setText("");
			typeTissuPatronLabel.setText("");
			longueurTissuPatronLabel.setText("");
		}
		setButtons();

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
			alert.setHeaderText("Pas de tissu selectionn??");
			alert.setContentText("Selectionnez un tissu dans la table");

			alert.showAndWait();
		}
		setButtons();

	}

	@FXML
	private void handleNewTissu() {
		TissuDto tempTissu = new TissuDto(
				new Tissu(0, "", 0, 0, "", null, null, 0, UnitePoids.NON_RENSEIGNE, false, "", null, false));
		boolean okClicked = mainApp.showTissuEditDialog(tempTissu);
		if (okClicked) {
		}
		setButtons();

	}

	@FXML
	private void handleEditTissu() {
		tissuSelected = tissuTable.getSelectionModel().getSelectedItem();
		System.out.println(tissuSelected);
		if (tissuSelected != null) {
			try {
				boolean okClicked = mainApp.showTissuEditDialog(tissuSelected);
				if (okClicked) {
					showTissuDetails(tissuSelected);
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
		setButtons();

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
		int longueurRequiseRestante = tissuRequisSelected.getLongueur();

			for (int id : projetSelected.getTissuUsed().get(tissuRequisSelected)) {
				longueurRequiseRestante -= tissuUsedService.getTissuUsedById(id).getLongueur();
			}

		
		int longueur = mainApp.showSetLongueurDialog(longueurRequiseRestante, tissuSelected.getLongueur());

		tissuUsedService.saveOrUpdate(new TissuUsed(tissuRequisSelected, projetSelected, tissuSelected, longueur));
	}

	@FXML
	private void handleArchiveTissu() {
		tissuService.archive(tissuSelected);
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
		setButtons();

	}

	@FXML
	private void handleEditPatron() {
		patronSelected = patronTable.getSelectionModel().getSelectedItem();
		if (patronSelected != null) {
			try {
				boolean okClicked = mainApp.showPatronEditDialog(patronSelected);
				if (okClicked) {
					showPatronDetails(patronSelected);
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
		setButtons();
	}

	@FXML
	private void handleDeletePatron() {
		DevInProgressService.notImplemented(mainApp);
	}

	@FXML
	private void handleNewProject() {
		projetPanel.setVisible(true);
		warningUnregistredLabel.setVisible(true);
		if (patronSelected == null) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.initOwner(mainApp.getPrimaryStage());
			alert.setTitle("No Selection");
			alert.setHeaderText("No patron Selected");
			alert.setContentText("Please select a patron in the table.");
			alert.showAndWait();
		} else {
			showProjetDetails(projetService.newProjetDto(patronSelected));
		}
		setButtons();
	}

	private void showProjetDetails(ProjetDto projet) {
		group.getToggles().clear();
		projetSelected = projet;
		projetTissusUsedPanel.getChildren().clear();

		if (projet != null) {
			if (projet.getProjectStatus() == null)
				projet.setProjectStatus(Constants.NON_ENREGISTRE);

			projetPanel.setVisible(true);
			descriptionProjetLabel.setText(projet.getDescription());
			projetStatusLabel.setText(projet.getProjectStatus());

			if (projet.getPatron() != null) {
				marqueProjetLabel.setText(projet.getPatron().getMarque() == null ? "" : projet.getPatron().getMarque());
				modelProjetLabel.setText(projet.getPatron().getModele() == null ? "" : projet.getPatron().getModele());
				typeVetementProjetLabel.setText(
						projet.getPatron().getTypeVetement() == null ? "" : projet.getPatron().getTypeVetement());
			} else {
				marqueProjetLabel.setText("");
				modelProjetLabel.setText("");
				typeVetementProjetLabel.setText("");
			}

			for (TissuRequisDto tr : projet.getTissuUsed().keySet()) {

				Label tissuRequisLbl = new Label(
						"tissu " + tr.getGammePoids() + " - " + tr.getLongueur() + "cm x " + tr.getLaize() + "cm");
				tissuRequisLbl.setStyle("-fx-font-size: 16");
				VBox vb = new VBox(tissuRequisLbl);
				vb.setPadding(new Insets(5, 5, 5, 5));
				vb.setBorder(new Border(new BorderStroke(Color.GREY, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
						BorderWidths.DEFAULT)));

				if (tr.getVariant() != null) {

					TitledPane tp = new TitledPane();
					// tp.setText("tissu " + tr.getGammePoids() + " - " + tr.getLongueur() + "cm x "
					// + tr.getLaize() + "cm");

					VBox content = new VBox();

					if (tr.getVariant().size() == 1)
						tp.setText("Tissu conseil?? ");
					else if (tr.getVariant().size() > 1) {
						tp.setText("Tissus conseil??s :");
					}
					tp.setContent(content);

					for (int i = 0; i < tr.getVariant().size(); i++) {
						String variant = tr.getVariant().get(i);
						content.getChildren().add(new Label(i + 1 + "- " + variant));
					}
					tp.setExpanded(false);
					VBox vbox = new VBox();

					int longueurInitiale = tr.getLongueur();
					int longueurFinale = 0;
					for (Integer id : projet.getTissuUsed().get(tr)) {
						TissuUsed tissuUsed = tissuUsedService.getTissuUsedById(id);
						vbox.getChildren().addAll(new Label(tissuUsed.getTissu().getDescription()),
								new Label(tissuUsed.getLongueur() + " cm"));
						longueurFinale += tissuUsed.getLongueur();
					}
					Text lbl = new Text(longueurFinale + "cm /" + longueurInitiale + "cm");
					lbl.setFill(longueurFinale < longueurInitiale ? Constants.colorDelete : Constants.colorAdd);
					;

					vbox.getChildren().add(lbl);

					FontAwesomeIconView plusOne = new FontAwesomeIconView(FontAwesomeIcon.PLUS_CIRCLE);
					plusOne.setFill(Constants.colorAdd);
					TissuRequisToggleButton rb1 = new TissuRequisToggleButton(tr);
					rb1.setDisable(projetSelected.getId() != 0);
					rb1.setToggleGroup(group);
					rb1.setGraphic(plusOne);
					rb1.setUserData(tr);
					
					Button filter = new Button();
					filter.setOnAction(new EventHandler<ActionEvent>() {
					    @Override public void handle(ActionEvent e) {
					        filterTissusByTissuRequis(tr);
					    }
					});
					filter.setGraphic( new FontAwesomeIconView(FontAwesomeIcon.SEARCH));

					HBox hb = new HBox(rb1, filter);
					vb.getChildren().addAll(tp, vbox, hb);

				}
				projetTissusUsedPanel.getChildren().add(vb);
				projetTissusUsedPanel.setSpacing(10);
			}
			// projetTissusUsedPanel.getChildren().add(new Label("EN COURS !!!!!"));

		} else {
			descriptionProjetLabel.setText("");
			marqueProjetLabel.setText("");
			modelProjetLabel.setText("");
			typeVetementProjetLabel.setText("");
			projetStatusLabel.setText("");
			projetPanel.setVisible(false);
		}
		setButtons();
	}
	
	private void filterTissusByTissuRequis(TissuRequisDto tr) {
		DevInProgressService.notImplemented(mainApp);
	}

	@FXML
	private void handleEditProjectDescription() {
		String newData = mainApp.showTextEditDialog(descriptionProjetLabel.getText(), "description");

		projetSelected.setDescription(newData);
		if (!projetStatusLabel.getText().equals(Constants.NON_ENREGISTRE))
			projetService.create(projetSelected);
		descriptionProjetLabel.setText(newData);
		setButtons();
	}

	@FXML
	private void handleEditProjectStatus() {
		String newData = mainApp.showChoiceBoxEditDialog(projetStatusLabel.getText(), ProjectStatus.class);
		projetSelected.setProjectStatus(newData);
		if (!newData.equals(Constants.NON_ENREGISTRE)) {
			projetSelected = projetService.create(projetSelected);
			warningUnregistredLabel.setVisible(false);
		}

		if (newData.equals(ProjectStatus.EN_COURS.label) || newData.equals(ProjectStatus.TERMINE)) {
			if (!istissuUsedValid()) {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.initOwner(mainApp.getPrimaryStage());
				alert.setTitle("Tissus requis");
				alert.setHeaderText("Du tissu semble manquer");
				alert.setContentText(
						"Il manque du tissu pour r??aliser ce mod??le! Vous pouvez continuer tout de m??me, ou annuler");
				Optional<ButtonType> option = alert.showAndWait();

				if (option.get() != null && option.get() == ButtonType.OK) {
					projetStatusLabel.setText(newData);
					setButtons();
				}
			}
		}
	}

	@FXML
	private void handleDeselectProjet() {
		showProjetDetails(null);

	}

	@FXML
	private void handleSelectProjet() {
		showProjetDetails(projetPanSelected);
	}

	@FXML
	private void handleDeleteProjet() {
		DevInProgressService.notImplemented(mainApp);

	}

	private void setButtons() {
		addTissuButton.setDisable(false);
		editTissuButton.setDisable(tissuSelected == null);
		deleteTissuButton.setDisable(tissuSelected == null);
		generateCouponButton.setDisable(tissuSelected == null);
		searchPatronButton.setDisable(tissuSelected == null);
		addInProjectButton.setDisable(tissuSelected == null || projetSelected == null
				|| projetSelected.getIdProperty() == null || projetSelected.getId() == 0);
		archiveTissuButton.setDisable(tissuSelected == null);
		addPatronButton.setDisable(false);
		editPatronButton.setDisable(patronSelected == null);
		deletePatronButton.setDisable(patronSelected == null);
		createProjectButton.setDisable(patronSelected == null || projetSelected != null);
		filtrePatronButton.setDisable(false);
		filtreResetPatronButton.setDisable(false);
		editProjetDescription.setDisable(false);
		editProjetStatus.setDisable(false);
		selectProjetPanButton.setDisable(projetPanSelected == null);
		deleteProjetPanButton.setDisable(projetPanSelected == null);

		group.getToggles().forEach(toggle -> {
			Node node = (Node) toggle;
			node.setDisable(projetSelected == null || projetSelected.getId() == 0);
		});
	}

	private boolean istissuUsedValid() {

		for (TissuRequisDto tr : projetSelected.getTissuUsed().keySet()) {
			int longueur = tr.getLongueur();
			for (int id : projetSelected.getTissuUsed().get(tr)) {
				longueur -= tissuUsedService.getTissuUsedById(id).getLongueur();
			}
			if (longueur > 0)
				return false;
		}
		return true;
	}

}
