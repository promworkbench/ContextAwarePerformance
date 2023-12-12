package org.processmining.contextawareperformance.models.functions.context.event;

import java.util.Objects;

import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.context.ContextType;
import org.processmining.contextawareperformance.models.parameters.context.event.EventAttributeContextParameters;

/**
 * Context function that maps an event in an event collection to the value of a
 * specified attribute of that event.
 * 
 * @author B.F.A. Hompes
 *
 */
public class EventAttributeContext extends Context<String> {

	private static final long serialVersionUID = 3744667883601963302L;

	private EventAttributeContextParameters parameters;

	public EventAttributeContext() {
		super(ContextType.EVENTATTRIBUTE);
		setParameters(new EventAttributeContextParameters());
	}

	public EventAttributeContextParameters getParameters() {
		return parameters;
	}

	public void setParameters(EventAttributeContextParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public String label(EventCollectionEntity entity, EventCollection collection) {
		XEvent event = collection.viewAs(EventCollectionViewType.EVENT).get(entity).iterator().next();

		StringBuilder builder = new StringBuilder();

		for (String att : parameters.getAttributeNames()) {
			builder.append(event.getAttributes().get(att));
			builder.append(",");
		}

		builder.deleteCharAt(builder.lastIndexOf(","));

		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EventAttributeContext))
			return false;

		EventAttributeContext context = (EventAttributeContext) obj;

		if (!context.getParameters().equals(getParameters()))
			return false;

		return super.equals(context);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), parameters);
	}

	@Override
	public String toString() {
		return super.toString() + " " + parameters.toString();
	}
}
