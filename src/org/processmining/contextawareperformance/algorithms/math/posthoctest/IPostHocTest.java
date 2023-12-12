package org.processmining.contextawareperformance.algorithms.math.posthoctest;

import java.util.List;
import java.util.Map;

import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;

import com.google.common.collect.Table;

/**
 * Interface for posthoc test.
 * 
 * @author B.F.A. Hompes
 *
 * @param <N>
 *            extends number
 */
public interface IPostHocTest<N extends Number> {

	Table<ContextResult<?>, ContextResult<?>, Double>
			computeTest(Map<ContextResult<?>, List<PerformanceMeasurement<N>>> samples);

}
