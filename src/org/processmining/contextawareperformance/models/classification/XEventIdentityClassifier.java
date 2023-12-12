package org.processmining.contextawareperformance.models.classification;

import org.deckfour.xes.classification.XEventAttributeClassifier;
import org.deckfour.xes.extension.std.XIdentityExtension;

/**
 * Implements an event classifier based on the event identity attribute of
 * events.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class XEventIdentityClassifier extends XEventAttributeClassifier {

	private static final long serialVersionUID = -4928730645233406046L;

	/**
	 * Creates a new instance of this event classifier.
	 */
	public XEventIdentityClassifier() {
		super("Identity", XIdentityExtension.KEY_ID);
	}

}
