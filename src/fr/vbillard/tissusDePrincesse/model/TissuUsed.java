package fr.vbillard.tissusDePrincesse.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusDePrincesse.dtosFx.ProjetDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuDto;
import fr.vbillard.tissusDePrincesse.dtosFx.TissuRequisDto;
import fr.vbillard.tissusDePrincesse.mappers.ProjetMapper;
import fr.vbillard.tissusDePrincesse.mappers.TissuMapper;
import fr.vbillard.tissusDePrincesse.mappers.TissuRequisMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TissuUsed extends AbstractEntity {

	protected int longueur;

	@ManyToOne
	Tissu tissu;
	@ManyToOne
	Projet projet;
	@ManyToOne
	TissuRequis tissuRequis;

	public TissuUsed(TissuRequisDto tissuRequisSelected, ProjetDto projetSelected, TissuDto tissuSelected,
			int longueur) {
		this.tissu = new TissuMapper().map(tissuSelected);
		this.projet = new ProjetMapper().map(projetSelected);
		this.tissuRequis = new TissuRequisMapper().map(tissuRequisSelected);
		this.longueur = longueur;

	}

}
