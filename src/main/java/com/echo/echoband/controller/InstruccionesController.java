package com.echo.echoband.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class InstruccionesController {

    @FXML private MFXButton btnMemorama, btnReflejos, btnSimon;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        if (btnMemorama != null) {
            btnMemorama.setOnAction(e -> cambiarVista("/com/echo/echoband/memoryView.fxml", "/com/echo/echoband/memoryStyle.css", false));
        }
        if (btnReflejos != null) {
            btnReflejos.setOnAction(e -> cambiarVista("/com/echo/echoband/fastButtonView.fxml", "/com/echo/echoband/fastButtonStyle.css", false));
        }
        if (btnSimon != null) {
            btnSimon.setOnAction(e -> cambiarVista("/com/echo/echoband/simonSaysView.fxml", "/com/echo/echoband/simonSaysStyle.css", false));
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
