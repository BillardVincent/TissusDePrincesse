package fr.vbillard.tissusDePrincesse.view;

import fr.vbillard.tissusDePrincesse.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ChargementController {

    @FXML
    private Label message;
    
    private MainApp mainApp;

@FXML
private void initialize() {
}

public void setMainApp(MainApp mainApp){
	this.mainApp = mainApp;
}

public void setMessage(String message) {
	this.message.setText(message);
}
}