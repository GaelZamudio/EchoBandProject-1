package com.echo.echoband.controller;

import com.echo.echoband.Training;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.io.IOException;

public class TrainingController {

    @FXML private MFXButton entrenar;

    @FXML private MFXButton estadisticas;

    @FXML private MFXButton amigos;

    @FXML private MFXButton perfil;

    @FXML private MFXButton liga;

    @FXML private MFXButton config;

    @FXML private MFXButton cerrar;

    public void irAConfig() throws IOException {
        Stage stage = (Stage) config.getScene().getWindow();
        Training app = new Training();
        app.cambiarEscena(stage, "viewConfig.fxml");
    }

    public void irAEntrenar() throws IOException {
        Stage stage = (Stage) entrenar.getScene().getWindow();
        Training app = new Training();
        app.cambiarEscena(stage, "trainingView.fxml");
    }

    public void irAEstadisticas() throws IOException {
        Stage stage = (Stage) estadisticas.getScene().getWindow();
        Training app = new Training();
        app.cambiarEscena(stage, "viewEstadisticas.fxml");
    }
}
