package org.processmining.contextawareperformance.models.functions.context.event;

import java.util.Objects;

import org.deckfour.xes.extension.std.XOrganizationalExtension;
import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.context.ContextType;
import org.processmining.contextawareperformance.models.parameters.context.ContextParameters;
import org.processmining.contextawareperformance.models.parameters.context.DummyContextParameters;

/**
 * Context function that maps an event in an event collection to the name of the
 * group that executed the event.
 * 
 * @author B.F.A. Hompes
 *
 */
public class EventExecutingGroupContext extends Context<String> {

	private static final long serialVersionUID = 8618312305625619597L;

	public EventExecutingGroupContext() {
		super(ContextType.EVENTEXECUTINGGROUP);
		VERB = "the group(s) involved in";
	}

	@Override
	public String label(EventCollectionEntity entity, EventCollection collection) {
		XEvent event = collection.viewAs(EventCollectionViewType.EVENT).get(entity).iterator().next();

		String resource = event.getAttributes().containsKey(XOrganizationalExtension.KEY_GROUP)
				? XOrganizationalExtension.instance().extractResource(event)
				: "unknown";

		return resource;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EventExecutingGroupContext))
			return false;

		EventExecutingGroupContext context = (EventExecutingGroupContext) obj;

		return super.equals(context);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode());
	}

	public ContextParameters getParameters() {
		return new DummyContextParameters();
	}

}
