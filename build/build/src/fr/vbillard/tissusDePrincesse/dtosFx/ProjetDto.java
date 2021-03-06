package fr.vbillard.tissusDePrincesse.dtosFx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.vbillard.tissusDePrincesse.model.enums.ProjectStatus;
import fr.vbillard.tissusDePrincesse.utils.Constants;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleMapProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class ProjetDto {

	
	private IntegerProperty id;
	
	private StringProperty description;
	
	private ObjectProperty<PatronDto> patron;
	
	private MapProperty<TissuRequisDto, List<Integer>> tissuUsed;
	
	private MapProperty<FounitureRequiseDto, List<Integer>> fournitureUsed;
	
	private StringProperty status;
	
	public ProjetDto(){
		this.id = new SimpleIntegerProperty();
		this.description = new SimpleStringProperty();
		
		this.status = new SimpleStringProperty();
		this.patron = new SimpleObjectProperty<PatronDto>();
		setPatron(new PatronDto());
		
		this.tissuUsed = new SimpleMapProperty<TissuRequisDto, List<Integer>>();
		setTissuUsed(new HashMap<TissuRequisDto, List<Integer>>());
		
		this.fournitureUsed = new SimpleMapProperty<FounitureRequiseDto, List<Integer>>();
		setFournitureUsed(new HashMap<FounitureRequiseDto, List<Integer>>());
	}
	
	
	

	public int getId() {
		return id.get();
	}
	
	public IntegerProperty getIdProperty() {
		return id;
	}

	public void setId(int id) {
		this.id.set(id);
	}
	
	public String getDescription() {
		return description.get();
	}
	public StringProperty getDescriptionProperty() {
		return description;
	}

	public void setDescription(String description) {
		this.description.set(description);
	}
	
	public void setProjectStatus(ProjectStatus status) {
		if (status == null) this.status.set(Constants.NON_ENREGISTRE);
		else this.status.set(status.label);
	}
	public void setProjectStatus(String label) {
		this.status.set(label);		
	}
	
	public String getProjectStatus() {
		return status.get();
	}
	public StringProperty getProjectStatusProperty() {
		return status;
	}
	
	
	public PatronDto getPatron() {
		return patron.get();
	}
	
	public ObjectProperty<PatronDto> getPatronProperty() {
		return patron;
	}

	public void setPatron(PatronDto patron) {
		this.patron.set(patron);
	}
	
	public Map<TissuRequisDto, List<Integer>> getTissuUsed() {
		return tissuUsed.get();
	}
	
	public MapProperty<TissuRequisDto, List<Integer>> getTissuUsedProperty() {
		return tissuUsed;
	}

	public void setTissuUsed(Map<TissuRequisDto, List<Integer>> tissuUsed) {
		this.tissuUsed.set(FXCollections.observableMap(tissuUsed));
	}
	
	
	public Map<FounitureRequiseDto, List<Integer>>  getFournitureUsed() {
		return fournitureUsed.get();
	}
	
	public MapProperty<FounitureRequiseDto, List<Integer>> getFournitureUsedProperty() {
		return fournitureUsed;
	}

	public void setFournitureUsed( Map<FounitureRequiseDto, List<Integer>> fournitureUsed) {
		this.fournitureUsed.set(FXCollections.observableMap(fournitureUsed));
	}
	
	
	
}
