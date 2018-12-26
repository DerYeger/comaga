package eu.yeger.comaga.controller;

import eu.yeger.comaga.model.Field;
import eu.yeger.comaga.model.Game;
import eu.yeger.comaga.model.Grid;
import eu.yeger.comaga.model.Model;

import eu.yeger.comaga.view.ViewBuilder;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class GameScreenController {

    @FXML
    private VBox vBox;

    @FXML
    private Label scoreLabel;

    public void initialize() {
        initGrid();
        addListeners();
    }

    private void initGrid() {
        Grid grid = Model.getInstance().getGame().getGrid();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        //adds field elements to the game's grid
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                Field field = grid.getFields().get(x + grid.getWidth() * y);
                StackPane stackPane = ViewBuilder.buildFieldView(field);
                gridPane.add(stackPane, field.getXPos(), grid.getHeight() - field.getYPos() - 1);
            }
        }

        vBox.getChildren().add(gridPane);
    }

    private void addListeners() {
        Model.getInstance().getGame().addPropertyChangeListener(Game.PROPERTY_score, evt -> updateScoreLabel());
    }

    private void updateScoreLabel() {
        scoreLabel.setText(Integer.toString(Model.getInstance().getGame().getScore()));
    }
}
