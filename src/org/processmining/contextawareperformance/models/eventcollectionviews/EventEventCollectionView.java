package org.processmining.contextawareperformance.models.eventcollectionviews;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.deckfour.xes.extension.std.XIdentityExtension;
import org.deckfour.xes.id.XID;
import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.eventcollectionentities.IdentityEventCollectionEntity;

import com.google.common.collect.Sets;

public class EventEventCollectionView extends EventCollectionView<IdentityEventCollectionEntity, Set<XEvent>> {

	public static final EventCollectionViewType TYPE;

	static {
		TYPE = EventCollectionViewType.EVENT;
	}

	public EventEventCollectionView() {
		super(TYPE);
	}

	@Override
	public Map<IdentityEventCollectionEntity, Set<XEvent>> view(Set<XEvent> collection) {
		Map<IdentityEventCollectionEntity, Set<XEvent>> map = new HashMap<IdentityEventCollectionEntity, Set<XEvent>>();

		for (XEvent event : collection) {
			XID eventID = XIdentityExtension.instance().extractID(event);
			if (!map.containsKey(new IdentityEventCollectionEntity(eventID, TYPE))) {
				IdentityEventCollectionEntity entity = new IdentityEventCollectionEntity(eventID, TYPE);
				entity.setType(this.getType());
				map.put(entity, Sets.<XEvent>newHashSet());
			}
			map.get(new IdentityEventCollectionEntity(eventID, TYPE)).add(event);
		}

		return map;
	}

}
