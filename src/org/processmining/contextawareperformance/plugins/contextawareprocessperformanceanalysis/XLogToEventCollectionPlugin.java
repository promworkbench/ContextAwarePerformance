package org.processmining.contextawareperformance.plugins.contextawareprocessperformanceanalysis;

import java.util.Collection;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.algorithms.contextawareprocessperformanceanalysis.XLogToEventCollectionAlgorithm;
import org.processmining.contextawareperformance.connections.XLogToEventCollectionConnection;
import org.processmining.contextawareperformance.constants.AuthorConstants;
import org.processmining.contextawareperformance.constants.help.XLogToEventCollectionHelp;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.XLogToEventCollectionParameters;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.connections.ConnectionCannotBeObtained;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

/**
 * Plug-in that creates an event collection from an event log.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
@Plugin(
		name = "Create event collection from event log",
		parameterLabels = { "Event log", "Parameters" },
		returnLabels = { "Output" },
		returnTypes = { EventCollection.class },
		help = XLogToEventCollectionHelp.TEXT)
public class XLogToEventCollectionPlugin extends XLogToEventCollectionAlgorithm {

	@UITopiaVariant(
			author = AuthorConstants.NAME,
			email = AuthorConstants.EMAIL,
			affiliation = AuthorConstants.AFFILIATION)
	@PluginVariant(
			variantLabel = "Convert Event log to Event Collection",
			requiredParameterLabels = { 0 })
	public EventCollection runUI(UIPluginContext pluginContext, XLog eventlog) {
		XLogToEventCollectionParameters parameters = new XLogToEventCollectionParameters();
		parameters.setClone(false);
		return runConnectionsXLog(pluginContext, eventlog, parameters);
	}

	private EventCollection runConnectionsXLog(PluginContext pluginContext, XLog eventlog,
			XLogToEventCollectionParameters parameters) {
		if (parameters.isTryConnections()) {
			Collection<XLogToEventCollectionConnection> connections;
			try {
				connections = pluginContext.getConnectionManager().getConnections(XLogToEventCollectionConnection.class,
						pluginContext, eventlog);
				for (XLogToEventCollectionConnection connection : connections) {
					if (connection.getObjectWithRole(XLogToEventCollectionConnection.EVENTLOG).equals(eventlog)
							&& connection.getParameters().equals(parameters)) {
						parameters.displayMessage("Connection found, returning the previously calculated output.");
						return connection.getObjectWithRole(XLogToEventCollectionConnection.EVENTCOLLECTION);
					}
				}
			} catch (ConnectionCannotBeObtained e) {
				parameters.displayMessage("No connection found, have to calculate now.");
			}
		}

		EventCollection output = runPrivate(pluginContext, eventlog, parameters);

		if (parameters.isTryConnections()) {
			pluginContext.getConnectionManager()
					.addConnection(new XLogToEventCollectionConnection(eventlog, output, parameters));
		}

		return output;
	}

	private EventCollection runPrivate(PluginContext pluginContext, XLog eventlog,
			XLogToEventCollectionParameters parameters) {
		long time = -System.currentTimeMillis();
		parameters.displayMessage("[Algorithm] Start");
		parameters.displayMessage("[Algorithm] Parameters: " + parameters.toString());

		EventCollection output;
		output = apply(pluginContext, eventlog, parameters);

		time += System.currentTimeMillis();
		parameters.displayMessage("[Algorithm] End (took " + DurationFormatUtils.formatDurationHMS(time) + ").");
		return output;
	}

}
