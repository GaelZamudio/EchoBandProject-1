package com.echo.echoband.controller;

import com.echo.echoband.EntrarEnCalor;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.IOException;

public class EntrarEnCalorController {

    @FXML private MFXButton entrenar;
    @FXML private MFXButton estadisticas;
    @FXML private MFXButton amigos;
    @FXML private MFXButton perfil;
    @FXML private MFXButton liga;
    @FXML private MFXButton config;
    @FXML private MFXButton cerrar;
    @FXML private MFXButton iniciarEjercicio;

    public void irAConfig() throws IOException {
        Stage stage = (Stage) config.getScene().getWindow();
        EntrarEnCalor app = new EntrarEnCalor();
        app.cambiarEscena(stage, "configurationView.fxml");
        stage.setTitle("Configuración");
    }

    public void irAEntrenar() throws IOException {
        Stage stage = (Stage) entrenar.getScene().getWindow();
        EntrarEnCalor app = new EntrarEnCalor();
        app.cambiarEscena(stage, "trainingView.fxml");
        stage.setTitle("Entrenamiento");
    }

    public void irAEstadisticas() throws IOException {
        Stage stage = (Stage) estadisticas.getScene().getWindow();
        EntrarEnCalor app = new EntrarEnCalor();
        app.cambiarEscena(stage, "statisticsView.fxml");
        stage.setTitle("Estadísticas");
    }

    public void irAPerfil() throws IOException {
        Stage stage = (Stage) perfil.getScene().getWindow();
        EntrarEnCalor app = new EntrarEnCalor();
        app.cambiarEscena(stage, "userProfileView.fxml");
        stage.setTitle("Perfil de Usuario");
    }

    public void irALogOut() throws IOException {
        Platform.exit();
    }

    public void irAMemorama() throws IOException {
        Stage stage = (Stage) iniciarEjercicio.getScene().getWindow();
        EntrarEnCalor app = new EntrarEnCalor();
        app.cambiarEscena(stage, "memoryView.fxml");
        stage.setTitle("Ejercicio 1-Memorama");
    }
}
