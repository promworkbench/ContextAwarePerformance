package org.processmining.contextawareperformance.connections;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.XLogToEventCollectionParameters;
import org.processmining.framework.connections.impl.AbstractConnection;

/**
 * Connection between an event log and an event collection created from that
 * event log.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class XLogToEventCollectionConnection extends AbstractConnection {

	public static final String EVENTLOG = "Event log";
	public static final String EVENTCOLLECTION = "Event collection";

	private XLogToEventCollectionParameters parameters;

	public XLogToEventCollectionConnection(XLog eventlogin, EventCollection collection,
			XLogToEventCollectionParameters parameters) {
		super("Event log to event collection connection");
		put(EVENTLOG, eventlogin);
		put(EVENTCOLLECTION, collection);
		this.parameters = parameters;
	}

	public XLogToEventCollectionParameters getParameters() {
		return parameters;
	}

}
