package com.echo.echoband.controller;

import com.echo.echoband.UserProfile;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;

import java.io.IOException;

public class UserProfileController {

    @FXML private MFXButton entrenar;
    @FXML private MFXButton estadisticas;
    @FXML private MFXButton amigos;
    @FXML private MFXButton perfil;
    @FXML private MFXButton liga;
    @FXML private MFXButton config;
    @FXML private MFXButton cerrar;

    public void irAConfig() throws IOException {
        Stage stage = (Stage) config.getScene().getWindow();
        UserProfile app = new UserProfile();
        app.cambiarEscena(stage, "configurationView.fxml");
        stage.setTitle("Configuración");
    }

    public void irAEntrenar() throws IOException {
        Stage stage = (Stage) entrenar.getScene().getWindow();
        UserProfile app = new UserProfile();
        app.cambiarEscena(stage, "trainingView.fxml");
        stage.setTitle("Entrenamiento");
    }

    public void irAEstadisticas() throws IOException {
        Stage stage = (Stage) estadisticas.getScene().getWindow();
        UserProfile app = new UserProfile();
        app.cambiarEscena(stage, "statisticsView.fxml");
        stage.setTitle("Estadísticas");
    }

    public void irAPerfil() throws IOException {
        Stage stage = (Stage) perfil.getScene().getWindow();
        UserProfile app = new UserProfile();
        app.cambiarEscena(stage, "userProfileView.fxml");
        stage.setTitle("Perfil de Usuario");
    }

    public void irALogOut() throws IOException {
        Platform.exit();
    }
}
