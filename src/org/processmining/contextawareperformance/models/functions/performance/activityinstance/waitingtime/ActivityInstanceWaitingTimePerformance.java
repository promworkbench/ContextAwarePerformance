package org.processmining.contextawareperformance.models.functions.performance.activityinstance.waitingtime;

import java.util.Objects;

import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.functions.performance.PerformanceType;
import org.processmining.contextawareperformance.models.parameters.performance.activityinstance.waitingtime.ActivityInstanceWaitingTimePerformanceParameters;

/**
 * Performance that gives the waiting time of activity instances.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ActivityInstanceWaitingTimePerformance extends Performance<Long> {

	private static final long serialVersionUID = 6292725451859616746L;

	private ActivityInstanceWaitingTimePerformanceParameters parameters;

	public ActivityInstanceWaitingTimePerformance() {
		super(PerformanceType.ACTIVITY_WAITINGTIME);
		setParameters(new ActivityInstanceWaitingTimePerformanceParameters());
		ADJECTIVE_GREATER_THAN = "higher";
		ADJECTIVE_SMALLER_THAN = "lower";
		VERB = "be";
	}

	public ActivityInstanceWaitingTimePerformanceParameters getParameters() {
		return parameters;
	}

	public void setParameters(ActivityInstanceWaitingTimePerformanceParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	protected PerformanceMeasurement<Long> compute(EventCollectionEntity entity, EventCollection collection) {
		PerformanceMeasurement<Long> result = null;
		switch (parameters.getWaitingTimeCalculationStrategy()) {
			case BASIC :
				result = (new BasicActivityInstanceWaitingTimeStrategy()).compute(entity, collection);
				break;
			default :
				System.out.println("Invalid execution time type parameter");
				break;
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActivityInstanceWaitingTimePerformance))
			return false;

		ActivityInstanceWaitingTimePerformance performance = (ActivityInstanceWaitingTimePerformance) obj;

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
