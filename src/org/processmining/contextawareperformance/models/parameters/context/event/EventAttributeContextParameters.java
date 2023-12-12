package org.processmining.contextawareperformance.models.parameters.context.event;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.parameters.context.ContextParameters;
import org.processmining.contextawareperformance.view.dialogs.ContextProMTitledScrollContainerChild;
import org.processmining.contextawareperformance.view.utils.swing.SpringUtilities;
import org.processmining.framework.util.ui.widgets.ProMComboBox;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;

import com.fluxicon.slickerbox.factory.SlickerFactory;

public class EventAttributeContextParameters extends ContextParameters {

	private static final long serialVersionUID = -2251838778449999876L;

	public static final String DESCRIPTION;
	public static final String DEFAULT_ATTRIBUTENAME;

	static {
		DESCRIPTION = "Event attribute context parameters";
		DEFAULT_ATTRIBUTENAME = "concept:name";
	}

	private List<String> attributeNames;

	public EventAttributeContextParameters() {
		super(DESCRIPTION);
		setAttributeNames(new ArrayList<String>());
		getAttributeNames().add(DEFAULT_ATTRIBUTENAME);
	}

	public List<String> getAttributeNames() {
		return attributeNames;
	}

	public void setAttributeNames(List<String> attributeNames) {
		this.attributeNames = attributeNames;
	}

	public void setAttributeNames(String... attributes) {
		attributeNames = new ArrayList<String>();
		for (String attribute : attributes) {
			attributeNames.add(attribute);
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof EventAttributeContextParameters))
			return false;

		EventAttributeContextParameters parameters = (EventAttributeContextParameters) obj;

		if (!parameters.getAttributeNames().equals(getAttributeNames()))
			return false;

		return super.equals(parameters);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("attribute value ");
		builder.append(getAttributeNames().toString());

		return builder.toString();
	}

	@Override
	public ContextProMTitledScrollContainerChild getGUIPanel(ProMScrollContainer parent, Context<?> context,
			XLog eventlog) {
		ContextProMTitledScrollContainerChild panel = new ContextProMTitledScrollContainerChild(parent, context);

		JPanel content = panel.getContentPanel();
		content.setLayout(new SpringLayout());

		Set<String> attributeSet = new HashSet<String>();
		for (XTrace trace : eventlog) {
			for (XEvent event : trace) {
				attributeSet.addAll(event.getAttributes().keySet());
			}
		}
		List<String> attributeList = new ArrayList<String>(attributeSet);
		Collections.sort(attributeList);

		final ProMComboBox<String> cmbAttributeNames = new ProMComboBox<String>(attributeList);
		cmbAttributeNames.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					attributeNames = new ArrayList<String>();
					attributeNames.add(cmbAttributeNames.getSelectedItem().toString());
				}
			}
		});

		JLabel lblAttributeNames = SlickerFactory.instance().createLabel("Attribute:");
		lblAttributeNames.setLabelFor(cmbAttributeNames);

		content.add(lblAttributeNames);
		content.add(cmbAttributeNames);

		SpringUtilities.makeCompactGrid(content, 1, 2, //rows, cols
				6, 6, //initX, initY
				6, 6); //xPad, yPad

		return panel;
	}

}
