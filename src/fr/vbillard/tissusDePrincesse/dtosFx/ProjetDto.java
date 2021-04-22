package fr.vbillard.tissusDePrincesse.dtosFx;

import java.util.List;
import java.util.Map;

import fr.vbillard.tissusDePrincesse.model.FounitureRequise;
import fr.vbillard.tissusDePrincesse.model.Patron;
import fr.vbillard.tissusDePrincesse.model.TissuRequis;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.MapProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.StringProperty;

public class ProjetDto {

	
	private IntegerProperty id;
	
	private StringProperty description;
	
	private ObjectProperty<Patron> patron;
	
	private MapProperty<TissuRequis, List<Integer>> tissuUsed;
	
	private MapProperty<FounitureRequise, List<Integer>> fournitureUsed;
	
	
}
