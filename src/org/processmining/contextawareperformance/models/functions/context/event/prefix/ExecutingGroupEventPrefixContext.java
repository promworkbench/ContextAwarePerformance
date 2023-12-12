package org.processmining.contextawareperformance.models.functions.context.event.prefix;

import java.util.Objects;

import org.deckfour.xes.extension.std.XOrganizationalExtension;
import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.functions.context.ContextType;
import org.processmining.contextawareperformance.models.parameters.context.PrefixContextParameters;

/**
 * Context function that maps an event in an event collection to the names of
 * the resources that executed the previous events in the trace of the event.
 * 
 * @author B.F.A. Hompes
 *
 */
public class ExecutingGroupEventPrefixContext extends EventPrefixContext {

	private static final long serialVersionUID = -1767570333178695955L;

	public ExecutingGroupEventPrefixContext() {
		super(ContextType.EVENTRESOURCEPREFIX);
		setParameters(new PrefixContextParameters());
	}

	@Override
	public String labelEvent(XEvent event) {
		String group = event.getAttributes().containsKey(XOrganizationalExtension.KEY_GROUP)
				? XOrganizationalExtension.instance().extractGroup(event)
				: "unknown";

		return group;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ExecutingGroupEventPrefixContext))
			return false;

		ExecutingGroupEventPrefixContext context = (ExecutingGroupEventPrefixContext) obj;

		return super.equals(context);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode());
	}

}
