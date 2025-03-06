package com.echo.echoband.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MemoryController {

    @FXML private ImageView image00, image01, image02, image03, image04, image05, image10, image11, image12, image13, image14, image15, image20, image21, image22, image23, image24, image25;
    @FXML private Label timeRemainingLabel;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private Image[] frontImages = {
            new Image("entremosencalorEntrenar19.jpg"),
            new Image("entremosencalorEntrenar19.jpg"),
            new Image("entremosencalorEntrenar18.jpg"),
            new Image("entremosencalorEntrenar18.jpg"),
            new Image("entremosencalorEntrenar17.png"),
            new Image("entremosencalorEntrenar17.png"),
            new Image("entremosencalorEntrenar11.png"),
            new Image("entremosencalorEntrenar11.png"),
            new Image("entremosencalorEntrenar12.png"),
            new Image("entremosencalorEntrenar12.png"),
            new Image("entremosencalorEntrenar13.png"),
            new Image("entremosencalorEntrenar13.png"),
            new Image("entremosencalorEntrenar16.png"),
            new Image("entremosencalorEntrenar16.png"),
            new Image("entremosencalorEntrenar14.png"),
            new Image("entremosencalorEntrenar14.png"),
            new Image("entremosencalorEntrenar15.jpeg"),
            new Image("entremosencalorEntrenar15.jpeg")
    };

    private boolean[] flipped = new boolean[18];
    private int firstFlippedIndex = -1;
    private int secondFlippedIndex = -1;
    private int timeRemaining = 60;
    private Timeline countdownTimeline;

    @FXML
    public void initialize() {
        shuffleImages();
        setupButtons();
        startCountdown();
    }

    private void setupButtons() {
        MFXButton[] buttons = new MFXButton[]{
                (MFXButton) image00.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image01.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image02.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image03.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image04.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image05.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image10.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image11.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image12.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image13.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image14.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image15.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image20.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image21.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image22.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image23.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image24.getParent().getChildrenUnmodifiable().get(2),
                (MFXButton) image25.getParent().getChildrenUnmodifiable().get(2)
        };
        for (int i = 0; i < buttons.length; i++) {
            int index = i;
            buttons[i].setOnAction(event -> flipCard(index));
        }
    }

    private void startCountdown() {
        timeRemainingLabel.setText(timeRemaining + " segundos");
        countdownTimeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timeRemaining--;
            timeRemainingLabel.setText(timeRemaining + " segundos");
            if (timeRemaining <= 0) {
                countdownTimeline.stop();
                System.out.println("El tiempo se ha acabado, cambiando a lostGameView");
                cambiarVista("/com/echo/echoband/lostGameView.fxml", "/com/echo/echoband/lostGameStyle.css", false);
            }
        }));
        countdownTimeline.setCycleCount(Timeline.INDEFINITE);
        countdownTimeline.play();
    }

    private void shuffleImages() {
        List<Image> imageList = new ArrayList<>();
        Collections.addAll(imageList, frontImages);
        Collections.shuffle(imageList);
        frontImages = imageList.toArray(new Image[0]);
    }

    private void flipCard(int index) {
        if (!flipped[index] && secondFlippedIndex == -1) {
            setCardImage(index, true);
            flipped[index] = true;
            if (firstFlippedIndex == -1) {
                firstFlippedIndex = index;
            } else {
                secondFlippedIndex = index;
                if (frontImages[firstFlippedIndex].getUrl().equals(frontImages[secondFlippedIndex].getUrl())) {
                    hideCards(firstFlippedIndex, secondFlippedIndex);
                } else {
                    PauseTransition pause = new PauseTransition(Duration.seconds(1));
                    pause.setOnFinished(event -> resetCards(firstFlippedIndex, secondFlippedIndex));
                    pause.play();
                }
            }
        }
    }

    private void setCardImage(int index, boolean visible) {
        switch (index) {
            case 0: image00.setImage(visible ? frontImages[index] : null); image00.setVisible(visible); break;
            case 1: image01.setImage(visible ? frontImages[index] : null); image01.setVisible(visible); break;
            case 2: image02.setImage(visible ? frontImages[index] : null); image02.setVisible(visible); break;
            case 3: image03.setImage(visible ? frontImages[index] : null); image03.setVisible(visible); break;
            case 4: image04.setImage(visible ? frontImages[index] : null); image04.setVisible(visible); break;
            case 5: image05.setImage(visible ? frontImages[index] : null); image05.setVisible(visible); break;
            case 6: image10.setImage(visible ? frontImages[index] : null); image10.setVisible(visible); break;
            case 7: image11.setImage(visible ? frontImages[index] : null); image11.setVisible(visible); break;
            case 8: image12.setImage(visible ? frontImages[index] : null); image12.setVisible(visible); break;
            case 9: image13.setImage(visible ? frontImages[index] : null); image13.setVisible(visible); break;
            case 10: image14.setImage(visible ? frontImages[index] : null); image14.setVisible(visible); break;
            case 11: image15.setImage(visible ? frontImages[index] : null); image15.setVisible(visible); break;
            case 12: image20.setImage(visible ? frontImages[index] : null); image20.setVisible(visible); break;
            case 13: image21.setImage(visible ? frontImages[index] : null); image21.setVisible(visible); break;
            case 14: image22.setImage(visible ? frontImages[index] : null); image22.setVisible(visible); break;
            case 15: image23.setImage(visible ? frontImages[index] : null); image23.setVisible(visible); break;
            case 16: image24.setImage(visible ? frontImages[index] : null); image24.setVisible(visible); break;
            case 17: image25.setImage(visible ? frontImages[index] : null); image25.setVisible(visible); break;
        }
    }

    private void hideCards(int index1, int index2) {
        firstFlippedIndex = -1;
        secondFlippedIndex = -1;

        boolean allFlipped = true;
        for (boolean flippedCard : flipped) {
            if (!flippedCard) {
                allFlipped = false;
                break;
            }
        }
        if (allFlipped && timeRemaining > 0) {
            countdownTimeline.stop();
            System.out.println("Juego completo, cambiando a gameWon");
            cambiarVista("/com/echo/echoband/gameWonView.fxml", "/com/echo/echoband/gameWonStyle.css", false);
        }
    }

    private void resetCards(int index1, int index2) {
        setCardImage(index1, false);
        setCardImage(index2, false);
        flipped[index1] = false;
        flipped[index2] = false;
        firstFlippedIndex = -1;
        secondFlippedIndex = -1;
    }

    private void cambiarVista(String fxmlFile, String cssFile, boolean mostrarSidebar) {
        if (mainController != null) {
            mainController.showScreen(fxmlFile, cssFile, mostrarSidebar);
        } else {
            System.err.println("❌ ERROR: MainController es null.");
        }
    }
}
