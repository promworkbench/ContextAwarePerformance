package org.processmining.contextawareperformance.models;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.processmining.contextawareperformance.models.functions.performance.Performance;

public class PerformanceMeasurement<T> implements Serializable {

	private static final long serialVersionUID = 8083835720978702924L;

	private T result;
	private Date measurementDate;
	private long calculationTime;
	private Performance<T> usedPerformance;

	public PerformanceMeasurement() {

	}

	public PerformanceMeasurement(T result, long calculationTime) {
		setResult(result);
		setCalculationTime(calculationTime);
	}

	public T getResult() {
		return result;
	}

	public void setResult(T result) {
		this.result = result;
	}

	public long getCalculationTime() {
		return calculationTime;
	}

	public void setCalculationTime(long calculationTime) {
		this.calculationTime = calculationTime;
	}

	public Performance<T> getUsedPerformance() {
		return usedPerformance;
	}

	public void setUsedPerformance(Performance<T> usedPerformance) {
		this.usedPerformance = usedPerformance;
	}

	public Date getMeasurementDate() {
		return measurementDate;
	}

	public void setMeasurementDate(Date measurementDate) {
		this.measurementDate = measurementDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof PerformanceMeasurement))
			return false;

		PerformanceMeasurement<?> performanceResult = (PerformanceMeasurement<?>) obj;

		if (!performanceResult.getResult().equals(getResult()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode());
	}

	@Override
	public String toString() {
		return getResult().toString();
	}

}
