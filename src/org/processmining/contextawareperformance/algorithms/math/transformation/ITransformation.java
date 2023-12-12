package org.processmining.contextawareperformance.algorithms.math.transformation;

import java.util.List;
import java.util.Map;

import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;

/**
 * Interface for transformation.
 * 
 * @author B.F.A. Hompes
 *
 * @param <N>
 *            extends number
 */
public interface ITransformation<N extends Number> {

	Map<ContextResult<?>, List<PerformanceMeasurement<N>>>
			transform(Map<ContextResult<?>, List<PerformanceMeasurement<N>>> samples);

}
