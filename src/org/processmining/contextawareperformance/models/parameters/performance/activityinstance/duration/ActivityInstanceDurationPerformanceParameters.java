package org.processmining.contextawareperformance.models.parameters.performance.activityinstance.duration;

import java.util.Objects;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.parameters.performance.PerformanceParameters;
import org.processmining.contextawareperformance.view.dialogs.PerformanceProMTitledScrollContainerChild;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;

public class ActivityInstanceDurationPerformanceParameters extends PerformanceParameters {

	private static final long serialVersionUID = 2689351104366544637L;

	public static final String DESCRIPTION;
	public static final DurationCalculationStrategy DEFAULT_DURATIONCALCULATIONSTRATEGY;

	static {
		DESCRIPTION = "Activity instance duration parameters";
		DEFAULT_DURATIONCALCULATIONSTRATEGY = DurationCalculationStrategy.BASIC;
	}

	private DurationCalculationStrategy durationCalculationStrategy;

	public ActivityInstanceDurationPerformanceParameters() {
		super(DESCRIPTION);
		setDurationCalculationStrategy(DEFAULT_DURATIONCALCULATIONSTRATEGY);
	}

	public DurationCalculationStrategy getDurationCalculationStrategy() {
		return durationCalculationStrategy;
	}

	public void setDurationCalculationStrategy(DurationCalculationStrategy durationCalculationStrategy) {
		this.durationCalculationStrategy = durationCalculationStrategy;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActivityInstanceDurationPerformanceParameters))
			return false;

		ActivityInstanceDurationPerformanceParameters parameters = (ActivityInstanceDurationPerformanceParameters) obj;

		if (!parameters.getDurationCalculationStrategy().equals(getDurationCalculationStrategy()))
			return false;

		return super.equals(parameters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), durationCalculationStrategy);
	}

	@Override
	public String toString() {
		return "Strategy: " + getDurationCalculationStrategy().description;
	}

	//@formatter:off
	public enum DurationCalculationStrategy {
		BASIC("Ignore missing complete timestamps and assume atomic activities for missing start timestamps.");

		String description;

		private DurationCalculationStrategy(String description) {
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
