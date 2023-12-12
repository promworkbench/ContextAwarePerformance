package org.processmining.contextawareperformance.algorithms.math.significancetest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.SignificanceResult;
import org.processmining.contextawareperformance.models.parameters.math.significancetest.OneWayANOVASignificanceTestParameters;

import com.google.common.primitives.Doubles;

import jsc.datastructures.GroupedData;
import jsc.independentsamples.OneWayANOVA;

/**
 * One-way ANOVA test (JSC library).
 * 
 * @author B.F.A. Hompes
 *
 */
public class OneWayANOVASignificanceTestJSC extends SignificanceTest {

	public static final String DESCRIPTION;

	static {
		DESCRIPTION = "Performs a one-way ANOVA test, evaluating the null hypothesis that there is no difference "
				+ "among the means of the data categories, using a optional alpha value";
	}

	private OneWayANOVASignificanceTestParameters parameters;

	public OneWayANOVASignificanceTestJSC() {
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

		List<Long> valueList = new ArrayList<Long>();
		List<String> labelList = new ArrayList<String>();

		for (ContextResult<?> contextResult : samples.keySet()) {

			for (PerformanceMeasurement<Long> performanceMeasurement : samples.get(contextResult)) {

				if (performanceMeasurement.getResult() != null) {
					valueList.add(performanceMeasurement.getResult());
					labelList.add(contextResult.toString());
				}

			}

		}

		double[] values = Doubles.toArray(valueList);
		String[] labels = new String[labelList.size()];
		labels = labelList.toArray(labels);

		GroupedData data = new GroupedData(values, labels);

		/******************************************************************************************
		 * 
		 * Perform the One-way ANOVA test
		 * 
		 ******************************************************************************************/

		OneWayANOVA anova = new OneWayANOVA(data);

		SignificanceResult result = new SignificanceResult();

		result.setpValue(anova.getSP());
		result.setSignificant(result.getpValue() < parameters.getAlpha());

		double sumOfMeans = 0;

		for (int i = 0; i < anova.getTreatmentCount(); i++) {
			double meanI = anova.getMean(i);
			sumOfMeans += meanI;
		}
		result.setMean(sumOfMeans / anova.getTreatmentCount());

		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		double minDiffToMean = Double.MAX_VALUE;
		double maxDiffToMean = Double.MIN_VALUE;
		double meanDiffToMean = 0d;

		for (int i = 0; i < anova.getTreatmentCount(); i++) {
			double meanI = anova.getMean(i);

			if (meanI < min)
				min = meanI;
			if (meanI > max)
				max = meanI;

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
		result.setMeanDiffToMean(meanDiffToMean / anova.getTreatmentCount());
		result.setTotalNumberOfMeasurements(anova.getTreatmentCount());

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
		if (!(obj instanceof OneWayANOVASignificanceTestJSC))
			return false;

		OneWayANOVASignificanceTestJSC anovaJSC = (OneWayANOVASignificanceTestJSC) obj;

		if (!anovaJSC.getParameters().equals(getParameters()))
			return false;

		return super.equals(anovaJSC);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), parameters);
	}
}
