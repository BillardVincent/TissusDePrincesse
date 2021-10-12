package fr.vbillard.tissusDePrincesse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Tissage implements AbstractEntity{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String tissage;

	
	public Tissage(int id, String tissage) {
		this.id = id;
		this.tissage = tissage;
	}
	
	public Tissage(String tissage) {
		this.tissage = tissage;
	}


}
