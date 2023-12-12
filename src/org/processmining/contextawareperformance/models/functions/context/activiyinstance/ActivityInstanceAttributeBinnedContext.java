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

public class ActivityInstanceAttributeBinnedContext extends Context<String> {

	private static final long serialVersionUID = 4803373931095685915L;

	private EventAttributeContextParameters parameters;

	public ActivityInstanceAttributeBinnedContext() {
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
				builder.append(startEvent.getAttributes().get(att));

			if (startEvent == null && completeEvent != null)
				builder.append(completeEvent.getAttributes().get(att));

			if (startEvent != null && completeEvent != null) {
				if (startEvent.getAttributes().get(att).equals(completeEvent.getAttributes().get(att))) {
					builder.append(startEvent.getAttributes().get(att));
				} else {
					builder.append(startEvent.getAttributes().get(att));
					builder.append(" - ");
					builder.append(completeEvent.getAttributes().get(att));
				}
			}
		}

		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActivityInstanceAttributeBinnedContext))
			return false;

		ActivityInstanceAttributeBinnedContext context = (ActivityInstanceAttributeBinnedContext) obj;

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
