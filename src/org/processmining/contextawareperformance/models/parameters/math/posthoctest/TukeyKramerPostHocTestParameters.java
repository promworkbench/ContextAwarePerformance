package org.processmining.contextawareperformance.models.parameters.math.posthoctest;

public class TukeyKramerPostHocTestParameters {

	public static final double DEFAULT_ALPHA;

	static {
		DEFAULT_ALPHA = 0.05;
	}

	private double alpha;

	public TukeyKramerPostHocTestParameters() {
		setAlpha(DEFAULT_ALPHA);
	}

	public double getAlpha() {
		return alpha;
	}

	public void setAlpha(double alpha) {
		this.alpha = alpha;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TukeyKramerPostHocTestParameters))
			return false;

		TukeyKramerPostHocTestParameters parameters = (TukeyKramerPostHocTestParameters) obj;

		if (parameters.getAlpha() != getAlpha())
			return false;

		return super.equals(parameters);
	}
}
