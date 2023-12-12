package org.processmining.contextawareperformance.models.functions.context.event.prefix;

import java.util.Objects;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.functions.context.ContextType;
import org.processmining.contextawareperformance.models.parameters.context.PrefixContextParameters;

/**
 * Context function that maps an event in an event collection to the
 * conceptnames of the previous events in the trace of the event.
 * 
 * @author B.F.A. Hompes
 *
 */
public class ActivityNameEventPrefixContext extends EventPrefixContext {

	private static final long serialVersionUID = -7787236721760644992L;

	public ActivityNameEventPrefixContext() {
		super(ContextType.EVENTACTIVITYNAMEPREFIX);
		setParameters(new PrefixContextParameters());
	}

	@Override
	public String labelEvent(XEvent event) {
		String conceptName = event.getAttributes().containsKey(XConceptExtension.KEY_NAME)
				? XConceptExtension.instance().extractName(event)
				: "unknown";

		return conceptName;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActivityNameEventPrefixContext))
			return false;

		ActivityNameEventPrefixContext context = (ActivityNameEventPrefixContext) obj;

		return super.equals(context);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode());
	}

}
