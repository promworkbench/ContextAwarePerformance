package org.processmining.contextawareperformance.models.functions.context.trace;

import java.util.Objects;

import org.deckfour.xes.model.XTrace;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.context.ContextType;
import org.processmining.contextawareperformance.models.parameters.context.trace.CaseAttributeContextParameters;

/**
 * Context function that maps a case entity in an event collection to the value
 * of a specified attribute of that entity, which it got from the any event in
 * it the trace of the case.
 * 
 * Note that any event is used to avoid having to order the case. This works
 * under the assumption that all events in the trace have the same value for the
 * specified attribute.
 * 
 * @author B.F.A. Hompes
 *
 */
public class CaseAttributeContext extends Context<String> {

	private static final long serialVersionUID = 7077957813601585834L;

	private CaseAttributeContextParameters parameters;

	public CaseAttributeContext() {
		super(ContextType.CASEATTRIBUTE);
		setParameters(new CaseAttributeContextParameters());
		VERB = "have attribute value";
	}

	public CaseAttributeContextParameters getParameters() {
		return parameters;
	}

	public void setParameters(CaseAttributeContextParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public String label(EventCollectionEntity entity, EventCollection collection) {
		XTrace trace = (XTrace) collection.viewAs(EventCollectionViewType.CASE).get(entity);

		StringBuilder builder = new StringBuilder();

		for (String att : parameters.getAttributeNames()) {
			String label = trace.get(0).getAttributes().containsKey(parameters.CASE_ATTRIBUTE_PREFIX + att)
					? trace.get(0).getAttributes().get(parameters.CASE_ATTRIBUTE_PREFIX + att).toString()
					: "unknown";

			builder.append(label);
			builder.append(",");
		}

		builder.deleteCharAt(builder.lastIndexOf(","));

		return builder.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CaseAttributeContext))
			return false;

		CaseAttributeContext context = (CaseAttributeContext) obj;

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
