package org.processmining.contextawareperformance.models.functions.performance.activityinstance.duration;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension;
import org.deckfour.xes.extension.std.XLifecycleExtension.StandardModel;
import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionentities.LiteralEventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;

/**
 * 
 * Basic activity instance duration strategy.
 * 
 * The duration is calculated as the time between the timestamps of the events
 * representing the start and complete transitions of the activity instance. If
 * one of the lifecycle transitions are not present, the duration is considered
 * to be zero (i.e. the activity is atomic).
 * 
 * The measurement date is the timestamp of the event representing the complete
 * lifecycle transition. If this event is not present, the date will be the
 * timestamp of the event representing the start lifecycle transition.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class BasicActivityInstanceDurationStrategy implements IActivityInstanceDurationStrategy {

	@Override
	public PerformanceMeasurement<Long> compute(EventCollectionEntity entity, EventCollection collection) {
		EventCollectionEntity lit;
		Collection<XEvent> events;
		Set<XEvent> activityInstanceEvents;

		switch (entity.getType()) {
			case EVENT :
				events = collection.viewAs(EventCollectionViewType.EVENT).get(entity);
				lit = new LiteralEventCollectionEntity(
						XConceptExtension.instance().extractInstance(events.iterator().next()),
						EventCollectionViewType.EVENT);
				break;
			case ACTIVITYINSTANCE :
				lit = entity;
				break;
			default :
				throw new UnsupportedOperationException(
						"It is not possible to compute activity instance duration with this type of entity as input!");
		}

		activityInstanceEvents = (Set<XEvent>) collection.viewAs(EventCollectionViewType.ACTIVITYINSTANCE).get(lit);

		return computePrivate(activityInstanceEvents);
	}

	private PerformanceMeasurement<Long> computePrivate(Set<XEvent> activityInstance) {
		Date start = null, complete = null;

		XLifecycleExtension le = XLifecycleExtension.instance();
		XTimeExtension te = XTimeExtension.instance();

		for (XEvent e : activityInstance) {
			if (le.extractStandardTransition(e).equals(StandardModel.START))
				start = te.extractTimestamp(e);
			else if (le.extractStandardTransition(e).equals(StandardModel.COMPLETE))
				complete = te.extractTimestamp(e);
		}

		PerformanceMeasurement<Long> measurement = new PerformanceMeasurement<>();
		long duration = Long.MIN_VALUE;

		if (start != null && complete != null) {
			duration = complete.getTime() - start.getTime();
			measurement.setMeasurementDate(complete);
		} else if (start == null && complete != null) {
			duration = 0l;
			measurement.setMeasurementDate(complete);
		} else if (start != null && complete == null) {
			duration = 0l;
			measurement.setMeasurementDate(start);
		} else {
			// this only happens when we have no START or COMPLETE event but we do have other lifecycle events which are not handled in the basic strategy
		}

		measurement.setResult(duration);

		return measurement;
	}

}
