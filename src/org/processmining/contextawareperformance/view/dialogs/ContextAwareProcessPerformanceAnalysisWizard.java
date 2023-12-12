package org.processmining.contextawareperformance.view.dialogs;

import org.deckfour.xes.model.XLog;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisParameters;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.framework.util.ui.wizard.ListWizard;
import org.processmining.framework.util.ui.wizard.ProMWizardDisplay;
import org.processmining.framework.util.ui.wizard.TextStep;

public class ContextAwareProcessPerformanceAnalysisWizard {

	public static ContextAwareProcessPerformanceAnalysisParameters show(UIPluginContext context, XLog eventlog) {
		ContextAwareProcessPerformanceAnalysisParameters parameters = new ContextAwareProcessPerformanceAnalysisParameters();

		@SuppressWarnings("unchecked")
		ListWizard<ContextAwareProcessPerformanceAnalysisParameters> wizard = new ListWizard<ContextAwareProcessPerformanceAnalysisParameters>(
				new TextStep<ContextAwareProcessPerformanceAnalysisParameters>(
						"Performance analysis: Information", "<html>"
								+ "<p>In the following screens you can set up the context-aware performance analysis algorithm. <br/>"
								+ "The context-aware performance analysis algorithm discovers statistically significant differences in performance between contexts. <br/>"
								+ "For example, different resources can lead to different durations for certain activities, or different case-types can lead to differences in the total case duration. <br/>"
								+ "In these scenarios, the activity and case duration are the performance metrics, and the involved resources and case-types are the contexts.</p> <br/>"

								+ "<p>On the following two screens, you can select which performance metrics and context functions to use. <br/>"
								+ "Some metrics and functions have additional parameters that have to be set up as well. <br/>"
								+ "Hovering over the different choices will show more information. <br/>"
								+ "Setting up the choices is vital to finding interesting insights.</p> <br/>"

								+ "<p>NOTICE: This tool is in 'open beta'.</p> <br/>"

								+ "<p>Should you have any questions, please post them on the ProM forum.</p> <br/>"

								+ "<p>Bart (B.F.A.) Hompes</p>"

								+ "</html>"),
				new PerformanceFunctionsParametersStep("Performance analysis: Performance functions", parameters,
						eventlog),
				new ContextFunctionsParametersStep("Performance analysis: Context functions", parameters, eventlog));
		/**
		 * The summary step is currently not shown since for some reason the
		 * parameters object isn't shown quite right there. This surely has to
		 * do with an issue in the ListWizard or related classes.
		 */
		//,
		//				new SettingsSummaryStep("Performance analysis: Summary", parameters));

		return ProMWizardDisplay.show(context, wizard, parameters);
	}

}
