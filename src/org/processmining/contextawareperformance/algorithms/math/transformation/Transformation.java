package org.processmining.contextawareperformance.algorithms.math.transformation;

import java.util.Objects;

/**
 * Abstract class for transformation.
 * 
 * @author B.F.A. Hompes
 *
 */
public abstract class Transformation implements ITransformation<Long> {

	private String description;

	public Transformation(String description) {
		setDescription(description);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Transformation))
			return false;

		Transformation transformation = (Transformation) obj;

		if (!transformation.getDescription().equals(getDescription()))
			return false;

		return super.equals(transformation);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description);
	}

}
