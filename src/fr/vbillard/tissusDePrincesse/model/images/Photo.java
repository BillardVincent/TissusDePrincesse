package fr.vbillard.tissusDePrincesse.model.images;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import fr.vbillard.tissusDePrincesse.model.AbstractEntity;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.Tissu;
import fr.vbillard.tissusDePrincesse.model.enums.ImageFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Photo implements AbstractEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String nom;
	
	@Enumerated(EnumType.STRING)
	private ImageFormat format;
	
	@Lob
	private byte[] data;
	
	@ManyToOne
	private Tissu tissu;
	
	@ManyToOne
	private Patron patron;
	
	
	
	
}
