package org.processmining.contextawareperformance.algorithms.math.normalitytest;

import java.util.Objects;

/**
 * Abstract class for normality test.
 * 
 * @author B.F.A. Hompes
 *
 */
public abstract class NormalityTest implements INormalityTest<Long> {

	private String description;

	public NormalityTest(String description) {
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
		if (!(obj instanceof NormalityTest))
			return false;

		NormalityTest normalityTest = (NormalityTest) obj;

		if (!normalityTest.getDescription().equals(getDescription()))
			return false;

		return super.equals(normalityTest);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description);
	}
}
