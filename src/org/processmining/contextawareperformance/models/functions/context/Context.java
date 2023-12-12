package org.processmining.contextawareperformance.models.functions.context;

import java.io.Serializable;
import java.util.Objects;

import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;

/**
 * Abstract context function. Maps an event in a collection to a ContextResult
 * of type T.
 * 
 * @author B.F.A. Hompes
 *
 * @param <T>
 */
public abstract class Context<T> implements IContext<T>, Serializable {

	private static final long serialVersionUID = 5865787536168043003L;

	public String VERB;

	private ContextType type;

	public Context(ContextType type) {
		setType(type);
	}

	public ContextType getType() {
		return type;
	}

	public void setType(ContextType type) {
		this.type = type;
	}

	@Override
	public ContextResult<T> mapToContext(EventCollectionEntity entity, EventCollection collection) {
		if (!this.getType().getApplicableViewTypes().contains(entity.getType()))
			throw new UnsupportedOperationException(
					"It is not possible to compute context \"" + this.getType().getDescription()
							+ "\" with this entity type \"" + entity.getType().getDescription() + "\" as input!");

		long calculationTime = -System.currentTimeMillis();

		ContextResult<T> contextResult = new ContextResult<T>();

		T result = label(entity, collection);
		contextResult.setResult(result);

		contextResult.setUsedContext(this);

		calculationTime += System.currentTimeMillis();
		contextResult.setCalculationTime(calculationTime);

		return contextResult;
	}

	public abstract T label(EventCollectionEntity entity, EventCollection collection);

	@Override
	public String toString() {
		return this.getType().toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Context<?>))
			return false;

		Context<?> context = (Context<?>) obj;

		if (!context.getType().equals(this.getType()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(type);
	}

}
