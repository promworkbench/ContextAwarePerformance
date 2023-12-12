package org.processmining.contextawareperformance.models.functions.performance.activityinstance.sojourntime;

import java.util.Objects;

import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.functions.performance.PerformanceType;
import org.processmining.contextawareperformance.models.parameters.performance.activityinstance.sojourntime.ActivityInstanceSojournTimePerformanceParameters;

/**
 * Performance that gives the sojourn time of activity instances.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ActivityInstanceSojournTimePerformance extends Performance<Long> {

	private static final long serialVersionUID = -4441166944186942390L;

	private ActivityInstanceSojournTimePerformanceParameters parameters;

	public ActivityInstanceSojournTimePerformance() {
		super(PerformanceType.ACTIVITY_SOJOURNTIME);
		setParameters(new ActivityInstanceSojournTimePerformanceParameters());
		ADJECTIVE_GREATER_THAN = "higher";
		ADJECTIVE_SMALLER_THAN = "lower";
		VERB = "be";
	}

	public ActivityInstanceSojournTimePerformanceParameters getParameters() {
		return parameters;
	}

	public void setParameters(ActivityInstanceSojournTimePerformanceParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	protected PerformanceMeasurement<Long> compute(EventCollectionEntity entity, EventCollection collection) {
		PerformanceMeasurement<Long> result = null;
		switch (parameters.getSojournTimeCalculationStrategy()) {
			case BASIC :
				result = (new BasicActivityInstanceSojournTimeStrategy()).compute(entity, collection);
				break;
			default :
				System.out.println("Invalid execution time type parameter");
				break;
		}
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActivityInstanceSojournTimePerformance))
			return false;

		ActivityInstanceSojournTimePerformance performance = (ActivityInstanceSojournTimePerformance) obj;

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
