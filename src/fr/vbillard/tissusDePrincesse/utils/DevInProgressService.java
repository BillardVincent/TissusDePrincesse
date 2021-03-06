package fr.vbillard.tissusDePrincesse.utils;

import fr.vbillard.tissusDePrincesse.MainApp;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

public class DevInProgressService {

	
	public static void notImplemented(MainApp mainApp) {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.initOwner(mainApp.getPrimaryStage());
        alert.setTitle("NON DISPONIBLE");
        alert.setHeaderText("Fonction non implémentée");
        alert.setContentText("Nous travaillons sur cette fonctionnalité. Elle sera bientot disponible !");

        alert.showAndWait();
	}
}
