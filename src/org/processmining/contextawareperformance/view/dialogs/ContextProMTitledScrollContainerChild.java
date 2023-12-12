package org.processmining.contextawareperformance.view.dialogs;

import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;
import org.processmining.framework.util.ui.widgets.ProMTitledScrollContainerChild;

public class ContextProMTitledScrollContainerChild extends ProMTitledScrollContainerChild {

	private static final long serialVersionUID = 699752126790755772L;

	private Context<?> context;

	public ContextProMTitledScrollContainerChild(ProMScrollContainer parent, Context<?> context) {
		super(context.getType().getDescription(), parent, true);
		setContext(context);
	}

	public Context<?> getContext() {
		return context;
	}

	public void setContext(Context<?> context) {
		this.context = context;
	}

}
