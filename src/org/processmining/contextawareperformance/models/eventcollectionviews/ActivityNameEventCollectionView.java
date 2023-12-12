package org.processmining.contextawareperformance.models.eventcollectionviews;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.eventcollectionentities.LiteralEventCollectionEntity;

import com.google.common.collect.Sets;

public class ActivityNameEventCollectionView extends EventCollectionView<LiteralEventCollectionEntity, Set<XEvent>> {

	public static final EventCollectionViewType TYPE;

	static {
		TYPE = EventCollectionViewType.ACTIVITY;
	}

	public ActivityNameEventCollectionView() {
		super(TYPE);
	}

	@Override
	public Map<LiteralEventCollectionEntity, Set<XEvent>> view(Set<XEvent> collection) {
		Map<LiteralEventCollectionEntity, Set<XEvent>> map = new HashMap<LiteralEventCollectionEntity, Set<XEvent>>();

		for (XEvent event : collection) {
			String activityInstanceName = XConceptExtension.instance().extractName(event);
			if (!map.containsKey(new LiteralEventCollectionEntity(activityInstanceName, TYPE))) {
				LiteralEventCollectionEntity entity = new LiteralEventCollectionEntity(activityInstanceName, TYPE);
				entity.setType(this.getType());
				map.put(entity, Sets.<XEvent>newHashSet());
			}
			map.get(new LiteralEventCollectionEntity(activityInstanceName, TYPE)).add(event);
		}

		return map;
	}

}
