package eu.yeger.comaga;

import eu.yeger.comaga.view.ViewBuilder;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        VBox vBox = ViewBuilder.buildIntroScreen(primaryStage);
        primaryStage.setScene(new Scene(vBox));
        primaryStage.show();
    }
}
