package com.echo.echoband.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.prefs.Preferences;

public class SimonSaysController {

    @FXML private Label instructionsText;
    @FXML private Label ronda;
    @FXML private ImageView upArrow, downArrow, leftArrow, rightArrow;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private final List<String> sequence = new ArrayList<>();
    private final List<String> userInput = new ArrayList<>();
    private int currentRound = 1;
    private int currentInputIndex = 0;

    public void initialize() {
        loadImages();
        setupEventHandlers();
        startGame();
    }

    private void loadImages() {
        upArrow.setImage(loadImage("/up_arrow.png"));
        downArrow.setImage(loadImage("/down_arrow.png"));
        leftArrow.setImage(loadImage("/left_arrow.png"));
        rightArrow.setImage(loadImage("/right_arrow.png"));
    }

    private Image loadImage(String path) {
        try {
            return new Image(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            System.err.println("Error al cargar la imagen: " + path);
            return null;
        }
    }

    private void setupEventHandlers() {
        upArrow.setOnMouseClicked(event -> handleInput("ARRIBA"));
        downArrow.setOnMouseClicked(event -> handleInput("ABAJO"));
        leftArrow.setOnMouseClicked(event -> handleInput("IZQUIERDA"));
        rightArrow.setOnMouseClicked(event -> handleInput("DERECHA"));
    }

    private void startGame() {
        sequence.clear();
        currentRound = 1;
        ronda.setText("Ronda: " + currentRound);
        nextRound();
    }

    private void nextRound() {
        userInput.clear();
        currentInputIndex = 0;
        generateSequence();

        Platform.runLater(() -> instructionsText.setText("Observa la secuencia..."));

        enableArrows(false);
        upArrow.setImage(loadImage("/disabled_up_arrow.png"));
        downArrow.setImage(loadImage("/disabled_down_arrow.png"));
        leftArrow.setImage(loadImage("/disabled_right_arrow.png"));
        rightArrow.setImage(loadImage("/disabled_left_arrow.png"));

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> showSequence());
            }
        }, 2000);
    }

    private void generateSequence() {
        String[] directions = {"ARRIBA", "ABAJO", "IZQUIERDA", "DERECHA"};
        Random random = new Random();
        sequence.add(directions[random.nextInt(directions.length)]);
    }

    private void showSequence() {
        Platform.runLater(() -> instructionsText.setText(""));

        Timer timer = new Timer();
        int delay = 0;

        for (String direction : sequence) {
            int finalDelay = delay;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> instructionsText.setText(direction));
                }
            }, finalDelay);

            delay += 650;

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Platform.runLater(() -> instructionsText.setText(""));
                }
            }, finalDelay + 150);
        }

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(() -> {
                    instructionsText.setText("Ingresa la secuencia");
                    enableArrows(true);
                    upArrow.setImage(loadImage("/up_arrow.png"));
                    downArrow.setImage(loadImage("/down_arrow.png"));
                    leftArrow.setImage(loadImage("/left_arrow.png"));
                    rightArrow.setImage(loadImage("/right_arrow.png"));
                });
            }
        }, delay);
    }

    private void handleInput(String direction) {
        if (currentInputIndex < sequence.size()) {
            userInput.add(direction);
            if (!userInput.get(currentInputIndex).equals(sequence.get(currentInputIndex))) {
                gameOver();
                return;
            }
            currentInputIndex++;

            if (currentInputIndex == sequence.size()) {
                instructionsText.setText("¡Correcto! Siguiente ronda.");
                currentRound++;
                ronda.setText("Ronda: " + currentRound);
                enableArrows(false);
                upArrow.setImage(loadImage("/disabled_up_arrow.png"));
                downArrow.setImage(loadImage("/disabled_down_arrow.png"));
                leftArrow.setImage(loadImage("/disabled_right_arrow.png"));
                rightArrow.setImage(loadImage("/disabled_left_arrow.png"));

                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        nextRound();
                    }
                }, 1500);
            }
        }
    }

    private void gameOver() {
        instructionsText.setText("¡Incorrecto! Fin del juego.");
        enableArrows(false);
        upArrow.setImage(loadImage("/disabled_up_arrow.png"));
        downArrow.setImage(loadImage("/disabled_down_arrow.png"));
        leftArrow.setImage(loadImage("/disabled_right_arrow.png"));
        rightArrow.setImage(loadImage("/disabled_left_arrow.png"));
        cambiarVista("/com/echo/echoband/gameWonView.fxml", "/com/echo/echoband/gameWonStyle.css", false);
    }

    private void enableArrows(boolean enable) {
        upArrow.setDisable(!enable);
        downArrow.setDisable(!enable);
        leftArrow.setDisable(!enable);
        rightArrow.setDisable(!enable);
    }

    private void cambiarVista(String fxmlFile, String cssFile, boolean mostrarSidebar) {
        Preferences prefs = Preferences.userRoot().node("com.echo.echoband");
        prefs.put("ultimo_ejercicio", "Retención");
        if (mainController != null) {
            mainController.showScreen(fxmlFile, cssFile, mostrarSidebar);
        } else {
            System.err.println("❌ ERROR: MainController es null.");
        }
    }
}
