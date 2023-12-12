package org.processmining.contextawareperformance.models.functions.performance;

import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.parameters.performance.PerformanceParameters;

public interface IPerformance<T> {

	/**
	 * Map the entity to its performance.
	 * 
	 * @param entity
	 * @param collection
	 * @return PerformanceMeasurement. Set result to NULL to indicate that the
	 *         performance function cannot be calculated on this input.
	 */
	PerformanceMeasurement<T> mapToPerformance(EventCollectionEntity entity, EventCollection collection);

	/**
	 * 
	 * Make it mandatory to have a getParameters function, even for functions
	 * without parameters, as this is used in the GUI dialogs
	 * 
	 * @return the parameters (or null in case no parameters are needed)
	 */
	//@Nullable
	PerformanceParameters getParameters();

}
