package org.processmining.contextawareperformance.view.dialogs;

import java.awt.Component;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import org.processmining.framework.util.ui.widgets.ProMPropertiesPanel;
import org.processmining.framework.util.ui.widgets.ProMScrollablePanel;

/**
 * Custom extension to the ProMTextfield to allow Tooltips (and in the future
 * also more strict formatting checks etc.)
 * 
 * @author jbuijs
 * 
 */
public class TooltipHelper {

	/**
	 * This helper function takes a propertiesPanel and a component (e.g.
	 * ProMTextfield) on the panel and looks for this element and attaches the
	 * provided tooltip text to the component and its preceding label.
	 * 
	 * @param propertiesPane
	 *            The pane that contains the elements
	 * @param attachTo
	 *            The component that should have the tooltip attached, but also
	 *            to its sibblings (e.g. on the same level)
	 * @param tooltip
	 *            The tooltip text to attach.
	 */
	public static void addTooltip(ProMPropertiesPanel propertiesPanel, JComponent attachTo, String tooltip) {
		if (attachTo == null || propertiesPanel == null || propertiesPanel.getComponent(0) == null) {
			return;
		}

		//First get the component that actually holds all the GUI things
		ProMScrollablePanel panel = (ProMScrollablePanel) ((JViewport) ((JScrollPane) propertiesPanel.getComponent(0))
				.getComponent(0)).getComponent(0);

		attachTo.setToolTipText(tooltip);

		//Now find the panel the component belongs to
		if (!(attachTo instanceof JLabel)) {
			//Now get all components
			Component[] components = panel.getComponents();
			for (int c = 0; c < components.length; c++) {
				//And loop through all its children
				Component[] currentComponents = ((JComponent) components[c]).getComponents();
				for (int child = 0; child < currentComponents.length; child++) {
					//When we found the provided component in this panel
					if (currentComponents[child].equals(attachTo)) {
						//Add the tooltip to all components on this panel :)
						((JComponent) currentComponents[child]).setToolTipText(tooltip);
						for (int tooltipIndex = 0; tooltipIndex < currentComponents.length; tooltipIndex++) {
							if (currentComponents[tooltipIndex] instanceof JComponent) {
								((JComponent) currentComponents[tooltipIndex]).setToolTipText(tooltip);
							}
						}
					}
				}
			}
		}
	}
}
