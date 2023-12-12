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
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.context.ContextFactory;
import org.processmining.contextawareperformance.models.functions.context.ContextType;
import org.processmining.contextawareperformance.models.parameters.context.ContextParameters;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisParameters;
import org.processmining.framework.util.ui.widgets.ProMComboBox;
import org.processmining.framework.util.ui.widgets.ProMHeaderPanel;
import org.processmining.framework.util.ui.widgets.ProMScrollContainer;
import org.processmining.framework.util.ui.widgets.ProMScrollContainerChild;
import org.processmining.framework.util.ui.wizard.ProMWizardStep;

import com.fluxicon.slickerbox.factory.SlickerFactory;

public class ContextFunctionsParametersStep extends ProMHeaderPanel
		implements ProMWizardStep<ContextAwareProcessPerformanceAnalysisParameters> {

	private static final long serialVersionUID = 1054786127714893932L;

	private String title;

	private JPanel pnlAddFunctions;
	private ProMComboBox<ContextType> cmbAvailableFunctions;
	private JButton btnAddFunction;
	private ProMScrollContainer lstSelectedFunctions;

	public ContextFunctionsParametersStep(String title,
			final ContextAwareProcessPerformanceAnalysisParameters parameters, final XLog eventlog) {
		super(title);
		setTitle(title);

		// Panel that shows all available context functions in a combobox and the button to add them to the selection.
		pnlAddFunctions = new JPanel();
		pnlAddFunctions.setLayout(new BoxLayout(pnlAddFunctions, BoxLayout.X_AXIS));

		Comparator<ContextType> contextTypeComparator = new Comparator<ContextType>() {
			public int compare(ContextType t1, ContextType t2) {
				return t1.getDescription().compareTo(t2.getDescription());
			}
		};
		ContextType[] allAvailableContextFunctions = ContextType.values();
		Arrays.sort(allAvailableContextFunctions, contextTypeComparator);
		cmbAvailableFunctions = new ProMComboBox<ContextType>(allAvailableContextFunctions);
		pnlAddFunctions.add(cmbAvailableFunctions);

		btnAddFunction = SlickerFactory.instance().createButton("+");
		btnAddFunction.setToolTipText("Add this context function.");
		pnlAddFunctions.add(btnAddFunction);

		// Panel (ProMScrollContainer) that shows all selected context functions and their settings. 
		lstSelectedFunctions = new ProMScrollContainer();

		btnAddFunction.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// Add the selected context function to the list
				// Note that only a panel (ContextProMTitledScrollContainerChild) is added, that keeps in it as a field the context.
				// These contexts objects are retrieved and added to the parameters object in the apply method.
				ContextType selectedFunction = (ContextType) cmbAvailableFunctions.getSelectedItem();

				Context<?> context = ContextFactory.construct(selectedFunction);
				ContextParameters contextParameters = context.getParameters();
				ContextProMTitledScrollContainerChild contextParametersPanel = contextParameters
						.getGUIPanel(lstSelectedFunctions, context, eventlog);

				lstSelectedFunctions.addChild(contextParametersPanel);
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

		parameters.getContextsToUse().clear();

		// Save all set parameters back to the parameters object.
		// Loop over the scroll container and add all children to the parameters object
		for (ProMScrollContainerChild child : lstSelectedFunctions.getChildren()) {
			ContextProMTitledScrollContainerChild contextChild = (ContextProMTitledScrollContainerChild) child;
			parameters.getContextsToUse().add(contextChild.getContext());
		}

		return parameters;
	}

	public JComponent getComponent(ContextAwareProcessPerformanceAnalysisParameters parameters) {
		lstSelectedFunctions.clearChildren();

		//		// Show Context functions that are already set (usually these will be the default ones)
		//		for (Context<?> context : parameters.getContextsToUse()) {
		//			ProMScrollContainerChild contextParametersPanel = context.getParameters().getGUIPanel(lstSelectedFunctions,
		//					context);
		//			lstSelectedFunctions.addChild(contextParametersPanel);
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
