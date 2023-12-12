package org.processmining.contextawareperformance.view.visualizers.fx.model;

import org.processmining.contextawareperformance.models.functions.context.Context;

import jiconfont.IconCode;
import jiconfont.icons.FontAwesome;

public class ContextIconFactory {

	public static IconCode returnIconCode(Context<?> context) {

		switch (context.getType()) {
			case ACTIVITYINSTANCEATTRIBUTE :
				return FontAwesome.FILE_O;
			case ACTIVITYINSTANCEEXECUTINGGROUP :
				return FontAwesome.USERS;
			case ACTIVITYINSTANCEEXECUTINGRESOURCE :
				return FontAwesome.USER;
			case ACTIVITYINSTANCEEXECUTINGROLE :
				return FontAwesome.USER_PLUS;
			case CASEATTRIBUTE :
				return FontAwesome.FILE_O;
			case EVENTACTIVITYNAMEANDLIFECYCLEPREFIX :
				return FontAwesome.ANGLE_DOUBLE_RIGHT;
			case EVENTACTIVITYNAMEPREFIX :
				return FontAwesome.ANGLE_DOUBLE_RIGHT;
			case EVENTATTRIBUTE :
				return FontAwesome.FILE_O;
			case EVENTATTRIBUTEPREFIX :
				return FontAwesome.ANGLE_DOUBLE_RIGHT;
			case EVENTEXECUTINGGROUP :
				return FontAwesome.USERS;
			case EVENTEXECUTINGRESOURCE :
				return FontAwesome.USER;
			case EVENTEXECUTINGROLE :
				return FontAwesome.USER_PLUS;
			case EVENTRESOURCEPREFIX :
				return FontAwesome.ANGLE_DOUBLE_RIGHT;
			default :
				break;
		}

		return FontAwesome.QUESTION;
	}

}
