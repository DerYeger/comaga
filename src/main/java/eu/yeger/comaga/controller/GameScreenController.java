package eu.yeger.comaga.controller;

import eu.yeger.comaga.model.Game;
import eu.yeger.comaga.model.Model;
import eu.yeger.comaga.view.ViewBuilder;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class GameScreenController {

    @FXML
    private VBox vBox;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label turnLabel;

    public void initialize() {
        initGridPane();
        addListeners();
        updateTurnLabel();
    }

    private void initGridPane() {
        Game game = Model.getInstance().getGame();
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);

        //adds field elements to the game's grid view
        game.getFields().stream().forEach(field -> {
            gridPane.add(ViewBuilder.buildFieldViewStackPane(field), field.getXPos(), game.getHeight() - field.getYPos() - 1);
        });

        vBox.getChildren().add(gridPane);
    }

    private void addListeners() {
        Model.getInstance().getGame().addPropertyChangeListener(Game.PROPERTY_score, evt -> updateScoreLabel());
        Model.getInstance().getGame().addPropertyChangeListener(Game.PROPERTY_turnCount, evt -> updateTurnLabel());
    }

    private void updateScoreLabel() {
        scoreLabel.setText(Integer.toString(Model.getInstance().getGame().getScore()));
    }

    private void updateTurnLabel() {
        turnLabel.setText(Integer.toString(ControllerUtilities.TURN_DURATION - (Model.getInstance().getGame().getTurnCount() + 1) % ControllerUtilities.TURN_DURATION));
    }
}
