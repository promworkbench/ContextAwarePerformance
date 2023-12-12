package org.processmining.contextawareperformance.models.comparators;

import java.io.Serializable;
import java.util.Comparator;

import org.deckfour.xes.extension.std.XLifecycleExtension;
import org.deckfour.xes.extension.std.XTimeExtension;
import org.deckfour.xes.model.XEvent;

/**
 * Event comparator based on the timestamp and lifecycle transition.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class XEventTimeStampAndLifeCycleTransitionComparator implements Comparator<XEvent>, Serializable {

	private static final long serialVersionUID = -2708908478140668860L;

	public static final String DESCRIPTION;

	static {
		DESCRIPTION = "Sort events based on their timestamp and their lifecycle transition.";
	}

	XTimeExtension te = XTimeExtension.instance();
	XLifecycleExtension le = XLifecycleExtension.instance();

	@Override
	public int compare(XEvent e1, XEvent e2) {
		//TODO use lifecycle information!
		return te.extractTimestamp(e1).compareTo(te.extractTimestamp(e2));
	}

	@Override
	public String toString() {
		return DESCRIPTION;
	}
}