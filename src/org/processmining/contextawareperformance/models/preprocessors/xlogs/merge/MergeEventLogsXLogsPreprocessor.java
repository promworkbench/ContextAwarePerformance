package org.processmining.contextawareperformance.models.preprocessors.xlogs.merge;

import org.deckfour.xes.factory.XFactoryRegistry;
import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.preprocessors.xlogs.XLogsPreprocessor;

/**
 * Pre-processor that merges multiple event logs into one event log by adding
 * the traces (traces with the same name will not be merged).
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class MergeEventLogsXLogsPreprocessor extends XLogsPreprocessor {

	private static final String NAME;

	static {
		NAME = "";
	}

	public MergeEventLogsXLogsPreprocessor() {
		super(NAME);
	}

	@Override
	public XLog preprocess(XLog[] eventlogs) {
		XLog eventlogout = XFactoryRegistry.instance().currentDefault().createLog();

		for (XLog eventlog : eventlogs) {
			eventlogout.addAll(eventlog);
		}

		return eventlogout;
	}

}
