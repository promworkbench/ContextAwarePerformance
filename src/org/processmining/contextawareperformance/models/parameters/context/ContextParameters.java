package org.processmining.contextawareperformance.models.parameters.context;

import java.io.Serializable;
import java.util.Objects;

public abstract class ContextParameters implements IContextParameters, Serializable {

	private static final long serialVersionUID = -3502104085460583150L;

	private String description;

	public ContextParameters(String description) {
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
		if (!(obj instanceof ContextParameters))
			return false;

		ContextParameters parameters = (ContextParameters) obj;

		if (!parameters.getDescription().equals(getDescription()))
			return false;

		return super.equals(parameters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), description);
	}

}
