package eu.yeger.comaga.view;

import eu.yeger.comaga.controller.FieldController;
import eu.yeger.comaga.controller.GameOverScreenController;
import eu.yeger.comaga.controller.IntroScreenController;
import eu.yeger.comaga.model.Field;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewBuilder {

    public static Scene buildIntroScreenScene(final Stage stage) {
        VBox vBox = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewBuilder.class.getResource("introScreen.fxml"));
            vBox = fxmlLoader.load();
            IntroScreenController introScreenController = fxmlLoader.getController();
            introScreenController.initialize(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Scene(vBox);
    }

    public static Scene buildGameScreenScene() {
        VBox vBox = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewBuilder.class.getResource("gameScreen.fxml"));
            vBox = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Scene(vBox);
    }

    public static StackPane buildFieldViewStackPane(final Field field) {
        StackPane stackPane = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewBuilder.class.getResource("field.fxml"));
            stackPane = fxmlLoader.load();
            FieldController fieldController = fxmlLoader.getController();
            fieldController.setField(field);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return stackPane;
    }

    public static Scene buildGameOverScreenScene(final Stage stage) {
        VBox vBox = null;
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(ViewBuilder.class.getResource("gameOverScreen.fxml"));
            vBox = fxmlLoader.load();
            GameOverScreenController gameOverScreenController = fxmlLoader.getController();
            gameOverScreenController.initialize(stage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Scene(vBox);
    }
}
