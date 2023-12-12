package org.processmining.contextawareperformance.models.preprocessors.xlog.trace.reorder;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.parameters.preprocessors.xlog.ReorderTracesXLogProcessorParameters;

public interface IReorderTracesXLogPreprocessorStrategy {

	XLog reorder(XLog eventlog, ReorderTracesXLogProcessorParameters parameters);

}
