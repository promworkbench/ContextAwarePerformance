package org.processmining.contextawareperformance.models.functions.performance.activityinstance.duration;

import java.util.Objects;

import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.functions.performance.PerformanceType;
import org.processmining.contextawareperformance.models.parameters.performance.activityinstance.duration.ActivityInstanceDurationPerformanceParameters;

/**
 * Performance that gives the duration of activity instances.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ActivityInstanceDurationPerformance extends Performance<Long> {

	private static final long serialVersionUID = -2306481552143362647L;

	private ActivityInstanceDurationPerformanceParameters parameters;

	public ActivityInstanceDurationPerformance() {
		super(PerformanceType.ACTIVITY_DURATION);
		setParameters(new ActivityInstanceDurationPerformanceParameters());
		ADJECTIVE_GREATER_THAN = "higher";
		ADJECTIVE_SMALLER_THAN = "lower";
		VERB = "be";
	}

	public ActivityInstanceDurationPerformanceParameters getParameters() {
		return parameters;
	}

	public void setParameters(ActivityInstanceDurationPerformanceParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	protected PerformanceMeasurement<Long> compute(EventCollectionEntity entity, EventCollection collection) {
		PerformanceMeasurement<Long> result = null;
		switch (parameters.getDurationCalculationStrategy()) {
			case BASIC :
				result = (new BasicActivityInstanceDurationStrategy()).compute(entity, collection);
				break;
			default :
				System.out.println("Invalid execution time type parameter");
				break;
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActivityInstanceDurationPerformance))
			return false;

		ActivityInstanceDurationPerformance performance = (ActivityInstanceDurationPerformance) obj;

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
