package com.echo.echoband.controller;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class SidebarController {

    @FXML private ImageView profileImage;
    @FXML private Label profileName;
    @FXML private Button btnTraining;
    @FXML private Button btnStatistics;
    @FXML private Button btnFriends;
    @FXML private Button btnProfile;
    @FXML private Button btnLeague;
    @FXML private Button btnConfiguration;
    @FXML private Button btnClose;
    @FXML private VBox sideBarC; // Contenedor del Sidebar

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        initialize();
    }

    public Node getSidebarNode() {
        if (sideBarC == null) {
            System.err.println("❌ ERROR: sideBarC es null. Asegúrate de que está bien enlazado en el FXML.");
        }
        return sideBarC;
    }

    @FXML
    public void initialize() {
        if (mainController == null) {
            System.err.println("❌ ERROR: mainController es null. No se pueden asignar eventos.");
            return;
        }

        btnTraining.setOnAction(e -> cambiarVista("/com/echo/echoband/trainingView.fxml", "/com/echo/echoband/trainingStyle.css", true));
        btnStatistics.setOnAction(e -> cambiarVista("/com/echo/echoband/statisticsView.fxml", "/com/echo/echoband/statisticsStyle.css", true));
        btnFriends.setOnAction(e -> cambiarVista("/com/echo/echoband/friendsView.fxml", "/com/echo/echoband/friendsStyle.css", true));
        btnProfile.setOnAction(e -> cambiarVista("/com/echo/echoband/userProfileView.fxml", "/com/echo/echoband/userProfileStyle.css", true));
        btnLeague.setOnAction(e -> cambiarVista("/com/echo/echoband/leagueView.fxml", "/com/echo/echoband/leagueStyle.css", true));
        btnConfiguration.setOnAction(e -> cambiarVista("/com/echo/echoband/configurationView.fxml", "/com/echo/echoband/configurationStyle.css", true));
        btnClose.setOnAction(e -> cerrarAplicacion());
    }

    private void cambiarVista(String fxmlFile, String cssFile, boolean mostrarSidebar) {
        if (mainController != null) {
            mainController.showScreen(fxmlFile, cssFile, mostrarSidebar);
        } else {
            System.err.println("❌ ERROR: MainController es null.");
        }
    }

    private void cerrarAplicacion() {
        System.out.println("❌ Cerrando aplicación...");
        System.exit(0);
    }
}
