package com.echo.echoband.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.Node;
import java.io.IOException;
import java.net.URL;

public class MainController {

    @FXML private BorderPane mainLayout;  // Layout principal con espacio para sidebar y contenido
    @FXML private StackPane contentPane;  // Contenedor central para las vistas dinámicas

    private SidebarController sidebarController;

    @FXML
    public void initialize() {
        loadSidebar();
        showScreen("/com/echo/echoband/logInView.fxml", "/com/echo/echoband/logInStyle.css", false);
    }

    private void loadSidebar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/echo/echoband/sidebarView.fxml"));
            Node sidebar = loader.load();

            sidebarController = loader.getController();
            sidebarController.setMainController(this);

            mainLayout.setLeft(sidebar);
            System.out.println("✅ Sidebar cargado correctamente.");
        } catch (IOException e) {
            System.err.println("❌ ERROR cargando Sidebar: " + e.getMessage());
        }
    }

    public void showScreen(String fxmlFile, String cssFile, boolean mostrarSidebar) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Node screen = loader.load();

            Object controller = loader.getController();
            inyectarMainController(controller);

            if (mostrarSidebar) {
                if (sidebarController != null) {
                    mainLayout.setLeft(sidebarController.getSidebarNode());
                } else {
                    System.err.println("❌ ERROR: sidebarController es null.");
                }
            } else {
                mainLayout.setLeft(null);
            }

            if (contentPane != null) {
                contentPane.getChildren().setAll(screen);
            } else {
                System.err.println("❌ ERROR: contentPane es null.");
            }

            Scene scene = contentPane.getScene();
            if (scene != null) {
                scene.getStylesheets().clear();
                URL cssUrl = getClass().getResource(cssFile);
                if (cssUrl != null) {
                    scene.getStylesheets().add(cssUrl.toExternalForm());
                } else {
                    System.err.println("❌ ERROR: No se encontró el archivo CSS " + cssFile);
                }
            }

            System.out.println("✅ Vista cambiada a: " + fxmlFile + " (Sidebar: " + mostrarSidebar + ")");
        } catch (IOException e) {
            System.err.println("❌ ERROR al cambiar de vista: " + e.getMessage());
        }
    }

    // Método para inyectar MainController si el controlador de la vista lo soporta
    private void inyectarMainController(Object controller) {
        if (controller instanceof LoginController) {
            ((LoginController) controller).setMainController(this);
        }
        if (controller instanceof SignUpController) {
            ((SignUpController) controller).setMainController(this);
        }
        if (controller instanceof TrainingController) {
            ((TrainingController) controller).setMainController(this);
        }
        if (controller instanceof InstruccionesController) {
            ((InstruccionesController) controller).setMainController(this);
        }
        if (controller instanceof MemoryController) {
            ((MemoryController) controller).setMainController(this);
        }
        if (controller instanceof FastButtonController) {
            ((FastButtonController) controller).setMainController(this);
        }
        if (controller instanceof SimonSaysController) {
            ((SimonSaysController) controller).setMainController(this);
        }
        if (controller instanceof GameWonController) {
            ((GameWonController) controller).setMainController(this);
        }
        if (controller instanceof LostGameController) {
            ((LostGameController) controller).setMainController(this);
        }
    }

    public SidebarController getSidebarController() {
        return sidebarController;
    }
}
