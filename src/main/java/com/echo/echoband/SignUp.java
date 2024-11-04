package com.echo.echoband;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.IOException;

public class SignUp extends Application {
    public void cambiarEscena(Stage stage, String vistaFXML) throws IOException {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource(vistaFXML));
        AnchorPane root = cargador.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("signUpStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setFullScreen(true);
    }

    @Override
    public void start(Stage stage) throws IOException {
        cambiarEscena(stage, "signUpView.fxml");
        stage.setTitle("Sign Up");
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
