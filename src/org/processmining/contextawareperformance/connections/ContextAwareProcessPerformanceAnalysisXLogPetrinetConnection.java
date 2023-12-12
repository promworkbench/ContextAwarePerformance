package org.processmining.contextawareperformance.connections;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisParameters;
import org.processmining.framework.connections.impl.AbstractConnection;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;

/**
 * Connection between an event log and a Petri net and context-aware performance
 * output calculated based on that event log and Petri net.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ContextAwareProcessPerformanceAnalysisXLogPetrinetConnection extends AbstractConnection {
	public static final String EVENTLOG = "Event log";
	public static final String PETRINET = "Petrinet";
	public static final String OUTPUT = "Output";

	private ContextAwareProcessPerformanceAnalysisParameters parameters;

	public ContextAwareProcessPerformanceAnalysisXLogPetrinetConnection(XLog eventlog, Petrinet petrinet,
			ContextAwareProcessPerformanceAnalysisOutput output,
			ContextAwareProcessPerformanceAnalysisParameters parameters) {
		super("Connection");
		put(EVENTLOG, eventlog);
		put(PETRINET, petrinet);
		put(OUTPUT, output);
	}

	public ContextAwareProcessPerformanceAnalysisParameters getParameters() {
		return parameters;
	}
}
