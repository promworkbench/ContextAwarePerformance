package org.processmining.contextawareperformance.models.parameters.performance.trace.duration;

import java.util.Comparator;

import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.comparators.XEventTimeStampComparator;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.parameters.performance.PerformanceParameters;
import org.processmining.contextawareperformance.view.dialogs.PerformanceProMTitledScrollContainerChild;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;

public class CaseDurationPerformanceParameters extends PerformanceParameters {

	private static final long serialVersionUID = -7345579354506920358L;

	public static final String DESCRIPTION;
	public static final Comparator<XEvent> DEFAULT_COMPARATOR;

	static {
		DESCRIPTION = "Case duration parameters";
		DEFAULT_COMPARATOR = new XEventTimeStampComparator();
	}

	private Comparator<XEvent> comparator;

	public CaseDurationPerformanceParameters() {
		super(DESCRIPTION);
		setComparator(DEFAULT_COMPARATOR);
	}

	public Comparator<XEvent> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<XEvent> comparator) {
		this.comparator = comparator;
	}

	@Override
	public String toString() {
		return "";
	}

	@Override
	public PerformanceProMTitledScrollContainerChild getGUIPanel(ProMScrollContainer parent, Performance<?> performance,
			XLog eventlog) {
		PerformanceProMTitledScrollContainerChild panel = new PerformanceProMTitledScrollContainerChild(parent,
				performance);
		return panel;
	}

}
