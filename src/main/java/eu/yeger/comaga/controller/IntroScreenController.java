package eu.yeger.comaga.controller;

import eu.yeger.comaga.view.ViewBuilder;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class IntroScreenController {

    private static final int DEFAULT_WIDTH = 7;
    private static final int MINIMUM_WIDTH = 4;
    private static final int MAXIMUM_WIDTH = 10;

    private static final int DEFAULT_HEIGHT = 9;
    private static final int MINIMUM_HEIGHT = 6;
    private static final int MAXIMUM_HEIGHT = 12;

    @FXML
    private Button decreaseWidthButton;

    @FXML
    private Button increaseWidthButton;

    @FXML
    private Button decreaseHeightButton;

    @FXML
    private Button increaseHeightButton;

    @FXML
    private Button startButton;

    @FXML
    private Label widthLabel;

    @FXML
    private Label heightLabel;

    private int width;

    private int height;

    private Stage stage;

    public void initialize(final Stage stage) {
        this.stage = stage;

        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;

        widthLabel.setText(Integer.toString(width));
        heightLabel.setText(Integer.toString(height));

        addHandlers();
    }

    private void addHandlers() {
        decreaseWidthButton.setOnAction(event -> {
            width--;
            decreaseWidthButton.setDisable(width <= MINIMUM_WIDTH);
            increaseWidthButton.setDisable(false);
            widthLabel.setText(Integer.toString(width));
        });

        increaseWidthButton.setOnAction(event -> {
            width++;
            decreaseWidthButton.setDisable(false);
            increaseWidthButton.setDisable(width >= MAXIMUM_WIDTH);
            widthLabel.setText(Integer.toString(width));
        });

        decreaseHeightButton.setOnAction(event -> {
            height--;
            decreaseHeightButton.setDisable(height <= MINIMUM_HEIGHT);
            increaseHeightButton.setDisable(false);
            heightLabel.setText(Integer.toString(height));
        });

        increaseHeightButton.setOnAction(event -> {
            height++;
            decreaseHeightButton.setDisable(false);
            increaseHeightButton.setDisable(height >= MAXIMUM_HEIGHT);
            heightLabel.setText(Integer.toString(height));
        });

        startButton.setOnAction(event -> switchToGameScreen());
    }

    private void switchToGameScreen() {
        new GameController().initGame(stage, width, height);
        stage.setScene(ViewBuilder.buildGameScreenScene());
    }
}
