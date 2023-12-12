package org.processmining.contextawareperformance.view.visualizers.fx.controller;

import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.Tile.SkinType;
import eu.hansolo.tilesfx.Tile.TextSize;
import eu.hansolo.tilesfx.TileBuilder;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Controller for the result card FXML.
 * 
 * @author B.F.A. Hompes <b.f.a.hompes@tue.nl>
 *
 */
public class ResultCardController {

	@FXML
	private Label lblTitle;
	@FXML
	private TilePane tilePaneIcons;
	@FXML
	private VBox vBoxResults;

	@FXML
	public void initialize() {
		//		tilePaneIcons.getStyleClass().add("icons");
	}

	public void setTitle(String title) {
		this.lblTitle.setText(title);
	}

	public void setIcons(Node... icons) {
		tilePaneIcons.getChildren().clear();
		for (Node icon : icons) {
			tilePaneIcons.getChildren().add(icon);
		}
	}

	public void setDetails(DescriptiveStatistics... groups) {
		int counter = 0;
		for (DescriptiveStatistics group : groups) {
			counter++;

			VBox vBoxGroup = new VBox();
			vBoxGroup.setPadding(new Insets(5, 10, 5, 10));

			Label label = new Label("Group " + counter);
			vBoxGroup.getChildren().add(label);

			HBox hBoxGroup = new HBox();

			//@formatter:off
			Tile observations = TileBuilder.create()
					.skinType(SkinType.NUMBER)
					.prefSize(100, 100)
					.value(group.getN())
					.decimals(0)
					.description("Observations")
					.textSize(TextSize.BIGGER)
					.textVisible(true)
					.backgroundColor(Color.LIGHTGRAY)
					.build();
			//@formatter:on
			hBoxGroup.getChildren().add(observations);

			System.out.println(group.getMean());

			//@formatter:off
			Tile time = TileBuilder.create()
                    .prefSize(100, 100)
                    .skinType(SkinType.TIME)
                    .duration(LocalTime.of(
                    		(int)TimeUnit.NANOSECONDS.toHours((long)group.getMean()),
                    		(int)TimeUnit.NANOSECONDS.toMinutes((long)group.getMean()),
                    		(int)TimeUnit.NANOSECONDS.toSeconds((long)group.getMean())))
                    .description("Average")
                    .textVisible(true)
					.backgroundColor(Color.LIGHTGRAY)
                	.build();
			//@formatter:on
			hBoxGroup.getChildren().add(time);

			vBoxGroup.getChildren().add(hBoxGroup);

			//			vBoxResults.getChildren().add(vBoxGroup);
		}
	}

}
