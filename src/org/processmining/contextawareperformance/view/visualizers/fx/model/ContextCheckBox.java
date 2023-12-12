package org.processmining.contextawareperformance.view.visualizers.fx.model;

import org.processmining.contextawareperformance.models.functions.context.Context;

import javafx.scene.control.CheckBox;

public class ContextCheckBox extends CheckBox {

	private Context<?> context;

	public ContextCheckBox(Context<?> context) {
		super(context.toString());
		setGraphic(IconFactory.createIcon(context));
		setContext(context);
	}

	public Context<?> getContext() {
		return context;
	}

	public void setContext(Context<?> context) {
		this.context = context;
	}

}
