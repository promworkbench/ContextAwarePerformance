package org.processmining.contextawareperformance.view.visualizers.fx.model;

import org.processmining.contextawareperformance.models.functions.performance.Performance;

import jiconfont.IconCode;
import jiconfont.icons.FontAwesome;

public class PerformanceIconFactory {

	public static IconCode returnIconCode(Performance<?> performance) {

		switch (performance.getType()) {
			case ACTIVITY_DURATION :
				return FontAwesome.CLOCK_O;
			case ACTIVITY_SOJOURNTIME :
				return FontAwesome.CLOCK_O;
			case ACTIVITY_WAITINGTIME :
				return FontAwesome.HOURGLASS;
			case CASE_ACTIVITYOCCURRENCE :
				return FontAwesome.LIST_OL;
			case CASE_DURATION :
				return FontAwesome.CLOCK_O;
			case CASE_DURATIONBETWEENTWOACTIVITIES :
				return FontAwesome.CLOCK_O;
			case CASE_DURATIONUNTILACTIVITY :
				return FontAwesome.CLOCK_O;
			case CASE_FITNESS :
				return FontAwesome.BAR_CHART;
			default :
				break;
		}

		return FontAwesome.QUESTION;
	}

}
