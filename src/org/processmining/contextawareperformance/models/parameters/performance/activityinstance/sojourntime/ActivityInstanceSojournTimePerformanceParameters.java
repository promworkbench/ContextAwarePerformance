package org.processmining.contextawareperformance.models.parameters.performance.activityinstance.sojourntime;

import java.util.Objects;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.parameters.performance.PerformanceParameters;
import org.processmining.contextawareperformance.view.dialogs.PerformanceProMTitledScrollContainerChild;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;

public class ActivityInstanceSojournTimePerformanceParameters extends PerformanceParameters {

	private static final long serialVersionUID = 3944807104497538017L;

	public static final String DESCRIPTION;
	public static final SojournTimeCalculationStrategy DEFAULT_SOJOURNTIMECALCULATIONSTRATEGY;

	static {
		DESCRIPTION = "Activity instance sojourn time parameters";
		DEFAULT_SOJOURNTIMECALCULATIONSTRATEGY = SojournTimeCalculationStrategy.BASIC;
	}

	private SojournTimeCalculationStrategy sojournTimeCalculationStrategy;

	public ActivityInstanceSojournTimePerformanceParameters() {
		super(DESCRIPTION);
		setSojournTimeCalculationStrategy(DEFAULT_SOJOURNTIMECALCULATIONSTRATEGY);
	}

	public SojournTimeCalculationStrategy getSojournTimeCalculationStrategy() {
		return sojournTimeCalculationStrategy;
	}

	public void setSojournTimeCalculationStrategy(SojournTimeCalculationStrategy sojournTimeCalculationStrategy) {
		this.sojournTimeCalculationStrategy = sojournTimeCalculationStrategy;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ActivityInstanceSojournTimePerformanceParameters))
			return false;

		ActivityInstanceSojournTimePerformanceParameters parameters = (ActivityInstanceSojournTimePerformanceParameters) obj;

		if (!parameters.getSojournTimeCalculationStrategy().equals(getSojournTimeCalculationStrategy()))
			return false;

		return super.equals(parameters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), sojournTimeCalculationStrategy);
	}

	@Override
	public String toString() {
		return "Strategy: " + getSojournTimeCalculationStrategy().description;
	}

	//@formatter:off
	public enum SojournTimeCalculationStrategy {
		BASIC("Sojourn time equals time between complete timestamp of current event and start timestamp of previous event.");
		
		String description;

		private SojournTimeCalculationStrategy(String description) {
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
