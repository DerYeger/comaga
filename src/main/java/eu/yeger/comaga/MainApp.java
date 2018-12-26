package eu.yeger.comaga;

import eu.yeger.comaga.controller.IntroScreenController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(IntroScreenController.class.getResource("introScreen.fxml"));
        Parent parent = fxmlLoader.load();
        IntroScreenController introScreenController = fxmlLoader.getController();
        primaryStage.setScene(new Scene(parent));
        primaryStage.show();
    }
}
