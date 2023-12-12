package org.processmining.contextawareperformance.models.eventcollectionviews;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.eventcollectionentities.NumberEventCollectionEntity;

public class ProcessEventCollectionView extends EventCollectionView<NumberEventCollectionEntity, Set<XEvent>> {

	public static final EventCollectionViewType TYPE;

	static {
		TYPE = EventCollectionViewType.PROCESS;
	}

	public ProcessEventCollectionView() {
		super(TYPE);
	}

	@Override
	public Map<NumberEventCollectionEntity, Set<XEvent>> view(Set<XEvent> collection) {
		Map<NumberEventCollectionEntity, Set<XEvent>> map = new HashMap<NumberEventCollectionEntity, Set<XEvent>>();

		NumberEventCollectionEntity entity = new NumberEventCollectionEntity(collection.size(), TYPE);
		entity.setType(this.getType());
		map.put(entity, collection);

		return map;
	}

}
