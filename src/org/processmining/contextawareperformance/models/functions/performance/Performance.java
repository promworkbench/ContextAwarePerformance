package org.processmining.contextawareperformance.models.functions.performance;

import java.io.Serializable;
import java.util.Objects;

import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;

public abstract class Performance<T> implements IPerformance<T>, Serializable {

	private static final long serialVersionUID = 4356821425102874956L;

	public String ADJECTIVE_GREATER_THAN;
	public String ADJECTIVE_SMALLER_THAN;
	public String VERB;

	private PerformanceType type;

	public Performance(PerformanceType type) {
		setType(type);
	}

	public PerformanceType getType() {
		return type;
	}

	public void setType(PerformanceType type) {
		this.type = type;
	}

	@Override
	public final PerformanceMeasurement<T> mapToPerformance(EventCollectionEntity entity, EventCollection collection) {
		if (!this.getType().getApplicableViewTypes().contains(entity.getType()))
			throw new UnsupportedOperationException(
					"It is not possible to compute performance \"" + this.getType().getDescription()
							+ "\" with this entity type \"" + entity.getType().getDescription() + "\" as input!");

		long calculationTime = -System.currentTimeMillis();

		PerformanceMeasurement<T> performanceMeasurement;

		performanceMeasurement = compute(entity, collection);

		performanceMeasurement.setUsedPerformance(this);

		calculationTime += System.currentTimeMillis();
		performanceMeasurement.setCalculationTime(calculationTime);

		return performanceMeasurement;
	}

	protected abstract PerformanceMeasurement<T> compute(EventCollectionEntity entity, EventCollection collection);

	@Override
	public String toString() {
		return getType().toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Performance))
			return false;

		Performance<?> performance = (Performance<?>) obj;

		if (!performance.getType().equals(this.getType()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode());
	}

}
