package com.echo.echoband.controller;

import com.echo.echoband.LogIn;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private Text txtcrear;
    @FXML private MFXTextField fieldusuario;
    @FXML private MFXPasswordField fieldcontrasena;
    @FXML private Label labelusuario;
    @FXML private Label labelcontrasena;
    @FXML private MFXButton botoniniciar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BooleanBinding camposValidos =(fieldusuario.textProperty().isNotEmpty())
                .and(fieldcontrasena.textProperty().isNotEmpty());

        botoniniciar.disableProperty().bind(camposValidos.not());
    }

    @FXML
    public void irAMenu() {
        try {
            Stage stage = (Stage) botoniniciar.getScene().getWindow();
            LogIn app = new LogIn();
            stage.setTitle("Entrenamiento");

            app.cambiarEscena(stage, "/com/echoband/echoband/trainingView.fxml");

            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(LogIn.class.getResource("/com/echoband/echoband/trainingStyle.css").toExternalForm());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar trainingView.fxml");
        }
    }

    public void irASignUp() throws IOException {
        Stage stage = (Stage) txtcrear.getScene().getWindow();
        LogIn app = new LogIn();
        app.cambiarEscena(stage, "signUpView.fxml");
        stage.setTitle("Sign Up");
    }
}
