package org.processmining.contextawareperformance.models.functions.performance.activityinstance.waitingtime;

import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;

public interface IActivityInstanceWaitingTimeStrategy {

	PerformanceMeasurement<Long> compute(EventCollectionEntity entity, EventCollection collection);

}
