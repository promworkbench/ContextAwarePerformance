package org.processmining.contextawareperformance.models.functions.context.event.prefix;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XTrace;
import org.processmining.contextawareperformance.models.EventCollection;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionentities.LiteralEventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.extensions.XCaseExtension;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.context.ContextType;
import org.processmining.contextawareperformance.models.parameters.context.PrefixContextParameters;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Lists;
import com.google.common.collect.Multiset;

import gnu.trove.set.hash.THashSet;

public abstract class EventPrefixContext extends Context<String> {

	private static final long serialVersionUID = -243657087496127233L;

	private PrefixContextParameters parameters;

	public EventPrefixContext(ContextType type) {
		super(type);
		setParameters(new PrefixContextParameters());
	}

	public PrefixContextParameters getParameters() {
		return parameters;
	}

	public void setParameters(PrefixContextParameters parameters) {
		this.parameters = parameters;
	}

	/**
	 * Labels an event entity with its prefix of events. Different
	 * implementations are available which each give a different label to an
	 * event.
	 */
	@Override
	public String label(EventCollectionEntity entity, EventCollection collection) {
		XEvent event = collection.viewAs(EventCollectionViewType.EVENT).get(entity).iterator().next();

		// Get all events that share this event's case
		XTrace trace = (XTrace) collection.viewAs(EventCollectionViewType.CASE).get(new LiteralEventCollectionEntity(
				XCaseExtension.instance().extractCase(event), EventCollectionViewType.CASE));

		// Get horizon-limited prefix
		List<XEvent> prefix;
		if (getParameters().getHorizon() == 0)
			prefix = Lists.newArrayList(event);
		else
			prefix = trace.subList(
					getParameters().getHorizon() == -1 ? 0
							: Math.max(0, trace.indexOf(event) - Math.abs(getParameters().getHorizon())),
					trace.indexOf(event));

		// Continue depending on the prefix abstraction.
		Object result = null;
		switch (getParameters().getPrefixAbstractionType()) {
			case SEQUENCE :
				PrefixList<String> sequence = new PrefixList<String>();
				for (XEvent e : prefix) {
					sequence.add(labelEvent(e));
				}
				result = sequence;
				break;
			case MULTISET :
				Multiset<String> multiset = HashMultiset.create();
				for (XEvent e : prefix) {
					multiset.add(labelEvent(e));
				}
				result = multiset;
				break;
			case SET :
				Set<String> set = new THashSet<String>();
				for (XEvent e : prefix) {
					set.add(labelEvent(e));
				}
				result = set;
				break;
			default :
				System.out.println("Invalid prefix abstraction type parameter!");
				break;
		}

		return result.toString();
	}

	protected abstract String labelEvent(XEvent event);

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EventPrefixContext))
			return false;

		EventPrefixContext context = (EventPrefixContext) obj;

		if (!context.getParameters().equals(getParameters()))
			return false;

		return super.equals(context);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), parameters);
	}

	@Override
	public String toString() {
		return super.toString() + " (Parameters: " + getParameters().toString() + ")";
	}

	/**
	 * Used for custom equals and hashcode methods for lists.
	 * 
	 * @author B.F.A. Hompes
	 *
	 * @param <T>
	 */
	protected class PrefixList<T> extends ArrayList<T> {

		private static final long serialVersionUID = -6388694827744592542L;

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof PrefixList))
				return false;

			@SuppressWarnings("rawtypes")
			PrefixList list = (PrefixList) obj;

			if (list.size() != this.size())
				return false;

			for (int i = 0; i < list.size(); i++) {
				if (!list.get(i).equals(this.get(i)))
					return false;
			}

			return true;
		}

		@Override
		public int hashCode() {
			int sum = 0;
			for (int i = 0; i < this.size(); i++) {
				sum += (i + 1) * this.get(i).hashCode();
			}
			return sum;
		}
	}

}
