package fr.vbillard.tissusDePrincesse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.exception.AbstractTissuDePricesseException;
import fr.vbillard.tissusDePrincesse.model.Photo;
import fr.vbillard.tissusDePrincesse.model.Preference;
import fr.vbillard.tissusDePrincesse.model.enums.ImageFormat;
import fr.vbillard.tissusDePrincesse.services.MatiereService;
import fr.vbillard.tissusDePrincesse.services.PatronService;
import fr.vbillard.tissusDePrincesse.services.PreferenceService;
import fr.vbillard.tissusDePrincesse.services.ProjetService;
import fr.vbillard.tissusDePrincesse.services.TissageService;
import fr.vbillard.tissusDePrincesse.services.TissuRequisService;
import fr.vbillard.tissusDePrincesse.services.TissuService;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;
import fr.vbillard.tissusDePrincesse.view.ChargementController;
import fr.vbillard.tissusDePrincesse.view.FxmlPath;
import fr.vbillard.tissusDePrincesse.view.GenericChoiceBoxEditController;
import fr.vbillard.tissusDePrincesse.view.GenericTextEditController;
import fr.vbillard.tissusDePrincesse.view.MainOverviewController;
import fr.vbillard.tissusDePrincesse.view.MatiereEditController;
import fr.vbillard.tissusDePrincesse.view.PatronEditDialogController;
import fr.vbillard.tissusDePrincesse.view.PictureExpended;
import fr.vbillard.tissusDePrincesse.view.RootLayoutController;
import fr.vbillard.tissusDePrincesse.view.SetLongueurDialogController;
import fr.vbillard.tissusDePrincesse.view.TissageEditController;
import fr.vbillard.tissusDePrincesse.view.TissuEditDialogController;
import fr.vbillard.tissusDePrincesse.view.TypeEditController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;
import lombok.AllArgsConstructor;

public class MainApp extends Application {

	private static Stage primaryStage;
	private BorderPane rootLayout;
	private TissuService tissuService;
	private PatronService patronService;
	private TypeTissuService typeTissuService;
	private MatiereService matiereService;
	private TissageService tissageService;
	private TissuRequisService tissuRequisService;
	private ProjetService projetService;
	private MainOverviewController tissuOverviewController;
	private PreferenceService preferenceService;
	private AnchorPane tissuOverview;

	private Image icon = new Image("file:resources/images/cut-cloth-red.png");
	JMetro jMetro;

	@Override
	public void start(Stage primaryStage) {
		Thread.setDefaultUncaughtExceptionHandler(MainApp::showError);

		MainApp.primaryStage = primaryStage;

		MainApp.primaryStage.setTitle("Les Tissus de Princesse");
		tissageService = new TissageService();
		tissuService = new TissuService();
		tissuRequisService = new TissuRequisService();
		patronService = new PatronService();
		typeTissuService = new TypeTissuService();
		matiereService = new MatiereService();
		projetService = new ProjetService();
		preferenceService = new PreferenceService();
		jMetro = new JMetro(Style.LIGHT);
		MainApp.primaryStage.getIcons().add(icon);
		MainApp.primaryStage.setMaximized(true);

		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource(FxmlPath.MAIN_OVERVIEW));
		try {
			tissuOverview = (AnchorPane) loader.load();
		} catch (IOException e) {
			e.printStackTrace();
		}

		tissuOverviewController = loader.getController();
		tissuOverviewController.setMainApp(tissuService, patronService, projetService, this);

		initRootLayout();

