package test;

import java.awt.Color;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.apache.commons.math3.random.RandomDataGenerator;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.impl.factory.Sets;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.labels.BoxAndWhiskerToolTipGenerator;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.util.RelativeDateFormat;
import org.jfree.data.statistics.DefaultBoxAndWhiskerCategoryDataset;
import org.processmining.contextawareperformance.view.visualizers.swing.CustomBoxAndWhiskerRenderer;

public class TestSwingBoxPlot {

	public static void main(String[] args) {

		testFitness();
		testDuration();

	}

	private static void testDuration() {
		ImmutableSet<String> prefixes = Sets.immutable.of("ABC", "ACB", "AB");

		final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

		for (String prefix : prefixes) {
			dataset.add(createSampleDuration(prefix), prefix, prefix);
		}

		final CategoryAxis xAxis = new CategoryAxis("Previous activities");
		xAxis.setTickLabelsVisible(false);
		xAxis.setCategoryMargin(0.00001d);
		xAxis.setLabelFont(xAxis.getLabelFont().deriveFont(14f));

		final DateAxis yAxis = new DateAxis("Activity duration");
		RelativeDateFormat rdf = new RelativeDateFormat();
		rdf.setShowZeroDays(false);
		rdf.setMinuteFormatter(new DecimalFormat("00"));
		yAxis.setDateFormatOverride(rdf);
		yAxis.setLabelFont(yAxis.getLabelFont().deriveFont(14f));

		final CustomBoxAndWhiskerRenderer renderer = new CustomBoxAndWhiskerRenderer();
		renderer.setFillBox(true);
		renderer.setBaseToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
		renderer.setSeriesPaint(0, new Color(85, 85, 255));
		renderer.setSeriesPaint(1, new Color(85, 255, 85));
		renderer.setSeriesPaint(2, new Color(255, 85, 255));
		renderer.setSeriesPaint(3, new Color(255, 175, 175));
		renderer.setSeriesPaint(4, new Color(192, 0, 0));
		renderer.setSeriesPaint(5, new Color(85, 255, 255));
		renderer.setMeanVisible(false);
		renderer.setUseOutlinePaintForWhiskers(false);

		final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setRangePannable(true);
		plot.setRangeGridlinePaint(Color.white);

		final JFreeChart chart = new JFreeChart("Activity D", new Font("SansSerif", Font.BOLD, 18), plot, true);

		final ChartPanel chartPanel = new ChartPanel(chart);
		chart.setBackgroundPaint(Color.white);
		chartPanel.setBackground(Color.white);

		// Show graph
		JFrame frame = new JFrame();
		frame.add(chartPanel);
		frame.setTitle("Root cause analysis");
		frame.setSize(1280, 720);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private static void testFitness() {
		ImmutableSet<String> resourceNames = Sets.immutable.of("John", "Bob", "Mary", "Sue", "Brian", "Tom");

		final DefaultBoxAndWhiskerCategoryDataset dataset = new DefaultBoxAndWhiskerCategoryDataset();

		for (String resourceName : resourceNames) {
			dataset.add(createSampleFitness(resourceName), resourceName, resourceName);
		}

		final CategoryAxis xAxis = new CategoryAxis("Involved resource");
		xAxis.setTickLabelsVisible(false);
		xAxis.setCategoryMargin(0.00001d);
		xAxis.setLabelFont(xAxis.getLabelFont().deriveFont(14f));

		final NumberAxis yAxis = new NumberAxis("Case fitness");
		yAxis.setLowerBound(0d);
		yAxis.setUpperBound(1d);
		yAxis.setTickUnit(new NumberTickUnit(0.1d));
		yAxis.setLabelFont(yAxis.getLabelFont().deriveFont(14f));

		final CustomBoxAndWhiskerRenderer renderer = new CustomBoxAndWhiskerRenderer();
		renderer.setFillBox(true);
		renderer.setBaseToolTipGenerator(new BoxAndWhiskerToolTipGenerator());
		renderer.setSeriesPaint(0, new Color(85, 85, 255));
		renderer.setSeriesPaint(1, new Color(85, 255, 85));
		renderer.setSeriesPaint(2, new Color(255, 85, 255));
		renderer.setSeriesPaint(3, new Color(255, 175, 175));
		renderer.setSeriesPaint(4, new Color(192, 0, 0));
		renderer.setSeriesPaint(5, new Color(85, 255, 255));
		renderer.setMeanVisible(false);
		renderer.setUseOutlinePaintForWhiskers(false);

		final CategoryPlot plot = new CategoryPlot(dataset, xAxis, yAxis, renderer);
		plot.setOrientation(PlotOrientation.VERTICAL);
		plot.setRangePannable(true);
		plot.setRangeGridlinePaint(Color.white);

		final JFreeChart chart = new JFreeChart("Activity A", new Font("SansSerif", Font.BOLD, 18), plot, true);
		//			chart.removeLegend();

		final ChartPanel chartPanel = new ChartPanel(chart);

		chart.setBackgroundPaint(Color.white);
		chartPanel.setBackground(Color.white);

		// Show graph
		JFrame frame = new JFrame();
		frame.add(chartPanel);
		frame.setTitle("Root cause analysis");
		frame.setSize(1280, 720);
		frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		frame.pack();
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private static ArrayList<Double> createSampleDuration(String prefix) {
		// Settings
		int minSampleSize = 200;
		int maxSampleSize = 1000;

		// Variables
		RandomDataGenerator r = new RandomDataGenerator();
		ArrayList<Double> sample = new ArrayList<Double>();

		// Logic
		switch (prefix) {
			case "AB" :
				sample = createSample(r.nextInt(minSampleSize, maxSampleSize), 1 * 60 * 1000, Integer.MAX_VALUE,
						14 * 60 * 1000, 3 * 60 * 1000, 1);
				break;
			default :
				sample = createSample(r.nextInt(minSampleSize, maxSampleSize), 1, Integer.MAX_VALUE,
						(int) r.nextUniform(6, 8) * 60 * 1000, (int) r.nextUniform(2, 4) * 60 * 1000, 1);
				;
		}

		return sample;
	}

	private static ArrayList<Double> createSampleFitness(String resourceName) {
		// Settings
		int minSampleSize = 200;
		int maxSampleSize = 1000;

		// Variables
		RandomDataGenerator r = new RandomDataGenerator();
		ArrayList<Double> sample = new ArrayList<Double>();

		// Logic
		switch (resourceName) {
			case "Bob" :
				sample = createSample(r.nextInt(minSampleSize, maxSampleSize), 0, 1, 40, 11, 100);
				break;
			default :
				sample = createSample(r.nextInt(minSampleSize, maxSampleSize), 0, 1, (int) r.nextUniform(65, 80),
						(int) r.nextUniform(3, 6), 100);
				;
		}

		return sample;
	}

	private static ArrayList<Double> createSample(int size, int min, int max, int mu, int sigma, int divisor) {
		ArrayList<Double> sample = new ArrayList<Double>();
		RandomDataGenerator r = new RandomDataGenerator();
		for (int i = 0; i < size; i++) {
			double value = r.nextGaussian(mu, sigma);
			sample.add(Math.min(max, Math.max(min, value / divisor)));
		}
		return sample;
	}

}
