package org.processmining.contextawareperformance.models;

import java.util.Collection;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.deckfour.xes.extension.std.XIdentityExtension;
import org.deckfour.xes.factory.XFactoryRegistry;
import org.deckfour.xes.id.XIDFactory;
import org.deckfour.xes.model.XAttribute;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.deckfour.xes.model.impl.XAttributeListImpl;
import org.deckfour.xes.model.impl.XAttributeTimestampImpl;
import org.deckfour.xes.util.XAttributeUtils;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.ActivityInstanceEventCollectionView;
import org.processmining.contextawareperformance.models.eventcollectionviews.ActivityNameEventCollectionView;
import org.processmining.contextawareperformance.models.eventcollectionviews.CaseEventCollectionView;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventEventCollectionView;
import org.processmining.contextawareperformance.models.eventcollectionviews.GroupEventCollectionView;
import org.processmining.contextawareperformance.models.eventcollectionviews.ProcessEventCollectionView;
import org.processmining.contextawareperformance.models.eventcollectionviews.ResourceEventCollectionView;
import org.processmining.contextawareperformance.models.eventcollectionviews.RoleEventCollectionView;
import org.processmining.contextawareperformance.models.extensions.XCaseExtension;
import org.processmining.contextawareperformance.models.parameters.context.trace.CaseAttributeContextParameters;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

