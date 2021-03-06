package fr.vbillard.tissusDePrincesse.model;

import javax.persistence.Entity;
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
@Entity
public class TissuVariant extends AbstractEntity{
	
	@Override
	public String toString() {
		return "matiere : "+matiere+", type : "+typeTissu+", tissage : "+tissage;
	}

	@ManyToOne
	private TissuRequis tissuRequis;
	@ManyToOne
	private TypeTissu typeTissu;
	@ManyToOne
	private Matiere matiere;
	@ManyToOne
	private Tissage tissage;
	
	

}
