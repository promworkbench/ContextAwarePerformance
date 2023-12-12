package org.processmining.contextawareperformance.models.eventcollectionviews;

import java.util.Collection;

import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;

public abstract class EventCollectionView<E extends EventCollectionEntity, C extends Collection<XEvent>>
		implements IEventCollectionView<E, C> {

	private EventCollectionViewType type;

	public EventCollectionView(EventCollectionViewType type) {
		setType(type);
	}

	public EventCollectionViewType getType() {
		return type;
	}

	public void setType(EventCollectionViewType type) {
		this.type = type;
	}

}