/**
 * Keeps track of event log in the form of a set of events. Can return different
 * views of the event log, and takes care of caching for these views.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class EventCollection {

	private Set<XEvent> collection;
	private BiMap<String, String> changedCaseAttributeNames;
	private LoadingCache<EventCollectionViewType, Map<? extends EventCollectionEntity, ? extends Collection<XEvent>>> cache;

	private EventCollection() {
		initialize();
	}

	public EventCollection(XLog eventlog) {
		this();
		createCollectionFromXLog(eventlog);
	}

	public EventCollection(Set<XEvent> collection) {
		this();
		this.collection = collection;
	}

	private void initialize() {
		changedCaseAttributeNames = HashBiMap.create();
		cache = CacheBuilder.newBuilder().build(
				new CacheLoader<EventCollectionViewType, Map<? extends EventCollectionEntity, ? extends Collection<XEvent>>>() {
					@Override
					public Map<? extends EventCollectionEntity, ? extends Collection<XEvent>> load(
							EventCollectionViewType viewType) throws Exception {
						return loadViewAs(viewType);
					}
				});
	}

	public Set<XEvent> getCollection() {
		return collection;
	}

	public BiMap<String, String> getChangedCaseAttributeNames() {
		return changedCaseAttributeNames;
	}

	/**
	 * Returns the set of EventCollectionViewTypes that are applicable for the
	 * given EventCollection.
	 * 
	 * @param collection
	 *            The event collection.
	 * @return The set of applicable event collection view types.
	 */
	public Set<EventCollectionViewType> getApplicableViewTypes() {
		EnumSet<EventCollectionViewType> applicableTypes = EnumSet.noneOf(EventCollectionViewType.class);

		// For every type, check whether the collection contains the necessary event attributes
		for (EventCollectionViewType viewType : EnumSet.allOf(EventCollectionViewType.class)) {
			//TODO Add logic to determine which view types are applicable
			// based on the event attributes that are present (or on classifiers stored) (yes/no)
			// based on some threshold (x % of events should have the attribute)
			if (true)
				applicableTypes.add(viewType);
		}

		return applicableTypes;
	}

	/**
	 * Creates an event collection from an XLog. Events are given a traceid
	 * attribute representing their trace id (by XCaseExtension). Trace
	 * attributes are turned into event attributes. Existing event attributes
	 * with that key are kept, and the trace attribute is lost.
	 * 
	 * @param eventlog
	 *            The event log
	 */
	//TODO Make sure that no existing event attributes get overridden
	private void createCollectionFromXLog(XLog eventlog) {
		collection = new HashSet<XEvent>();

		for (XTrace trace : eventlog) {
			Set<String> caseAttributes = trace.getAttributes().keySet();
			for (String caseAttribute : caseAttributes)
				getChangedCaseAttributeNames().put(new String(caseAttribute),
						CaseAttributeContextParameters.CASE_ATTRIBUTE_PREFIX + new String(caseAttribute));

		}

		for (XTrace trace : eventlog) {
			for (XEvent event : trace) {

				XEvent eventCopy = (XEvent) event.clone();

				XCaseExtension.instance().assignCase(eventCopy, trace);
				XCaseExtension.instance().assignCaseIndex(eventCopy, trace);
				XIdentityExtension.instance().assignID(eventCopy, XIDFactory.instance().createId());

				for (String traceAttributeKey : trace.getAttributes().keySet()) {

					// Find the attribute value and its attribute type
					XAttribute traceAttributeValue = trace.getAttributes().get(traceAttributeKey);
					String traceAttributeType = XAttributeUtils.getTypeString(traceAttributeValue);

					// Get the attribute key that will be used in the event
					String changedTraceAttributeKey = getChangedCaseAttributeNames().get(traceAttributeKey);
					// Create the new attribute to add to the event, depending on the attribute type
					XAttribute changedAttribute;

					switch (traceAttributeType) {
						case "LITERAL" :
							changedAttribute = XFactoryRegistry.instance().currentDefault().createAttributeLiteral(
									changedTraceAttributeKey, traceAttributeValue.toString(), null);
							break;
						case "ID" :
							changedAttribute = XFactoryRegistry.instance().currentDefault().createAttributeID(
									changedTraceAttributeKey,
									(XIdentityExtension.instance().extractID(traceAttributeValue)), null);
							break;
						case "BOOLEAN" :
							changedAttribute = XFactoryRegistry.instance().currentDefault().createAttributeBoolean(
									changedTraceAttributeKey, Boolean.parseBoolean(traceAttributeValue.toString()),
									null);
							break;
						case "DISCRETE" :
							changedAttribute = XFactoryRegistry.instance().currentDefault().createAttributeDiscrete(
									changedTraceAttributeKey, Long.parseLong(traceAttributeValue.toString()), null);
							break;
						case "CONTINUOUS" :
							changedAttribute = XFactoryRegistry.instance().currentDefault().createAttributeContinuous(
									changedTraceAttributeKey, Double.parseDouble(traceAttributeValue.toString()), null);
							break;
						case "LIST" :
							changedAttribute = XFactoryRegistry.instance().currentDefault()
									.createAttributeList(changedTraceAttributeKey, null);
							((XAttributeListImpl) changedAttribute).addToCollection(traceAttributeValue);
							break;
						case "TIMESTAMP" :
							changedAttribute = XFactoryRegistry.instance().currentDefault().createAttributeTimestamp(
									changedTraceAttributeKey,
									((XAttributeTimestampImpl) traceAttributeValue).getValue(), null);
							break;
						default : // By default, choose a Literal
							System.out.println("Unrecognized trace attribute type is: " + traceAttributeType
									+ ", converting to LITERAL.");
							changedAttribute = XFactoryRegistry.instance().currentDefault().createAttributeLiteral(
									changedTraceAttributeKey, traceAttributeValue.toString(), null);
							break;
					}

					// Add the attribute to the event
					eventCopy.getAttributes().put(changedTraceAttributeKey, changedAttribute);
				}

				collection.add(eventCopy);
				eventCopy = null;
			}
		}

	}

	/**
	 * Returns the requested view on the event log. Only used internally to fill
	 * cache.
	 * 
	 * @param viewType
	 *            The required view.
	 * @return The view on the collection. Returns null when the view type is
	 *         not known.
	 */
	//@Nullable
	private Map<? extends EventCollectionEntity, ? extends Collection<XEvent>> loadViewAs(
			EventCollectionViewType viewType) {

		Map<? extends EventCollectionEntity, ? extends Collection<XEvent>> view = null;

		switch (viewType) {
			case CASE :
				view = new CaseEventCollectionView().view(collection);
				break;
			case ACTIVITY :
				view = new ActivityNameEventCollectionView().view(collection);
				break;
			case ACTIVITYINSTANCE :
				view = new ActivityInstanceEventCollectionView().view(collection);
				break;
			case EVENT :
				view = new EventEventCollectionView().view(collection);
				break;
			case PROCESS :
				view = new ProcessEventCollectionView().view(collection);
				break;
			case RESOURCE :
				view = new ResourceEventCollectionView().view(collection);
				break;
			case ROLE :
				view = new RoleEventCollectionView().view(collection);
				break;
			case GROUP :
				view = new GroupEventCollectionView().view(collection);
				break;
			default :
				view = null;
				break;
		}

		return view;
	}

	/**
	 * Returns the requested view on the event log from cache.
	 * 
	 * @param viewType
	 *            The required view.
	 * @return The view on the collection (from cache). Returns null when the
	 *         view type is not applicable or not known.
	 */
	//@Nullable
	public Map<? extends EventCollectionEntity, ? extends Collection<XEvent>> viewAs(EventCollectionViewType viewType) {
		return cache.getUnchecked(viewType);
	}

}
