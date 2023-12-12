package org.processmining.contextawareperformance.models.parameters.performance.activityinstance.waitingtime;

import java.util.Objects;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.parameters.performance.PerformanceParameters;
import org.processmining.contextawareperformance.view.dialogs.PerformanceProMTitledScrollContainerChild;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;

public class ActivityInstanceWaitingTimePerformanceParameters extends PerformanceParameters {

	private static final long serialVersionUID = 3660358704424686452L;

	public static final String DESCRIPTION;
	public static final WaitingTimeCalculationStrategy DEFAULT_WAITINGTIMECALCULATIONSTRATEGY;

	static {
		DESCRIPTION = "Activity instance waiting time parameters";
		DEFAULT_WAITINGTIMECALCULATIONSTRATEGY = WaitingTimeCalculationStrategy.BASIC;
	}

	private WaitingTimeCalculationStrategy waitingTimeCalculationStrategy;

	public ActivityInstanceWaitingTimePerformanceParameters() {
		super(DESCRIPTION);
		setWaitingTimeCalculationStrategy(DEFAULT_WAITINGTIMECALCULATIONSTRATEGY);
	}

	public WaitingTimeCalculationStrategy getWaitingTimeCalculationStrategy() {
		return waitingTimeCalculationStrategy;
	}

	public void setWaitingTimeCalculationStrategy(WaitingTimeCalculationStrategy waitingTimeCalculationStrategy) {
		this.waitingTimeCalculationStrategy = waitingTimeCalculationStrategy;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActivityInstanceWaitingTimePerformanceParameters))
			return false;

		ActivityInstanceWaitingTimePerformanceParameters parameters = (ActivityInstanceWaitingTimePerformanceParameters) obj;

		if (!parameters.getWaitingTimeCalculationStrategy().equals(getWaitingTimeCalculationStrategy()))
			return false;

		return super.equals(parameters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), waitingTimeCalculationStrategy);
	}

	@Override
	public String toString() {
		return "Strategy: " + getWaitingTimeCalculationStrategy().description;
	}

	//@formatter:off
	public enum WaitingTimeCalculationStrategy {
		BASIC("Waiting time equals time between complete timestamps of current and previous event.");
		
		String description;

		private WaitingTimeCalculationStrategy(String description) {
			this.description = description;
		}

		@Override
		public String toString() {
			return description;
		}
	}
	//@formatter:on

	@Override
	public PerformanceProMTitledScrollContainerChild getGUIPanel(ProMScrollContainer parent, Performance<?> performance,
			XLog eventlog) {
		PerformanceProMTitledScrollContainerChild panel = new PerformanceProMTitledScrollContainerChild(parent,
				performance);
		return panel;
	}
}
