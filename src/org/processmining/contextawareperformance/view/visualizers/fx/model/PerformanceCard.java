package org.processmining.contextawareperformance.view.visualizers.fx.model;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * PerformanceCard is a custom control that is it's own controller.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class PerformanceCard extends AnchorPane {

	@FXML
	private Label lblTitle;

	public PerformanceCard() {
		super();

		FXMLLoader loader = new FXMLLoader(getClass().getResource("../view/PerformanceCard.fxml"));
		loader.setRoot(this);
		loader.setController(this);

		try {
			loader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@FXML
	public void initialize() {
		setTitle("PERFORMANCE CARD DEFAULT TITLE");
	}

	public String getTitle() {
		return lblTitle.getText();
	}

	public void setTitle(String title) {
		lblTitle.setText(title);
	}
}
