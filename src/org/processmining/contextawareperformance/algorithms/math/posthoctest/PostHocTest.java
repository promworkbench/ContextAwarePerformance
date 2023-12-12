package org.processmining.contextawareperformance.algorithms.math.posthoctest;

import java.util.Objects;

/**
 * Abstract class for posthoc test (using Long values).
 * 
 * @author B.F.A. Hompes
 *
 */
public abstract class PostHocTest implements IPostHocTest<Long> {

	private String description;

	public PostHocTest(String description) {
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
		if (!(obj instanceof PostHocTest))
			return false;

		PostHocTest postHocTest = (PostHocTest) obj;

		if (!postHocTest.getDescription().equals(getDescription()))
			return false;

		return super.equals(postHocTest);
	}

	@Override
	public int hashCode() {
		return Objects.hash(description);
	}

}
