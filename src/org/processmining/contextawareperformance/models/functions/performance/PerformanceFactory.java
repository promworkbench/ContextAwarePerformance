package org.processmining.contextawareperformance.models.functions.performance;

import org.processmining.contextawareperformance.models.functions.performance.activityinstance.duration.ActivityInstanceDurationPerformance;
import org.processmining.contextawareperformance.models.functions.performance.activityinstance.sojourntime.ActivityInstanceSojournTimePerformance;
import org.processmining.contextawareperformance.models.functions.performance.activityinstance.waitingtime.ActivityInstanceWaitingTimePerformance;
import org.processmining.contextawareperformance.models.functions.performance.trace.duration.CaseDurationBetweenTwoActivitiesPerformance;
import org.processmining.contextawareperformance.models.functions.performance.trace.duration.CaseDurationPerformance;
import org.processmining.contextawareperformance.models.functions.performance.trace.duration.CaseDurationUntilActivityPerformance;
import org.processmining.contextawareperformance.models.functions.performance.trace.fitness.CaseFitnessPerformance;
import org.processmining.contextawareperformance.models.functions.performance.trace.occurrence.CaseActivityOccurrencePerformance;

public class PerformanceFactory {
	public static Performance<?> construct(PerformanceType type) {
		Performance<?> performance = null;
		switch (type) {
			case ACTIVITY_DURATION :
				performance = new ActivityInstanceDurationPerformance();
				break;
			case ACTIVITY_SOJOURNTIME :
				performance = new ActivityInstanceSojournTimePerformance();
				break;
			case ACTIVITY_WAITINGTIME :
				performance = new ActivityInstanceWaitingTimePerformance();
				break;
			case CASE_ACTIVITYOCCURRENCE :
				performance = new CaseActivityOccurrencePerformance();
				break;
			case CASE_DURATION :
				performance = new CaseDurationPerformance();
				break;
			case CASE_DURATIONBETWEENTWOACTIVITIES :
				performance = new CaseDurationBetweenTwoActivitiesPerformance();
				break;
			case CASE_DURATIONUNTILACTIVITY :
				performance = new CaseDurationUntilActivityPerformance();
				break;
			case CASE_FITNESS :
				performance = new CaseFitnessPerformance();
				break;
			default :
				// We should never reach the default statement
				System.out.println("The given performance type is not implemented in the factory yet.");
				break;
		}
		return performance;
	}
}
