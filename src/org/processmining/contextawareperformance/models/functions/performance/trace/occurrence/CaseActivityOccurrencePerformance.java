package org.processmining.contextawareperformance.models.functions.performance.trace.occurrence;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XTrace;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionentities.LiteralEventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.extensions.XCaseExtension;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.functions.performance.PerformanceType;
import org.processmining.contextawareperformance.models.parameters.performance.trace.occurrence.CaseActivityOccurrencePerformanceParameters;

/**
 * Performance that returns 1 when the specified activity is present in the case
 * and 0 when it is not.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class CaseActivityOccurrencePerformance extends Performance<Long> {

	private static final long serialVersionUID = -5629000012321944248L;

	private CaseActivityOccurrencePerformanceParameters parameters;

	public CaseActivityOccurrencePerformance() {
		super(PerformanceType.CASE_ACTIVITYOCCURRENCE);
		setParameters(new CaseActivityOccurrencePerformanceParameters());
		ADJECTIVE_GREATER_THAN = "more often";
		ADJECTIVE_SMALLER_THAN = "less often";
		VERB = "occur";
	}

	public CaseActivityOccurrencePerformanceParameters getParameters() {
		return parameters;
	}

	public void setParameters(CaseActivityOccurrencePerformanceParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	protected PerformanceMeasurement<Long> compute(EventCollectionEntity entity, EventCollection collection) {
		EventCollectionEntity lit;
		Collection<XEvent> events;
		XTrace trace = null;

		switch (entity.getType()) {
			case EVENT :
				events = collection.viewAs(EventCollectionViewType.EVENT).get(entity);
				lit = new LiteralEventCollectionEntity(XCaseExtension.instance().extractCase(events.iterator().next()),
						EventCollectionViewType.CASE);
				break;
			case ACTIVITYINSTANCE :
				events = collection.viewAs(EventCollectionViewType.ACTIVITYINSTANCE).get(entity);
				lit = new LiteralEventCollectionEntity(XCaseExtension.instance().extractCase(events.iterator().next()),
						EventCollectionViewType.CASE);
				break;
			case CASE :
				lit = entity;
				break;
			default : // should never happen because the abstract class catches this.
				throw new UnsupportedOperationException(
						"It is not possible to compute performance \"" + this.getType().getDescription()
								+ "\" with this entity type \"" + entity.getType().getDescription() + "\" as input!");
		}

		trace = (XTrace) collection.viewAs(EventCollectionViewType.CASE).get(lit);
		return computePrivate(trace);
	}

	private PerformanceMeasurement<Long> computePrivate(XTrace trace) {
		Collections.sort(trace, parameters.getComparator());

		long result = 0l;

		XConceptExtension ce = XConceptExtension.instance();
		XTimeExtension te = XTimeExtension.instance();

		for (XEvent event : trace) {
			if (ce.extractName(event).equals(parameters.getActivityConceptname()))
				result = 1l;
			break;
		}

		PerformanceMeasurement<Long> measurement = new PerformanceMeasurement<>();
		measurement.setResult(result);
		measurement.setMeasurementDate(te.extractTimestamp(trace.get(trace.size() - 1)));

		return measurement;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CaseActivityOccurrencePerformance))
			return false;

		CaseActivityOccurrencePerformance performance = (CaseActivityOccurrencePerformance) obj;

		if (!performance.getParameters().equals(getParameters()))
			return false;

		return super.equals(performance);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), parameters);
	}

	@Override
	public String toString() {
		return parameters.getActivityConceptname();
	}

}
