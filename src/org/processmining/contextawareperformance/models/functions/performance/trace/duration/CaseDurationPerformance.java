package org.processmining.contextawareperformance.models.functions.performance.trace.duration;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

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
import org.processmining.contextawareperformance.models.parameters.performance.trace.duration.CaseDurationPerformanceParameters;

/**
 * Performance that gives the duration of case.
 * 
 * The duration of a case is the difference in time (in milliseconds) between
 * the timestamps of the last and first recorded events in the ordered trace.
 * 
 * The trace is sorted based on a specified Comparator<XEvent>, e.g. the
 * XEventTimestampComparator.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class CaseDurationPerformance extends Performance<Long> {

	private static final long serialVersionUID = 4462341515639840262L;

	private CaseDurationPerformanceParameters parameters;

	public CaseDurationPerformance() {
		super(PerformanceType.CASE_DURATION);
		parameters = new CaseDurationPerformanceParameters();
		ADJECTIVE_GREATER_THAN = "higher";
		ADJECTIVE_SMALLER_THAN = "lower";
		VERB = "be";
	}

	public CaseDurationPerformanceParameters getParameters() {
		return parameters;
	}

	public void setParameters(CaseDurationPerformanceParameters parameters) {
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
			default : // should never happen because it abstract class catches this.
				throw new UnsupportedOperationException(
						"It is not possible to compute performance \"" + this.getType().getDescription()
								+ "\" with this entity type \"" + entity.getType().getDescription() + "\" as input!");
		}

		trace = (XTrace) collection.viewAs(EventCollectionViewType.CASE).get(lit);
		return computePrivate(trace);
	}

	private PerformanceMeasurement<Long> computePrivate(XTrace trace) {
		// Get the events belonging to this case entity and sort them by the specified comparator.
		Collections.sort(trace, parameters.getComparator());

		PerformanceMeasurement<Long> measurement = new PerformanceMeasurement<>();
		Long duration = -1l;

		if (trace.size() > 0) {
			Date firstEventDate = XTimeExtension.instance().extractTimestamp(trace.get(0));
			Date lastEventDate = XTimeExtension.instance().extractTimestamp(trace.get(trace.size() - 1));
			duration = lastEventDate.getTime() - firstEventDate.getTime();
			measurement.setMeasurementDate(lastEventDate);
		}

		measurement.setResult(duration);

		return measurement;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CaseDurationPerformance))
			return false;

		CaseDurationPerformance performance = (CaseDurationPerformance) obj;

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
		return super.toString();// + " " + parameters.toString();
	}

}
