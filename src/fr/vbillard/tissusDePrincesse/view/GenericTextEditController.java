package fr.vbillard.tissusDePrincesse.view;

import fr.vbillard.tissusDePrincesse.MainApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class GenericTextEditController {
	private Stage dialogStage;
	private String oldData;
	private String result;
	private MainApp mainApp;


	@FXML
	private Label beforeChangeLabel;
	@FXML
	private TextArea textBox;
	@FXML
	private Button valdateBtn;
	@FXML Button cancelBtn;
	
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	
	@FXML
	public void handleValidate() {
		result = oldData;
		dialogStage.close();
	}
	@FXML
	public void handleCancel() {
		dialogStage.close();
	}
	
	public String result() {
		// TODO Auto-generated method stub
		return result;
	}
	public void setData(MainApp mainApp, String value) {
		if (value == null ) value = "";
		this.mainApp = mainApp;
		this.result = value;
		this.oldData = value;
		textBox.setText(oldData);
		beforeChangeLabel.setText(oldData.equals("")? "-- champ vide --" : oldData);
		

		
	}

}
