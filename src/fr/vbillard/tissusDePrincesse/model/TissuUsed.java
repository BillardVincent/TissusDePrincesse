package fr.vbillard.tissusDePrincesse.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TissuUsed {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	protected int longueur;

	@ManyToOne
	Tissu tissu;
	@ManyToOne
	Projet projet;
	
	
}
