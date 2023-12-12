package org.processmining.contextawareperformance.models.functions.context.activiyinstance;

import java.util.Collection;
import java.util.Objects;

import org.deckfour.xes.extension.std.XLifecycleExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension.StandardModel;
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
 * Context function that maps an activity instance in an event collection to the
 * role of the resource that executed the events of the activity instance.
 * 
 * This implementation returns the roles of the resources that executed the
 * events that represent the start lifecycle transition and the complete
 * lifecycle transitions of the activity instance in ordered form (list). For
 * example: [Manager, Auditor].
 * 
 * @author B.F.A. Hompes
 *
 */
public class ActivityInstanceExecutingRoleContext extends Context<String> {

	private static final long serialVersionUID = 2076948309981632581L;

	public ActivityInstanceExecutingRoleContext() {
		super(ContextType.ACTIVITYINSTANCEEXECUTINGROLE);
		VERB = "be executed by";
	}

	@Override
	public String label(EventCollectionEntity entity, EventCollection collection) {
		Collection<XEvent> activityInstance = collection.viewAs(EventCollectionViewType.ACTIVITYINSTANCE).get(entity);

		XEvent startEvent = null, completeEvent = null;
		XLifecycleExtension le = XLifecycleExtension.instance();
		XOrganizationalExtension oe = XOrganizationalExtension.instance();

		for (XEvent e : activityInstance) {
			if (le.extractStandardTransition(e).equals(StandardModel.START))
				startEvent = e;
			if (le.extractStandardTransition(e).equals(StandardModel.COMPLETE))
				completeEvent = e;
		}

		String labelStart = startEvent != null
				? oe.extractRole(startEvent) != null ? oe.extractRole(startEvent) : "unknown"
				: "unknown";
		String labelComplete = completeEvent != null
				? oe.extractRole(completeEvent) != null ? oe.extractRole(completeEvent) : "unknown"
				: "unknown";

		return labelStart.equals(labelComplete) ? labelStart : labelStart + " - " + labelComplete;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActivityInstanceExecutingRoleContext))
			return false;

		ActivityInstanceExecutingRoleContext context = (ActivityInstanceExecutingRoleContext) obj;

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
