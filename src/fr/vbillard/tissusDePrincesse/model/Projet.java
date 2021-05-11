package fr.vbillard.tissusDePrincesse.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusDePrincesse.model.enums.ProjectStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Projet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String description;
	
	@ManyToOne
	private Patron patron;
	
	@Enumerated(EnumType.STRING)
	private ProjectStatus status;
	
	/*
	@OneToMany
	private TissuUsed tissuUsed;
	
	private Map<FounitureRequise, List<FournitureUsed>> fournitureUsed;
	
	Projet(Patron patron){
		tissuUsed = new HashMap<TissuRequis, List<TissuUsed>>();
		for(TissuRequis tr : patron.getTissusRequis()) {
			tissuUsed.put(tr, new ArrayList());
		}
		fournitureUsed = new HashMap<FounitureRequise, List<FournitureUsed>>();
		for(FounitureRequise fr : patron.getFournituresRequises()) {
			fournitureUsed.put(fr, new ArrayList());
		}
		
	}
	*/
}
