package eu.yeger.comaga.controller;

import eu.yeger.comaga.view.ViewBuilder;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class IntroScreenController {

    private static final int DEFAULT_WIDTH = 5;
    private static final int MINIMUM_WIDTH = 3;
    private static final int MAXIMUM_WIDTH = 10;

    private static final int DEFAULT_HEIGHT = 10;
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

    public void initialize(Stage stage) {
        this.stage = stage;

        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;

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
        GameController gc = new GameController();
        gc.initGame(width, height);
        VBox vBox = ViewBuilder.buildGameScreen();
        Scene scene = new Scene(vBox);
        stage.setScene(scene);
    }
}
