package org.processmining.contextawareperformance.models;

import java.io.Serializable;
import java.util.Objects;

import org.processmining.contextawareperformance.models.functions.context.Context;

public class ContextResult<T> implements Comparable<ContextResult<T>>, Serializable {

	private static final long serialVersionUID = -2765592251651705290L;

	private Context<T> usedContext;
	private T result;
	private long calculationTime;

	public ContextResult() {

	}

	public ContextResult(Context<T> usedContext, T result, long calculationTime) {
		setUsedContext(usedContext);
		setResult(result);
		setCalculationTime(calculationTime);
	}

	public Context<T> getUsedContext() {
		return usedContext;
	}

	public void setUsedContext(Context<T> usedContext) {
		this.usedContext = usedContext;
	}

	public Object getResult() {
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

	@Override
	public int compareTo(ContextResult<T> o) {
		return result.toString().compareTo(o.result.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ContextResult))
			return false;

		@SuppressWarnings("unchecked")
		ContextResult<T> contextResult = (ContextResult<T>) obj;

		if (!contextResult.getUsedContext().equals(getUsedContext()))
			return false;

		if (!contextResult.getResult().equals(getResult()))
			return false;

		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(usedContext, result);
	}

	@Override
	public String toString() {
		return getResult().toString();
	}

}
