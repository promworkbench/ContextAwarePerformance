package org.processmining.contextawareperformance.models.extensions;

import java.net.URI;

import org.deckfour.xes.extension.XExtension;
import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.factory.XFactory;
import org.deckfour.xes.factory.XFactoryRegistry;
import org.deckfour.xes.model.XAttribute;
import org.deckfour.xes.model.XAttributeDiscrete;
import org.deckfour.xes.model.XAttributeLiteral;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XTrace;

/**
 * Extension that adds a Case ID to events. When traditional XLogs are converted
 * to EventCollections the case notion is lost. The Case extension can be used
 * to retrieve event belonging to the same case.
 * 
 * @author B.F.A. Hompes
 */
public class XCaseExtension extends XExtension {

	private static final long serialVersionUID = -4974421470391509121L;

	/**
	 * Properties of this XES extension. Note that this extension exists only in
	 * code, it does not have a unique URI at this moment.
	 */
	private static final String NAME;
	private static final String PREFIX;
	private static final URI URII;

	/**
	 * Singleton instance of this extension.
	 */
	private static XCaseExtension SINGLETON;

	/**
	 * Keys for the attribute.
	 */
	public static final String KEY_CASE;
	public static final String KEY_INDEX;

	/**
	 * Attribute prototype.
	 */
	public static XAttributeLiteral ATTR_CASE;
	public static XAttributeDiscrete ATTR_INDEX;

	static {
		NAME = "case";
		PREFIX = "case";
		URII = URI.create("none");

		KEY_CASE = "case";
		KEY_INDEX = "case_index";

		SINGLETON = new XCaseExtension();
	}

	/**
	 * Private constructor
	 */
	private XCaseExtension() {
		super(NAME, PREFIX, URII);
		XFactory factory = XFactoryRegistry.instance().currentDefault();
		ATTR_CASE = factory.createAttributeLiteral(KEY_CASE, "__INVALID__", this);
		ATTR_INDEX = factory.createAttributeDiscrete(KEY_INDEX, -1, this);
		this.eventAttributes.add((XAttributeLiteral) ATTR_CASE.clone());
		this.eventAttributes.add((XAttributeDiscrete) ATTR_INDEX.clone());
	}

	/**
	 * Provides static access to the singleton instance of this extension.
	 * 
	 * @return Singleton instance.
	 */
	public static XCaseExtension instance() {
		return SINGLETON;
	}

	private Object readResolve() {
		return SINGLETON;
	}

	public String extractCase(XEvent event) {
		XAttribute attribute = event.getAttributes().get(KEY_CASE);
		if (attribute == null) {
			return null;
		} else {
			return ((XAttributeLiteral) attribute).getValue();
		}
	}

	public void assignCase(XEvent event, String caseid) {
		if (caseid != null && caseid.trim().length() > 0) {
			XAttributeLiteral attr = (XAttributeLiteral) ATTR_CASE.clone();
			attr.setValue(caseid.trim());
			event.getAttributes().put(KEY_CASE, attr);
		}
	}

	public void assignCase(XEvent event, XTrace trace) {
		assignCase(event, XConceptExtension.instance().extractName(trace));
	}

	public Long extractCaseIndex(XEvent event) {
		XAttribute attribute = event.getAttributes().get(KEY_INDEX);
		if (attribute == null)
			return null;
		else
			return ((XAttributeDiscrete) attribute).getValue();
	}

	public void assignCaseIndex(XEvent event, int index) {
		if (index != -1) {
			XAttributeDiscrete attr = (XAttributeDiscrete) ATTR_INDEX.clone();
			attr.setValue(index);
			event.getAttributes().put(KEY_INDEX, attr);
		}
	}

	public void assignCaseIndex(XEvent event, XTrace trace) {
		assignCaseIndex(event, trace.indexOf(event));
	}
}
