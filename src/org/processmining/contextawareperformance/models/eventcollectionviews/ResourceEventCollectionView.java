package org.processmining.contextawareperformance.models.eventcollectionviews;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.deckfour.xes.extension.std.XOrganizationalExtension;
import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.eventcollectionentities.LiteralEventCollectionEntity;

import com.google.common.collect.Sets;

public class ResourceEventCollectionView extends EventCollectionView<LiteralEventCollectionEntity, Set<XEvent>> {

	public static final EventCollectionViewType TYPE;

	static {
		TYPE = EventCollectionViewType.RESOURCE;
	}

	public ResourceEventCollectionView() {
		super(TYPE);
	}

	@Override
	public Map<LiteralEventCollectionEntity, Set<XEvent>> view(Set<XEvent> collection) {
		Map<LiteralEventCollectionEntity, Set<XEvent>> map = new HashMap<LiteralEventCollectionEntity, Set<XEvent>>();

		for (XEvent event : collection) {
			String resourceID = XOrganizationalExtension.instance().extractResource(event);
			if (!map.containsKey(new LiteralEventCollectionEntity(resourceID, TYPE))) {
				LiteralEventCollectionEntity entity = new LiteralEventCollectionEntity(resourceID, TYPE);
				entity.setType(this.getType());
				map.put(entity, Sets.<XEvent>newHashSet());
			}
			map.get(new LiteralEventCollectionEntity(resourceID, TYPE)).add(event);
		}

		return map;
	}

}
