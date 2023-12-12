package org.processmining.contextawareperformance.models;

import java.io.Serializable;

import com.google.common.collect.Table;

public class SignificanceResult implements Serializable {

	private static final long serialVersionUID = -5081371957143923767L;

	private double pValue;
	private boolean significant;
	private Table<ContextResult<?>, ContextResult<?>, Double> postHocResult;

	private int totalNumberOfMeasurements;
	private double min;
	private double max;
	private double mean;
	private double minDiffToMean;
	private double maxDiffToMean;
	private double meanDiffToMean;

	public SignificanceResult() {

	}

	public SignificanceResult(double pValue, boolean significant,
			Table<ContextResult<?>, ContextResult<?>, Double> postHocResult) {
		this.setpValue(pValue);
		this.setSignificant(significant);
		this.setPostHocResult(postHocResult);
	}

	public double getpValue() {
		return pValue;
	}

	public void setpValue(double pValue) {
		this.pValue = pValue;
	}

	public boolean isSignificant() {
		return significant;
	}

	public void setSignificant(boolean significant) {
		this.significant = significant;
	}

	public Table<ContextResult<?>, ContextResult<?>, Double> getPostHocResult() {
		return postHocResult;
	}

	public void setPostHocResult(Table<ContextResult<?>, ContextResult<?>, Double> postHocResult) {
		this.postHocResult = postHocResult;
	}

	public int getTotalNumberOfObservations() {
		return totalNumberOfMeasurements;
	}

	public void setTotalNumberOfMeasurements(int totalNumberOfObservations) {
		this.totalNumberOfMeasurements = totalNumberOfObservations;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getMean() {
		return mean;
	}

	public void setMean(double mean) {
		this.mean = mean;
	}

	public double getMinDiffToMean() {
		return minDiffToMean;
	}

	public void setMinDiffToMean(double minDiffToMean) {
		this.minDiffToMean = minDiffToMean;
	}

	public double getMaxDiffToMean() {
		return maxDiffToMean;
	}

	public void setMaxDiffToMean(double maxDiffToMean) {
		this.maxDiffToMean = maxDiffToMean;
	}

	public double getMeanDiffToMean() {
		return meanDiffToMean;
	}

	public void setMeanDiffToMean(double avgDiffToMean) {
		this.meanDiffToMean = avgDiffToMean;
	}

}
