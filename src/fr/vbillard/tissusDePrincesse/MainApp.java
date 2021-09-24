package fr.vbillard.tissusDePrincesse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.security.acl.LastOwnerException;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.filechooser.FileSystemView;

import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.model.Preference;
import fr.vbillard.tissusDePrincesse.model.enums.ImageFormat;
import fr.vbillard.tissusDePrincesse.model.images.Photo;
import fr.vbillard.tissusDePrincesse.services.MatiereService;
import fr.vbillard.tissusDePrincesse.services.PatronService;
import fr.vbillard.tissusDePrincesse.services.PreferenceService;
import fr.vbillard.tissusDePrincesse.services.ProjetService;
import fr.vbillard.tissusDePrincesse.services.TissageService;
import fr.vbillard.tissusDePrincesse.services.TissuRequisService;
import fr.vbillard.tissusDePrincesse.services.TissuService;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;
import fr.vbillard.tissusDePrincesse.view.ChargementController;
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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import jfxtras.styles.jmetro.JMetro;
import jfxtras.styles.jmetro.Style;

public class MainApp extends Application {

	private Stage primaryStage;
    private BorderPane rootLayout;
    private Stage chargementStage;
    private AnchorPane chargement;
    private TissuService tissuService;
    private PatronService patronService;
    private TypeTissuService typeTissuService;
    private MatiereService matiereService;
    private TissageService tissageService;
    private TissuRequisService tissuRequisService;
    private ProjetService projetService;
    private MainOverviewController tissuOverviewController;
    private ChargementController chargementController;
    private PreferenceService preferenceService;
    private AnchorPane tissuOverview;
    
    //------------------- test h2 -----------------
	//private static final String persistenceUnit = "persistUnit";
	private Image icon = new Image("file:resources/images/cut-cloth-red.png");

    @Override
    public void start(Stage primaryStage) {
    	//AquaFx.style();
        this.primaryStage = primaryStage;

        //primaryStage.setFullScreen(true);
        //chargement();
        
        this.primaryStage.setTitle("Les Tissus de Princesse");
        tissageService = new TissageService();

        tissuService = new TissuService();
        
        tissuRequisService = new TissuRequisService();

        patronService = new PatronService();

        typeTissuService = new TypeTissuService();

        matiereService = new MatiereService();
        projetService = new ProjetService();
        preferenceService = new PreferenceService();
        //chargementController.setMessage("Chargement en cours : Init");

        //chargementController.setMessage("Chargement en cours : Matieres");

        matiereService.init();
        //chargementController.setMessage("Chargement en cours : Tissage");

        tissageService.init();
        //chargementController.setMessage("Chargement en cours : Types");

        typeTissuService.init();
        //initDataService.init();
        //chargementController.setMessage("Chargement en cours : Init OK !");


        this.primaryStage.getIcons().add(icon);
        this.primaryStage.setMaximized(true);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("view/MainOverview.fxml"));
        try {
			tissuOverview = (AnchorPane) loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // Give the controller access to the main app.
        tissuOverviewController = loader.getController();
        tissuOverviewController.setMainApp(tissuService, patronService, projetService, this);
        
        //chargementStage.close();
        initRootLayout();

        rootLayout.setCenter(tissuOverview);
        

    }
    /*
    private void chargement() {
		// TODO Auto-generated method stub
    	try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/Chargement.fxml"));
            chargement = (AnchorPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(chargement);
            chargementStage = new Stage(StageStyle.UNIFIED);
            chargementStage.setScene(scene);

            // Give the controller access to the main app.
            chargementController = loader.getController();
            chargementController.setMainApp(this);
            chargementController.setMessage("");
            chargementStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}*/

