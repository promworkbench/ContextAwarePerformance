package org.processmining.contextawareperformance.view.visualizers.fx.controller;

import java.util.HashMap;

import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/**
 * Controller for the root layout FXML.
 * 
 * Content screen controlling based on the ScreensController class from the
 * tutorial on
 * {@link https://blogs.oracle.com/acaicedo/entry/managing_multiple_screens_in_javafx1}.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class RootLayoutController {

	//@formatter:off
	public enum Screen {
		OVERVIEW_SCREEN("Overview screen", "../view/OverviewScreen.fxml"), 
		RESULT_SCREEN("Detailed results screen", "../view/ResultScreen.fxml");

		private String name;
		private String resourceFile;

		Screen(String name, String resourceFile) {
			this.name = name;
			this.resourceFile = resourceFile;
		}
		
		protected String getName(){
			return name;
		}

		protected String getResourceFile() {
			return resourceFile;
		}
		
		@Override
		public String toString(){
			return name;
		}
	}
	//@formatter:on

	private HashMap<Screen, Node> screens;
	private OverviewScreenController overviewScreenController;
	private ResultScreenController resultScreenController;
	private ContextAwareProcessPerformanceAnalysisOutput cappao;

	@FXML
	private Label lblTitle;
	@FXML
	private Label lblLeftStatus;
	@FXML
	private Label lblRightStatus;
	@FXML
	private Button btnHome;
	@FXML
	private Button btnExportReport;
	@FXML
	private StackPane contentPane;

	public RootLayoutController(ContextAwareProcessPerformanceAnalysisOutput cappao) {
		screens = new HashMap<Screen, Node>();

		overviewScreenController = new OverviewScreenController(cappao);
		overviewScreenController.setRootLayoutController(this);
		resultScreenController = new ResultScreenController(cappao);
		resultScreenController.setRootLayoutController(this);

		setCappao(cappao);
	}

	@FXML
	private void initialize() {
		System.out.println("Initialize RootLayout");

		// Load all screens
		loadScreen(Screen.OVERVIEW_SCREEN, overviewScreenController);
		loadScreen(Screen.RESULT_SCREEN, resultScreenController);

		// Set the initial screen
		setActiveScreen(Screen.OVERVIEW_SCREEN);
	}

	public ContextAwareProcessPerformanceAnalysisOutput getCappao() {
		return cappao;
	}

	public void setCappao(ContextAwareProcessPerformanceAnalysisOutput cappao) {
		this.cappao = cappao;
	}

	private void addScreen(Screen screen, Node node) {
		screens.put(screen, node);
	}

	private boolean loadScreen(Screen screen, Object controller) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(screen.getResourceFile()));
			loader.setController(controller);
			Parent parent = (Parent) loader.load();
			addScreen(screen, parent);
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	protected boolean setActiveScreen(final Screen screen) {
		// Check if screen is loaded
		if (screens.get(screen) != null) {
			final DoubleProperty opacity = contentPane.opacityProperty();
			// Is there already is a screen active, fade it out first, then show (fade in) the new one
			if (!contentPane.getChildren().isEmpty()) {
				Timeline fade = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
						new KeyFrame(new Duration(100), new EventHandler<ActionEvent>() {
							@Override
							public void handle(ActionEvent arg0) {
								//remove displayed screen
								contentPane.getChildren().clear();
								//add new screen
								contentPane.getChildren().add(screens.get(screen));
								Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
										new KeyFrame(new Duration(100), new KeyValue(opacity, 1.0)));
								fadeIn.play();
							}
						}, new KeyValue(opacity, 0.0)));
				fade.play();
			} else {
				// No other screen active yet, so show (fade in) the new one
				contentPane.setOpacity(0.0);
				contentPane.getChildren().add(screens.get(screen));
				Timeline fadeIn = new Timeline(new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
						new KeyFrame(new Duration(100), new KeyValue(opacity, 1.0)));
				fadeIn.play();
			}
			System.out.println("Loaded \"" + screen + "\"");
			setLeftStatus("Loaded \"" + screen + "\"");
			return true;
		} else {
			System.out.println(
					"Screen \"" + screen + "\" couldn't been loaded! Load the screen before setting it as active!");
			setLeftStatus("Something went wrong loading screens.");
			return false;
		}
	}

	@SuppressWarnings("unused")
	private boolean unloadScreen(Screen screen) {
		if (screens.remove(screen) == null) {
			System.out.println("Screen \"" + screen + "\" didn't exist. So did nothing.");
			return false;
		} else {
			return true;
		}
	}

	@FXML
	private void exportReport() {
		System.out.println("Export report");
	}

	@FXML
	private void showPreferences() {
		System.out.println("Show preferences");
	}

	@FXML
	private void showAbout() {
		System.out.println("Show about");
	}

	@FXML
	private void goToHome() {
		System.out.println("Show home");
		setActiveScreen(Screen.OVERVIEW_SCREEN);
	}

	public void setTitle(String title) {
		lblTitle.setText(title);
	}

	private void setLeftStatus(String status) {
		lblLeftStatus.setText(status);
	}

	@SuppressWarnings("unused")
	private void setRightStatus(String status) {
		lblRightStatus.setText(status);
	}

	public OverviewScreenController getOverviewScreenController() {
		return overviewScreenController;
	}

	public void setOverviewScreenController(OverviewScreenController overviewScreenController) {
		this.overviewScreenController = overviewScreenController;
	}

	public ResultScreenController getResultScreenController() {
		return resultScreenController;
	}

	public void setResultScreenController(ResultScreenController resultScreenController) {
		this.resultScreenController = resultScreenController;
	}

}
