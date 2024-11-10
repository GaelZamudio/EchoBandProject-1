package com.echo.echoband;

import com.echo.echoband.connection.Connector;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignUp extends Application {
    public void cambiarEscena(Stage stage, String vistaFXML) throws IOException {
        FXMLLoader cargador = new FXMLLoader(getClass().getResource(vistaFXML));
        AnchorPane root = cargador.load();

        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("signUpStyle.css").toExternalForm());
        stage.setScene(scene);
        stage.setFullScreen(true);
        stage.centerOnScreen();
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


