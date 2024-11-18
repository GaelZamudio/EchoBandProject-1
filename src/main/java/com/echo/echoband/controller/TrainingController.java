package com.echo.echoband.controller;

import com.echo.echoband.Statistics;
import com.echo.echoband.Training;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
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

    @FXML private MFXButton calor;

    public void irAConfig() throws IOException {
        Stage stage = (Stage) config.getScene().getWindow();
        Training app = new Training();
        app.cambiarEscena(stage, "configurationView.fxml");
        stage.setTitle("Configuración");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Training.class.getResource("/com/echo/echoband/configurationStyle.css").toExternalForm());
    }

    public void irAAmigos() throws IOException {
        Stage stage = (Stage) amigos.getScene().getWindow();
        Training app = new Training();
        app.cambiarEscena(stage, "friendsView.fxml");
        stage.setTitle("Amigos");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Training.class.getResource("/com/echo/echoband/friendsStyle.css").toExternalForm());
    }

    public void irALiga() throws IOException {
        Stage stage = (Stage) liga.getScene().getWindow();
        Training app = new Training();
        app.cambiarEscena(stage, "leagueView.fxml");
        stage.setTitle("Liga");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Training.class.getResource("/com/echo/echoband/leagueStyle.css").toExternalForm());
    }

    public void irAEntrenar() throws IOException {
        Stage stage = (Stage) entrenar.getScene().getWindow();
        Training app = new Training();
        app.cambiarEscena(stage, "trainingView.fxml");
        stage.setTitle("Entrenamiento");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Training.class.getResource("/com/echo/echoband/trainingStyle.css").toExternalForm());
    }

    public void irAEstadisticas() throws IOException {
        Stage stage = (Stage) estadisticas.getScene().getWindow();
        Training app = new Training();
        app.cambiarEscena(stage, "statisticsView.fxml");
        stage.setTitle("Estadísticas");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Training.class.getResource("/com/echo/echoband/staticsStyle.css").toExternalForm());
    }

    public void irAPerfil() throws IOException {
        Stage stage = (Stage) perfil.getScene().getWindow();
        Training app = new Training();
        app.cambiarEscena(stage, "userProfileView.fxml");
        stage.setTitle("Perfil de Usuario");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Training.class.getResource("/com/echo/echoband/userProfileStyle.css").toExternalForm());
    }

    public void irALogOut() throws IOException {
        Platform.exit();
    }

    public void irACalor() throws IOException {
        Stage stage = (Stage) perfil.getScene().getWindow();
        Training app = new Training();
        app.cambiarEscena(stage, "entrarEnCalorView.fxml");
        stage.setTitle("Entrar en Calor");
    }
}
