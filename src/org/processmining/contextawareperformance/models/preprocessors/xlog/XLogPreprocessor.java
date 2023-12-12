package org.processmining.contextawareperformance.models.preprocessors.xlog;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.preprocessors.IPreprocessor;

public abstract class XLogPreprocessor implements IPreprocessor<XLog, XLog> {

	private String name;

	public XLogPreprocessor(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
