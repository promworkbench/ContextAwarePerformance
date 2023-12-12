package org.processmining.contextawareperformance.view.visualizers.fx.model;

import java.util.List;
import java.util.Map;

import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.SignificanceResult;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.performance.Performance;

public class ResultCard {

	private String title;

	private Performance<?> performance;
	private Context<?> context;

	private Map<ContextResult<?>, List<PerformanceMeasurement<?>>> results;
	private SignificanceResult significanceResult;

	public ResultCard(String title, Performance<?> performance, Context<?> context) {
		setTitle(title);
		setPerformance(performance);
		setContext(context);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Performance<?> getPerformance() {
		return performance;
	}

	public void setPerformance(Performance<?> performance) {
		this.performance = performance;
	}

	public Context<?> getContext() {
		return context;
	}

	public void setContext(Context<?> context) {
		this.context = context;
	}

	public Map<ContextResult<?>, List<PerformanceMeasurement<?>>> getResults() {
		return results;
	}

	public void setResults(Map<ContextResult<?>, List<PerformanceMeasurement<?>>> results) {
		this.results = results;
	}

	public SignificanceResult getSignificanceResult() {
		return significanceResult;
	}

	public void setSignificanceResult(SignificanceResult significanceResult) {
		this.significanceResult = significanceResult;
	}

}
