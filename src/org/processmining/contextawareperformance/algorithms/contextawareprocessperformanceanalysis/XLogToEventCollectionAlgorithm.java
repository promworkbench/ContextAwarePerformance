package org.processmining.contextawareperformance.algorithms.contextawareprocessperformanceanalysis;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.XLogToEventCollectionParameters;
import org.processmining.contextawareperformance.models.preprocessors.xlog.event.remove.RemoveEventsWithoutTimestampXLogPreprocessor;
import org.processmining.framework.plugin.PluginContext;
import org.processmining.lifecycleperformance.models.preprocessors.AssignActivityInstanceXLogPreprocessor;

public class XLogToEventCollectionAlgorithm {

	public static EventCollection apply(PluginContext pluginContext, XLog eventlog,
			XLogToEventCollectionParameters parameters) {

		XLog eventlogout = parameters.isClone() ? (XLog) eventlog.clone() : eventlog;

		/******************************************************************************************
		 * 
		 * Pre-processing: remove events without timestamps
		 * 
		 ******************************************************************************************/

		pluginContext.log("Start removing events without timestamps.");
		System.out.println("Start removing events without timestamps.");

		RemoveEventsWithoutTimestampXLogPreprocessor removeEventsWithoutTimestampPreprocessor = new RemoveEventsWithoutTimestampXLogPreprocessor();
		eventlogout = removeEventsWithoutTimestampPreprocessor.preprocess(eventlogout);

		pluginContext.log("Finished removing events without timestamps.");
		System.out.println("Finished removing events without timestamps.");

		/******************************************************************************************
		 * 
		 * Pre-processing: assign activity instance identifiers
		 * 
		 ******************************************************************************************/

		pluginContext.log("Start assigning activity instance identifiers.");
		System.out.println("Start assigning activity instance identifiers.");

		AssignActivityInstanceXLogPreprocessor activityInstancePreprocessor = new AssignActivityInstanceXLogPreprocessor();
		eventlogout = activityInstancePreprocessor.preprocess(eventlogout);

		pluginContext.log("Finished assigning activity instance identifiers.");
		System.out.println("Finished assigning activity instance identifiers.");

		/******************************************************************************************
		 * 
		 * Create event collection
		 * 
		 ******************************************************************************************/

		pluginContext.log("Start creating event collection from XLog.");
		System.out.println("Start creating event collection from XLog.");

		EventCollection collection = new EventCollection(eventlogout);

		pluginContext.log("Finished creating event collection from XLog.");
		System.out.println("Finished creating event collection from XLog.");

		return collection;
	}

}
