package org.processmining.contextawareperformance.models.parameters.math.significancetest;

public class KruskalWallisSignificanceTestParameters {

	public static final double DEFAULT_ALPHA;

	static {
		DEFAULT_ALPHA = 0.05;
	}

	private double alpha;

	public KruskalWallisSignificanceTestParameters() {
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
		if (!(obj instanceof KruskalWallisSignificanceTestParameters))
			return false;

		KruskalWallisSignificanceTestParameters parameters = (KruskalWallisSignificanceTestParameters) obj;

		if (parameters.getAlpha() != getAlpha())
			return false;

		return super.equals(parameters);
	}
}
