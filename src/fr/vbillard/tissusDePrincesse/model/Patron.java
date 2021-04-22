package fr.vbillard.tissusDePrincesse.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Patron {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String reference;
	private String marque;
	private String modele;
	private String typeVetement;
	@ManyToOne
	private TypeTissu typeTissu;
	
	@ManyToMany
	@JoinTable(
		    name = "patron_tissusrequis",
		            joinColumns = @JoinColumn(name = "patron_id") ,
		            inverseJoinColumns = @JoinColumn(name = "tissurequis_id"))
	private List<TissuRequis> tissusRequis;
	@Transient
	private List<FounitureRequise> fournituresRequises;
	

}
