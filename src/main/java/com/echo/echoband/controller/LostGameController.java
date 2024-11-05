package com.echo.echoband.controller;

import com.echo.echoband.LostGame;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LostGameController {
    @FXML private MFXButton inicio;
    @FXML private MFXButton jugar;

    public void irAInicio() throws IOException {
        Stage stage = (Stage) inicio.getScene().getWindow();
        LostGame app = new LostGame();
        app.cambiarEscena(stage, "trainingView.fxml");
        stage.setTitle("Entrenamiento");
    }

    public void irAVolverAJugar() throws IOException {
        try {
            Stage stage = (Stage) jugar.getScene().getWindow();
            LostGame app = new LostGame();
            stage.setTitle("Entrar en Calor");

            app.cambiarEscena(stage, "/com/echoband/echoband/entrarEnCalorView.fxml");

            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(LostGame.class.getResource("/com/echoband/echoband/entrarEnCalorStyle.css").toExternalForm());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar entrarEnCalorView.fxml");
        }
    }
}
