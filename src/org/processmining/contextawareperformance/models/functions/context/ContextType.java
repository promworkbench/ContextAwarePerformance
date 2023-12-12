package org.processmining.contextawareperformance.models.functions.context;

import java.util.EnumSet;
import java.util.Set;

import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;

/**
 * This enum holds all context functions (types). Every function has a
 * description and is applicable only to a selected set of event collection
 * views.
 * 
 * @author B.F.A. Hompes
 *
 */
//@formatter:off
public enum ContextType {
	EVENTACTIVITYNAMEANDLIFECYCLEPREFIX("Event activity concept name and lifecycle prefix", EnumSet.of(
			EventCollectionViewType.EVENT)),
	EVENTACTIVITYNAMEPREFIX("Event activity concept name prefix", EnumSet.of(
			EventCollectionViewType.EVENT)),
	EVENTATTRIBUTEPREFIX("Event attribute prefix", EnumSet.of(
			EventCollectionViewType.EVENT)),
	EVENTRESOURCEPREFIX("Event executing resource prefix", EnumSet.of(
			EventCollectionViewType.EVENT)),
	EVENTEXECUTINGRESOURCE("Event executing resource", EnumSet.of(
			EventCollectionViewType.EVENT)),
	EVENTEXECUTINGROLE("Event executing role", EnumSet.of(
			EventCollectionViewType.EVENT)),
	EVENTEXECUTINGGROUP("Event executing group", EnumSet.of(
			EventCollectionViewType.EVENT)),
	ACTIVITYINSTANCEEXECUTINGRESOURCE("Activity instance executing resource", EnumSet.of(
			EventCollectionViewType.ACTIVITYINSTANCE)),
	ACTIVITYINSTANCEEXECUTINGROLE("Activity instance executing role", EnumSet.of(
			EventCollectionViewType.ACTIVITYINSTANCE)),
	ACTIVITYINSTANCEEXECUTINGGROUP("Activity instance executing group", EnumSet.of(
			EventCollectionViewType.ACTIVITYINSTANCE)),
	ACTIVITYINSTANCEATTRIBUTE("Activity instance attribute", EnumSet.of(
			EventCollectionViewType.ACTIVITYINSTANCE)),
	EVENTATTRIBUTE("Event attribute", EnumSet.of(
			EventCollectionViewType.EVENT)),
	CASEATTRIBUTE("Case attribute", EnumSet.of(
			EventCollectionViewType.CASE));

	private String description;
	private Set<EventCollectionViewType> applicableToViewTypes;

	ContextType(String description, Set<EventCollectionViewType> applicableToViewTypes) {
		this.description = description;
		this.applicableToViewTypes = applicableToViewTypes;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Set<EventCollectionViewType> getApplicableViewTypes(){
		return applicableToViewTypes;
	}
	
	@Override
	public String toString(){
		return description;
	}
}
//@formatter:on