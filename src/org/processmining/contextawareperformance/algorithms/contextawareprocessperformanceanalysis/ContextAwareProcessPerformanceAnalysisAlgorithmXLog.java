package org.processmining.contextawareperformance.algorithms.contextawareprocessperformanceanalysis;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisParameters;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.XLogToEventCollectionParameters;
import org.processmining.framework.plugin.PluginContext;

/**
 * Algorithm that computes performance statistics based on the event log alone.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ContextAwareProcessPerformanceAnalysisAlgorithmXLog {

	protected static ContextAwareProcessPerformanceAnalysisOutput apply(PluginContext pluginContext, XLog eventlog,
			ContextAwareProcessPerformanceAnalysisParameters parameters) {

		XLogToEventCollectionParameters conversionParameters = new XLogToEventCollectionParameters();
		conversionParameters.setClone(parameters.isClone());

		EventCollection collection = XLogToEventCollectionAlgorithm.apply(pluginContext, eventlog,
				conversionParameters);

		return ContextAwareProcessPerformanceAnalysisAlgorithmEventCollection.apply(pluginContext, collection,
				parameters);

	}

}
