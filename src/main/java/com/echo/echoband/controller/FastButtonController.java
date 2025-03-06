package com.echo.echoband.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class FastButtonController {

    @FXML private MFXButton startButton;
    @FXML private MFXButton redButton;
    @FXML private Circle greenLight;
    @FXML private Label timeLabel;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private Timeline countdownTimer;
    private Timeline lightTimer;
    private double timeElapsed = 0.0;
    private boolean lightOn = false;
    private boolean gameStarted = false;

    @FXML
    private void startPreparation() {
        if (!gameStarted) {
            gameStarted = true;
            timeElapsed = 0.0;
            timeLabel.setText("Tiempo: 0.00");
            greenLight.setFill(Color.TRANSPARENT);
            greenLight.setVisible(false);
            lightOn = false;

            lightTimer = new Timeline(
                    new KeyFrame(Duration.seconds(5), e -> turnOnGreenLight())
            );
            lightTimer.setCycleCount(1);
            lightTimer.play();
        }
    }

    private void updateTime() {
        if (lightOn) {
            timeElapsed += 0.01;
            timeLabel.setText(String.format("Tiempo: %.2f", timeElapsed));
        }
    }

    private void turnOnGreenLight() {
        greenLight.setFill(Color.GREEN);
        greenLight.setVisible(true);
        lightOn = true;

        countdownTimer = new Timeline(
                new KeyFrame(Duration.millis(10), e -> updateTime())
        );
        countdownTimer.setCycleCount(Timeline.INDEFINITE);
        countdownTimer.play();
    }

    @FXML
    private void buttonPressed() {
        if (lightOn) {
            countdownTimer.stop();
            greenLight.setFill(Color.RED);
            timeLabel.setText(String.format("Tiempo de reacción: %.2f segundos", timeElapsed));
            gameStarted = false;
            startButton.setDisable(true);
            Timeline transitionTimer = new Timeline(
                    new KeyFrame(Duration.seconds(4), e -> changeToGameWonScene())
            );
            transitionTimer.setCycleCount(1);
            transitionTimer.play();
        } else {
            timeLabel.setText("¡Te adelantaste! Espera a la luz verde.");
        }
    }

    private void changeToGameWonScene() {
        cambiarVista("/com/echo/echoband/gameWonView.fxml", "/com/echo/echoband/gameWonStyle.css", false);
    }

    private void cambiarVista(String fxmlFile, String cssFile, boolean mostrarSidebar) {
        if (mainController != null) {
            mainController.showScreen(fxmlFile, cssFile, mostrarSidebar);
        } else {
            System.err.println("❌ ERROR: MainController es null.");
        }
    }
}
