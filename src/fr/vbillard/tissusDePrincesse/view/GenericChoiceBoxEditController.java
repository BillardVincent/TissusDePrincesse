package fr.vbillard.tissusDePrincesse.view;

import java.lang.reflect.Method;
import java.util.List;

import fr.vbillard.tissusDePrincesse.MainApp;
import fr.vbillard.tissusDePrincesse.utils.Constants;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;

public class GenericChoiceBoxEditController implements IController{
	private Stage dialogStage;
	private String newData;
	private Class<?> enumerated;
	private MainApp mainApp;
	private ObservableList<String> choices;
	private String result;

	@FXML
	private ChoiceBox<String> choicBox;
	@FXML
	private Button valdateBtn;
	@FXML Button cancelBtn;
	
	@FXML
	private void initialize() {
	}
	
	public void setDialogStage(Stage dialogStage) {
		this.dialogStage = dialogStage;
	}
	

	public void setData(MainApp mainApp, String value, Class<?> enumerated) {
		this.mainApp = mainApp;
		this.result = value;
		this.newData = value;
		this.enumerated = enumerated;
		
		Integer myNumber = null;
		try {
			Class<?>[] parameterType = new Class[] {};
			Method myMethod = enumerated.getMethod("labels", parameterType);
			Object[] myParam = new Object[] {};
			choices = FXCollections.observableArrayList((List<String>)myMethod.invoke(null, myParam));
			if (value.equals(Constants.NON_ENREGISTRE)) {
				choices.add(Constants.NON_ENREGISTRE);
			}
			
			for (String s : choices) {
				System.out.println(s);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		choicBox.setItems(choices);
		choicBox.setValue(newData);
	}
	
	@FXML
	public void handleValidate() {
		result = choicBox.getValue();
		dialogStage.close();
	}
	@FXML
	public void handleCancel() {
		dialogStage.close();
	}
	
	public String result() {
		return result;
	}

}
