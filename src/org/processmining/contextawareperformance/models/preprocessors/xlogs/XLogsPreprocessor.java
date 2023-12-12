package org.processmining.contextawareperformance.models.preprocessors.xlogs;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.preprocessors.IPreprocessor;

public abstract class XLogsPreprocessor implements IPreprocessor<XLog[], XLog> {

	private String name;

	public XLogsPreprocessor(String name) {
		setName(name);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
