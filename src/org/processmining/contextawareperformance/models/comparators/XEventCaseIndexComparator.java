package org.processmining.contextawareperformance.models.comparators;

import java.io.Serializable;
import java.util.Comparator;

import org.deckfour.xes.model.XEvent;
import org.processmining.contextawareperformance.models.extensions.XCaseExtension;

/**
 * Event comparator based on the original ordering, i.e. the index in the trace
 * of the case.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class XEventCaseIndexComparator implements Comparator<XEvent>, Serializable {

	private static final long serialVersionUID = 4496857600325182378L;

	public static final String DESCRIPTION;

	static {
		DESCRIPTION = "Sort events based on their original ordering.";
	}

	private XCaseExtension ce = XCaseExtension.instance();

	@Override
	public int compare(XEvent e1, XEvent e2) {
		return ce.extractCaseIndex(e1).compareTo(ce.extractCaseIndex(e2));
	}

	@Override
	public String toString() {
		return DESCRIPTION;
	}
}