package org.processmining.contextawareperformance.models.parameters.context;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.view.dialogs.ContextProMTitledScrollContainerChild;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;

public class DummyContextParameters extends ContextParameters {

	private static final long serialVersionUID = -2485923682337687564L;

	public static final String DESCRIPTION;

	static {
		DESCRIPTION = "Dummy context parameters";
	}

	public DummyContextParameters() {
		super(DESCRIPTION);
	}

	@Override
	public String toString() {
		return "";
	}

	@Override
	public ContextProMTitledScrollContainerChild getGUIPanel(ProMScrollContainer parent, Context<?> context,
			XLog eventlog) {
		ContextProMTitledScrollContainerChild panel = new ContextProMTitledScrollContainerChild(parent, context);
		return panel;
	}

}
