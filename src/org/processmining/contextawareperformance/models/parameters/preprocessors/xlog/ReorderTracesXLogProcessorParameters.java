package org.processmining.contextawareperformance.models.parameters.preprocessors.xlog;

import java.util.Objects;

import org.processmining.basicutils.parameters.impl.PluginParametersImpl;

public class ReorderTracesXLogProcessorParameters extends PluginParametersImpl {

	public static final String DESCRIPTION;
	public static final Reordermethod DEFAULT_REORDERMETHOD;
	public static boolean DEFAULT_CLONE;

	static {
		DESCRIPTION = "ReorderTraces parameters";
		DEFAULT_REORDERMETHOD = Reordermethod.EVENTTIMESTAMP;
		DEFAULT_CLONE = false;
	}

	private Reordermethod reordermethod;
	private boolean clone;

	public ReorderTracesXLogProcessorParameters() {
		super();
		setReordermethod(DEFAULT_REORDERMETHOD);
		setClone(DEFAULT_CLONE);
	}

	public ReorderTracesXLogProcessorParameters(ReorderTracesXLogProcessorParameters parameters) {
		super(parameters);
		setReordermethod(parameters.getReordermethod());
	}

	public Reordermethod getReordermethod() {
		return reordermethod;
	}

	public void setReordermethod(Reordermethod reordermethod) {
		this.reordermethod = reordermethod;
	}

	public boolean isClone() {
		return clone;
	}

	public void setClone(boolean clone) {
		this.clone = clone;
	}

	@Override
	public String toString() {
		return DESCRIPTION + reordermethod.getDescription();
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof ReorderTracesXLogProcessorParameters))
			return false;

		ReorderTracesXLogProcessorParameters parameters = (ReorderTracesXLogProcessorParameters) obj;

		if (!getReordermethod().equals(parameters.getReordermethod()))
			return false;

		if (isClone() != parameters.isClone())
			return false;

		return super.equals(parameters);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), reordermethod, clone);
	}

	//@formatter:off
	public enum Reordermethod {
		EVENTTIMESTAMP("Reorder using event timestamps only. No lifecycle information is used."),
		EVENTCASEINDEX("Reorder using original case ordering. No timestamp or lifecycle information is used.");
		//TODO add additional reorder strategies (e.g. one that uses partial order theory or a process model as additional input)
//		EVENTTIMESTAMPSANDLIFECYCLETRANSITION("Reorder using event timestamps and lifecycle information.");

		private String description;

		Reordermethod(String description) {
			setDescription(description);
		}
		
		@Override
		public String toString(){
			return description;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}
	//@formatter:on
}