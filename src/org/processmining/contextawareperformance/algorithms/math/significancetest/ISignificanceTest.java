package org.processmining.contextawareperformance.algorithms.math.significancetest;

import java.util.List;
import java.util.Map;

import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.SignificanceResult;

/**
 * Interface for significance test.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 * @param <N>
 *            extends number
 */
public interface ISignificanceTest<N extends Number> {

	SignificanceResult computeTest(Map<ContextResult<?>, List<PerformanceMeasurement<N>>> samples);

}
