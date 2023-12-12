package org.processmining.contextawareperformance.models.functions.performance.trace.duration;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Objects;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension.StandardModel;
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
import org.processmining.contextawareperformance.models.parameters.performance.trace.duration.CaseDurationBetweenTwoActivitiesPerformanceParameters;

/**
 * Performance that gives the duration between two activities in case. Very
 * naive implementation. The duration is the time between the timestamp of the
 * event that represents the complete lifecycle transition of the first activity
 * and the timestamp representing the start lifecycle transition of the second
 * activity.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class CaseDurationBetweenTwoActivitiesPerformance extends Performance<Long> {

	private static final long serialVersionUID = 5235776935461102795L;

	private CaseDurationBetweenTwoActivitiesPerformanceParameters parameters;

	public CaseDurationBetweenTwoActivitiesPerformance() {
		super(PerformanceType.CASE_DURATIONBETWEENTWOACTIVITIES);
		setParameters(new CaseDurationBetweenTwoActivitiesPerformanceParameters());
		ADJECTIVE_GREATER_THAN = "higher";
		ADJECTIVE_SMALLER_THAN = "lower";
		VERB = "be";
	}

	public CaseDurationBetweenTwoActivitiesPerformanceParameters getParameters() {
		return parameters;
	}

	public void setParameters(CaseDurationBetweenTwoActivitiesPerformanceParameters parameters) {
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
		Collections.sort(trace, parameters.getComparator());

		XEvent fromEvent = null, toEvent = null;

		XConceptExtension ce = XConceptExtension.instance();
		XLifecycleExtension le = XLifecycleExtension.instance();

		event: for (XEvent event : trace) {
			if (ce.extractName(event).equals(parameters.getFromActivityConceptname())
					&& le.extractStandardTransition(event).equals(StandardModel.COMPLETE)) {
				fromEvent = event;
				continue event;
			}
			if (ce.extractName(event).equals(parameters.getToActivityConceptname())
					&& le.extractStandardTransition(event).equals(StandardModel.START)) {
				toEvent = event;
				continue event;
			}
		}
		// If there's no COMPLETE event for the from activity, take the START event
		if (fromEvent == null) {
			for (XEvent event : trace) {
				if (ce.extractName(event).equals(parameters.getFromActivityConceptname())
						&& le.extractStandardTransition(event).equals(StandardModel.START)) {
					fromEvent = event;
					break;
				}
			}
		}
		// If there's no START event for the to activity, take the COMPLETE event
		if (toEvent == null) {
			for (XEvent event : trace) {
				if (ce.extractName(event).equals(parameters.getToActivityConceptname())
						&& le.extractStandardTransition(event).equals(StandardModel.COMPLETE)) {
					toEvent = event;
					break;
				}
			}
		}

		long duration = -1l;

		PerformanceMeasurement<Long> measurement = new PerformanceMeasurement<>();

		if (fromEvent != null && toEvent != null) {
			Date fromEventDate = XTimeExtension.instance().extractTimestamp(fromEvent);
			Date toEventDate = XTimeExtension.instance().extractTimestamp(toEvent);
			duration = toEventDate.getTime() - fromEventDate.getTime();
			measurement.setMeasurementDate(toEventDate);
		}

		measurement.setResult(duration);

		return measurement;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CaseDurationBetweenTwoActivitiesPerformance))
			return false;

		CaseDurationBetweenTwoActivitiesPerformance performance = (CaseDurationBetweenTwoActivitiesPerformance) obj;

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
		return "Duration between " + parameters.getFromActivityConceptname() + " and "
				+ parameters.getToActivityConceptname();
	}

}
