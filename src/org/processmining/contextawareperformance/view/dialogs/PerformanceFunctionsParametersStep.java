package org.processmining.contextawareperformance.view.dialogs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.functions.performance.PerformanceFactory;
import org.processmining.contextawareperformance.models.functions.performance.PerformanceType;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisParameters;
import org.processmining.contextawareperformance.models.parameters.performance.PerformanceParameters;
import org.processmining.framework.util.ui.widgets.ProMComboBox;
import org.processmining.framework.util.ui.widgets.ProMHeaderPanel;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;
import org.processmining.framework.util.ui.widgets.ProMScrollContainerChild;
import org.processmining.framework.util.ui.wizard.ProMWizardStep;

import com.fluxicon.slickerbox.factory.SlickerFactory;

public class PerformanceFunctionsParametersStep extends ProMHeaderPanel
		implements ProMWizardStep<ContextAwareProcessPerformanceAnalysisParameters> {

	private static final long serialVersionUID = 1054786127714893932L;

	private String title;

	private JPanel pnlAddFunctions;
	private ProMComboBox<PerformanceType> cmbAvailableFunctions;
	private JButton btnAddFunction;
	private ProMScrollContainer lstSelectedFunctions;

	public PerformanceFunctionsParametersStep(String title,
			final ContextAwareProcessPerformanceAnalysisParameters parameters, final XLog eventlog) {
		super(title);
		setTitle(title);

		// Panel that shows all available performance functions in a combobox and the button to add them to the selection.
		pnlAddFunctions = new JPanel();
		pnlAddFunctions.setLayout(new BoxLayout(pnlAddFunctions, BoxLayout.X_AXIS));

		//TODO check which functions are available and which ones are not (grey out / leave out) rather than showing all functions
		Comparator<PerformanceType> performanceTypeComparator = new Comparator<PerformanceType>() {
			public int compare(PerformanceType t1, PerformanceType t2) {
				return t1.getDescription().compareTo(t2.getDescription());
			}
		};
		PerformanceType[] allAvailablePerformanceFunctions = PerformanceType.values();
		Arrays.sort(allAvailablePerformanceFunctions, performanceTypeComparator);
		cmbAvailableFunctions = new ProMComboBox<PerformanceType>(allAvailablePerformanceFunctions);
		pnlAddFunctions.add(cmbAvailableFunctions);

		btnAddFunction = SlickerFactory.instance().createButton("+");
		btnAddFunction.setToolTipText("Add this performance function.");
		pnlAddFunctions.add(btnAddFunction);

		// Panel (ProMScrollContainer) that shows all selected performance functions and their settings. 
		lstSelectedFunctions = new ProMScrollContainer();

		btnAddFunction.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// Add the selected performance function to the list
				// Note that only a panel (PerformanceProMTitledScrollContainerChild) is added, that keeps in it as a field the performance.
				// These performance objects are retrieved and added to the parameters object in the apply method.
				PerformanceType selectedFunction = (PerformanceType) cmbAvailableFunctions.getSelectedItem();

				Performance<?> performance = PerformanceFactory.construct(selectedFunction);
				PerformanceParameters performanceParameters = performance.getParameters();
				PerformanceProMTitledScrollContainerChild performanceParametersPanel = performanceParameters
						.getGUIPanel(lstSelectedFunctions, performance, eventlog);

				lstSelectedFunctions.addChild(performanceParametersPanel);
			}

		});

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPreferredSize(new Dimension(this.getPreferredSize().width, 550));
		this.add(pnlAddFunctions);
		this.add(lstSelectedFunctions);
	}

	public boolean canApply(ContextAwareProcessPerformanceAnalysisParameters parameters, JComponent component) {
		return true;
	}

	public ContextAwareProcessPerformanceAnalysisParameters
			apply(ContextAwareProcessPerformanceAnalysisParameters parameters, JComponent component) {

		parameters.getPerformanceMeasuresToUse().clear();

		// Save all set parameters back to the parameters object.
		// Loop over the scroll container and add all children to the parameters object
		for (ProMScrollContainerChild child : lstSelectedFunctions.getChildren()) {
			PerformanceProMTitledScrollContainerChild performanceChild = (PerformanceProMTitledScrollContainerChild) child;
			parameters.getPerformanceMeasuresToUse().add(performanceChild.getPerformance());
		}

		return parameters;
	}

	public JComponent getComponent(ContextAwareProcessPerformanceAnalysisParameters parameters) {
		lstSelectedFunctions.clearChildren();

		//		// Show performance functions that are already set (usually these will be the default ones)
		//		for (Performance<?> performance : parameters.getPerformanceMeasuresToUse()) {
		//			ProMScrollContainerChild performanceParametersPanel = performance.getParameters()
		//					.getGUIPanel(lstSelectedFunctions, performance);
		//			lstSelectedFunctions.addChild(performanceParametersPanel);
		//		}

		return this;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
