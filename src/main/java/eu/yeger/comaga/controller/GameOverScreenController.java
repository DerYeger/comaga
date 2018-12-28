package eu.yeger.comaga.controller;

import eu.yeger.comaga.model.Model;
import eu.yeger.comaga.view.ViewBuilder;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GameOverScreenController {

    @FXML
    Label scoreLabel;

    @FXML
    Button newGameButton;

    @FXML
    Button exitButton;

    private Stage stage;

    public void initialize(Stage stage) {
        this.stage = stage;
        scoreLabel.setText(Integer.toString(Model.getInstance().getGame().getScore()));
        addHandlers();
    }

    private void addHandlers() {
        newGameButton.setOnAction(event -> stage.setScene(ViewBuilder.buildIntroScreenScene(stage)));
        exitButton.setOnAction(event -> stage.close());
    }
}
