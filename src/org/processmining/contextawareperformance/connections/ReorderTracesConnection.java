package org.processmining.contextawareperformance.connections;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.parameters.preprocessors.xlog.ReorderTracesXLogProcessorParameters;
import org.processmining.framework.connections.impl.AbstractConnection;

/**
 * Connection between an input event log and event log with reordered traces.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ReorderTracesConnection extends AbstractConnection {

	public static final String EVENTLOGIN = "Event log";
	public static final String EVENTLOGOUT = "Reorderd event log";

	private ReorderTracesXLogProcessorParameters parameters;

	public ReorderTracesConnection(XLog eventlogin, XLog eventlogout, ReorderTracesXLogProcessorParameters parameters) {
		super("Reorder traces connection");
		put(EVENTLOGIN, eventlogin);
		put(EVENTLOGOUT, eventlogout);
		this.parameters = parameters;
	}

	public ReorderTracesXLogProcessorParameters getParameters() {
		return parameters;
	}

}