	/**
     * Initializes the root layout.
     */
    public void initRootLayout() {
    	try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);
            
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(tissuService, patronService, tissuOverviewController, this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    

    
    public boolean showTissuEditDialog(TissuDto tissu) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TissuEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modification Tissu");
            dialogStage.getIcons().add(icon);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            TissuEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            //System.out.println("showPersonEditDialog : " + tissu.toString());
            if (tissu != null)
            controller.setTissu(tissu, tissuService, typeTissuService, matiereService, tissageService, this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean showTissuEditDialog(Map<TissuDto, Integer> mapTissu) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TissuEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Validation de fin de projet");
            dialogStage.getIcons().add(icon);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            TissuEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            //System.out.println("showPersonEditDialog : " + tissu.toString());
           
            if (mapTissu != null)
                controller.setMapTissu(mapTissu, tissuService, typeTissuService, matiereService, tissageService, this);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showPatronEditDialog(PatronDto patron) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PatronEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modification de patron");
            dialogStage.getIcons().add(icon);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PatronEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            //System.out.println("showPersonEditDialog : " + tissu.toString());
            controller.setPatron(patron, this, patronService, tissuRequisService, typeTissuService, tissageService, matiereService);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showMatiereEditDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/MatiereEdit.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Matieres");
            dialogStage.getIcons().add(icon);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            MatiereEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setData(this, matiereService);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showTypeTissuEditDialog() {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TypeEdit.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Types de tissu");
            dialogStage.getIcons().add(icon);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            TypeEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setData(this, typeTissuService);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public boolean showTissageEditDialog() {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/TissageEdit.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Tissages");
            dialogStage.getIcons().add(icon);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            TissageEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setData(this, tissageService);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public String showTextEditDialog(String value, String fieldName) {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/GenericTextEdit.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Modification : "+fieldName);
            dialogStage.getIcons().add(icon);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            GenericTextEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setData(this, value);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.result();
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }
    
    public <T extends Enum>  String showChoiceBoxEditDialog(String value, Class<T> class1) {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/GenericChoiceBoxEdit.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            
            String s = class1.getSimpleName();
           System.out.println(s);
            // Create the dialog Stage.
            Stage dialogStage = new Stage();
			Class<?>[] parameterType = new Class[] {};
            dialogStage.setTitle("Modification : "+ (String) class1.getMethod("displayClassName", parameterType).invoke(null, new Object[] {}));
            dialogStage.getIcons().add(icon);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            GenericChoiceBoxEditController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setData(this, value, class1);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.result();
        } catch (IOException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
            e.printStackTrace();
            return "Error";
        }
    }

	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }

	public int showSetLongueurDialog(int required, int  available) {
		try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/SetLongueurDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Longueur de tissu allouée");
            dialogStage.getIcons().add(icon);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            SetLongueurDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setData(this, required, available);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

            return controller.result();
        } catch (IOException e) {
            e.printStackTrace();
            return -1;
        }
	}
	
	public void showPictureExpended(Photo photo) {
        try {
            // Load the fxml file and create a new stage for the popup dialog.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/PictureExpended.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Create the dialog Stage.
            Stage dialogStage = new Stage();
            dialogStage.setTitle(photo.getNom());
            dialogStage.getIcons().add(icon);

            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            JMetro jMetro = new JMetro(Style.LIGHT);
            jMetro.setScene(scene);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            PictureExpended controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setData(this, photo);

            // Show the dialog and wait until the user closes it
            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	
	public File directoryChooser(Preference pref) {
		FileChooser fileChooser = new FileChooser();
		//String path = FileSystemView.getFileSystemView().getDefaultDirectory().getPath();
		pref = preferenceService.getPreferences();
		String path = pref.getPictureLastUploadPath();

		fileChooser.setInitialDirectory(new File(path));
		
		ImageFormat[] values = ImageFormat.values();
		String[] extensions = new String[values.length];
		for (int i = 0; i< values.length; i++) {
			extensions[i] = values[i].getExtension();
		}
		fileChooser.getExtensionFilters().add(new ExtensionFilter("Images ("+String.join(", ", extensions)+")", extensions));

		return fileChooser.showOpenDialog(primaryStage);
	}
	
	
}
