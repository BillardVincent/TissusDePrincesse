package fr.vbillard.tissusDePrincesse.fxCustomElements;

import fr.vbillard.tissusDePrincesse.dtosFx.TissuRequisDto;
import javafx.scene.control.ToggleButton;

public class TissuRequisToggleButton extends ToggleButton{

	private TissuRequisDto tissuUsed;
	
	public TissuRequisDto getTissuUsed() {
		return tissuUsed;
	}
	
	public TissuRequisToggleButton(TissuRequisDto tissuUsed) {
		this.tissuUsed = tissuUsed;
	}
}
