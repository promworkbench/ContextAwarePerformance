package org.processmining.contextawareperformance.plugins.preprocessors;

import java.util.Collection;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.algorithms.preprocessors.ReorderTracesAlgorithm;
import org.processmining.contextawareperformance.connections.ReorderTracesConnection;
import org.processmining.contextawareperformance.constants.AuthorConstants;
import org.processmining.contextawareperformance.constants.help.ReorderTracesHelp;
import org.processmining.contextawareperformance.models.parameters.preprocessors.xlog.ReorderTracesXLogProcessorParameters;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.connections.ConnectionCannotBeObtained;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

@Plugin(
		name = "Reorder traces in event log",
		parameterLabels = { "Event log", "Parameters" },
		returnLabels = { "Event log with reordered traces." },
		returnTypes = { XLog.class },
		help = ReorderTracesHelp.TEXT)
public class ReorderTracesPlugin extends ReorderTracesAlgorithm {

	/**
	 * The plug-in variant that runs in any context and uses the default
	 * parameters.
	 * 
	 * @param context
	 *            The context to run in.
	 * @param eventlogin
	 *            The event log.
	 * @return The reordered event log.
	 */
	@UITopiaVariant(
			uiHelp = ReorderTracesHelp.TEXT_DEFAULT,
			author = AuthorConstants.NAME,
			email = AuthorConstants.EMAIL,
			affiliation = AuthorConstants.AFFILIATION)
	@PluginVariant(
			variantLabel = "Reorder event log, default",
			requiredParameterLabels = { 0 })
	public XLog runDefault(PluginContext context, XLog eventlogin) {
		// Get the default parameters.
		ReorderTracesXLogProcessorParameters parameters = new ReorderTracesXLogProcessorParameters();
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
	 *            The parameters to use.
	 * @return The reordered event log.
	 */
	@UITopiaVariant(
			uiHelp = ReorderTracesHelp.TEXT,
			author = AuthorConstants.NAME,
			email = AuthorConstants.EMAIL,
			affiliation = AuthorConstants.AFFILIATION)
	@PluginVariant(
			variantLabel = "Reorder event log, parameters",
			requiredParameterLabels = { 0, 1 })
	public XLog run(PluginContext context, XLog eventlogin, ReorderTracesXLogProcessorParameters parameters) {
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
	 * @return The reordered event log.
	 */
	private XLog runConnections(PluginContext context, XLog eventlogin,
			ReorderTracesXLogProcessorParameters parameters) {
		if (parameters.isTryConnections()) {
			// Try to found a connection that matches the inputs and the parameters.
			Collection<ReorderTracesConnection> connections;
			try {
				connections = context.getConnectionManager().getConnections(ReorderTracesConnection.class, context,
						eventlogin);
				for (ReorderTracesConnection connection : connections) {
					if (connection.getObjectWithRole(ReorderTracesConnection.EVENTLOGIN).equals(eventlogin)
							&& connection.getParameters().equals(parameters)) {
						// Found a match. Return the associated output as result of the algorithm.
						return connection.getObjectWithRole(ReorderTracesConnection.EVENTLOGOUT);
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
					.addConnection(new ReorderTracesConnection(eventlogin, eventlogout, parameters));
		}
		// Return the output.
		return eventlogout;
	}

}
