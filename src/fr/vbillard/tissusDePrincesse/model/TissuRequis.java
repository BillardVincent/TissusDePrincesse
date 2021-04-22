package fr.vbillard.tissusDePrincesse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TissuRequis {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	protected int longueur;
	protected int laize;
	protected String matiere;
	protected String tissage;

	//private Patron patron;
	
	public TissuRequis(int id, int longeur, int laize, String matiere, String tissage){
		
		this.id = id;
		this.longueur = longeur;
		this.laize =laize;
		this.matiere = matiere;
		this.tissage = tissage;
	}
	
}
