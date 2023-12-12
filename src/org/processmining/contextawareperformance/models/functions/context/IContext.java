package org.processmining.contextawareperformance.models.functions.context;

import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.parameters.context.ContextParameters;

/**
 * Interface for context function.
 * 
 * @author B.F.A. Hompes
 *
 * @param <T>
 */
public interface IContext<T> {

	ContextResult<T> mapToContext(EventCollectionEntity entity, EventCollection collection);

	/**
	 * 
	 * Make it mandatory to have a getParameters function, even for functions
	 * without parameters, as this is used in the GUI dialogs
	 * 
	 * @return the parameters (or null in case no parameters are needed)
	 */
	//@Nullable
	ContextParameters getParameters();
}
