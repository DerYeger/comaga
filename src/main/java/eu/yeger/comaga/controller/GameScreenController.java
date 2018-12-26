package eu.yeger.comaga.controller;

import eu.yeger.comaga.model.Game;
import eu.yeger.comaga.model.Grid;
import eu.yeger.comaga.model.Model;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

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
                try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("field.fxml"));
                    StackPane stackPane = fxmlLoader.load();
                    FieldController fieldController = fxmlLoader.getController();
                    fieldController.setField(grid.getFields().get(x + grid.getWidth() * y));
                    gridPane.add(stackPane, x, y);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        vBox.getChildren().add(gridPane);
    }

    private void addListeners() {
        Game game = Model.getInstance().getGame();
        game.addPropertyChangeListener(Game.PROPERTY_score, evt -> updateScoreLabel());
    }

    private void updateScoreLabel() {
        scoreLabel.setText(Integer.toString(Model.getInstance().getGame().getScore()));
    }
}
