package org.processmining.contextawareperformance.algorithms.preprocessors;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.parameters.preprocessors.xlog.RemoveEventsWithoutTimestampXLogProcessorParameters;
import org.processmining.contextawareperformance.models.preprocessors.xlog.event.remove.RemoveEventsWithoutTimestampXLogPreprocessor;

/**
 * Algorithm to remove events without timestamps from an event log.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class RemoveEventsWithoutTimestampAlgorithm {

	protected XLog apply(XLog eventlogin, RemoveEventsWithoutTimestampXLogProcessorParameters parameters) {

		XLog eventlogout = parameters.isClone() ? (XLog) eventlogin.clone() : eventlogin;

		return new RemoveEventsWithoutTimestampXLogPreprocessor().preprocess(eventlogout);

	}
}
