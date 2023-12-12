package org.processmining.contextawareperformance.view.visualizers.fx;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JPanel;

import org.processmining.contextawareperformance.models.ContextAwareProcessPerformanceAnalysisOutput;
import org.processmining.contextawareperformance.view.visualizers.fx.controller.RootLayoutController;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.Visualizer;
import org.processmining.framework.plugin.annotations.Plugin;
import org.processmining.framework.plugin.annotations.PluginVariant;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import jiconfont.icons.FontAwesome;
import jiconfont.javafx.IconFontFX;

/**
 * This visualizer uses JavaFX to show the results of the context-aware
 * performance analysis.
 * 
 * A single JavaFX scene is loaded in a JFXPanel in the Swing component that is
 * the visualizer. In this single scene multiple layouts are interactively shown
 * (all in the FX thread).
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
@Plugin(
		name = "@0 Visualize performance analysis results",
		returnLabels = { "Visualized performance characteristics using JavaFX." },
		returnTypes = { JComponent.class },
		parameterLabels = { "Output" },
		userAccessible = true)
@Visualizer
public class FXVisualizer {

	FXMLLoader loader;
	RootLayoutController rootLayoutController;

	@PluginVariant(
			requiredParameterLabels = { 0 })
	public JComponent visualize(UIPluginContext pluginContext,
			final ContextAwareProcessPerformanceAnalysisOutput cappao) {
		// Base Swing panel of the visualizer. 
		JPanel panel = new JPanel(new BorderLayout());

		// Swing wrapper panel for JavaFX GUI.
		final JFXPanel fxPanel = new JFXPanel();
		fxPanel.setAlignmentX(0);
		fxPanel.setAlignmentY(0);
		fxPanel.setAutoscrolls(true);
		panel.add(fxPanel, BorderLayout.CENTER);
		panel.setVisible(true);

		// Make sure that the JavaFX content isn't implicitly removed (occurs when changing visualizers or after long periods of inactivity).
		Platform.setImplicitExit(false);

		// Add the root layout to the screen.
		Platform.runLater(new Runnable() {
			// This is the thread for JavaFX (not the Swing Event Dispatch Thread (EDT)).
			@Override
			public void run() {
				// This method is invoked on the JavaFX thread and thus can create the scene.
				fxPanel.setScene(createScene(cappao));
			}
		});

		return panel;

	}

	//This method is invoked on the JavaFX thread and thus can create the scene, will return null when loading of FXML fails.
	//@Nullable
	private Scene createScene(ContextAwareProcessPerformanceAnalysisOutput cappao) {
		RootLayoutController rootLayoutController = new RootLayoutController(cappao);

		// The root layout is saved in the RootLayout.FXML file, so load it.
		loader = new FXMLLoader(this.getClass().getResource("view/RootLayout.fxml"));
		// Set the controller
		loader.setController(rootLayoutController);
		// FYI, alternatively, we could also get the controller after calling loader.load().
		// rootLayoutController = loader.getController();

		Scene scene = null;

		try {
			// Let's try to load our main GUI
			Parent rootLayout = (Parent) loader.load();

			// Can only set the title after the FXML is loaded (because otherwise the title label isn't injected yet (thus will be null).
			rootLayoutController.setTitle(("process performance analysis").toUpperCase());

			// The scene is the actual window that shows our content
			scene = new Scene(rootLayout);

			// Add some custom styling
			scene.getStylesheets().add(this.getClass().getResource("view/css/listViewResults.css").toExternalForm());
			scene.getStylesheets().add(this.getClass().getResource("view/css/overViewScreen.css").toExternalForm());

			IconFontFX.register(FontAwesome.getIconFont());

		} catch (IOException e) {
			System.out.println("Couldn't load root layout.");
			e.printStackTrace();
		}

		return scene;
	}

}
