package org.processmining.contextawareperformance.models.preprocessors.xlog.trace.reorder;

import java.util.Collections;

import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.contextawareperformance.models.comparators.XEventCaseIndexComparator;
import org.processmining.contextawareperformance.models.parameters.preprocessors.xlog.ReorderTracesXLogProcessorParameters;

public class EventCaseIndexOnlyReorderTracesXLogPreprocessorStrategy extends ReorderTracesXLogPreprocessorStrategy {

	@Override
	public XLog reorder(XLog eventlog, ReorderTracesXLogProcessorParameters parameters) {
		if (eventlog.getExtensions().contains(XTimeExtension.instance())) {
			for (XTrace trace : eventlog) {
				Collections.sort(trace, new XEventCaseIndexComparator());
			}
		}
		return eventlog;
	}

}
