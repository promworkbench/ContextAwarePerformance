package org.processmining.contextawareperformance.models.functions.performance;

import java.util.EnumSet;
import java.util.Set;

import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;

/**
 * This enum holds all performance functions (types). Every function has a
 * description and is applicable only to a selected set of event collection
 * views.
 * 
 * @author B.F.A. Hompes
 *
 */
//@formatter:off
public enum PerformanceType {
	CASE_DURATION("Case duration", EnumSet.of(
			EventCollectionViewType.CASE, 
			EventCollectionViewType.ACTIVITY,
			EventCollectionViewType.ACTIVITYINSTANCE, 
			EventCollectionViewType.EVENT)), 
	CASE_DURATIONBETWEENTWOACTIVITIES("Duration between two activities", EnumSet.of(
			EventCollectionViewType.CASE,
			EventCollectionViewType.ACTIVITY,
			EventCollectionViewType.ACTIVITYINSTANCE, 
			EventCollectionViewType.EVENT)),
	CASE_DURATIONUNTILACTIVITY("Duration until activity", EnumSet.of(
			EventCollectionViewType.CASE,
			EventCollectionViewType.ACTIVITY,
			EventCollectionViewType.ACTIVITYINSTANCE, 
			EventCollectionViewType.EVENT)),
	CASE_FITNESS("Case fitness", EnumSet.of(
			EventCollectionViewType.CASE, 
			EventCollectionViewType.ACTIVITY,
			EventCollectionViewType.ACTIVITYINSTANCE,
			EventCollectionViewType.EVENT)),
	CASE_ACTIVITYOCCURRENCE("Case activity occurrence", EnumSet.of(
			EventCollectionViewType.CASE, 
			EventCollectionViewType.ACTIVITY,
			EventCollectionViewType.ACTIVITYINSTANCE,
			EventCollectionViewType.EVENT)),
	ACTIVITY_DURATION("Activity duration", EnumSet.of(
			EventCollectionViewType.ACTIVITYINSTANCE, 
			EventCollectionViewType.EVENT)),
	ACTIVITY_WAITINGTIME("Activity waiting time", EnumSet.of(
			EventCollectionViewType.ACTIVITYINSTANCE, 
			EventCollectionViewType.EVENT)),
	ACTIVITY_SOJOURNTIME("Activity sojourn time", EnumSet.of(
			EventCollectionViewType.ACTIVITYINSTANCE, 
			EventCollectionViewType.EVENT));

	private String description;
	private Set<EventCollectionViewType> applicableToViewTypes;

	PerformanceType(String description, Set<EventCollectionViewType> applicableToViewTypes) {
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