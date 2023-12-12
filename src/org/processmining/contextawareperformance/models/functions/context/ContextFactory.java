package org.processmining.contextawareperformance.models.functions.context;

import org.processmining.contextawareperformance.models.functions.context.activiyinstance.ActivityInstanceAttributeContext;
import org.processmining.contextawareperformance.models.functions.context.activiyinstance.ActivityInstanceExecutingGroupContext;
import org.processmining.contextawareperformance.models.functions.context.activiyinstance.ActivityInstanceExecutingResourceContext;
import org.processmining.contextawareperformance.models.functions.context.activiyinstance.ActivityInstanceExecutingRoleContext;
import org.processmining.contextawareperformance.models.functions.context.event.EventAttributeContext;
import org.processmining.contextawareperformance.models.functions.context.event.EventExecutingResourceContext;
import org.processmining.contextawareperformance.models.functions.context.event.prefix.ActivityNameAndLifeCycleEventPrefixContext;
import org.processmining.contextawareperformance.models.functions.context.event.prefix.ActivityNameEventPrefixContext;
import org.processmining.contextawareperformance.models.functions.context.event.prefix.AttributeEventPrefixContext;
import org.processmining.contextawareperformance.models.functions.context.event.prefix.ExecutingResourceEventPrefixContext;
import org.processmining.contextawareperformance.models.functions.context.trace.CaseAttributeContext;

public class ContextFactory {

	public static Context<?> construct(ContextType type) {
		Context<?> context = null;
		switch (type) {
			case ACTIVITYINSTANCEATTRIBUTE :
				context = new ActivityInstanceAttributeContext();
				break;
			case ACTIVITYINSTANCEEXECUTINGRESOURCE :
				context = new ActivityInstanceExecutingResourceContext();
				break;
			case ACTIVITYINSTANCEEXECUTINGROLE :
				context = new ActivityInstanceExecutingRoleContext();
				break;
			case ACTIVITYINSTANCEEXECUTINGGROUP :
				context = new ActivityInstanceExecutingGroupContext();
				break;
			case CASEATTRIBUTE :
				context = new CaseAttributeContext();
				break;
			case EVENTACTIVITYNAMEANDLIFECYCLEPREFIX :
				context = new ActivityNameAndLifeCycleEventPrefixContext();
				break;
			case EVENTACTIVITYNAMEPREFIX :
				context = new ActivityNameEventPrefixContext();
				break;
			case EVENTATTRIBUTE :
				context = new EventAttributeContext();
				break;
			case EVENTATTRIBUTEPREFIX :
				context = new AttributeEventPrefixContext();
				break;
			case EVENTEXECUTINGRESOURCE :
				context = new EventExecutingResourceContext();
				break;
			case EVENTEXECUTINGROLE :
				context = new EventExecutingResourceContext();
				break;
			case EVENTEXECUTINGGROUP :
				context = new EventExecutingResourceContext();
				break;
			case EVENTRESOURCEPREFIX :
				context = new ExecutingResourceEventPrefixContext();
				break;
			default :
				// We should never reach the default statement
				System.out.println("The given context type is not implemented in the factory yet.");
				break;
		}
		return context;
	}

}
