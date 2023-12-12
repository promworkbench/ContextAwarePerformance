package org.processmining.contextawareperformance.models.eventcollectionviews;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;

public interface IEventCollectionView<E extends EventCollectionEntity, C extends Collection<XEvent>> {

	Map<E, C> view(Set<XEvent> collection);

}
