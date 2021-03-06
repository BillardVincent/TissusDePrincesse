package fr.vbillard.tissusDePrincesse.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusDePrincesse.model.enums.GammePoids;
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
	@ManyToOne
	private Patron patron;
	private int longueur;
	private int laize;
	@Enumerated(EnumType.STRING)
	private GammePoids gammePoids;

	
	

	//private Patron patron;
	
	
	
}
