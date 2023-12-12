package org.processmining.contextawareperformance.connections;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.parameters.preprocessors.xlog.RemoveEventsWithoutTimestampXLogProcessorParameters;
import org.processmining.framework.connections.impl.AbstractConnection;

/**
 * Connection between an input event log and output event log without events
 * without timestamps.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class RemoveEventsWithoutTimestampConnection extends AbstractConnection {

	public static final String EVENTLOGIN = "Event log";
	public static final String EVENTLOGOUT = "Cleaned event log";

	private RemoveEventsWithoutTimestampXLogProcessorParameters parameters;

	public RemoveEventsWithoutTimestampConnection(XLog eventlogin, XLog eventlogout,
			RemoveEventsWithoutTimestampXLogProcessorParameters parameters) {
		super("Remove events without timestamps connection");
		put(EVENTLOGIN, eventlogin);
		put(EVENTLOGOUT, eventlogout);
		this.parameters = parameters;
	}

	public RemoveEventsWithoutTimestampXLogProcessorParameters getParameters() {
		return parameters;
	}

}
