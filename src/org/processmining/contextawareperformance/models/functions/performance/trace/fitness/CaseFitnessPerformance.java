package org.processmining.contextawareperformance.models.functions.performance.trace.fitness;

import java.util.Collection;

import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionentities.LiteralEventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.extensions.XCaseExtension;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.functions.performance.PerformanceType;
import org.processmining.contextawareperformance.models.parameters.performance.trace.fitness.CaseFitnessPerformanceParameters;
import org.processmining.framework.connections.ConnectionCannotBeObtained;
import org.processmining.framework.plugin.events.Logger.MessageLevel;
import org.processmining.log.utils.XLogBuilder;
import org.processmining.plugins.petrinet.replayer.PNLogReplayer;
import org.processmining.plugins.petrinet.replayresult.PNRepResult;

import nl.tue.astar.AStarException;

/**
 * Performance that gives the fitness of case on a supplied model (Petri net).
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl> <br/>
 *         P.M. Dixit <prabhakar.dixit@philips.com>
 *
 */
public class CaseFitnessPerformance extends Performance<Double> {

	private static final long serialVersionUID = 1304317511738642220L;

	private CaseFitnessPerformanceParameters parameters;

	public CaseFitnessPerformance() {
		super(PerformanceType.CASE_FITNESS);
		setParameters(new CaseFitnessPerformanceParameters());
		ADJECTIVE_GREATER_THAN = "higher";
		ADJECTIVE_SMALLER_THAN = "lower";
		VERB = "be";
	}

	@Override
	protected PerformanceMeasurement<Double> compute(EventCollectionEntity entity, EventCollection collection) {
		EventCollectionEntity lit;
		Collection<XEvent> events;
		XTrace trace = null;

		switch (entity.getType()) {
			case EVENT :
				events = collection.viewAs(EventCollectionViewType.EVENT).get(entity);
				lit = new LiteralEventCollectionEntity(XCaseExtension.instance().extractCase(events.iterator().next()),
						EventCollectionViewType.CASE);
				break;
			case ACTIVITYINSTANCE :
				events = collection.viewAs(EventCollectionViewType.ACTIVITYINSTANCE).get(entity);
				lit = new LiteralEventCollectionEntity(XCaseExtension.instance().extractCase(events.iterator().next()),
						EventCollectionViewType.CASE);
				break;
			case CASE :
				lit = entity;
				break;
			default : // should never happen because it abstract class catches this.
				throw new UnsupportedOperationException(
						"It is not possible to compute performance \"" + this.getType().getDescription()
								+ "\" with this entity type \"" + entity.getType().getDescription() + "\" as input!");
		}

		trace = (XTrace) collection.viewAs(EventCollectionViewType.CASE).get(lit);
		return computePrivate(trace);
	}

	private PerformanceMeasurement<Double> computePrivate(XTrace trace) {
		// Build an event log with a single trace
		XLog singleTraceLog = XLogBuilder.newInstance().build();
		singleTraceLog.add(trace);

		PerformanceMeasurement<Double> measuremenet = new PerformanceMeasurement<Double>();

		double fitness = -1l;

		PNLogReplayer replayer = new PNLogReplayer();
		try {
			PNRepResult result = replayer.replayLog(parameters.getContext(), parameters.getPetriNet(), singleTraceLog);

			fitness = result.iterator().next().getInfo().get(PNRepResult.TRACEFITNESS);
		} catch (ConnectionCannotBeObtained e) {
			parameters.getContext().log("A problem has occured while checking fitness. Could not obtain connection.",
					MessageLevel.ERROR);
		} catch (AStarException e) {
			parameters.getContext().log("A problem has occured while checking fitness. AStarException.",
					MessageLevel.ERROR);
		}

		measuremenet.setResult(fitness);
		measuremenet.setMeasurementDate(XTimeExtension.instance().extractTimestamp(trace.get(trace.size() - 1)));

		return measuremenet;
	}

	public CaseFitnessPerformanceParameters getParameters() {
		return parameters;
	}

	public void setParameters(CaseFitnessPerformanceParameters parameters) {
		this.parameters = parameters;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof CaseFitnessPerformance))
			return false;

		CaseFitnessPerformance performance = (CaseFitnessPerformance) obj;

		if (!performance.getParameters().equals(getParameters()))
			return false;

		return super.equals(performance);
	}

	@Override
	public String toString() {
		return "Fitness with regards to model " + parameters.getPetriNet().getLabel();
	}

}
