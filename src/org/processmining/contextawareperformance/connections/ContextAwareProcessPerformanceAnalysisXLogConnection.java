package org.processmining.contextawareperformance.connections;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisParameters;
import org.processmining.framework.connections.impl.AbstractConnection;

/**
 * Connection between an event log and context-aware performance output
 * calculated based on only that event log.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ContextAwareProcessPerformanceAnalysisXLogConnection extends AbstractConnection {
	public static final String EVENTLOG = "Event log";
	public static final String OUTPUT = "Output";

	private ContextAwareProcessPerformanceAnalysisParameters parameters;

	public ContextAwareProcessPerformanceAnalysisXLogConnection(XLog eventlog,
			ContextAwareProcessPerformanceAnalysisOutput output,
			ContextAwareProcessPerformanceAnalysisParameters parameters) {
		super("Connection");
		put(EVENTLOG, eventlog);
		put(OUTPUT, output);
		this.parameters = parameters;
	}

	public ContextAwareProcessPerformanceAnalysisParameters getParameters() {
		return parameters;
	}

}
