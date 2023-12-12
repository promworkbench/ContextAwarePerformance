package org.processmining.contextawareperformance.plugins.contextawareprocessperformanceanalysis;

import java.util.Collection;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.algorithms.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisAlgorithmXLog;
import org.processmining.contextawareperformance.connections.ContextAwareProcessPerformanceAnalysisXLogConnection;
import org.processmining.contextawareperformance.constants.AuthorConstants;
import org.processmining.contextawareperformance.constants.help.ContextAwareProcessPerformanceAnalysisHelp;
import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisParameters;
import org.processmining.contextawareperformance.view.dialogs.ContextAwareProcessPerformanceAnalysisWizard;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.connections.ConnectionCannotBeObtained;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

/**
 * Plug-in that calculates performance characteristics based on only an event
 * log.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
@Plugin(
		name = "Find context-based differences in process performance using event log",
		parameterLabels = { "Event log", "Parameters" },
		returnLabels = { "Performance analysis" },
		returnTypes = { ContextAwareProcessPerformanceAnalysisOutput.class },
		help = ContextAwareProcessPerformanceAnalysisHelp.TEXT)
public class ContextAwareProcessPerformanceAnalysisXLogPlugin
		extends ContextAwareProcessPerformanceAnalysisAlgorithmXLog {

	@UITopiaVariant(
			author = AuthorConstants.NAME,
			email = AuthorConstants.EMAIL,
			affiliation = AuthorConstants.AFFILIATION)
	@PluginVariant(
			variantLabel = "Performance analysis, Log only, UI",
			requiredParameterLabels = { 0 })
	public ContextAwareProcessPerformanceAnalysisOutput runUI(UIPluginContext pluginContext, XLog eventlog) {
		ContextAwareProcessPerformanceAnalysisParameters parameters = ContextAwareProcessPerformanceAnalysisWizard
				.show(pluginContext, eventlog);

		if (parameters == null) {
			// The dialog was cancelled
			pluginContext.getFutureResult(0).cancel(true);
			return null;
		}

		return runConnectionsXLog(pluginContext, eventlog, parameters);
	}

	@PluginVariant(
			variantLabel = "Performance analysis, Log only, Parameters",
			requiredParameterLabels = { 0, 1 })
	public ContextAwareProcessPerformanceAnalysisOutput runParameters(PluginContext pluginContext, XLog eventlog,
			ContextAwareProcessPerformanceAnalysisParameters parameters) {
		return runConnectionsXLog(pluginContext, eventlog, parameters);
	}

	@PluginVariant(
			variantLabel = "Performance analysis, Log only, Default parameters",
			requiredParameterLabels = { 0 })
	public ContextAwareProcessPerformanceAnalysisOutput runDefault(PluginContext pluginContext, XLog eventlog) {
		ContextAwareProcessPerformanceAnalysisParameters parameters = new ContextAwareProcessPerformanceAnalysisParameters();
		return runConnectionsXLog(pluginContext, eventlog, parameters);
	}

	private ContextAwareProcessPerformanceAnalysisOutput runConnectionsXLog(PluginContext pluginContext, XLog eventlog,
			ContextAwareProcessPerformanceAnalysisParameters parameters) {

		if (parameters.isTryConnections()) {
			Collection<ContextAwareProcessPerformanceAnalysisXLogConnection> connections;
			try {
				connections = pluginContext.getConnectionManager().getConnections(
						ContextAwareProcessPerformanceAnalysisXLogConnection.class, pluginContext, eventlog);
				for (ContextAwareProcessPerformanceAnalysisXLogConnection connection : connections) {
					if (connection.getObjectWithRole(ContextAwareProcessPerformanceAnalysisXLogConnection.EVENTLOG)
							.equals(eventlog) && connection.getParameters().equals(parameters)) {
						parameters.displayMessage("Connection found, returning the previously calculated output.");
						return connection
								.getObjectWithRole(ContextAwareProcessPerformanceAnalysisXLogConnection.OUTPUT);
					}
				}
			} catch (ConnectionCannotBeObtained e) {
				parameters.displayMessage("No connection found, have to calculate now.");
			}
		}

		ContextAwareProcessPerformanceAnalysisOutput output = runPrivate(pluginContext, eventlog, parameters);

		if (parameters.isTryConnections()) {
			pluginContext.getConnectionManager().addConnection(
					new ContextAwareProcessPerformanceAnalysisXLogConnection(eventlog, output, parameters));
		}

		return output;
	}

	private ContextAwareProcessPerformanceAnalysisOutput runPrivate(PluginContext pluginContext, XLog eventlog,
			ContextAwareProcessPerformanceAnalysisParameters parameters) {
		long time = -System.currentTimeMillis();
		parameters.displayMessage("[Algorithm] Start");
		parameters.displayMessage("[Algorithm] Parameters: " + parameters.toString());

		ContextAwareProcessPerformanceAnalysisOutput output;
		output = apply(pluginContext, eventlog, parameters);

		time += System.currentTimeMillis();
		parameters.displayMessage("[Algorithm] End (took " + DurationFormatUtils.formatDurationHMS(time) + ").");
		return output;
	}

}
