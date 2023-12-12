package org.processmining.contextawareperformance.models.preprocessors.xlog.trace.reorder;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.parameters.preprocessors.xlog.ReorderTracesXLogProcessorParameters;
import org.processmining.contextawareperformance.models.preprocessors.xlog.XLogPreprocessor;

/**
 * Pre-processes an event log by reordering traces based on some reordering
 * strategy.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ReorderTracesXLogProcessor extends XLogPreprocessor {

	private static final String NAME;

	static {
		NAME = "Reorder traces XLog preprocessor";
	}

	private ReorderTracesXLogProcessorParameters parameters;

	public ReorderTracesXLogProcessor() {
		super(NAME);
		setParameters(new ReorderTracesXLogProcessorParameters());
	}

	public ReorderTracesXLogProcessorParameters getParameters() {
		return parameters;
	}

	public void setParameters(ReorderTracesXLogProcessorParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public XLog preprocess(XLog eventlogin) {

		XLog eventlogout = (XLog) eventlogin.clone();

		switch (parameters.getReordermethod()) {
			case EVENTTIMESTAMP :
				return new EventTimeStampOnlyReorderTracesXLogPreprocessorStrategy().reorder(eventlogout, parameters);
			case EVENTCASEINDEX :
				return new EventCaseIndexOnlyReorderTracesXLogPreprocessorStrategy().reorder(eventlogout, parameters);
			default :
				throw new UnsupportedOperationException("Invalid reorder method parameter!");
		}
	}

}
