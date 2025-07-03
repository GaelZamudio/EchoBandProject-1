package com.echo.echoband.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class SidebarController implements Initializable {

    @FXML private ImageView profileImage;
    @FXML private Label profileName;
    @FXML private Button btnTraining;
    @FXML private Button btnStatistics;
    @FXML private Button btnFriends;
    @FXML private Button btnProfile;
    @FXML private Button btnLeague;
    @FXML private Button btnConfiguration;
    @FXML private Button btnClose;
    @FXML private VBox sideBarC;

    private MainController mainController;

    private int idDatos;
    private String nomUsuario;
    private String nomReal;
    private String apPat;
    private String apMat;
    private String correo;
    private String contrasena;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
        actualizarDatosVisuales();

    }

    public Node getSidebarNode() {
        return sideBarC;
    }

    private void cambiarVista(String fxmlFile, String cssFile, boolean mostrarSidebar) {
        if (mainController != null) {
            mainController.showScreen(fxmlFile, cssFile, mostrarSidebar);
        } else {
            System.err.println("❌ ERROR: MainController es null.");
        }
    }

    public void obtenerDatosGuardados() {
        Preferences prefs = Preferences.userRoot().node("com.echo.echoband");

        idDatos = prefs.getInt("id_datos", -1);
        nomUsuario = prefs.get("nom_usuario", "");
        nomReal = prefs.get("nom_real", "");
        apPat = prefs.get("ap_pat", "");
        apMat = prefs.get("ap_mat", "");
        correo = prefs.get("correo", "");
        contrasena = prefs.get("contrasena", "");

        System.out.println("✅ Datos cargados: " + nomReal + " " + apPat);


    }

    private void cerrarAplicacion() {
        Preferences prefs = Preferences.userRoot().node("com.echo.echoband");
        prefs.remove("id_datos");
        prefs.remove("nom_usuario");
        prefs.remove("nom_real");
        prefs.remove("ap_pat");
        prefs.remove("ap_mat");
        prefs.remove("correo");
        prefs.remove("contrasena");

        System.out.println("❌ Cerrando aplicación...");
        System.exit(0);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        asignarEventos();
    }

    private void asignarEventos() {
        btnTraining.setOnAction(e -> cambiarVista("/com/echo/echoband/trainingView.fxml", "/com/echo/echoband/trainingStyle.css", true));
        btnStatistics.setOnAction(e -> cambiarVista("/com/echo/echoband/statisticsView.fxml", "/com/echo/echoband/statisticsStyle.css", true));
        btnFriends.setOnAction(e -> cambiarVista("/com/echo/echoband/friendsView.fxml", "/com/echo/echoband/friendsStyle.css", true));
        btnProfile.setOnAction(e -> cambiarVista("/com/echo/echoband/userProfileView.fxml", "/com/echo/echoband/userProfileStyle.css", true));
        btnLeague.setOnAction(e -> cambiarVista("/com/echo/echoband/leagueView.fxml", "/com/echo/echoband/leagueStyle.css", true));
        btnConfiguration.setOnAction(e -> cambiarVista("/com/echo/echoband/configurationView.fxml", "/com/echo/echoband/configurationStyle.css", true));
        btnClose.setOnAction(e -> cerrarAplicacion());
    }

    public void actualizarDatosVisuales() {
        obtenerDatosGuardados();
        profileName.setText(nomReal + " " + apPat);
        profileImage.setFitWidth(90);
        profileImage.setFitHeight(90);
        profileImage.setImage(
                new Image(getClass().getResource("/fotodeperfilConfig.png").toExternalForm())
        );
    }
}
