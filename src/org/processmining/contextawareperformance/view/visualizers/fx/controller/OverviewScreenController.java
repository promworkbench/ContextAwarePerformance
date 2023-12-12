package org.processmining.contextawareperformance.view.visualizers.fx.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.view.utils.Java;
import org.processmining.contextawareperformance.view.visualizers.fx.controller.RootLayoutController.Screen;
import org.processmining.contextawareperformance.view.visualizers.fx.model.PerformanceCard;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.Tile.TextSize;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Controller for the overview screen FXML.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class OverviewScreenController implements IControlledScreen {

	private RootLayoutController rootLayoutController;

	private ContextAwareProcessPerformanceAnalysisOutput cappao;

	@FXML
	private Label lblTitle;
	@FXML
	private Label lblDescription;
	@FXML
	private Label lblNrSignificantResults;
	@FXML
	private Label lblNrSignificantPerformanceFunctions;
	@FXML
	private Label lblNrSignificantContextFunctions;
	@FXML
	private Label lblUnsignificantPerformanceFunctions;
	@FXML
	private Label lblUnsignificantContextFunctions;
	@FXML
	private FlowPane contentPane;

	public OverviewScreenController(ContextAwareProcessPerformanceAnalysisOutput cappao) {
		this.cappao = cappao;
	}

	@FXML
	public void initialize() {
		System.out.println("Initialize OverviewScreen");
		setValues();
		createPerformanceCards();
	}

	public void setRootLayoutController(RootLayoutController rootLayoutController) {
		this.rootLayoutController = rootLayoutController;
	}

	public void setTitle(String title) {
		lblTitle.setText(title);
	}

	public void setDescription(String description) {
		lblDescription.setText(description);
	}

	public void setValues() {
		Set<Performance<?>> significantPerformanceFunctions = new HashSet<Performance<?>>();
		Set<Performance<?>> unsignificantPerformanceFunctions = new HashSet<Performance<?>>();
		Set<Context<?>> significantContextFunctions = new HashSet<Context<?>>();
		Set<Context<?>> unsignificantContextFunctions = new HashSet<Context<?>>();
		int nrSignificantResults = 0;

		if (!cappao.getResults().isEmpty()) {

			final EventCollectionViewType viewType = EventCollectionViewType.ACTIVITY;

			for (final EventCollectionEntity entity : cappao.getResults().get(viewType).keySet()) {

				for (final Performance<?> performance : cappao.getResults().get(viewType).get(entity).keySet()) {

					if (!significantPerformanceFunctions.contains(performance))
						unsignificantPerformanceFunctions.add(performance);

					for (final Context<?> context : cappao.getResults().get(viewType).get(entity).get(performance)
							.keySet()) {

						if (!significantContextFunctions.contains(context))
							unsignificantContextFunctions.remove(context);

						if (null != cappao.getSignificance().get(viewType).get(entity).get(performance).get(context)) {
							boolean significant = cappao.getSignificance().get(viewType).get(entity).get(performance)
									.get(context).isSignificant();
							if (significant && !cappao.getSignificance().get(viewType).get(entity).get(performance)
									.get(context).getPostHocResult().isEmpty()) {

								significantPerformanceFunctions.add(performance);
								unsignificantPerformanceFunctions.remove(performance);
								significantContextFunctions.add(context);
								unsignificantContextFunctions.remove(context);
								nrSignificantResults++;

							}
						}

					}

				}

			}

		}

		lblNrSignificantResults.setText("significant results: " + nrSignificantResults);
		lblNrSignificantPerformanceFunctions
				.setText("performance functions: " + significantPerformanceFunctions.size());
		lblNrSignificantContextFunctions.setText("context functions: " + significantContextFunctions.size());

		if (unsignificantPerformanceFunctions.size() > 0) {
			lblUnsignificantPerformanceFunctions
					.setText("No significant results found for: " + unsignificantPerformanceFunctions.toString());
		} else {
			lblUnsignificantPerformanceFunctions.setVisible(false);
		}
		if (unsignificantContextFunctions.size() > 0) {
			lblUnsignificantContextFunctions
					.setText("No significant results found for: " + unsignificantContextFunctions.toString());
		} else {
			lblUnsignificantContextFunctions.setVisible(false);
		}
	}

	private void createPerformanceCards() {
		HashMap<Performance<?>, Integer> significantPerformances = new HashMap<Performance<?>, Integer>();

		final EventCollectionViewType viewType = EventCollectionViewType.ACTIVITY;

		for (final EventCollectionEntity entity : cappao.getResults().get(viewType).keySet()) {

			for (final Performance<?> performance : cappao.getResults().get(viewType).get(entity).keySet()) {

				for (final Context<?> context : cappao.getResults().get(viewType).get(entity).get(performance)
						.keySet()) {

					if (null != cappao.getSignificance().get(viewType).get(entity).get(performance).get(context)) {
						boolean significant = cappao.getSignificance().get(viewType).get(entity).get(performance)
								.get(context).isSignificant();

						if (significant && !cappao.getSignificance().get(viewType).get(entity).get(performance)
								.get(context).getPostHocResult().isEmpty()) {

							if (!significantPerformances.containsKey(performance))
								significantPerformances.put(performance, 0);

							significantPerformances.put(performance, significantPerformances.get(performance) + 1);

						}

					}

				}

			}

		}

		for (final Performance<?> performance : significantPerformances.keySet()) {
			// Java 8 and onwards can use TilesFX, Java 7 and earlier has to fall back on performanceCards. 
			if (Java.JAVA_VERSION < 1.8) {
				// Show not so fancy cards
				// Create one card per performance
				PerformanceCard performanceCard = new PerformanceCard();
				String title = performance.toString().trim() + " lead to " + significantPerformances.get(performance)
						+ " observations.";
				performanceCard.setTitle(title);

				performanceCard.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						// Some debugging information
						System.out.println("Clicked " + performance.toString());

						// Tell the root controller to show the results screen
						if (rootLayoutController.setActiveScreen(Screen.RESULT_SCREEN)) {
							// Result screen loaded successfully, so tell the results screen to filter on only the selected performance function
							rootLayoutController.getResultScreenController().setShownPerformance(performance);
						}

						// Prevent the event from propagating up the scene graph (see http://stackoverflow.com/questions/34887546/javafx-check-if-the-mouse-is-on-nodes-children)
						event.consume();
					}
				});

				contentPane.getChildren().add(performanceCard);
			} else {
				// Show fancy tiles
				//@formatter:off
				Tile numberTile = TileBuilder.create()
						.skinType(SkinType.NUMBER)
						.prefSize(200, 200)
						.title(performance.toString())
						.textSize(TextSize.BIGGER)
						.text("Click to show details")
						.value(significantPerformances.get(performance))
						.decimals(0)
						.unit("")
						.description("observations")
						.textVisible(true)
						.titleColor(Color.WHITE)
						.valueColor(Color.WHITE)
						.unitColor(Color.WHITE)
						.descriptionColor(Color.WHITE)
						.backgroundColor(Color.LIGHTSLATEGREY)
						.build();
				//@formatter:on

				numberTile.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
					@Override
					public void handle(MouseEvent event) {
						// Some debugging information
						System.out.println("Clicked " + performance.toString());

						// Tell the root controller to show the results screen
						if (rootLayoutController.setActiveScreen(Screen.RESULT_SCREEN)) {
							// Result screen loaded successfully, so tell the results screen to filter on only the selected performance function
							rootLayoutController.getResultScreenController().setShownPerformance(performance);
						}

						// Prevent the event from propagating up the scene graph (see http://stackoverflow.com/questions/34887546/javafx-check-if-the-mouse-is-on-nodes-children)
						event.consume();
					}
				});
				numberTile.getStyleClass().add("performanceCard");
				contentPane.getChildren().add(numberTile);
			}
		}

		if (significantPerformances.keySet().size() == 0) {
			Label label = new Label();
			label.setText(
					"There were no significant differences found for the chosen performance and context functions.");
			label.setFont(new Font(16));
			contentPane.getChildren().add(label);
		}

	}
}
