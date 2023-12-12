package org.processmining.contextawareperformance.models.classification;

import org.deckfour.xes.classification.XEventAttributeClassifier;
import org.deckfour.xes.extension.std.XOrganizationalExtension;

/**
 * Implements an event classifier based on the group name attribute of events.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class XEventGroupClassifier extends XEventAttributeClassifier {

	private static final long serialVersionUID = -4928730645233406046L;

	/**
	 * Creates a new instance of this event classifier.
	 */
	public XEventGroupClassifier() {
		super("Group", XOrganizationalExtension.KEY_GROUP);
	}

}