		rootLayout.setCenter(tissuOverview);

	}

	public Stage getPrimaryStage() {
		return primaryStage;
	}

	public static void main(String[] args) {
		launch(args);
	}
	
	/**
	 * Initializes the root layout.
	 */
	public void initRootLayout() {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource(FxmlPath.ROOT));
			rootLayout = (BorderPane) loader.load();

			Scene scene = new Scene(rootLayout);

			jMetro.setScene(scene);

			primaryStage.setScene(scene);

			RootLayoutController controller = loader.getController();
			controller.setMainApp(tissuService, patronService, tissuOverviewController, this);

			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean showTissuEditDialog(TissuDto tissu) {
		try {
			ControlerHolder holder = setStageAndLoader(FxmlPath.TISSU_EDIT_DIALOG, "Modification Tissu");
			
			TissuEditDialogController controller = holder.loader.getController();
			controller.setDialogStage(holder.dialogStage);
			if (tissu != null)
				controller.setTissu(tissu, tissuService, typeTissuService, matiereService, tissageService, this);
			
			holder.dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showTissuEditDialog(Map<TissuDto, Integer> mapTissu) {
		try {
			ControlerHolder holder = setStageAndLoader(FxmlPath.TISSU_EDIT_DIALOG, "Validation de fin de projet");
			
			TissuEditDialogController controller = holder.loader.getController();
			controller.setDialogStage(holder.dialogStage);
			if (mapTissu != null)
				controller.setMapTissu(mapTissu, tissuService, typeTissuService, matiereService, tissageService, this);

			holder.dialogStage.showAndWait();
		
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showPatronEditDialog(PatronDto patron) {
		try {
			ControlerHolder holder = setStageAndLoader(FxmlPath.PATRON_EDIT_DIALOG, "Modification de patron");
			
			PatronEditDialogController controller = holder.loader.getController();
			controller.setDialogStage(holder.dialogStage);
			controller.setPatron(patron, this, patronService, tissuRequisService, typeTissuService, tissageService,
					matiereService);
			
			holder.dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showMatiereEditDialog() {
		try {
			ControlerHolder holder = setStageAndLoader(FxmlPath.MATIERE_EDIT, "Matieres");
			
			MatiereEditController controller = holder.loader.getController();
			controller.setDialogStage(holder.dialogStage);
			controller.setData(this, matiereService);

			
			holder.dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showTypeTissuEditDialog() {
		try {
			ControlerHolder holder = setStageAndLoader(FxmlPath.TYPE_EDIT, "Types de tissu");
			
			TypeEditController controller = holder.loader.getController();
			controller.setDialogStage(holder.dialogStage);
			controller.setData(this, typeTissuService);

			holder.dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean showTissageEditDialog() {
		try {
			ControlerHolder holder = setStageAndLoader(FxmlPath.TISSAGE_EDIT, "Tissages");
			
			TissageEditController controller = holder.loader.getController();
			controller.setDialogStage(holder.dialogStage);
			controller.setData(this, tissageService);
			
			holder.dialogStage.showAndWait();
			
			return controller.isOkClicked();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String showTextEditDialog(String value, String fieldName) {
		try {
			ControlerHolder holder = setStageAndLoader(FxmlPath.GENERIC_TEXT_EDIT, "Modification");
			
			GenericTextEditController controller = holder.loader.getController();
			controller.setDialogStage(holder.dialogStage);
			controller.setData(this, value);
			
			holder.dialogStage.showAndWait();

			return controller.result();
		} catch (IOException e) {
			e.printStackTrace();
			return "Error";
		}
	}

	public <T extends Enum> String showChoiceBoxEditDialog(String value, Class<T> class1) {
		try {
			Class<?>[] parameterType = new Class[] {};
			String title = "Modification : "
					+ (String) class1.getMethod("displayClassName", parameterType).invoke(null, new Object[] {});
			ControlerHolder holder = setStageAndLoader(FxmlPath.GENERIC_CHOICE_BOX_EDIT, title);
			
			GenericChoiceBoxEditController controller = holder.loader.getController();
			controller.setDialogStage(holder.dialogStage);
			controller.setData(this, value, class1);

			holder.dialogStage.showAndWait();
			
			return controller.result();
		} catch (IOException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return "Error";
		}
	}



	public int showSetLongueurDialog(int required, TissuDto available) {
		try {
			ControlerHolder holder = setStageAndLoader(FxmlPath.SET_LONGUEUR, "Longueur de tissu ?? allouer");
			
			SetLongueurDialogController controller = holder.loader.getController();
			controller.setDialogStage(holder.dialogStage);
			controller.setData(this, required, available.getLongueur());
			
			holder.dialogStage.showAndWait();

			return controller.result();
		} catch (IOException e) {
			e.printStackTrace();
			return -1;
		}
	}

	public void showPictureExpended(Photo photo) {
		try {
			ControlerHolder holder = setStageAndLoader(FxmlPath.PICTURE_EXPENDED, photo.getNom());
		
			PictureExpended controller = holder.loader.getController();
			controller.setDialogStage(holder.dialogStage);
			controller.setData(this, photo);
			
			holder.dialogStage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public File directoryChooser(Preference pref) {
		FileChooser fileChooser = new FileChooser();
		pref = preferenceService.getPreferences();
		String path = pref.getPictureLastUploadPath();

		fileChooser.setInitialDirectory(new File(path));

		ImageFormat[] values = ImageFormat.values();
		String[] extensions = new String[values.length];
		for (int i = 0; i < values.length; i++) {
			extensions[i] = values[i].getExtension();
		}
		fileChooser.getExtensionFilters()
				.add(new ExtensionFilter("Images (" + String.join(", ", extensions) + ")", extensions));

		return fileChooser.showOpenDialog(primaryStage);
	}

	private static void showError(Thread t, Throwable e) {
		System.err.println("***Default exception handler***");
		if (Platform.isFxApplicationThread()) {
			if (e instanceof AbstractTissuDePricesseException) {
				Alert alert = new Alert(((AbstractTissuDePricesseException) e).getAlertType());
				alert.initOwner(primaryStage);
				alert.setTitle(((AbstractTissuDePricesseException) e).getTitle());
				alert.setHeaderText(((AbstractTissuDePricesseException) e).getHeader());
				alert.setContentText(((AbstractTissuDePricesseException) e).getContent());
				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.initOwner(primaryStage);
				alert.setTitle("Erreur inatendue");
				alert.setHeaderText("Une erreur est survenue");
				alert.setContentText("Veuillez nous excuser de la g??ne occasionn??e. Contactez nous si le probl??me se r??p??te");
				alert.showAndWait();
			}

		} else {
			System.err.println("An unexpected error occurred in " + t);
			e.printStackTrace();
		}
	}
	
	private ControlerHolder setStageAndLoader(String path, String title) throws IOException {
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainApp.class.getResource(path));
		AnchorPane page = (AnchorPane) loader.load();

		Stage dialogStage = new Stage();
		dialogStage.getIcons().add(icon);
		dialogStage.setTitle(title);
		
		dialogStage.initModality(Modality.WINDOW_MODAL);
		dialogStage.initOwner(primaryStage);
		Scene scene = new Scene(page);
		jMetro.setScene(scene);
		dialogStage.setScene(scene);
		
		return new ControlerHolder(dialogStage, loader);
	}
	
	@AllArgsConstructor
	private class ControlerHolder{
		Stage dialogStage;
		FXMLLoader loader;
	}

}
