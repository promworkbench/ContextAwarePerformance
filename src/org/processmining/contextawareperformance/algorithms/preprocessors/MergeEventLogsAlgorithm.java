package org.processmining.contextawareperformance.algorithms.preprocessors;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.preprocessors.xlogs.merge.MergeEventLogsXLogsPreprocessor;

/**
 * Algorithm to merge multiple event logs into one event log.
 * 
 * @author B.F.A. Hompes
 *
 */
public class MergeEventLogsAlgorithm {

	protected XLog apply(XLog... eventlogs) {

		return new MergeEventLogsXLogsPreprocessor().preprocess(eventlogs);

	}
}
