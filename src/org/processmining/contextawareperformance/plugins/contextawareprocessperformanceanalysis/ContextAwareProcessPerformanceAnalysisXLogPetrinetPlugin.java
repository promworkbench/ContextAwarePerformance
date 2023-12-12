package org.processmining.contextawareperformance.plugins.contextawareprocessperformanceanalysis;

import java.util.Collection;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.algorithms.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisAlgorithmXLogPetrinet;
import org.processmining.contextawareperformance.connections.ContextAwareProcessPerformanceAnalysisXLogPetrinetConnection;
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
import org.processmining.models.graphbased.directed.petrinet.Petrinet;

/**
 * Plug-in that calculates performance characteristics based on an event log and
 * a Petri net.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
@Plugin(
		name = "Find context-based differences in process performance using event log and Petri net",
		parameterLabels = { "Event log", "Petri net", "Parameters" },
		returnLabels = { "Performance analysis" },
		returnTypes = { ContextAwareProcessPerformanceAnalysisOutput.class },
		help = ContextAwareProcessPerformanceAnalysisHelp.TEXT)
public class ContextAwareProcessPerformanceAnalysisXLogPetrinetPlugin
		extends ContextAwareProcessPerformanceAnalysisAlgorithmXLogPetrinet {

	@UITopiaVariant(
			author = AuthorConstants.NAME,
			email = AuthorConstants.EMAIL,
			affiliation = AuthorConstants.AFFILIATION)
	@PluginVariant(
			variantLabel = "Performance analysis, Log & Petri net, UI",
			requiredParameterLabels = { 0, 1 })
	public ContextAwareProcessPerformanceAnalysisOutput runUI(UIPluginContext pluginContext, XLog eventlog,
			Petrinet petrinet) {
		ContextAwareProcessPerformanceAnalysisParameters parameters = ContextAwareProcessPerformanceAnalysisWizard
				.show(pluginContext, eventlog);

		if (parameters == null) {
			// The dialog was cancelled
			pluginContext.getFutureResult(0).cancel(true);
			return null;
		}

		return runConnectionsXLogPetrinet(pluginContext, eventlog, petrinet, parameters);
	}

	@PluginVariant(
			variantLabel = "Performance analysis, Log & Petri net, Parameters",
			requiredParameterLabels = { 0, 1, 2 })
	public ContextAwareProcessPerformanceAnalysisOutput runParameters(PluginContext pluginContext, XLog eventlog,
			Petrinet petrinet, ContextAwareProcessPerformanceAnalysisParameters parameters) {
		return runConnectionsXLogPetrinet(pluginContext, eventlog, petrinet, parameters);
	}

	@PluginVariant(
			variantLabel = "Performance analysis, Log & Petri net, Default parameters",
			requiredParameterLabels = { 0 })
	public ContextAwareProcessPerformanceAnalysisOutput runDefault(PluginContext pluginContext, XLog eventlog,
			Petrinet petrinet) {
		ContextAwareProcessPerformanceAnalysisParameters parameters = new ContextAwareProcessPerformanceAnalysisParameters();
		return runConnectionsXLogPetrinet(pluginContext, eventlog, petrinet, parameters);
	}

	private ContextAwareProcessPerformanceAnalysisOutput runConnectionsXLogPetrinet(PluginContext pluginContext,
			XLog eventlog, Petrinet petrinet, ContextAwareProcessPerformanceAnalysisParameters parameters) {

		if (parameters.isTryConnections()) {
			Collection<ContextAwareProcessPerformanceAnalysisXLogPetrinetConnection> connections;
			try {
				connections = pluginContext.getConnectionManager().getConnections(
						ContextAwareProcessPerformanceAnalysisXLogPetrinetConnection.class, pluginContext, eventlog);
				for (ContextAwareProcessPerformanceAnalysisXLogPetrinetConnection connection : connections) {
					if (connection
							.getObjectWithRole(ContextAwareProcessPerformanceAnalysisXLogPetrinetConnection.EVENTLOG)
							.equals(eventlog)
							&& connection
									.getObjectWithRole(
											ContextAwareProcessPerformanceAnalysisXLogPetrinetConnection.PETRINET)
									.equals(petrinet)
							&& connection.getParameters().equals(parameters)) {
						parameters.displayMessage("Connection found, returning the previously calculated output.");
						return connection
								.getObjectWithRole(ContextAwareProcessPerformanceAnalysisXLogPetrinetConnection.OUTPUT);
					}
				}
			} catch (ConnectionCannotBeObtained e) {
				parameters.displayMessage("No connection found, have to calculate now.");
			}
		}

		ContextAwareProcessPerformanceAnalysisOutput output = runPrivate(pluginContext, eventlog, petrinet, parameters);

		if (parameters.isTryConnections()) {
			pluginContext.getConnectionManager()
					.addConnection(new ContextAwareProcessPerformanceAnalysisXLogPetrinetConnection(eventlog, petrinet,
							output, parameters));
		}

		return output;
	}

	private ContextAwareProcessPerformanceAnalysisOutput runPrivate(PluginContext pluginContext, XLog eventlog,
			Petrinet petrinet, ContextAwareProcessPerformanceAnalysisParameters parameters) {
		long time = -System.currentTimeMillis();
		parameters.displayMessage("[Algorithm] Start");
		parameters.displayMessage("[Algorithm] Parameters: " + parameters.toString());

		ContextAwareProcessPerformanceAnalysisOutput output;
		output = apply(pluginContext, eventlog, petrinet, parameters);

		time += System.currentTimeMillis();
		parameters.displayMessage("[Algorithm] End (took " + DurationFormatUtils.formatDurationHMS(time) + ").");
		return output;
	}

}
