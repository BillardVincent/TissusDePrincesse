package fr.vbillard.tissusDePrincesse.dtosFx;

import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.enums.UnitePoids;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.NoArgsConstructor;


@NoArgsConstructor
public class TissuDto {

	@Override
	public String toString() {
		String unit = unitePoids.get().equals(UnitePoids.NON_RENSEIGNE.label)?"":unitePoids.get();
		return "tissu [ longueur=" + longueur.get() + "cm, laize=" + laize.get() + "cm, description=" + description.get() +
				", type=" + type.get() +  ", matiere=" + matiere.get() + ", tissage=" + tissage.get() + 
				", poids=" + poids.get() + unit + "]";
	}

	private IntegerProperty id;
	private StringProperty  reference;
	private IntegerProperty longueur;
	private IntegerProperty longueurRestante;
	private IntegerProperty laize;
	private StringProperty  description;
	private StringProperty  matiere;
	private StringProperty  type;
	private StringProperty  unitePoids;
	private IntegerProperty poids;
	private StringProperty  lieuAchat;
	private BooleanProperty decati;
	private StringProperty  tissage;
	private BooleanProperty chute;
	
	public TissuDto(Tissu tissu){
		this.id = new SimpleIntegerProperty(tissu.getId());
		this.reference = new SimpleStringProperty(tissu.getReference());

		this.longueur = new SimpleIntegerProperty(tissu.getLongueur());
		this.laize = new SimpleIntegerProperty(tissu.getLaize());
		this.description = new SimpleStringProperty(tissu.getDescription());
		this.matiere = new SimpleStringProperty(tissu.getMatiere() == null ? "" : tissu.getMatiere().getValue());
		this.type = new SimpleStringProperty(tissu.getTypeTissu() == null ? "" : tissu.getTypeTissu().getValue());
		this.laize = new SimpleIntegerProperty(tissu.getLaize());
		this.poids = new SimpleIntegerProperty(tissu.getPoids());
		this.lieuAchat = new SimpleStringProperty(tissu.getLieuAchat());
		this.decati = new SimpleBooleanProperty(tissu.isDecati());
		this.unitePoids = new SimpleStringProperty(tissu.getUnitePoids().label);
		this.tissage = new SimpleStringProperty(tissu.getTissage() == null ? "" : tissu.getTissage().getValue() );
		this.chute = new SimpleBooleanProperty(tissu.isChute());
		this.longueurRestante = new SimpleIntegerProperty();

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
	
	public int getLongueurRestante() {
		return longueurRestante.get();
	}
	
	public IntegerProperty getLongueurRestanteProperty() {
		return longueurRestante;
	}


	public void setLongueurRestante(int longueurRestante) {
		this.longueurRestante.set(longueurRestante);
	}

	public int getLongueur() {
		return longueur.get();
	}
	public IntegerProperty getLongueurProperty() {
		return longueur;
	}

	public void setLongueur(int longueur) {
		this.longueur.set(longueur);
	}
	
	public int getLaize() {
		return laize.get();
	}
	public IntegerProperty getLaizeProperty() {
		return laize;
	}

	public void setLaize(int laize) {
		this.laize.set(laize);
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

	public String getMatiere() {
		return matiere.get();
	}
	
	public StringProperty getMatiereProperty() {
		return matiere;
	}


	public void setMatiere(String matiere) {
		this.matiere.set(matiere);
	}


	public String getType() {
		return type.get();
	}
	public StringProperty getTypeProperty() {
		return type;
	}

	public void setType(String type) {
		this.type.set(type);
	}

	public int getPoids() {
		return poids.get();
	}
	
	public IntegerProperty getPoidseProperty() {
		return poids;
	}
	
	public void setPoids(int poids) {
		this.poids.set(poids);
	}

	public void setUnitePoids(UnitePoids unitePoids) {
		this.unitePoids.set(unitePoids.label);
	}
	public void setUnitePoids(String label) {
		this.unitePoids.set(label);		
	}
	
	public String getUnitePoids() {
		return unitePoids.get();
	}
	
	public StringProperty getUnitePoidsProperty() {
		return unitePoids;
	}
	
	public StringProperty getLieuAchatProperty() {
		return lieuAchat;
	}
	
	public String getLieuAchat() {
		return lieuAchat.get();
	}


	public void setLieuAchat(String lieuAchat) {
		this.lieuAchat.set(lieuAchat);
	}

	public BooleanProperty getDecatiProperty() {
		return decati;
	}
	
	public boolean isDecati() {
		return decati.get();
	}

	public void setDecati(boolean decati) {
		this.decati.set(decati);
	}
	
	public void setChute(boolean chute) {
		this.chute.set(chute);
	}
	
	public BooleanProperty getChuteProperty() {
		return chute;
	}
	
	public boolean isChute() {
		return chute.get();
	}
	
	public StringProperty getTissageProperty() {
		return tissage;
	}
	public String getTissage() {
		return tissage.get();
	}

	public void setTissage(String tissage) {
		this.tissage.set(tissage);
	}

}