package org.processmining.contextawareperformance.view.dialogs;

import java.awt.BorderLayout;

import javax.swing.JComponent;
import javax.swing.JLabel;

import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.parameters.contextawareprocessperformanceanalysis.ContextAwareProcessPerformanceAnalysisParameters;
import org.processmining.framework.util.ui.widgets.ProMHeaderPanel;
import org.processmining.framework.util.ui.wizard.ProMWizardStep;

public class SettingsSummaryStep extends ProMHeaderPanel
		implements ProMWizardStep<ContextAwareProcessPerformanceAnalysisParameters> {

	private static final long serialVersionUID = 1054786127714893932L;

	private String title;

	public SettingsSummaryStep(String title, ContextAwareProcessPerformanceAnalysisParameters parameters) {
		super(title);
		setTitle(title);
	}

	public boolean canApply(ContextAwareProcessPerformanceAnalysisParameters parameters, JComponent component) {
		return true;
	}

	public ContextAwareProcessPerformanceAnalysisParameters
			apply(ContextAwareProcessPerformanceAnalysisParameters parameters, JComponent component) {
		return parameters;
	}

	public JComponent getComponent(ContextAwareProcessPerformanceAnalysisParameters parameters) {
		this.removeAll();
		this.setLayout(new BorderLayout());

		StringBuilder summary = new StringBuilder();

		summary.append("<html>");
		summary.append("<h3>Performance functions:</h3><ul>");
		for (Performance<?> performance : parameters.getPerformanceMeasuresToUse()) {
			summary.append("<li>" + performance.toString() + "</li>");
		}
		summary.append("<h3>Context functions:</h3><ul>");
		for (Context<?> context : parameters.getContextsToUse()) {
			summary.append("<li>" + context.toString() + "</li>");
		}
		summary.append("</ul></html>");

		JLabel lblSummary = new JLabel(summary.toString());

		this.add(lblSummary, BorderLayout.CENTER);

		return this;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
