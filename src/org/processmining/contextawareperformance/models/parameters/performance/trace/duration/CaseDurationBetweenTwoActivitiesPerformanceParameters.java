package org.processmining.contextawareperformance.models.parameters.performance.trace.duration;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;

import org.deckfour.xes.extension.std.XConceptExtension;
import org.deckfour.xes.model.XEvent;
import org.deckfour.xes.model.XLog;
import org.deckfour.xes.model.XTrace;
import org.processmining.contextawareperformance.models.comparators.XEventTimeStampComparator;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.parameters.performance.PerformanceParameters;
import org.processmining.contextawareperformance.view.dialogs.PerformanceProMTitledScrollContainerChild;
import org.processmining.contextawareperformance.view.utils.swing.SpringUtilities;
import org.processmining.framework.util.ui.widgets.ProMComboBox;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;

import com.fluxicon.slickerbox.factory.SlickerFactory;

public class CaseDurationBetweenTwoActivitiesPerformanceParameters extends PerformanceParameters {

	private static final long serialVersionUID = 807066196577517084L;

	public static final String DESCRIPTION;
	public static final Comparator<XEvent> DEFAULT_COMPARATOR;
	public static final String DEFAULT_FROM_ACTIVITY_CONCEPTNAME;
	public static final String DEFAULT_TO_ACTIVITY_CONCEPTNAME;

	static {
		DESCRIPTION = "Case duration between two activities parameters";
		DEFAULT_COMPARATOR = new XEventTimeStampComparator();
		DEFAULT_FROM_ACTIVITY_CONCEPTNAME = "";
		DEFAULT_TO_ACTIVITY_CONCEPTNAME = "";
	}

	private String fromActivityConceptname;
	private String toActivityConceptname;
	private Comparator<XEvent> comparator;

	public CaseDurationBetweenTwoActivitiesPerformanceParameters() {
		super(DESCRIPTION);
		setComparator(DEFAULT_COMPARATOR);
		setFromActivityConceptname(DEFAULT_FROM_ACTIVITY_CONCEPTNAME);
		setToActivityConceptname(DEFAULT_TO_ACTIVITY_CONCEPTNAME);
	}

	public String getFromActivityConceptname() {
		return fromActivityConceptname;
	}

	public void setFromActivityConceptname(String fromEventConceptname) {
		this.fromActivityConceptname = fromEventConceptname;
	}

	public String getToActivityConceptname() {
		return toActivityConceptname;
	}

	public void setToActivityConceptname(String toEventConceptName) {
		this.toActivityConceptname = toEventConceptName;
	}

	public Comparator<XEvent> getComparator() {
		return comparator;
	}

	public void setComparator(Comparator<XEvent> comparator) {
		this.comparator = comparator;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append("from activity " + fromActivityConceptname + " to activity " + toActivityConceptname);

		return builder.toString();
	}

	@Override
	public PerformanceProMTitledScrollContainerChild getGUIPanel(ProMScrollContainer parent, Performance<?> performance,
			XLog eventlog) {
		PerformanceProMTitledScrollContainerChild panel = new PerformanceProMTitledScrollContainerChild(parent,
				performance);

		JPanel content = panel.getContentPanel();
		content.setLayout(new SpringLayout());

		Set<String> activityNameSet = new HashSet<String>();
		for (XTrace trace : eventlog) {
			for (XEvent event : trace) {
				if (event.getAttributes().containsKey(XConceptExtension.KEY_NAME))
					activityNameSet.add(XConceptExtension.instance().extractName(event));
			}
		}

		List<String> activityNameList = new ArrayList<String>(activityNameSet);
		Collections.sort(activityNameList);

		final ProMComboBox<String> cmbFromActivity = new ProMComboBox<String>(activityNameList);
		cmbFromActivity.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					fromActivityConceptname = cmbFromActivity.getSelectedItem().toString();
				}
			}
		});
		JLabel lblFromActivity = SlickerFactory.instance().createLabel("From activity:");
		lblFromActivity.setLabelFor(cmbFromActivity);
		if (activityNameList.size() > 0)
			cmbFromActivity.setSelectedIndex(0);

		final ProMComboBox<String> cmbToActivity = new ProMComboBox<String>(activityNameList);
		cmbToActivity.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					toActivityConceptname = cmbToActivity.getSelectedItem().toString();
				}
			}
		});
		JLabel lblToActivity = SlickerFactory.instance().createLabel("To activity:");
		lblToActivity.setLabelFor(cmbToActivity);
		if (activityNameList.size() > 0)
			cmbToActivity.setSelectedIndex(0);

		content.add(lblFromActivity);
		content.add(cmbFromActivity);
		content.add(lblToActivity);
		content.add(cmbToActivity);

		SpringUtilities.makeCompactGrid(content, 2, 2, //rows, cols
				6, 6, //initX, initY
				6, 6); //xPad, yPad

		fromActivityConceptname = "";
		toActivityConceptname = "";

		return panel;
	}
}
