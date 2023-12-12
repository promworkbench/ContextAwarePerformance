package org.processmining.contextawareperformance.algorithms.math.transformation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;

import com.google.common.collect.Maps;
import com.google.common.primitives.Doubles;

import flanagan.analysis.BoxCox;

/**
 * Box-Cox transformation.
 * 
 * @author B.F.A. Hompes
 *
 */
public class BoxCoxTransformation extends Transformation {

	public static final String DESCRIPTION;

	static {
		DESCRIPTION = "The Box-Cox transformation is used to attempt to transform an array of data, y_i, to one, y_i(lambda), that conforms to a sample taken from a Gaussian (normal) distribution.";
	}

	public BoxCoxTransformation() {
		super(DESCRIPTION);
	}

	@SuppressWarnings("unused")
	//@Nullable
	@Override
	//FIXME UNFINISHED - DO NOT USE
	public Map<ContextResult<?>, List<PerformanceMeasurement<Long>>>
			transform(Map<ContextResult<?>, List<PerformanceMeasurement<Long>>> samples) {

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

		BoxCox boxCox = new BoxCox(values);

		values = boxCox.transformedData();

		Map<ContextResult<?>, List<PerformanceMeasurement<Long>>> transformedSamples = Maps
				.newHashMapWithExpectedSize(samples.size());

		for (ContextResult<?> contextResult : samples.keySet()) {
			//TODO finish transformedSamples
		}

		return null;
	}

}
