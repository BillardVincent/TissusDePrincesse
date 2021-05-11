package fr.vbillard.tissusDePrincesse.model.enums;

import java.util.ArrayList;
import java.util.List;

public enum GammePoids {
	NON_RENSEIGNE("N/A"){
		@Override
	    public String toString() {
	      return "N/A";
	}},
	LEGER("léger"){
	    @Override
	    public String toString() {
	      return "léger";
	    }},
	MOYEN("moyen"){
		    @Override
		    public String toString() {
		      return "moyen";
		    }},
	LOURD("lourd"){
	    @Override
	    public String toString() {
	      return "lourd";
	    }};
	    
    public final String label;

    private GammePoids(String label) {
        this.label = label;
    }
    
    public String getLabel() {
        return this.label;
      }
    
    public static GammePoids getEnum(String code){
        for(GammePoids e : GammePoids.values()){
            if(e.label.equals(code)) return e;
        }
        return null;
    }
    
    public static List<String> labels(){
    	List<String> list = new ArrayList();
        for(GammePoids e : GammePoids.values()){
            list.add(e.label);
        }
        return list;
    }
    
    public static String displayClassName() {
    	return "Gamme de poids";
    }
}
