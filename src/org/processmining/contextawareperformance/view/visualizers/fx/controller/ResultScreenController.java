package org.processmining.contextawareperformance.view.visualizers.fx.controller;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

import javax.swing.SwingUtilities;

import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contextawareperformance.models.ContextResult;
import org.processmining.contextawareperformance.models.PerformanceMeasurement;
import org.processmining.contextawareperformance.models.SignificanceResult;
import org.processmining.contextawareperformance.models.eventcollectionentities.EventCollectionEntity;
import org.processmining.contextawareperformance.models.eventcollectionviews.EventCollectionViewType;
import org.processmining.contextawareperformance.models.functions.context.Context;
import org.processmining.contextawareperformance.models.functions.performance.Performance;
import org.processmining.contextawareperformance.models.nlg.NaturalLanguageGenerator;
import org.processmining.contextawareperformance.view.visualizers.fx.model.ContextCheckBox;
import org.processmining.contextawareperformance.view.visualizers.fx.model.ResultCard;
import org.processmining.contextawareperformance.view.visualizers.fx.model.ResultCardCell;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

/**
 * Controller for the result screen FXML.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ResultScreenController implements IControlledScreen {

	@SuppressWarnings("unused")
	private RootLayoutController rootLayoutController;

	private ContextAwareProcessPerformanceAnalysisOutput cappao;
	private ObservableList<ResultCard> resultCards;
	private FilteredList<ResultCard> filteredResultCards;
	@SuppressWarnings("unused")
	private SortedList<ResultCard> sortedResultCards;

	// Used for filtering
	private Performance<?> shownPerformance;
	private Set<Context<?>> contextFunctions;

	private int maxNrOfContexts;

	@FXML
	private Slider sliderMinimumTime;

	@FXML
	private Slider sliderMinimumContexts;

	@FXML
	private ListView<ResultCard> listViewResults;

	@FXML
	private Label lblMinimumContexts;

	@FXML
	private VBox vBoxContextFunctions;

	@FXML
	private Label lblMinimumTime;

	@FXML
	private TextField txtSearch;

	public ResultScreenController(ContextAwareProcessPerformanceAnalysisOutput cappao) {
		this.cappao = cappao;
		resultCards = FXCollections.observableArrayList();
		contextFunctions = new HashSet<Context<?>>();

		createResultCards();

		filteredResultCards = new FilteredList<ResultCard>(resultCards);
	}

	@FXML
	private void initialize() {
		System.out.println("Initialize ResultScreen");

		// Listen for changes in the search text box
		txtSearch.textProperty().addListener(new ChangeListener<String>() {
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				filter();
			}
		});

		// Listen for slider value changes
		sliderMinimumTime.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				DecimalFormat df = new DecimalFormat("0.00");
				lblMinimumTime.setText(df.format(newValue));
			}
		});
		sliderMinimumContexts.setMin(2d);
		sliderMinimumContexts.setMax(maxNrOfContexts);
		sliderMinimumContexts.valueProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				lblMinimumContexts.setText(newValue.intValue() + "");
				filter();
			}
		});

		// Set the renderer for the result cards
		listViewResults.setCellFactory(new Callback<ListView<ResultCard>, ListCell<ResultCard>>() {
			@Override
			public ListCell<ResultCard> call(ListView<ResultCard> param) {
				return new ResultCardCell();
			}
		});
		listViewResults.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ResultCard>() {
			public void changed(ObservableValue<? extends ResultCard> ov, ResultCard previous,
					final ResultCard current) {
				if (current == null)
					return;
				System.out.println("Selected " + current.getTitle());

				SwingUtilities.invokeLater(new Runnable() {

					public void run() {

						//TODO [medium] show details of observation using charts, potentially in JavaFX thread
						//						Performance<?> performance = current.getPerformance();
						//						Context<?> context = current.getContext();
						//						Map<ContextResult<?>, List<PerformanceMeasurement<?>>> output = current.getResults();
						//						SignificanceResult significance = current.getSignificanceResult();
						//
						//						JFrame frame = new JFrame();
						//
						//						JPanel visPanel = new JPanel();
						//
						//						visPanel.add(new JLabel("hello"));
						//
						//						frame.add(visPanel);
						//
						//						frame.setTitle("Alert");
						//						frame.setSize(1280, 720);
						//						frame.setExtendedState(JFrame.NORMAL);
						//						frame.pack();
						//						frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
						//						frame.setVisible(true);

					}

				});

			}
		});
		listViewResults.setItems(filteredResultCards);

		createContextCheckboxes();

	}

	public void setRootLayoutController(RootLayoutController rootLayoutController) {
		this.rootLayoutController = rootLayoutController;
	}

	private void createResultCards() {
		if (!cappao.getResults().isEmpty()) {
			final EventCollectionViewType viewType = EventCollectionViewType.ACTIVITY;
			//			for (final EventCollectionViewType viewType : cappao.getResults().keySet()) {
			for (final EventCollectionEntity entity : cappao.getResults().get(viewType).keySet()) {
				for (final Performance<?> performance : cappao.getResults().get(viewType).get(entity).keySet()) {
					for (final Context<?> context : cappao.getResults().get(viewType).get(entity).get(performance)
							.keySet()) {
						if (null != cappao.getSignificance().get(viewType).get(entity).get(performance).get(context)) {
							if (cappao.getSignificance().get(viewType).get(entity).get(performance).get(context)
									.isSignificant()
									&& !cappao.getSignificance().get(viewType).get(entity).get(performance).get(context)
											.getPostHocResult().isEmpty()) {

								// Get the performance measurement results and the statistical significance of the differences.
								final Map<ContextResult<?>, List<PerformanceMeasurement<?>>> results = cappao
										.getResults().get(viewType).get(entity).get(performance).get(context);
								final SignificanceResult significanceResult = cappao.getSignificance().get(viewType)
										.get(entity).get(performance).get(context);

								// Create a resultcard
								String title = NaturalLanguageGenerator.generateStatement(viewType, entity, performance,
										context, results, significanceResult);
								ResultCard resultCard = new ResultCard(title, performance, context);
								resultCard.setResults(results);
								resultCard.setSignificanceResult(significanceResult);
								resultCards.add(resultCard);

								// Add the context to the list of filters (checkboxes)
								getContextFunctions().add(context);

								// Set maximum value of sliders
								if (results.keySet().size() > maxNrOfContexts)
									maxNrOfContexts = results.keySet().size();
							}
						}
					}
				}
			}
			//			}
		}
	}

	@FXML
	protected void resetFilters() {
		txtSearch.setText("");
		sliderMinimumTime.setValue(0d);
		sliderMinimumContexts.setValue(2d);
		for (Node child : vBoxContextFunctions.getChildren()) {
			ContextCheckBox cb = (ContextCheckBox) child;
			cb.setSelected(true);
		}
		filter();
	}

	@FXML
	protected void filter() {
		filteredResultCards.setPredicate(new Predicate<ResultCard>() {
			public boolean test(ResultCard resultCard) {
				// Only show the correct performance
				if (!resultCard.getPerformance().equals(shownPerformance))
					return false;

				// Only show the selected contexts (checkboxes)
				Set<Context<?>> selectedContexts = new HashSet<Context<?>>();
				for (Node child : vBoxContextFunctions.getChildren()) {
					ContextCheckBox cb = (ContextCheckBox) child;
					if (cb.isSelected())
						selectedContexts.add(cb.getContext());
				}
				if (!selectedContexts.contains(resultCard.getContext()))
					return false;

				if (resultCard.getResults().keySet().size() < sliderMinimumContexts.getValue())
					return false;

				String query = txtSearch.getText();
				if (!resultCard.getTitle().toLowerCase().trim().contains(query.toLowerCase().trim()))
					return false;

				return true;
			}
		});
	}

	private void createContextCheckboxes() {
		vBoxContextFunctions.getChildren().clear();

		for (final Context<?> context : getContextFunctions()) {
			ContextCheckBox cbContext = new ContextCheckBox(context);
			cbContext.setSelected(true);
			cbContext.selectedProperty().addListener(new ChangeListener<Boolean>() {
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue,
						final Boolean newValue) {
					// Trigger a filter of the results based on the selected context functions
					filter();
				}
			});
			vBoxContextFunctions.getChildren().add(cbContext);
		}
	}

	public Performance<?> getShownPerformance() {
		return shownPerformance;
	}

	public void setShownPerformance(Performance<?> shownPerformance) {
		this.shownPerformance = shownPerformance;
		filter();
	}

	public Set<Context<?>> getContextFunctions() {
		return contextFunctions;
	}

	public void setContextFunctions(Set<Context<?>> contextFunctions) {
		this.contextFunctions = contextFunctions;
	}

}
