package fr.vbillard.tissusDePrincesse.dtosFx;

import java.util.ArrayList;
import java.util.List;

import fr.vbillard.tissusDePrincesse.model.FounitureRequise;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;

public class PatronDto {

	
	private IntegerProperty id;
	private StringProperty reference;
	private StringProperty marque;
	private StringProperty modele;
	private StringProperty typeVetement;
	private StringProperty  typeTissu;
	private IntegerProperty longueurTissuTotale;
	private ListProperty<TissuRequis> tissusRequis;
	private ListProperty<FounitureRequise> fournituresRequises;
	
	
	public PatronDto() {
		this.id = new SimpleIntegerProperty();
		this.reference = new SimpleStringProperty();
		this.marque = new SimpleStringProperty();
		this.modele = new SimpleStringProperty();
		this.typeVetement = new SimpleStringProperty();
		this.typeTissu = new SimpleStringProperty();
		this.longueurTissuTotale = new SimpleIntegerProperty();
		this.tissusRequis = new SimpleListProperty<TissuRequis>();
		this.fournituresRequises = new SimpleListProperty<FounitureRequise>();
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
	public String getReference() {
		return reference.get();
	}
	public StringProperty getReferenceProperty() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference.set(reference);
	}
	
	public String getMarque() {
		return marque.get();
	}
	public StringProperty getMarqueProperty() {
		return marque;
	}

	public void setMarque(String marque) {
		this.marque.set(marque);
	}
	
	public String getModele() {
		return modele.get();
	}
	public StringProperty getModeleProperty() {
		return modele;
	}

	public void setModele(String modele) {
		this.modele.set(modele);
	}
	
	public String getTypeVetement() {
		return typeVetement.get();
	}
	public StringProperty getTypeVetementProperty() {
		return typeVetement;
	}

	public void setTypeVetement(String typeVetement) {
		this.typeVetement.set(typeVetement);
	}
	
	public String getTypeTissu() {
		return typeTissu.get();
	}
	public StringProperty getTypeTissuProperty() {
		return typeTissu;
	}

	public void setTypeTissu(String typeTissu) {
		this.typeTissu.set(typeTissu);
	}
	
	public int getLongueurTissuTotale() {
		return longueurTissuTotale.get();
	}
	
	public IntegerProperty getLongueurTissuTotaleProperty() {
		return longueurTissuTotale;
	}

	public void setLongueurTissuTotale(int longueurTissuTotale) {
		this.longueurTissuTotale.set(longueurTissuTotale);
	}
	
	public List<TissuRequis> getTissusRequis() {
		return tissusRequis.get();
	}
	public ListProperty<TissuRequis> getTissusRequisProperty() {
		return tissusRequis;
	}

	public void setTissusRequis(List<TissuRequis> tissusRequis) {
		this.tissusRequis.set(FXCollections.observableArrayList(tissusRequis));
	}
	
	public List<FounitureRequise> getFounitureRequise() {
		return fournituresRequises.get();
	}
	public ListProperty<FounitureRequise> getFounitureRequiseProperty() {
		return fournituresRequises;
	}

	public void setFounitureRequise(List<FounitureRequise> fournituresRequises) {
		this.fournituresRequises.set(FXCollections.observableArrayList(fournituresRequises));
	}
}
