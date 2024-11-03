package com.echo.echoband.controller;

import com.echo.echoband.LogIn;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;

public class LoginController {

    @FXML
    private Text txtcrear;
    @FXML private MFXTextField fieldusuario;
    @FXML private MFXPasswordField fieldcontrasena;
    @FXML private Label labelusuario;
    @FXML private Label labelcontrasena;
    @FXML private MFXButton botoniniciar;

    public void irASignUp() throws IOException {
        Stage stage = (Stage) txtcrear.getScene().getWindow();
        LogIn app = new LogIn();
        app.cambiarEscena(stage, "signUpView.fxml");
    }
}