package eu.yeger.comaga;

import eu.yeger.comaga.view.ViewBuilder;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(ViewBuilder.buildIntroScreenScene(primaryStage));
        primaryStage.show();
    }
}
