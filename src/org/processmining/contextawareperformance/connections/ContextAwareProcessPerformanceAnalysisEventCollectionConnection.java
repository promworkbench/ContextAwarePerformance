package org.processmining.contextawareperformance.connections;

import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisParameters;
import org.processmining.framework.connections.impl.AbstractConnection;

/**
 * Connection between an event collection and context-aware performance output
 * calculated based on only that event collection.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ContextAwareProcessPerformanceAnalysisEventCollectionConnection extends AbstractConnection {
	public static final String EVENTCOLLECTION = "Event collection";
	public static final String OUTPUT = "Output";

	private ContextAwareProcessPerformanceAnalysisParameters parameters;

	public ContextAwareProcessPerformanceAnalysisEventCollectionConnection(EventCollection collection,
			ContextAwareProcessPerformanceAnalysisOutput output,
			ContextAwareProcessPerformanceAnalysisParameters parameters) {
		super("Connection");
		put(EVENTCOLLECTION, collection);
		put(OUTPUT, output);
		this.parameters = parameters;
	}

	public ContextAwareProcessPerformanceAnalysisParameters getParameters() {
		return parameters;
	}

}
