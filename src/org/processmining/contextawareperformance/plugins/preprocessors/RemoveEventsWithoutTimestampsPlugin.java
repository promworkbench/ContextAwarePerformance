package org.processmining.contextawareperformance.plugins.preprocessors;

import java.util.Collection;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.algorithms.preprocessors.RemoveEventsWithoutTimestampAlgorithm;
import org.processmining.contextawareperformance.connections.RemoveEventsWithoutTimestampConnection;
import org.processmining.contextawareperformance.constants.AuthorConstants;
import org.processmining.contextawareperformance.constants.help.RemoveEventsWithoutTimestampsHelp;
import org.processmining.contextawareperformance.models.parameters.preprocessors.xlog.RemoveEventsWithoutTimestampXLogProcessorParameters;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.connections.ConnectionCannotBeObtained;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

@Plugin(
		name = "Remove events without timestamps",
		parameterLabels = { "Event log", "Parameters" },
		returnLabels = { "Event log with only events that have timestamps." },
		returnTypes = { XLog.class },
		help = RemoveEventsWithoutTimestampsHelp.TEXT)
public class RemoveEventsWithoutTimestampsPlugin extends RemoveEventsWithoutTimestampAlgorithm {

	/**
	 * The plug-in variant that runs in any context and uses the default
	 * parameters.
	 * 
	 * @param context
	 *            The context to run in.
	 * @param eventlogin
	 *            The event log.
	 * @return The cleaned event log.
	 */
	@UITopiaVariant(
			author = AuthorConstants.NAME,
			email = AuthorConstants.EMAIL,
			affiliation = AuthorConstants.AFFILIATION)
	@PluginVariant(
			variantLabel = "Remove events without timestamps, default",
			requiredParameterLabels = { 0 })
	public XLog runDefault(PluginContext context, XLog eventlogin) {
		// Get the default parameters.
		RemoveEventsWithoutTimestampXLogProcessorParameters parameters = new RemoveEventsWithoutTimestampXLogProcessorParameters();
		// Apply the algorithm depending on whether a connection already exists.
		return runConnections(context, eventlogin, parameters);
	}

	/**
	 * The plug-in variant that runs in any context and requires parameters.
	 * 
	 * @param context
	 *            The context to run in.
	 * @param eventlogin
	 *            The event log.
	 * @param parameters
	 *            The parameters.
	 * @return The cleaned event log.
	 */
	@UITopiaVariant(
			author = AuthorConstants.NAME,
			email = AuthorConstants.EMAIL,
			affiliation = AuthorConstants.AFFILIATION)
	@PluginVariant(
			variantLabel = "Remove events without timestamps, parameters",
			requiredParameterLabels = { 0, 1 })
	public XLog runDefault(PluginContext context, XLog eventlogin,
			RemoveEventsWithoutTimestampXLogProcessorParameters parameters) {
		// Apply the algorithm depending on whether a connection already exists.
		return runConnections(context, eventlogin, parameters);
	}

	/**
	 * Apply the algorithm depending on whether a connection already exists.
	 * 
	 * @param context
	 *            The context to run in.
	 * @param eventlogin
	 *            The event log.
	 * @param parameters
	 * @return The cleaned event log.
	 */
	private XLog runConnections(PluginContext context, XLog eventlogin,
			RemoveEventsWithoutTimestampXLogProcessorParameters parameters) {
		if (parameters.isTryConnections()) {
			// Try to found a connection that matches the inputs and the parameters.
			Collection<RemoveEventsWithoutTimestampConnection> connections;
			try {
				connections = context.getConnectionManager()
						.getConnections(RemoveEventsWithoutTimestampConnection.class, context, eventlogin);
				for (RemoveEventsWithoutTimestampConnection connection : connections) {
					if (connection.getObjectWithRole(RemoveEventsWithoutTimestampConnection.EVENTLOGIN)
							.equals(eventlogin) && connection.getParameters().equals(parameters)) {
						// Found a match. Return the associated output as result of the algorithm.
						return connection.getObjectWithRole(RemoveEventsWithoutTimestampConnection.EVENTLOGOUT);
					}
				}
			} catch (ConnectionCannotBeObtained e) {
			}
		}
		// No connection found. Apply the algorithm to compute a fresh output result.
		XLog eventlogout = apply(eventlogin, parameters);
		if (parameters.isTryConnections()) {
			// Store a connection containing the inputs, output, and parameters.
			context.getConnectionManager()
					.addConnection(new RemoveEventsWithoutTimestampConnection(eventlogin, eventlogout, parameters));
		}
		// Return the output.
		return eventlogout;
	}

}
