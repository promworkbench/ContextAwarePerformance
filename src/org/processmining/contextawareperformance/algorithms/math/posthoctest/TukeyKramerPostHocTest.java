package org.processmining.contextawareperformance.algorithms.math.posthoctest;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.parameters.math.posthoctest.TukeyKramerPostHocTestParameters;

import com.google.common.collect.Table;
import com.google.common.collect.TreeBasedTable;

import gov.sandia.cognition.statistics.method.TukeyKramerConfidence;
import gov.sandia.cognition.statistics.method.TukeyKramerConfidence.Statistic;

/**
 * Tukey-Kramer posthoc test.
 * 
 * @author B.F.A. Hompes
 *
 */
public class TukeyKramerPostHocTest extends PostHocTest {

	public static final String DESCRIPTION;

	static {
		DESCRIPTION = "Tukey Kramer Post Hoc test";
	}

	private TukeyKramerPostHocTestParameters parameters;

	public TukeyKramerPostHocTest() {
		super(DESCRIPTION);
		setParameters(new TukeyKramerPostHocTestParameters());
	}

	public TukeyKramerPostHocTestParameters getParameters() {
		return parameters;
	}

	public void setParameters(TukeyKramerPostHocTestParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public Table<ContextResult<?>, ContextResult<?>, Double>
			computeTest(Map<ContextResult<?>, List<PerformanceMeasurement<Long>>> samples) {

		/******************************************************************************************
		 * 
		 * Restructure the data
		 * 
		 ******************************************************************************************/

		List<ArrayList<Long>> data = new ArrayList<ArrayList<Long>>();
		List<ContextResult<?>> indices = new ArrayList<ContextResult<?>>();

		for (ContextResult<?> result : samples.keySet()) {

			indices.add(result);

			ArrayList<Long> sample = new ArrayList<Long>();

			for (PerformanceMeasurement<Long> measurement : samples.get(result)) {
				if (measurement != null)
					sample.add(measurement.getResult());
			}

			data.add(sample);

		}

		/******************************************************************************************
		 * 
		 * Perform the Tukey range test
		 * 
		 ******************************************************************************************/

		Table<ContextResult<?>, ContextResult<?>, Double> result = TreeBasedTable.create();

		try {

			TukeyKramerConfidence tukey = new TukeyKramerConfidence();
			Statistic stat = tukey.evaluateNullHypotheses(data, parameters.getAlpha());

			for (int i = 0; i < indices.size(); i++) {

				for (int j = i; j < indices.size(); j++) {

					if (i != j) {

						if (!stat.acceptNullHypothesis(i, j)) {

							result.put(indices.get(i), indices.get(j), stat.getTestStatistic(i, j));

						}

					}

				}

			}

		} catch (Exception ex) {

			System.out.println("Couldn't calculate Tukey-Kramer. Possible problem!");

		}

		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof TukeyKramerPostHocTest))
			return false;

		TukeyKramerPostHocTest tukeyKramer = (TukeyKramerPostHocTest) obj;

		if (!tukeyKramer.getParameters().equals(getParameters()))
			return false;

		return super.equals(tukeyKramer);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), parameters);
	}
}
