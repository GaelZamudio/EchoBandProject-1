package com.echo.echoband;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SignUp extends Application {
    public void cambiarEscena(Stage stage, String vistaFXML) throws IOException {
        // Guarda el estado de pantalla completa
        boolean estabaEnPantallaCompleta = stage.isFullScreen();

        // Cargar la nueva escena
        FXMLLoader cargador = new FXMLLoader(SignUp.class.getResource(vistaFXML));
        BorderPane root = cargador.load();

        // Configura la imagen en el lado derecho

        Image imagen = new Image(SignUp.class.getResource("/imgSignUp.jpg").toExternalForm());
        ImageView imageView = new ImageView(imagen);
        imageView.fitWidthProperty().bind(root.widthProperty().divide(2));
        imageView.fitHeightProperty().bind(root.heightProperty());
        imageView.setPreserveRatio(true);
        root.setRight(imageView);

        // Crear y configurar la nueva escena con el CSS
        Scene scene = new Scene(root, 1200, 700);
        scene.getStylesheets().add(SignUp.class.getResource("signUpStyle.css").toExternalForm());
        stage.setScene(scene);

        // Restaura el estado de pantalla completa
        stage.setFullScreen(estabaEnPantallaCompleta);
    }

    @Override
    public void start(Stage stage) throws IOException {
        cambiarEscena(stage, "signUpView.fxml");
        stage.setTitle("Registro de Cuenta");
        stage.setMinWidth(1200);
        stage.setMinHeight(700);
        stage.centerOnScreen();
        stage.setFullScreen(true);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}