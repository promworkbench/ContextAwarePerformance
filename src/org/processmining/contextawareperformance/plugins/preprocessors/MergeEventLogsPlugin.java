package org.processmining.contextawareperformance.plugins.preprocessors;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.algorithms.preprocessors.MergeEventLogsAlgorithm;
import org.processmining.contextawareperformance.constants.AuthorConstants;
import org.processmining.contextawareperformance.constants.help.MergeEventLogsHelp;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

@Plugin(
		name = "Merge event logs",
		parameterLabels = { "Event logs" },
		returnLabels = { "Merge event logs" },
		returnTypes = { XLog.class },
		help = MergeEventLogsHelp.TEXT)
public class MergeEventLogsPlugin extends MergeEventLogsAlgorithm {

	@UITopiaVariant(
			author = AuthorConstants.NAME,
			email = AuthorConstants.EMAIL,
			affiliation = AuthorConstants.AFFILIATION)
	@PluginVariant(
			variantLabel = "Merge event logs, default",
			requiredParameterLabels = { 0 })
	public XLog runDefault(PluginContext context, XLog... eventlogs) {
		return runConnections(context, eventlogs);
	}

	private XLog runConnections(PluginContext context, XLog... eventlogs) {
		XLog eventlogout = apply(eventlogs);
		return eventlogout;
	}

}
