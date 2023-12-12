package org.processmining.contextawareperformance.models.parameters.preprocessors.xlog;

import java.util.Objects;

import org.processmining.basicutils.parameters.impl.PluginParametersImpl;

public class RemoveEventsWithoutTimestampXLogProcessorParameters extends PluginParametersImpl {

	public static final String DESCRIPTION;
	public static final boolean DEFAULT_CLONE;

	static {
		DESCRIPTION = "RemoveEventsWithoutTimestamp parameters";
		DEFAULT_CLONE = false;
	}

	private boolean clone;

	public RemoveEventsWithoutTimestampXLogProcessorParameters() {
		super();
		setClone(DEFAULT_CLONE);
	}

	public boolean isClone() {
		return clone;
	}

	public void setClone(boolean clone) {
		this.clone = clone;
	}

	@Override
	public String toString() {
		return DESCRIPTION;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof RemoveEventsWithoutTimestampXLogProcessorParameters))
			return false;

		RemoveEventsWithoutTimestampXLogProcessorParameters parameters = (RemoveEventsWithoutTimestampXLogProcessorParameters) object;

		if (parameters.isClone() != isClone())
			return false;

		return super.equals(parameters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), clone);
	}
}