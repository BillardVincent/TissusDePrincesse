package fr.vbillard.tissusDePrincesse;

import java.io.File;
import java.io.IOException;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import fr.vbillard.tissusDePrincesse.dao.JPAHelper;
import fr.vbillard.tissusDePrincesse.dtosFx.PatronDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.services.InitDataService;
import fr.vbillard.tissusDePrincesse.services.MatiereService;
import fr.vbillard.tissusDePrincesse.services.PatronService;
import fr.vbillard.tissusDePrincesse.services.Serializer;
import fr.vbillard.tissusDePrincesse.services.TissageService;
import fr.vbillard.tissusDePrincesse.services.TissuRequisService;
import fr.vbillard.tissusDePrincesse.services.TissuService;
import fr.vbillard.tissusDePrincesse.services.TypeTissuService;
import fr.vbillard.tissusDePrincesse.view.ChargementController;
import fr.vbillard.tissusDePrincesse.view.MatiereEditController;
import fr.vbillard.tissusDePrincesse.view.PatronEditDialogController;
import fr.vbillard.tissusDePrincesse.view.RootLayoutController;
import fr.vbillard.tissusDePrincesse.view.TissageEditController;
import fr.vbillard.tissusDePrincesse.view.TissuEditDialogController;
import fr.vbillard.tissusDePrincesse.view.MainOverviewController;
import fr.vbillard.tissusDePrincesse.view.TypeEditController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private MainOverviewController tissuOverviewController;
    private ChargementController chargementController;
    private AnchorPane tissuOverview;
    private InitDataService initDataService;
    
    //------------------- test h2 -----------------
	private static final String persistenceUnit = "persistUnit";

    @Override
    public void start(Stage primaryStage) {
    	//  AquaFx.style();
        this.primaryStage = primaryStage;
        //chargement();
        
        this.primaryStage.setTitle("Les Tissus de Princesse");
        tissageService = new TissageService();

        tissuService = new TissuService();
        
        tissuRequisService = new TissuRequisService();

        patronService = new PatronService();

        typeTissuService = new TypeTissuService();
        initDataService = new InitDataService();

        matiereService = new MatiereService();
        //chargementController.setMessage("Chargement en cours : Init");

        //chargementController.setMessage("Chargement en cours : Matieres");

        matiereService.init();
        //chargementController.setMessage("Chargement en cours : Tissage");

        tissageService.init();
        //chargementController.setMessage("Chargement en cours : Types");

        typeTissuService.init();
        //initDataService.init();
        //chargementController.setMessage("Chargement en cours : Init OK !");


        this.primaryStage.getIcons().add(new Image("file:resources/images/cut-cloth-red.png"));

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
        tissuOverviewController.setMainApp(tissuService, patronService, this);
        
        //chargementStage.close();
        initRootLayout();

        rootLayout.setCenter(tissuOverview);


       // testConfigurationJpaHibernate();
        
        
        
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
            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(tissuService, patronService, tissuOverviewController, this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Try to load last opened person file.
    	/*
        File file = Serializer.getFilePath();
        if (file != null) {
            Serializer.Deserialize(file);
        }
        */
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
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            // Set the person into the controller.
            TissuEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            //System.out.println("showPersonEditDialog : " + tissu.toString());
            controller.setTissu(tissu, tissuService, typeTissuService, matiereService, tissageService, this);

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
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
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
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
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
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
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
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
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
    
	/**
	 * Returns the main stage.
	 * @return
	 */
	public Stage getPrimaryStage() {
		return primaryStage;
	}

    public static void main(String[] args) {
        launch(args);
    }
    

    
    public static void testConfigurationJpaHibernate() {
		EntityManagerFactory emf = null;
		EntityManager em = null;
		try {
			emf = Persistence.createEntityManagerFactory(persistenceUnit);
			System.out.println("CREATION EntityManagerFactory AVEC SUCCES");
			em = emf.createEntityManager();
			System.out.println("CREATION EntityManager AVEC SUCCES");
			System.out.println("BEGIN TRANSACTION");
			em.getTransaction().begin();
			System.out.println("COMMIT TRANSACTION");
			em.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
			try {
				// RollBack
				em.getTransaction().rollback();
			} catch (Exception exRollBack) {
				System.out.println("Une erreur s'est produite lors du RollBack, Exception : " + exRollBack.getMessage()
						+ " , Exception : " + exRollBack);
			}
			System.out.println("Une erreur s'est produite lors de l'execution la methode, Exception : " + e.getMessage()
					+ " , Exception : " + e);
		} finally {
			// libération ressources
			try {
				if (em != null) {
					em.close();
				}
				if (emf != null) {
					emf.close();
				}
			} catch (Exception e) {
				System.out.println("Une erreur s'est produite lors de l'execution la methode, Exception : " + e.getMessage()
						+ " , Exception : " + e);
			}
			System.out.println("FINALEMENT APRES LES CLOSES");
		}
	}

	
}
