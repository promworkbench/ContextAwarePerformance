package org.processmining.contextawareperformance.view.visualizers.swing;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JScrollPane;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.jdesktop.swingx.JXCollapsiblePane;
import org.jdesktop.swingx.JXCollapsiblePane.Direction;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BoxAndWhiskerRenderer;
import org.jfree.chart.util.RelativeDateFormat;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.view.utils.swing.ScrollablePanel;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

import com.google.common.collect.Table;

@Plugin(
		name = ".Boxplots",
		returnLabels = { "Visualized performance characteristics using Box plots." },
		returnTypes = { JComponent.class },
		parameterLabels = { "Output" },
		userAccessible = true)
@Visualizer
public class BoxPlotVisualizer {

	@PluginVariant(
			requiredParameterLabels = { 0 })
	public JComponent visualize(UIPluginContext uiPluginContext, ContextAwareProcessPerformanceAnalysisOutput output) {
		long time = -System.currentTimeMillis();
		System.out.println("[Visualizer] Start.");

		/******************************************************************************************
		 * 
		 * Create necessary panels
		 * 
		 ******************************************************************************************/

		ScrollablePanel panel = new ScrollablePanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
		JScrollPane scroll = new JScrollPane();
		scroll.setViewportView(panel);

		/******************************************************************************************
		 * 
		 * Loop through all results
		 * 
		 ******************************************************************************************/

		if (!output.getResults().isEmpty()) {

			//			for (EventCollectionViewType viewType : output.getResults().keySet()) {

			EventCollectionViewType viewType = EventCollectionViewType.ACTIVITY;

			for (EventCollectionEntity entity : output.getResults().get(viewType).keySet()) {

				for (Performance<?> performance : output.getResults().get(viewType).get(entity).keySet()) {

					for (Context<?> context : output.getResults().get(viewType).get(entity).get(performance).keySet()) {

						/******************************************************************************************
						 * 
						 * Only show significant results.
						 * 
						 ******************************************************************************************/

						if (null != output.getSignificance().get(viewType).get(entity).get(performance).get(context)) {

							boolean significant = output.getSignificance().get(viewType).get(entity).get(performance)
									.get(context).isSignificant();

							if (significant && !output.getSignificance().get(viewType).get(entity).get(performance)
									.get(context).getPostHocResult().isEmpty()) {

								/******************************************************************************************
								 * 
								 * Create a boxplot
								 * 
								 ******************************************************************************************/

								final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();
								final CategoryAxis xAxis = new CategoryAxis(context.toString());
								//			xAxis.setTickMarksVisible(false);
								//			xAxis.setCategoryLabelPositions(CategoryLabelPositions.DOWN_45);
								xAxis.setTickLabelsVisible(false);

								final DateAxis yAxis = new DateAxis(performance.toString());
								RelativeDateFormat rdf = new RelativeDateFormat();
								rdf.setShowZeroDays(false);
								rdf.setSecondFormatter(new DecimalFormat("00"));
								yAxis.setDateFormatOverride(rdf);

								final BoxAndWhiskerRenderer renderer = new BoxAndWhiskerRenderer();
								renderer.setFillBox(true);
								renderer.setBaseToolTipGenerator(new BoxAndWhiskerToolTipGenerator());

								/******************************************************************************************
								 * 
								 * Add measurements to boxplot (separated by
								 * context result)
								 * 
								 ******************************************************************************************/

								for (ContextResult<?> result : output.getResults().get(viewType).get(entity)
										.get(performance).get(context).keySet()) {

									List<PerformanceMeasurement<?>> results = output.getResults().get(viewType)
											.get(entity).get(performance).get(context).get(result);

									final List<Long> list = new ArrayList<Long>();
									for (PerformanceMeasurement<?> performanceResult : results) {
										list.add(Long.parseLong(performanceResult.getResult().toString()));
									}
									dataset.add(list, result.toString(), result.toString());
								}

								panel.add(new JLabel("<html><h3>View: " + viewType.getDescription() + "<br/>Entity: "
										+ entity.toString() + "<br/>Performance: " + performance.toString()
										+ " <br/>Context: " + context.getType().getDescription() + "</h3></html>"));

								/******************************************************************************************
								 * 
								 * Show boxplot
								 * 
								 ******************************************************************************************/

								final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
								plot.setOrientation(PlotOrientation.VERTICAL);
								plot.setRangePannable(true);

								final JFreeChart chart = new JFreeChart(entity.toString(),
										new Font("SansSerif", Font.BOLD, 12), plot, true);
								//			chart.removeLegend();

								final ChartPanel chartPanel = new ChartPanel(chart);
								Dimension screenResolution = Toolkit.getDefaultToolkit().getScreenSize();
								chartPanel.setPreferredSize(new Dimension((int) (screenResolution.getWidth() * 0.9),
										(int) (screenResolution.getHeight() * 0.6)));
								

								chartPanel.setPreferredSize(new Dimension(2500/2,1250/2));								

								panel.add(chartPanel);

								/******************************************************************************************
								 * 
								 * Show post hoc results
								 * 
								 ******************************************************************************************/

								Table<ContextResult<?>, ContextResult<?>, Double> postHocResults = output
										.getSignificance().get(viewType).get(entity).get(performance).get(context)
										.getPostHocResult();

								StringBuilder builder = new StringBuilder();

								builder.append("<html><table><tr><th>From</th><th>To</th><th>Test statistic</th></tr>");

								for (ContextResult<?> a : postHocResults.rowMap().keySet()) {
									Map<ContextResult<?>, Double> rowsForA = postHocResults.rowMap().get(a);
									for (ContextResult<?> b : rowsForA.keySet()) {
										builder.append("<tr><td>" + a.toString() + "</td><td>" + b.toString()
												+ "</td><td>" + rowsForA.get(b) + "</td></tr>");
									}
								}

								builder.append("</table></html>");

								JXCollapsiblePane collapsiblePane = new JXCollapsiblePane(Direction.DOWN);
								collapsiblePane.add(new JLabel(builder.toString()));
								collapsiblePane.setCollapsed(true);
								collapsiblePane.setAnimated(true);
								collapsiblePane.setAlignmentX(JXCollapsiblePane.LEFT_ALIGNMENT);

								JButton togglePostHoc = new JButton(
										collapsiblePane.getActionMap().get(JXCollapsiblePane.TOGGLE_ACTION));
								togglePostHoc.setText("Toggle post hoc results");

								panel.add(togglePostHoc);
								panel.add(collapsiblePane);

							} // if posthoc significant

						} // if significant

					} // for context

				} // for performance

			} // for entity

			//			} // for viewType

		} // if results is empty

		time += System.currentTimeMillis();
		System.out.println("[Visualizer] End (took " + DurationFormatUtils.formatDurationHMS(time) + ").");
		return scroll;
	}
}
