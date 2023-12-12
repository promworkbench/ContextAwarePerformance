package org.processmining.contextawareperformance.models.parameters.performance.trace.fitness;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.parameters.performance.PerformanceParameters;
import org.processmining.contextawareperformance.view.dialogs.PerformanceProMTitledScrollContainerChild;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;

public class CaseFitnessPerformanceParameters extends PerformanceParameters {

	private static final long serialVersionUID = 8866425534212848958L;

	public static final String DESCRIPTION;

	static {
		DESCRIPTION = "Case fitness parameters";
	}

	private Petrinet petriNet;
	private UIPluginContext context;

	public CaseFitnessPerformanceParameters() {
		super(DESCRIPTION);
	}

	public Petrinet getPetriNet() {
		return petriNet;
	}

	public void setPetriNet(Petrinet petriNet) {
		this.petriNet = petriNet;
	}

	public UIPluginContext getContext() {
		return context;
	}

	public void setContext(UIPluginContext pluginContext) {
		this.context = pluginContext;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("Petri net:" + petriNet.getLabel());

		return builder.toString();
	}

	@Override
	public PerformanceProMTitledScrollContainerChild getGUIPanel(ProMScrollContainer parent, Performance<?> performance,
			XLog eventlog) {
		PerformanceProMTitledScrollContainerChild panel = new PerformanceProMTitledScrollContainerChild(parent,
				performance);
		return panel;
	}

}
