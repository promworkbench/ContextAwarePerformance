package org.processmining.contextawareperformance.models.functions.performance.activityinstance.duration;

import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;

public interface IActivityInstanceDurationStrategy {

	PerformanceMeasurement<Long> compute(EventCollectionEntity entity, EventCollection collection);

}
