package org.processmining.contextawareperformance.models.parameters.performance;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.view.dialogs.PerformanceProMTitledScrollContainerChild;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;

public interface IPerformanceParameters {

	PerformanceProMTitledScrollContainerChild getGUIPanel(ProMScrollContainer parent, Performance<?> performance, XLog eventlog);

}
