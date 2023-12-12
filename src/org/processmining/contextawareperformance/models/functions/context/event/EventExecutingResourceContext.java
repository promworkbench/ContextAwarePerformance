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
 * resource that executed the event.
 * 
 * @author B.F.A. Hompes
 *
 */
public class EventExecutingResourceContext extends Context<String> {

	private static final long serialVersionUID = 6968398395523417721L;

	public EventExecutingResourceContext() {
		super(ContextType.EVENTEXECUTINGRESOURCE);
		VERB = "the resource(s) involved in";
	}

	@Override
	public String label(EventCollectionEntity entity, EventCollection collection) {
		XEvent event = collection.viewAs(EventCollectionViewType.EVENT).get(entity).iterator().next();

		String resource = event.getAttributes().containsKey(XOrganizationalExtension.KEY_RESOURCE)
				? XOrganizationalExtension.instance().extractResource(event)
				: "unknown";

		return resource;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EventExecutingResourceContext))
			return false;

		EventExecutingResourceContext context = (EventExecutingResourceContext) obj;

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
