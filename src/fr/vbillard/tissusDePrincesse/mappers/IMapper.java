package fr.vbillard.tissusDePrincesse.mappers;

import java.util.List;
import java.util.stream.Collectors;

import fr.vbillard.tissusDePrincesse.model.AbstractEntity;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public interface IMapper<T extends AbstractEntity, D> {

	T map(D dto);
	D map (T entity);
	
	default ObservableList<D> getAsObservable(List<T> list) {
		return FXCollections.observableArrayList(list.stream().map(t -> map(t)).collect(Collectors.toList()));
		
	}
}
