package eu.yeger.comaga.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class IntroScreenController {

    private static final int MINIMUM_WIDTH = 3;
    private static final int DEFAULT_WIDTH = 5;
    private static final int MAXIMUM_WIDTH = 10;

    private static final int MINIMUM_HEIGHT = 6;
    private static final int DEFAULT_HEIGHT = 10;
    private static final int MAXIMUM_HEIGHT = 12;

    @FXML
    private Button decreaseWidthButton;

    @FXML
    private Button increaseWidthButton;

    @FXML
    private Button decreaseHeightButton;

    @FXML
    private Button increaseHeightButton;

    private int width;

    private int height;

    public void initialize() {
        width = DEFAULT_WIDTH;
        height = DEFAULT_HEIGHT;
    }

    private void addHandlers() {
        decreaseWidthButton.setOnAction(event -> {
            width--;
            decreaseWidthButton.setDisable(width <= MINIMUM_WIDTH);
            increaseWidthButton.setDisable(false);
        });
    }
}
