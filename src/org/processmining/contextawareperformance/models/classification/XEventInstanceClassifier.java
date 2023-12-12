package org.processmining.contextawareperformance.models.classification;

import org.deckfour.xes.classification.XEventAttributeClassifier;
import org.deckfour.xes.extension.std.XConceptExtension;

/**
 * Implements an event classifier based on the caseid name attribute of events.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class XEventInstanceClassifier extends XEventAttributeClassifier {

	private static final long serialVersionUID = -4928730645233406046L;

	/**
	 * Creates a new instance of this event classifier.
	 */
	public XEventInstanceClassifier() {
		super("Instance", XConceptExtension.KEY_INSTANCE);
	}

}
