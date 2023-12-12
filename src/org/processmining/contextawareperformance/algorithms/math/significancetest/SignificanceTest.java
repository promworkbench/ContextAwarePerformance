package org.processmining.contextawareperformance.algorithms.math.significancetest;

import java.util.Objects;

/**
 * Abstract class for significance test.
 * 
 * @author B.F.A. Hompes
 *
 */
public abstract class SignificanceTest implements ISignificanceTest<Long> {

	private String description;

	public SignificanceTest(String description) {
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
		if (!(obj instanceof SignificanceTest))
			return false;

		SignificanceTest significanceTest = (SignificanceTest) obj;

		if (!significanceTest.getDescription().equals(getDescription()))
			return false;

		return super.equals(significanceTest);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description);
	}

}
