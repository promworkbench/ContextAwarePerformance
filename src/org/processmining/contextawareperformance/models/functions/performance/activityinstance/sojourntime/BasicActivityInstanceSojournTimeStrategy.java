package org.processmining.contextawareperformance.models.functions.performance.activityinstance.sojourntime;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension.StandardModel;
import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XTrace;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.comparators.XEventTimeStampAndLifeCycleTransitionComparator;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionentities.LiteralEventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.extensions.XCaseExtension;
import org.processmining.contextawareperformance.models.functions.performance.activityinstance.duration.BasicActivityInstanceDurationStrategy;

/**
 * Basic activity instance sojourn time strategy.
 * 
 * The sojourn time is calculated as the difference between the timestamp of the
 * event representing the complete lifecycle transition of the current activity
 * instance and the timestamp of the event representing the complete lifecycle
 * transition of the previous activity instance in the trace.
 * 
 * Events in traces are sorted based on their timestamp and lifecycle transition
 * ({@link XEventTimeStampAndLifeCycleTransitionComparator}). Only the start and
 * complete lifecycle transitions are considered.
 * 
 * If there are no events representing a complete lifecycle transition before
 * the event that represents the complete lifecycle of the current activity
 * instance, the sojourn time is considered to be equal to the duration (using
 * {@link BasicActivityInstanceDurationStrategy}.
 * 
 * Note: it might be that due to noise start and complete lifecycle transitions
 * are missing, or that the complete lifecycle transition occurs before the
 * start lifecycle transition. This is not dealt with in this strategy, i.e. it
 * assumes a noise free log with respect to lifecycle transitions and time.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class BasicActivityInstanceSojournTimeStrategy implements IActivityInstanceSojournTimeStrategy {

	@Override
	public PerformanceMeasurement<Long> compute(EventCollectionEntity entity, EventCollection collection) {
		EventCollectionEntity lit;
		Collection<XEvent> events;

		switch (entity.getType()) {
			case EVENT :
				events = collection.viewAs(EventCollectionViewType.EVENT).get(entity);
				lit = new LiteralEventCollectionEntity(
						XConceptExtension.instance().extractInstance(events.iterator().next()),
						EventCollectionViewType.ACTIVITYINSTANCE);
				break;
			case ACTIVITYINSTANCE :
				lit = entity;
				break;
			default :
				throw new UnsupportedOperationException(
						"It is not possible to compute case duration with this type of entity as input!");
		}

		return computePrivate(lit, collection);
	}

	private PerformanceMeasurement<Long> computePrivate(EventCollectionEntity entity, EventCollection collection) {
		Collection<XEvent> currentActivityInstance = collection.viewAs(EventCollectionViewType.ACTIVITYINSTANCE)
				.get(entity);

		XTrace trace = (XTrace) collection.viewAs(EventCollectionViewType.CASE)
				.get(new LiteralEventCollectionEntity(
						XCaseExtension.instance().extractCase(currentActivityInstance.iterator().next()),
						EventCollectionViewType.CASE));

		Collections.sort(trace, new XEventTimeStampAndLifeCycleTransitionComparator());

		XTimeExtension te = XTimeExtension.instance();
		XLifecycleExtension le = XLifecycleExtension.instance();

		long sojourntime = Long.MIN_VALUE;

		XEvent beginEvent = null;
		XEvent endEvent = null;
		Date beginEventDate = null;
		Date endEventDate = null;

		// Find the event representing the COMPLETE lifecycle transition of the current activity instance
		for (XEvent e : currentActivityInstance) {
			if (le.extractStandardTransition(e).equals(StandardModel.COMPLETE))
				endEvent = e;
		}
		// If no COMPLETE lifecycle is present, then take the START lifecycle event
		if (endEvent == null) {
			for (XEvent e : currentActivityInstance) {
				if (le.extractStandardTransition(e).equals(StandardModel.START))
					endEvent = e;
			}
		}

		// Find the last event that represents a COMPLETE lifecycle transition and 
		// occurred before the COMPLETE event of the current activity instance
		for (int i = trace.indexOf(endEvent); i == 0; i--) {
			XEvent event = trace.get(i);
			if (le.extractStandardTransition(event).equals(StandardModel.COMPLETE)) {
				beginEvent = event;
				break;
			}
		}

		PerformanceMeasurement<Long> measurement = new PerformanceMeasurement<>();

		if (beginEvent != null && endEvent != null) {
			beginEventDate = te.extractTimestamp(beginEvent);
			endEventDate = te.extractTimestamp(endEvent);
			sojourntime = endEventDate.getTime() - beginEventDate.getTime();
			measurement.setResult(sojourntime);
			measurement.setMeasurementDate(endEventDate);
		} else if (beginEvent == null && endEvent != null) {
			measurement = new BasicActivityInstanceDurationStrategy().compute(entity, collection);
		}

		return measurement;
	}

}
