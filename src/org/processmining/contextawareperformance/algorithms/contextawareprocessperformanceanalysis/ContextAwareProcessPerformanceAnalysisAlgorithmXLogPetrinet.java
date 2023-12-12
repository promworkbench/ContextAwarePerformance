package org.processmining.contextawareperformance.algorithms.contextawareprocessperformanceanalysis;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contextawareperformance.models.exceptions.NotImplementedYetException;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisParameters;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.models.graphbased.directed.petrinet.Petrinet;

/**
 * Algorithm that computes performance statistics based on the event log and a
 * Petri net.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ContextAwareProcessPerformanceAnalysisAlgorithmXLogPetrinet {

	protected static ContextAwareProcessPerformanceAnalysisOutput apply(PluginContext pluginContext, XLog eventlog,
			Petrinet petrinet, ContextAwareProcessPerformanceAnalysisParameters parameters) {

		throw new NotImplementedYetException("Not implemented yet. Use log-only variant.");

	}

}
