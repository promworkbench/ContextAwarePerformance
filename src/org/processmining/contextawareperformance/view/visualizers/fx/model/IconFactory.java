package org.processmining.contextawareperformance.view.visualizers.fx.model;

import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.performance.Performance;

import javafx.scene.Node;
import javafx.scene.control.Label;
import jiconfont.IconCode;
import jiconfont.javafx.IconNode;

public class IconFactory {

	private static Label createIconLabel(IconCode icon, int iconSize, String text) {
		IconNode iconNode = new IconNode(icon);
		iconNode.setIconSize(iconSize);
		Label label = new Label(text);
		label.setGraphic(iconNode);
		return label;
	}

	public static Node createIcon(Context<?> context) {
		return createIconLabel(ContextIconFactory.returnIconCode(context), 16, " ");
	}

	public static Node createIcon(Performance<?> performance) {
		return createIconLabel(PerformanceIconFactory.returnIconCode(performance), 16, " ");
	}

}