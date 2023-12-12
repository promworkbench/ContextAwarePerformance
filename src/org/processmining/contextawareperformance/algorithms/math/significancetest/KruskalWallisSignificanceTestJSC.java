package org.processmining.contextawareperformance.algorithms.math.significancetest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.SignificanceResult;
import org.processmining.contextawareperformance.models.parameters.math.significancetest.KruskalWallisSignificanceTestParameters;

import com.google.common.primitives.Doubles;

import jsc.datastructures.GroupedData;
import jsc.independentsamples.KruskalWallisTest;

/**
 * Kruskall-Wallis significance test.
 * 
 * @see <a href=
 *      "https://en.wikipedia.org/wiki/Kruskal%E2%80%93Wallis_one-way_analysis_of_variance">
 *      Kruskal-Wallis one-way analysis of variance</a>
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class KruskalWallisSignificanceTestJSC extends SignificanceTest {

	public static final String DESCRIPTION;

	static {
		DESCRIPTION = "The Kruskal-Wallis test ((also known as One-way ANOVA on ranks) is the non-parametric alternative to ANOVA. "
				+ "Performs a Kruskal-Wallis test, evaluating the null hypothesis that there is no difference "
				+ "among the medians of the data categories, using a optional alpha value";
	}

	private KruskalWallisSignificanceTestParameters parameters;

	public KruskalWallisSignificanceTestJSC() {
		super(DESCRIPTION);
		setParameters(new KruskalWallisSignificanceTestParameters());
	}

	@Override
	public SignificanceResult computeTest(Map<ContextResult<?>, List<PerformanceMeasurement<Long>>> samples) {
		/******************************************************************************************
		 * 
		 * Restructure the data
		 * 
		 ******************************************************************************************/

		List<Long> valueList = new ArrayList<Long>();
		List<String> labelList = new ArrayList<String>();
		long firstValue = samples.values().iterator().next().get(0).getResult();
		boolean changed = false;

		Double min = Double.MAX_VALUE;
		Double max = Double.MIN_VALUE;
		int sum = 0;
		int totalNumberOfMeasurements = 0;

		for (ContextResult<?> contextResult : samples.keySet()) {

			for (PerformanceMeasurement<Long> performanceMeasurement : samples.get(contextResult)) {
				Long measurement = performanceMeasurement.getResult();
				if (measurement != null) {
					valueList.add(measurement);
					labelList.add(contextResult.toString());

					if (measurement < min)
						min = measurement.doubleValue();
					if (measurement > max)
						max = measurement.doubleValue();
					sum += measurement;
					totalNumberOfMeasurements++;

					if (!changed && firstValue != performanceMeasurement.getResult())
						changed = true;
				}
			}

		}

		SignificanceResult result = new SignificanceResult();

		// When all the values are the same, return not significant (test cannot compute in that case)
		if (!changed) {

			result.setSignificant(false);
			result.setpValue(1);

			System.out.println("Cannot compute KW");

			return result;

		}

		// When there is variation in the values, compute the test
		double[] values = Doubles.toArray(valueList);
		String[] labels = new String[labelList.size()];
		labels = labelList.toArray(labels);

		GroupedData data = new GroupedData(values, labels);

		/******************************************************************************************
		 * 
		 * Perform the Kruskall-Wallis test
		 * 
		 ******************************************************************************************/

		KruskalWallisTest kwTest = new KruskalWallisTest(data, 0.0D, true);

		result.setpValue(kwTest.getSP());
		result.setSignificant(result.getpValue() < parameters.getAlpha());
		result.setMean(((double) sum) / totalNumberOfMeasurements);

		double minDiffToMean = Double.MAX_VALUE;
		double maxDiffToMean = Double.MIN_VALUE;
		double meanDiffToMean = 0d;

		for (int i = 0; i < kwTest.getK(); i++) {
			double meanI = kwTest.getMeanRank(i);

			double diffToMean = Math.abs(result.getMean() - meanI);
			meanDiffToMean += diffToMean;

			if (diffToMean < minDiffToMean)
				minDiffToMean = diffToMean;
			if (diffToMean > maxDiffToMean)
				maxDiffToMean = diffToMean;
		}
		result.setMin(min);
		result.setMax(max);
		result.setMinDiffToMean(minDiffToMean);
		result.setMaxDiffToMean(maxDiffToMean);
		result.setMeanDiffToMean(meanDiffToMean / kwTest.getK());
		result.setTotalNumberOfMeasurements(totalNumberOfMeasurements);

		System.out.println("\tSignificance: " + result.getpValue() + ",\t"
				+ (result.isSignificant() ? "significant" : "not significant"));

		return result;

	}

	public KruskalWallisSignificanceTestParameters getParameters() {
		return parameters;
	}

	public void setParameters(KruskalWallisSignificanceTestParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof KruskalWallisSignificanceTestJSC))
			return false;

		KruskalWallisSignificanceTestJSC kruskallWallis = (KruskalWallisSignificanceTestJSC) obj;

		if (!kruskallWallis.getParameters().equals(getParameters()))
			return false;

		return super.equals(kruskallWallis);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), parameters);
	}

}
