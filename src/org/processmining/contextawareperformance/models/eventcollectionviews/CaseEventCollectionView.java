package org.processmining.contextawareperformance.models.eventcollectionviews;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.deckfour.xes.factory.XFactoryRegistry;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XTrace;
import org.processmining.contextawareperformance.models.eventcollectionentities.LiteralEventCollectionEntity;
import org.processmining.contextawareperformance.models.extensions.XCaseExtension;

public class CaseEventCollectionView extends EventCollectionView<LiteralEventCollectionEntity, XTrace> {

	public static final EventCollectionViewType TYPE;

	static {
		TYPE = EventCollectionViewType.CASE;
	}

	public CaseEventCollectionView() {
		super(TYPE);
	}

	@Override
	public Map<LiteralEventCollectionEntity, XTrace> view(Set<XEvent> collection) {
		Map<LiteralEventCollectionEntity, XTrace> map = new HashMap<LiteralEventCollectionEntity, XTrace>();

		for (XEvent event : collection) {
			String caseID = XCaseExtension.instance().extractCase(event);
			LiteralEventCollectionEntity entity = new LiteralEventCollectionEntity(caseID, TYPE);
			entity.setType(this.getType());
			if (!map.containsKey(entity))
				map.put(entity, XFactoryRegistry.instance().currentDefault().createTrace());
			map.get(entity).add(event);
		}

		return map;
	}

}
