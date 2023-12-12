package org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.processmining.basicutils.parameters.impl.PluginParametersImpl;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.performance.Performance;

import com.google.common.collect.Sets;

public class ContextAwareProcessPerformanceAnalysisParameters extends PluginParametersImpl {

	private static final EnumSet<EventCollectionViewType> DEFAULT_VIEWTYPES;
	private static final Set<Context<?>> DEFAULT_CONTEXTS;
	private static final Set<Performance<?>> DEFAULT_PERFORMANCE_MEASURES;
	private static final boolean DEFAULT_CLONE;

	//@formatter:off
	static {
		DEFAULT_VIEWTYPES = EnumSet.of(
				EventCollectionViewType.ACTIVITYINSTANCE,
				EventCollectionViewType.PROCESS);
		DEFAULT_CONTEXTS = Sets.<Context<?>>newHashSet( 
//				new ActivityInstanceExecutingResourceContext()
				);
		DEFAULT_PERFORMANCE_MEASURES = Sets.<Performance<?>>newHashSet(
//				new ActivityInstanceDurationPerformance(),
//				new CaseDurationPerformance()
				);
		DEFAULT_CLONE = false;
	}
	//@formatter:on

	private Set<EventCollectionViewType> viewTypesToUse;
	private Set<Context<?>> contextsToUse;
	private Set<Performance<?>> performanceMeasuresToUse;
	private boolean clone;

	public ContextAwareProcessPerformanceAnalysisParameters() {
		super();
		setViewTypesToUse(DEFAULT_VIEWTYPES);
		setContextsToUse(DEFAULT_CONTEXTS);
		setPerformanceMeasuresToUse(DEFAULT_PERFORMANCE_MEASURES);
		setClone(DEFAULT_CLONE);
	}

	public ContextAwareProcessPerformanceAnalysisParameters(Set<EventCollectionViewType> viewTypes,
			Set<Context<?>> contexts, Set<Performance<?>> performanceMeasures, boolean clone) {
		super();
		setViewTypesToUse(viewTypes);
		setContextsToUse(contexts);
		setPerformanceMeasuresToUse(performanceMeasures);
		setClone(clone);
	}

	public Set<EventCollectionViewType> getViewTypesToUse() {
		return viewTypesToUse;
	}

	public void setViewTypesToUse(Set<EventCollectionViewType> viewTypesToUse) {
		this.viewTypesToUse = new HashSet<EventCollectionViewType>(viewTypesToUse);
	}

	public Set<Context<?>> getContextsToUse() {
		return contextsToUse;
	}

	public void setContextsToUse(Set<Context<?>> contextsToUse) {
		this.contextsToUse = new HashSet<Context<?>>(contextsToUse);
	}

	public void setContextsToUse(Context<?>... contexts) {
		contextsToUse.clear();
		for (Context<?> context : contexts) {
			contextsToUse.add(context);
		}
	}

	public Set<Performance<?>> getPerformanceMeasuresToUse() {
		return performanceMeasuresToUse;
	}

	public void setPerformanceMeasuresToUse(Set<Performance<?>> performanceMeasuresToUse) {
		this.performanceMeasuresToUse = new HashSet<Performance<?>>(performanceMeasuresToUse);
	}

	public void setPerformanceMeasuresToUse(Performance<?>... performances) {
		performanceMeasuresToUse.clear();
		for (Performance<?> performance : performances) {
			performanceMeasuresToUse.add(performance);
		}
	}

	public boolean isClone() {
		return clone;
	}

	public void setClone(boolean clone) {
		this.clone = clone;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ContextAwareProcessPerformanceAnalysisParameters))
			return false;

		ContextAwareProcessPerformanceAnalysisParameters parameters = (ContextAwareProcessPerformanceAnalysisParameters) obj;

		if (!parameters.getViewTypesToUse().equals(getViewTypesToUse()))
			return false;

		if (!parameters.getContextsToUse().equals(getContextsToUse()))
			return false;

		if (!parameters.getPerformanceMeasuresToUse().equals(getPerformanceMeasuresToUse()))
			return false;

		if (!parameters.isClone() == isClone())
			return false;

		return super.equals(parameters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), getViewTypesToUse(), getContextsToUse(), getPerformanceMeasuresToUse(),
				clone);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(System.lineSeparator());

		builder.append("Used views:" + System.lineSeparator());

		for (EventCollectionViewType viewType : viewTypesToUse)
			builder.append("- " + viewType.toString() + System.lineSeparator());

		builder.append("Used contexts:" + System.lineSeparator());

		for (Context<?> context : contextsToUse)
			builder.append("- " + context.toString() + System.lineSeparator());

		builder.append("Used performance measures:" + System.lineSeparator());

		for (Performance<?> performance : performanceMeasuresToUse)
			builder.append("- " + performance.toString() + System.lineSeparator());

		return builder.toString();
	}

}
