package org.processmining.contextawareperformance.models.eventcollectionviews;

import org.deckfour.xes.classification.XEventAttributeClassifier;
import org.deckfour.xes.classification.XEventNameClassifier;
import org.deckfour.xes.classification.XEventResourceClassifier;
import org.processmining.contextawareperformance.models.classification.XEventCaseClassifier;
import org.processmining.contextawareperformance.models.classification.XEventGroupClassifier;
import org.processmining.contextawareperformance.models.classification.XEventIdentityClassifier;
import org.processmining.contextawareperformance.models.classification.XEventInstanceClassifier;
import org.processmining.contextawareperformance.models.classification.XEventRoleClassifier;

import javassist.NotFoundException;

/**
 * Event collection view enumerator. Holds the possible views on an event
 * collection. Each view has a list of classifiers that have to be present in
 * the event collection for the view to be valid.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
//@formatter:off
public enum EventCollectionViewType {
	CASE("Case", new XEventCaseClassifier()),
	ACTIVITY("Activity", new XEventNameClassifier()),
	ACTIVITYINSTANCE("Activity instance", new XEventInstanceClassifier()),
	EVENT("Event", new XEventIdentityClassifier()),
	PROCESS("Process", null),
	RESOURCE("Resource", new XEventResourceClassifier()), 
	ROLE("Role", new XEventRoleClassifier()),
	GROUP("Group", new XEventGroupClassifier());

	private String description;
	private XEventAttributeClassifier classifier;

	EventCollectionViewType(String description, XEventAttributeClassifier classifier) {
		this.setDescription(description);
		this.setClassifier(classifier);
	}

	public void setClassifier(XEventAttributeClassifier classifier) {
		this.classifier = classifier;
	}

	public String getDescription() {
		return description;
	}

	public XEventAttributeClassifier getClassifier() {
		return classifier;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	/**
	 * Can be used to parse a String to an EventCollectionViewType.
	 * @param description The String to parse
	 * @return The first EventCollectionViewType that matches the description or null in case no such type is found. 
	 * @throws NotFoundException 
	 */
	//@Nullable
	public static EventCollectionViewType parse(String description) throws Exception{
		for(EventCollectionViewType type: EventCollectionViewType.values()){
			if(type.getDescription().equals(description))
				return type;
		}
		throw new NotFoundException("Could not parse EventCollectionType description: "+description);
	}
	
	@Override
	public String toString(){
		return getDescription();
	}
		
}
//@formatter:on