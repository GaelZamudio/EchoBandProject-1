package com.echo.echoband;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Memory extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/echo/echoband/memoryView.fxml"));
        AnchorPane root = loader.load();

        Scene scene = new Scene(root, 1500, 700);
        scene.getStylesheets().add(Training.class.getResource("memoryStyle.css").toExternalForm());
        primaryStage.setMinWidth(1500);
        primaryStage.setMinHeight(700);
        primaryStage.setFullScreen(true);
        primaryStage.centerOnScreen();
        primaryStage.setScene(scene);
        primaryStage.setTitle("Entrenar");

        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}