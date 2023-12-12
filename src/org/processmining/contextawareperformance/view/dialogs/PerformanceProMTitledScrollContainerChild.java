package org.processmining.contextawareperformance.view.dialogs;

import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;
import org.processmining.framework.util.ui.widgets.ProMTitledScrollContainerChild;

public class PerformanceProMTitledScrollContainerChild extends ProMTitledScrollContainerChild {

	private static final long serialVersionUID = -3569294464599403241L;

	private Performance<?> performance;

	public PerformanceProMTitledScrollContainerChild(ProMScrollContainer parent, Performance<?> performance) {
		super(performance.getType().getDescription(), parent, true);
		setPerformance(performance);
	}

	public Performance<?> getPerformance() {
		return performance;
	}

	public void setPerformance(Performance<?> performance) {
		this.performance = performance;
	}

}
