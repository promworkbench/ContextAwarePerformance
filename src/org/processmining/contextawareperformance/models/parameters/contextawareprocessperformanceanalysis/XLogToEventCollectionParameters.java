package org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis;

import org.processmining.basicutils.parameters.impl.PluginParametersImpl;

public class XLogToEventCollectionParameters extends PluginParametersImpl {

	private static final boolean DEFAULT_CLONE;

	static {
		DEFAULT_CLONE = false;
	}

	private boolean clone;

	public XLogToEventCollectionParameters() {
		setClone(DEFAULT_CLONE);
	}

	public XLogToEventCollectionParameters(boolean clone) {
		setClone(clone);
	}

	public boolean isClone() {
		return clone;
	}

	public void setClone(boolean clone) {
		this.clone = clone;
	}
}
