package fr.vbillard.tissusDePrincesse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class FounitureRequise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
}
