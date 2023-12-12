package org.processmining.contextawareperformance.algorithms.math.normalitytest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.ArrayUtils;
import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.SignificanceResult;
import org.processmining.contextawareperformance.models.parameters.math.normalitytest.ShapiroWilkNormalityTestParameters;

/**
 * Shapiro-Wilk normality test.
 * 
 * @author B.F.A. Hompes
 *
 */
public class ShapiroWilkNormalityTest extends NormalityTest {

	public static final String DESCRIPTION;

	static {
		DESCRIPTION = "Shapiro-Wilk normality test";
	}

	private ShapiroWilkNormalityTestParameters parameters;

	public ShapiroWilkNormalityTest() {
		super(DESCRIPTION);
		setParameters(new ShapiroWilkNormalityTestParameters());
	}

	public ShapiroWilkNormalityTestParameters getParameters() {
		return parameters;
	}

	public void setParameters(ShapiroWilkNormalityTestParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public SignificanceResult computeTest(Map<ContextResult<?>, List<PerformanceMeasurement<Long>>> samples) {

		ArrayList<Double> dataList = new ArrayList<Double>();

		for (ContextResult<?> contextResult : samples.keySet()) {

			for (PerformanceMeasurement<Long> performanceMeasurement : samples.get(contextResult)) {

				if (performanceMeasurement.getResult() != null)
					dataList.add(performanceMeasurement.getResult().doubleValue());

			}

		}

		double[] data = ArrayUtils.toPrimitive(dataList.toArray(new Double[dataList.size()]));

		net.sourceforge.jdistlib.disttest.NormalityTest SWTest = new net.sourceforge.jdistlib.disttest.NormalityTest();

		double statistic;

		try {
			statistic = SWTest.shapiro_wilk_statistic(data);
			statistic = SWTest.shapiro_wilk_pvalue(statistic, data.length);
		} catch (Exception ex) {
			// In case this point is reached, there's not enough variation in the values to perform the test.
			// We then assume the data to be normally distributed (all values almost the same).
			statistic = 1;
		}

		SignificanceResult result = new SignificanceResult();
		result.setpValue(statistic);
		// "Significant" here means the null hypothesis is rejected, i.e. normality cannot be assumed 
		result.setSignificant(result.getpValue() < parameters.getAlpha());

		System.out.println("\t\tNormality: " + result.getpValue() + ",\t" + (result.isSignificant() ? "yes" : "no"));

		return result;

	}

}
