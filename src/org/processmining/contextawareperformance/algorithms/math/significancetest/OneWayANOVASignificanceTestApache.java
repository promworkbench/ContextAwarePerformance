package org.processmining.contextawareperformance.algorithms.math.significancetest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.inference.OneWayAnova;
import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.SignificanceResult;
import org.processmining.contextawareperformance.models.parameters.math.significancetest.OneWayANOVASignificanceTestParameters;

/**
 * One-way ANOVA test (Apache library).
 * 
 * @author B.F.A. Hompes
 *
 */
public class OneWayANOVASignificanceTestApache extends SignificanceTest {

	public static final String DESCRIPTION;

	static {
		DESCRIPTION = "Performs a one-way ANOVA test, evaluating the null hypothesis that there is no difference "
				+ "among the means of the data categories, using a optional alpha value";
	}

	private OneWayANOVASignificanceTestParameters parameters;

	public OneWayANOVASignificanceTestApache() {
		super(DESCRIPTION);
		setParameters(new OneWayANOVASignificanceTestParameters());
	}

	@Override
	public SignificanceResult computeTest(Map<ContextResult<?>, List<PerformanceMeasurement<Long>>> samples) {
		/******************************************************************************************
		 * 
		 * Restructure the data
		 * 
		 ******************************************************************************************/

		Collection<SummaryStatistics> data = new ArrayList<SummaryStatistics>();

		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		int sum = 0;
		double minDiffToMean = Double.MAX_VALUE;
		double maxDiffToMean = Double.MIN_VALUE;
		double meanDiffToMean = 0d;
		int totalNumberOfMeasurements = 0;

		for (ContextResult<?> contextResult : samples.keySet()) {

			SummaryStatistics stats = new SummaryStatistics();

			for (PerformanceMeasurement<Long> performanceMeasurement : samples.get(contextResult)) {

				Long measurement = performanceMeasurement.getResult();
				if (measurement != null) {
					stats.addValue(measurement);
					sum += measurement;
					totalNumberOfMeasurements++;
				}

			}

			if (stats.getMin() < min)
				min = stats.getMin();
			if (stats.getMax() > max)
				max = stats.getMax();

			data.add(stats);

		}

		SignificanceResult result = new SignificanceResult();
		result.setMin(min);
		result.setMax(max);
		result.setMean(((double) sum) / totalNumberOfMeasurements);

		for (SummaryStatistics stats : data) {

			double diffToMean = Math.abs(sum - stats.getMean());

			if (diffToMean < minDiffToMean)
				minDiffToMean = diffToMean;
			if (diffToMean > maxDiffToMean)
				maxDiffToMean = diffToMean;
			meanDiffToMean += diffToMean;

		}

		/******************************************************************************************
		 * 
		 * Perform the One-way ANOVA test
		 * 
		 ******************************************************************************************/

		OneWayAnova oneWayAnova = new OneWayAnova();

		result.setpValue(oneWayAnova.anovaPValue(data, false));
		result.setSignificant(result.getpValue() < parameters.getAlpha());

		result.setMinDiffToMean(minDiffToMean);
		result.setMaxDiffToMean(maxDiffToMean);
		result.setMeanDiffToMean(meanDiffToMean / data.size());
		result.setTotalNumberOfMeasurements(totalNumberOfMeasurements);

		System.out.println("\tSignificance: " + result.getpValue() + ",\t"
				+ (result.isSignificant() ? "significant" : "not significant"));

		return result;
	}

	public OneWayANOVASignificanceTestParameters getParameters() {
		return parameters;
	}

	public void setParameters(OneWayANOVASignificanceTestParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof OneWayANOVASignificanceTestApache))
			return false;

		OneWayANOVASignificanceTestApache anovaApache = (OneWayANOVASignificanceTestApache) obj;

		if (!anovaApache.getParameters().equals(getParameters()))
			return false;

		return super.equals(anovaApache);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), parameters);
	}

}
