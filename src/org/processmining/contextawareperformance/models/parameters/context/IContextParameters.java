package org.processmining.contextawareperformance.models.parameters.context;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.view.dialogs.ContextProMTitledScrollContainerChild;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;

public interface IContextParameters {

	ContextProMTitledScrollContainerChild getGUIPanel(ProMScrollContainer parent, Context<?> context, XLog eventlog);

}
