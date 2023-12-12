package org.processmining.contextawareperformance.algorithms.contextawareprocessperformanceanalysis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.algorithms.math.normalitytest.NormalityTest;
import org.processmining.contextawareperformance.algorithms.math.normalitytest.ShapiroWilkNormalityTest;
import org.processmining.contextawareperformance.algorithms.math.posthoctest.PostHocTest;
import org.processmining.contextawareperformance.algorithms.math.posthoctest.TukeyKramerPostHocTest;
import org.processmining.contextawareperformance.algorithms.math.significancetest.KruskalWallisSignificanceTestJSC;
import org.processmining.contextawareperformance.algorithms.math.significancetest.OneWayANOVASignificanceTestApache;
import org.processmining.contextawareperformance.algorithms.math.significancetest.SignificanceTest;
import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.SignificanceResult;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionentities.LiteralEventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisParameters;
import org.processmining.framework.plugin.PluginContext;

import com.google.common.collect.Table;

/**
 * Algorithm that computes performance statistics based on the event log alone.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ContextAwareProcessPerformanceAnalysisAlgorithmEventCollection {

	@SuppressWarnings("unused")
	protected static ContextAwareProcessPerformanceAnalysisOutput apply(PluginContext pluginContext,
			EventCollection collection, ContextAwareProcessPerformanceAnalysisParameters parameters) {

		/******************************************************************************************
		 * 
		 * Variables
		 * 
		 ******************************************************************************************/

		// Output
		ContextAwareProcessPerformanceAnalysisOutput output = new ContextAwareProcessPerformanceAnalysisOutput();

		// Tests
		NormalityTest swNormalityTest = new ShapiroWilkNormalityTest();
		SignificanceTest anovaTest = new OneWayANOVASignificanceTestApache();
		SignificanceTest kwTest = new KruskalWallisSignificanceTestJSC();
		PostHocTest tkPostHocTest = new TukeyKramerPostHocTest();

		/******************************************************************************************
		 * 
		 * Apply applicable functions
		 * 
		 ******************************************************************************************/

		pluginContext.log("Start computing context and performance functions.");
		System.out.println("Start computing context and performance functions.");

		viewType: for (EventCollectionViewType viewType : parameters.getViewTypesToUse()) {

			if (collection.getApplicableViewTypes().contains(viewType)) {

				Map<? extends EventCollectionEntity, ? extends Collection<XEvent>> view = collection.viewAs(viewType);

				entity: for (EventCollectionEntity entity : view.keySet()) {

					performance: for (Performance<?> performance : parameters.getPerformanceMeasuresToUse()) {

						if (!performance.getType().getApplicableViewTypes().contains(viewType))
							continue performance;

						PerformanceMeasurement<?> performanceMeasurement = performance.mapToPerformance(entity,
								collection);

						context: for (Context<?> context : parameters.getContextsToUse()) {

							if (!context.getType().getApplicableViewTypes().contains(viewType))
								continue context;

							ContextResult<?> contextResult = context.mapToContext(entity, collection);

							if (!performanceMeasurement.getResult().equals(-1l))
								output.storeNewMeasurement(viewType, entity, performance, context, contextResult,
										performanceMeasurement);

						}

					}

				}

			}

		}

		pluginContext.log("Finished computing context and performance functions.");
		System.out.println("Finished computing context and performance functions.");

		/******************************************************************************************
		 * 
		 * Group from Activity instance to Activity
		 * 
		 ******************************************************************************************/

		if (output.getResults().containsKey(EventCollectionViewType.ACTIVITYINSTANCE)) {
			for (EventCollectionEntity activityInstance : output.getResults()
					.get(EventCollectionViewType.ACTIVITYINSTANCE).keySet()) {

				for (Performance<?> performance : output.getResults().get(EventCollectionViewType.ACTIVITYINSTANCE)
						.get(activityInstance).keySet()) {

					for (Context<?> context : output.getResults().get(EventCollectionViewType.ACTIVITYINSTANCE)
							.get(activityInstance).get(performance).keySet()) {

						for (ContextResult<?> contextResult : output.getResults()
								.get(EventCollectionViewType.ACTIVITYINSTANCE).get(activityInstance).get(performance)
								.get(context).keySet()) {

							for (PerformanceMeasurement<?> performanceMeasurement : output.getResults()
									.get(EventCollectionViewType.ACTIVITYINSTANCE).get(activityInstance)
									.get(performance).get(context).get(contextResult)) {

								output.storeNewMeasurement(EventCollectionViewType.ACTIVITY,
										new LiteralEventCollectionEntity(XConceptExtension.instance()
												.extractName(collection.viewAs(EventCollectionViewType.ACTIVITYINSTANCE)
														.get(activityInstance).iterator().next()),
												EventCollectionViewType.ACTIVITY),
										performance, context, contextResult, performanceMeasurement);

							}

						}

					}

				}
			}

			output.getResults().remove(EventCollectionViewType.ACTIVITYINSTANCE);

		}

		/******************************************************************************************
		 * 
		 * Check for statistically significant differences in measurements.
		 * 
		 ******************************************************************************************/

		pluginContext.log("Start hypothesis testing.");
		System.out.println("Start hypothesis testing.");

		for (EventCollectionViewType viewType : output.getResults().keySet()) {

			Map<? extends EventCollectionEntity, ? extends Collection<XEvent>> view = collection.viewAs(viewType);

			// If the view is not applicable, the view will be null
			if (view != null) {
				entity: for (EventCollectionEntity entity : output.getResults().get(viewType).keySet()) {

					performance: for (Performance<?> performance : output.getResults().get(viewType).get(entity)
							.keySet()) {

						context: for (Context<?> context : output.getResults().get(viewType).get(entity)
								.get(performance).keySet()) {

							System.out.println(viewType.getDescription() + ", \"" + entity.toString() + "\", "
									+ performance.toString() + ", " + context.toString());

							// Prepare and check if groups can be tested for significance.
							if (!canBeTestedForSignificance(
									output.getResults().get(viewType).get(entity).get(performance).get(context))) {

								System.out.println("\t-cannot be tested");

								/**
								 * The samples cannot be tested for significance
								 * (i.e. their results might not be numerical or
								 * there are not enough samples with the
								 * required number of observations). In this
								 * case, significance is 'null', and it is up to
								 * the visualizer to decide what will be shown.
								 */
								output.storeNewSignificance(viewType, entity, performance, context, null);

							} else {

								Map<ContextResult<?>, List<PerformanceMeasurement<?>>> genericSamples = output
										.getResults().get(viewType).get(entity).get(performance).get(context);

								Map<ContextResult<?>, List<PerformanceMeasurement<Long>>> samples = convertGenericSamplesToLong(
										genericSamples);

								// Test for normality.
								SignificanceResult significance = swNormalityTest.computeTest(samples);

								/**
								 * If the data is not normally distributed, try
								 * to transform it using the Box-Cox
								 * transformation
								 * 
								 */
								if (significance.isSignificant()) {
									//TODO [high] add Box-Cox transformation
								}

								/**
								 * If the data is normally distributed or can be
								 * transformed into normally distributed data,
								 * do the ANOVA, otherwise perform
								 * Kruskall-Wallis test.
								 * 
								 */
								if (!significance.isSignificant())
									significance = anovaTest.computeTest(samples);
								else
									significance = kwTest.computeTest(samples);

								/**
								 * If the anova / kw test show significant
								 * differences, do a post hoc test
								 * 
								 */
								if (significance.isSignificant()) {
									Table<ContextResult<?>, ContextResult<?>, Double> postHocResult = tkPostHocTest
											.computeTest(samples);
									significance.setPostHocResult(postHocResult);
								}

								// Store results for visualization
								output.storeNewSignificance(viewType, entity, performance, context, significance);

							}

						}

					}

				}

			}

		}

		pluginContext.log("Finished hypothesis testing.");
		System.out.println("Finished hypothesis testing.");

		pluginContext.log("Returning output.");
		System.out.println("Returning output.");

		return output;
	}

	private static Map<ContextResult<?>, List<PerformanceMeasurement<Long>>> convertGenericSamplesToLong(
			Map<ContextResult<?>, List<PerformanceMeasurement<?>>> genericSamples) {
		Map<ContextResult<?>, List<PerformanceMeasurement<Long>>> samples = new HashMap<ContextResult<?>, List<PerformanceMeasurement<Long>>>();

		for (ContextResult<?> result : genericSamples.keySet()) {
			List<PerformanceMeasurement<Long>> list = new ArrayList<PerformanceMeasurement<Long>>();
			for (PerformanceMeasurement<?> measurement : genericSamples.get(result)) {
				PerformanceMeasurement<Long> measurementLong = new PerformanceMeasurement<>();
				measurementLong.setCalculationTime(measurement.getCalculationTime());
				measurementLong.setResult(Long.parseLong(measurement.getResult().toString()));
				list.add(measurementLong);
			}
			samples.put(result, list);
		}

		return samples;
	}

	/**
	 * We need at least two samples with each at least two elements for
	 * hypothesis testing, otherwise we'll show no differences. Also, all
	 * elements should be numerical.
	 * 
	 * @param samples
	 *            The set of samples.
	 * @return Whether or not we can test for significance.
	 */
	private static boolean canBeTestedForSignificance(Map<ContextResult<?>, List<PerformanceMeasurement<?>>> samples) {
		// Every group needs at least two values
		for (ContextResult<?> sample : samples.keySet()) {
			if (samples.get(sample).size() < 2)
				samples.remove(sample);
		}

		// We need at least 2 groups
		if (samples.size() < 2)
			return false;

		// Every group needs at least two values
		for (List<PerformanceMeasurement<?>> sample : samples.values()) {
			if (sample.size() < 2)
				return false;
		}

		return true;
	}

}
