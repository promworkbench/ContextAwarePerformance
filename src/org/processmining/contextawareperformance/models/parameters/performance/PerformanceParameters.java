package org.processmining.contextawareperformance.models.parameters.performance;

import java.io.Serializable;
import java.util.Objects;

public abstract class PerformanceParameters implements IPerformanceParameters, Serializable {

	private static final long serialVersionUID = 7452940746845122367L;

	private String description;

	public PerformanceParameters(String description) {
		setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	private void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PerformanceParameters))
			return false;

		PerformanceParameters parameters = (PerformanceParameters) obj;

		if (!parameters.getDescription().equals(getDescription()))
			return false;

		return super.equals(parameters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), description);
	}

}
