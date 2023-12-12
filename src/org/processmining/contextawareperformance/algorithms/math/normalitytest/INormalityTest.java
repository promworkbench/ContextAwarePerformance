package org.processmining.contextawareperformance.algorithms.math.normalitytest;

import java.util.List;
import java.util.Map;

import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.SignificanceResult;

/**
 * Interface for normality test.
 * 
 * @author B.F.A. Hompes
 *
 * @param <N>
 *            extends number
 */
public interface INormalityTest<N extends Number> {

	public SignificanceResult computeTest(Map<ContextResult<?>, List<PerformanceMeasurement<N>>> samples);

}
