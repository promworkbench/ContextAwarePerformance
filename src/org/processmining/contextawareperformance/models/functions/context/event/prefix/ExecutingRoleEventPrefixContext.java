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
public class ExecutingRoleEventPrefixContext extends EventPrefixContext {

	private static final long serialVersionUID = -1767570333178695955L;

	public ExecutingRoleEventPrefixContext() {
		super(ContextType.EVENTRESOURCEPREFIX);
		setParameters(new PrefixContextParameters());
	}

	@Override
	public String labelEvent(XEvent event) {
		String role = event.getAttributes().containsKey(XOrganizationalExtension.KEY_ROLE)
				? XOrganizationalExtension.instance().extractRole(event)
				: "unknown";

		return role;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ExecutingRoleEventPrefixContext))
			return false;

		ExecutingRoleEventPrefixContext context = (ExecutingRoleEventPrefixContext) obj;

		return super.equals(context);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode());
	}

}
