package com.echo.echoband.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class TrainingController {

    @FXML private MFXButton btnCalor, btnEstrellas, btnCalma, btnProgresando, btnConcentrate, btnDificil;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        if (btnCalor != null) {
            btnCalor.setOnAction(e -> cambiarVista("/com/echo/echoband/entrarEnCalorView.fxml", "/com/echo/echoband/entrarEnCalorStyle.css", true));
        }
        if (btnProgresando != null) {
            btnProgresando.setOnAction(e -> cambiarVista("/com/echo/echoband/progresandoView.fxml", "/com/echo/echoband/progresandoStyle.css", true));
        }
        if (btnConcentrate != null) {
            btnConcentrate.setOnAction(e -> cambiarVista("/com/echo/echoband/concentrateView.fxml", "/com/echo/echoband/progresandoStyle.css", true));
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
