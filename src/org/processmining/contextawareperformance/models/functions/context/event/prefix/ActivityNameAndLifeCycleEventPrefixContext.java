package org.processmining.contextawareperformance.models.functions.context.event.prefix;

import java.util.Objects;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension;
import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.functions.context.ContextType;
import org.processmining.contextawareperformance.models.parameters.context.PrefixContextParameters;

/**
 * Context function that maps an event in an event collection to the
 * conceptnames and lifecycle transitions of the previous events in the trace of
 * the event.
 * 
 * @author B.F.A. Hompes
 *
 */
public class ActivityNameAndLifeCycleEventPrefixContext extends EventPrefixContext {

	private static final long serialVersionUID = 5159706748562034182L;

	public ActivityNameAndLifeCycleEventPrefixContext() {
		super(ContextType.EVENTACTIVITYNAMEANDLIFECYCLEPREFIX);
		setParameters(new PrefixContextParameters());
	}

	@Override
	public String labelEvent(XEvent event) {
		String conceptName = event.getAttributes().containsKey(XConceptExtension.KEY_NAME)
				? XConceptExtension.instance().extractName(event)
				: "unknown";

		String lifeCycleTransition = event.getAttributes().containsKey(XLifecycleExtension.KEY_TRANSITION)
				? XLifecycleExtension.instance().extractTransition(event)
				: "unknown";

		return "[" + conceptName + " - " + lifeCycleTransition + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActivityNameAndLifeCycleEventPrefixContext))
			return false;

		ActivityNameAndLifeCycleEventPrefixContext context = (ActivityNameAndLifeCycleEventPrefixContext) obj;

		return super.equals(context);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode());
	}

}
