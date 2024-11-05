package com.echo.echoband.controller;

import com.echo.echoband.GameWon;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GameWonController {
    @FXML private MFXButton inicio;
    @FXML private MFXButton jugar;

    public void irAInicio() throws IOException {
        Stage stage = (Stage) inicio.getScene().getWindow();
        GameWon app = new GameWon();
        app.cambiarEscena(stage, "/com/echo/echoband/trainingView.fxml");
        stage.setTitle("Entrenamiento");
    }

    public void irASiguiente() throws IOException {
        try {
            Stage stage = (Stage) jugar.getScene().getWindow();
            GameWon app = new GameWon();
            stage.setTitle("Entrar en Calor");

            app.cambiarEscena(stage, "/com/echo/echoband/entrarEnCalorView.fxml");

            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(GameWon.class.getResource("/com/echo/echoband/entrarEnCalorStyle.css").toExternalForm());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar entrarEnCalorView.fxml");
        }
    }
}
