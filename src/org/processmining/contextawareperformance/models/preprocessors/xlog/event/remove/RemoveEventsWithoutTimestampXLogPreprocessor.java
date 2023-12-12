package org.processmining.contextawareperformance.models.preprocessors.xlog.event.remove;

import java.util.Set;

import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.contextawareperformance.models.parameters.preprocessors.xlog.RemoveEventsWithoutTimestampXLogProcessorParameters;
import org.processmining.contextawareperformance.models.preprocessors.xlog.XLogPreprocessor;

import com.google.common.collect.Sets;

/**
 * Pre-processes an event log by removing all events that do not have a
 * timestamp. Note that possible empty traces are also removed, and therefore
 * the returned event log can be empty.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class RemoveEventsWithoutTimestampXLogPreprocessor extends XLogPreprocessor {

	private static final String NAME;

	static {
		NAME = "Remove events without timestamp XLog preprocessor";
	}

	private RemoveEventsWithoutTimestampXLogProcessorParameters parameters;

	public RemoveEventsWithoutTimestampXLogPreprocessor() {
		super(NAME);
		setParameters(new RemoveEventsWithoutTimestampXLogProcessorParameters());
	}

	public RemoveEventsWithoutTimestampXLogProcessorParameters getParameters() {
		return parameters;
	}

	public void setParameters(RemoveEventsWithoutTimestampXLogProcessorParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public XLog preprocess(XLog eventlog) {

		Set<XEvent> eventsToRemove = Sets.newHashSet();
		Set<XTrace> tracesToRemove = Sets.newHashSet();

		for (int t = 0; t < eventlog.size(); t++) {

			for (int e = 0; e < eventlog.get(t).size(); e++) {
				// Find events that do not have a timestamp.
				if (!eventlog.get(t).get(e).getAttributes().containsKey(XTimeExtension.KEY_TIMESTAMP))
					eventsToRemove.add(eventlog.get(t).get(e));
			}

			// Remove the events from the trace
			eventlog.get(t).removeAll(eventsToRemove);
			eventsToRemove.clear();

			if (eventlog.get(t).isEmpty())
				tracesToRemove.add(eventlog.get(t));

		}

		eventlog.removeAll(tracesToRemove);

		return eventlog;
	}

}
