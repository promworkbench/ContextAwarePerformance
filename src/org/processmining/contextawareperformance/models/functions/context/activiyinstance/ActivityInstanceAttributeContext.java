package org.processmining.contextawareperformance.models.functions.context.activiyinstance;

import java.util.Collection;
import java.util.Objects;

import org.deckfour.xes.extension.std.XLifecycleExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension.StandardModel;
import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.context.ContextType;
import org.processmining.contextawareperformance.models.parameters.context.event.EventAttributeContextParameters;

/**
 * Context function that maps an activity instance in an event collection to the
 * value(s) of the attribute with the specified name of the events of the
 * activity instance.
 * 
 * This implementation returns the values(s) of the attribute with the specified
 * name of the events that represent the start lifecycle transition and the
 * complete lifecycle transitions of the activity instance in ordered form
 * (list). For example: [27,27], for attribute "age".
 * 
 * @author B.F.A. Hompes
 *
 */
public class ActivityInstanceAttributeContext extends Context<String> {

	private static final long serialVersionUID = 2076948309981632581L;

	private EventAttributeContextParameters parameters;

	public ActivityInstanceAttributeContext() {
		super(ContextType.ACTIVITYINSTANCEATTRIBUTE);
		setParameters(new EventAttributeContextParameters());
		VERB = "have";
	}

	public EventAttributeContextParameters getParameters() {
		return parameters;
	}

	public void setParameters(EventAttributeContextParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public String label(EventCollectionEntity entity, EventCollection collection) {
		Collection<XEvent> activityInstance = collection.viewAs(EventCollectionViewType.ACTIVITYINSTANCE).get(entity);

		XEvent startEvent = null, completeEvent = null;
		XLifecycleExtension le = XLifecycleExtension.instance();

		for (XEvent e : activityInstance) {
			if (le.extractStandardTransition(e).equals(StandardModel.START))
				startEvent = e;
			if (le.extractStandardTransition(e).equals(StandardModel.COMPLETE))
				completeEvent = e;
		}

		StringBuilder builder = new StringBuilder();

		for (String att : parameters.getAttributeNames()) {
			//			builder.append(att + ": ");

			if (startEvent == null && completeEvent == null)
				builder.append("unknown");

			if (startEvent != null && completeEvent == null)
				builder.append(
						startEvent.getAttributes().containsKey(att) ? startEvent.getAttributes().get(att) : "unknown");

			if (startEvent == null && completeEvent != null)
				builder.append(completeEvent.getAttributes().containsKey(att) ? completeEvent.getAttributes().get(att)
						: "unknown");

			if (startEvent != null && completeEvent != null) {

				if (startEvent.getAttributes().containsKey(att) && completeEvent.getAttributes().containsKey(att)) {
					if (startEvent.getAttributes().get(att).equals(completeEvent.getAttributes().get(att))) {
						builder.append(startEvent.getAttributes().get(att));
					} else {
						builder.append(startEvent.getAttributes().get(att));
						builder.append(" - ");
						builder.append(completeEvent.getAttributes().get(att));
					}
				} else {
					if (startEvent.getAttributes().containsKey(att)
							&& !completeEvent.getAttributes().containsKey(att)) {
						builder.append(startEvent.getAttributes().get(att));
					} else {
						builder.append(completeEvent.getAttributes().get(att));
					}
				}
			}
		}

		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActivityInstanceAttributeContext))
			return false;

		ActivityInstanceAttributeContext context = (ActivityInstanceAttributeContext) obj;

		if (!getParameters().equals(context.getParameters()))
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
