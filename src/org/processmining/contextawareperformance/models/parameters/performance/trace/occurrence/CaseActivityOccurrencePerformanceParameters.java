package org.processmining.contextawareperformance.models.parameters.performance.trace.occurrence;

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

public class CaseActivityOccurrencePerformanceParameters extends PerformanceParameters {

	private static final long serialVersionUID = -6302610362522706985L;

	public static final String DESCRIPTION;
	public static final Comparator<XEvent> DEFAULT_COMPARATOR;
	public static final String DEFAULT_ACTIVITY_CONCEPTNAME;

	static {
		DESCRIPTION = "Case activity occurrence parameters";
		DEFAULT_COMPARATOR = new XEventTimeStampComparator();
		DEFAULT_ACTIVITY_CONCEPTNAME = "";
	}

	private String activityConceptname;
	private Comparator<XEvent> comparator;

	public CaseActivityOccurrencePerformanceParameters() {
		super(DESCRIPTION);
		setActivityConceptname(DEFAULT_ACTIVITY_CONCEPTNAME);
		setComparator(DEFAULT_COMPARATOR);
	}

	public String getActivityConceptname() {
		return activityConceptname;
	}

	public void setActivityConceptname(String activityConceptname) {
		this.activityConceptname = activityConceptname;
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

		builder.append("Activity: " + activityConceptname);

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
		final ProMComboBox<String> cmbActivity = new ProMComboBox<String>(activityNameList);
		cmbActivity.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					activityConceptname = cmbActivity.getSelectedItem().toString();
				}
			}
		});
		JLabel lblActivity = SlickerFactory.instance().createLabel("Activity:");
		lblActivity.setLabelFor(cmbActivity);
		if (activityNameList.size() > 0)
			cmbActivity.setSelectedIndex(0);

		content.add(lblActivity);
		content.add(cmbActivity);

		SpringUtilities.makeCompactGrid(content, 1, 2, //rows, cols
				6, 6, //initX, initY
				6, 6); //xPad, yPad

		return panel;
	}
}
