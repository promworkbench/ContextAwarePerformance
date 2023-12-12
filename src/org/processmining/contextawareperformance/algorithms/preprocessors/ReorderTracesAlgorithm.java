package org.processmining.contextawareperformance.algorithms.preprocessors;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.parameters.preprocessors.xlog.ReorderTracesXLogProcessorParameters;
import org.processmining.contextawareperformance.models.preprocessors.xlog.trace.reorder.ReorderTracesXLogProcessor;

/**
 * Algorithm that reorders the events in the traces of an event log.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ReorderTracesAlgorithm {

	protected XLog apply(XLog eventlogin, ReorderTracesXLogProcessorParameters parameters) {

		XLog eventlogout = parameters.isClone() ? (XLog) eventlogin.clone() : eventlogin;

		ReorderTracesXLogProcessor preprocessor = new ReorderTracesXLogProcessor();
		preprocessor.setParameters(parameters);

		return preprocessor.preprocess(eventlogout);

	}
}
