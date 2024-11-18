package com.echo.echoband;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class League extends Application {
    public void cambiarEscena(Stage stage, String vistaFXML) throws IOException {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource(vistaFXML));
        AnchorPane root = cargador.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("leagueStyle.css").toExternalForm());
        stage.setScene(scene);

        stage.setFullScreen(true);
        stage.centerOnScreen();
    }

    @Override
    public void start(Stage stage) throws IOException {
        cambiarEscena(stage, "leagueView.fxml");
        stage.setTitle("Liga");
        stage.setMinWidth(1500);
        stage.setMinHeight(700);
        stage.centerOnScreen();
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
