package com.echo.echoband.controller;

import com.echo.echoband.LostGame;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LostGameController {
    @FXML private MFXButton btnInicio;
    @FXML private MFXButton btnJugar;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        if (btnInicio != null) {
            btnInicio.setOnAction(e -> cambiarVista("/com/echo/echoband/statisticsView.fxml", "/com/echo/echoband/statisticsStyle.css", true));
        }
        if (btnJugar != null) {
            btnJugar.setOnAction(e -> cambiarVista("/com/echo/echoband/trainingView.fxml", "/com/echo/echoband/trainingStyle.css", true));
        }
    }

    private void cambiarVista(String fxmlFile, String cssFile, boolean mostrarSidebar) {
        if (mainController != null) {
            mainController.showScreen(fxmlFile, cssFile, mostrarSidebar);
        } else {
            System.err.println("❌ ERROR: MainController es null.");
        }
    }
}
