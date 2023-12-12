package org.processmining.contextawareperformance.view.visualizers.fx.model;

import java.io.IOException;

import org.processmining.contextawareperformance.view.visualizers.fx.controller.ResultCardController;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.ListCell;

/**
 * Display a result card as a cell in a listview using the custom FXML.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ResultCardCell extends ListCell<ResultCard> {

	private final Parent graphic;
	private final ResultCardController controller;

	public ResultCardCell() {
		FXMLLoader loader = new FXMLLoader(
				this.getClass().getResource("../view/ResultCard.fxml"));
		controller = new ResultCardController();
		loader.setController(controller);

		try {
			graphic = (Parent) loader.load();
		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
	}

	@Override
	protected void updateItem(ResultCard resultCard, boolean isEmpty) {
		if (isEmpty || resultCard == null) {
			setGraphic(null);
		} else {
			super.updateItem(resultCard, isEmpty);
			controller.setTitle(resultCard.getTitle());
			controller.setIcons(IconFactory.createIcon(resultCard.getPerformance()),
					IconFactory.createIcon(resultCard.getContext()));
			setGraphic(graphic);
		}

	}

}