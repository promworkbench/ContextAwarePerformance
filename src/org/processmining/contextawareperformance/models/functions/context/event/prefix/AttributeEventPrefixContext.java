package org.processmining.contextawareperformance.models.functions.context.event.prefix;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.functions.context.ContextType;
import org.processmining.contextawareperformance.models.parameters.context.PrefixContextParameters;
import org.processmining.contextawareperformance.models.parameters.context.event.EventAttributePrefixContextParameters;

/**
 * Context function that maps an event in an event collection to the values of a
 * specified attribute of the previous events in the trace of the event.
 * 
 * @author B.F.A. Hompes
 *
 */
public class AttributeEventPrefixContext extends EventPrefixContext {

	private static final long serialVersionUID = 2991732921448948981L;

	private EventAttributePrefixContextParameters parameters;

	public AttributeEventPrefixContext() {
		super(ContextType.EVENTATTRIBUTEPREFIX);
		setParameters(new PrefixContextParameters());
	}

	@Override
	public String labelEvent(XEvent event) {
		List<String> attributeValueNames = new ArrayList<String>();
		for (String attributeName : parameters.getAttributeNames()) {
			attributeValueNames.add(
					event.getAttributes().containsKey(attributeName) ? XConceptExtension.instance().extractName(event)
							: "unknown");
		}
		return attributeValueNames.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof AttributeEventPrefixContext))
			return false;

		AttributeEventPrefixContext context = (AttributeEventPrefixContext) obj;

		if (!getParameters().equals(context.getParameters()))
			return false;

		return super.equals(context);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), parameters);
	}

}
