package org.processmining.contextawareperformance.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.performance.Performance;

/**
 * Wrapper class used to keep output of algorithms that compute performance
 * characteristics.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ContextAwareProcessPerformanceAnalysisOutput implements Serializable {

	private static final long serialVersionUID = -8506857337881329447L;

	//@formatter:off
	private Map<EventCollectionViewType,
				Map<EventCollectionEntity,
					Map<Performance<?>,
						Map<Context<?>,
							Map<ContextResult<?>,
								List<PerformanceMeasurement<?>>>>>>> results;
	
	private Map<EventCollectionViewType,
				Map<EventCollectionEntity,
					Map<Performance<?>,
						Map<Context<?>,
							SignificanceResult>>>> significances;
	//@formatter:on

	public ContextAwareProcessPerformanceAnalysisOutput() {
		results = new HashMap<EventCollectionViewType, Map<EventCollectionEntity, Map<Performance<?>, Map<Context<?>, Map<ContextResult<?>, List<PerformanceMeasurement<?>>>>>>>();
		significances = new HashMap<EventCollectionViewType, Map<EventCollectionEntity, Map<Performance<?>, Map<Context<?>, SignificanceResult>>>>();
	}

	public Map<EventCollectionViewType, Map<EventCollectionEntity, Map<Performance<?>, Map<Context<?>, Map<ContextResult<?>, List<PerformanceMeasurement<?>>>>>>>
			getResults() {
		return results;
	}

	public void setResults(
			Map<EventCollectionViewType, Map<EventCollectionEntity, Map<Performance<?>, Map<Context<?>, Map<ContextResult<?>, List<PerformanceMeasurement<?>>>>>>> results) {
		this.results = results;
	}

	public Map<EventCollectionViewType, Map<EventCollectionEntity, Map<Performance<?>, Map<Context<?>, SignificanceResult>>>>
			getSignificance() {
		return significances;
	}

	public void setSignificance(
			Map<EventCollectionViewType, Map<EventCollectionEntity, Map<Performance<?>, Map<Context<?>, SignificanceResult>>>> significance) {
		this.significances = significance;
	}

	// Used to add new measurement to results map
	public boolean storeNewMeasurement(EventCollectionViewType viewType, EventCollectionEntity entity,
			Performance<?> performance, Context<?> context, ContextResult<?> contextResult,
			PerformanceMeasurement<?> performanceMeasurement) {

		if (!results.containsKey(viewType))
			results.put(viewType,
					new HashMap<EventCollectionEntity, Map<Performance<?>, Map<Context<?>, Map<ContextResult<?>, List<PerformanceMeasurement<?>>>>>>());

		if (!results.get(viewType).containsKey(entity))
			results.get(viewType).put(entity,
					new HashMap<Performance<?>, Map<Context<?>, Map<ContextResult<?>, List<PerformanceMeasurement<?>>>>>());

		if (!results.get(viewType).get(entity).containsKey(performance))
			results.get(viewType).get(entity).put(performance,
					new HashMap<Context<?>, Map<ContextResult<?>, List<PerformanceMeasurement<?>>>>());

		if (!results.get(viewType).get(entity).get(performance).containsKey(context))
			results.get(viewType).get(entity).get(performance).put(context,
					new HashMap<ContextResult<?>, List<PerformanceMeasurement<?>>>());

		if (!results.get(viewType).get(entity).get(performance).get(context).containsKey(contextResult))
			results.get(viewType).get(entity).get(performance).get(context).put(contextResult,
					new ArrayList<PerformanceMeasurement<?>>());

		return results.get(viewType).get(entity).get(performance).get(context).get(contextResult)
				.add(performanceMeasurement);
	}

	// Used to add new significance result to significances map
	public SignificanceResult storeNewSignificance(EventCollectionViewType viewType, EventCollectionEntity entity,
			Performance<?> performance, Context<?> context, SignificanceResult significance) {

		if (!significances.containsKey(viewType))
			significances.put(viewType,
					new HashMap<EventCollectionEntity, Map<Performance<?>, Map<Context<?>, SignificanceResult>>>());

		if (!significances.get(viewType).containsKey(entity))
			significances.get(viewType).put(entity, new HashMap<Performance<?>, Map<Context<?>, SignificanceResult>>());

		if (!significances.get(viewType).get(entity).containsKey(performance))
			significances.get(viewType).get(entity).put(performance, new HashMap<Context<?>, SignificanceResult>());

		return significances.get(viewType).get(entity).get(performance).put(context, significance);
	}

}
