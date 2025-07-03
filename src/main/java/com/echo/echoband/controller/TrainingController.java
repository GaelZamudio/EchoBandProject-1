package com.echo.echoband.controller;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

import java.util.prefs.Preferences;
import javafx.scene.control.Label;


public class TrainingController {

    @FXML private MFXButton btnCalor, btnEstrellas, btnCalma, btnProgresando, btnConcentrate, btnDificil;
    private MainController mainController;
    @FXML private Label labelSaludo;
    public int idDatos;
    public String nomUsuario;
    public String nomReal;
    public String apPat;
    public String apMat;
    public String correo;
    public String contrasena;

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


        obtenerDatosGuardados();

        labelSaludo.setText("Hola de nuevo, "+nomUsuario);


    }

    public void obtenerDatosGuardados() {
        Preferences prefs = Preferences.userRoot().node("com.echo.echoband");

        idDatos = prefs.getInt("id_datos", -1); // -1 es el valor por defecto si no existe
        nomUsuario = prefs.get("nom_usuario", ""); // "" por defecto
        nomReal = prefs.get("nom_real", "");
        apPat = prefs.get("ap_pat", "");
        apMat = prefs.get("ap_mat", "");
        correo = prefs.get("correo", "");
        contrasena = prefs.get("contrasena", "");

        System.out.println("ID: " + idDatos);
        System.out.println("Usuario: " + nomUsuario);
        System.out.println("Nombre real: " + nomReal);
        System.out.println("Apellido paterno: " + apPat);
        System.out.println("Apellido materno: " + apMat);
        System.out.println("Correo: " + correo);
        System.out.println("Contrasena: " + contrasena);
    }

    private void cambiarVista(String fxmlFile, String cssFile, boolean mostrarSidebar) {
        if (mainController != null) {
            mainController.showScreen(fxmlFile, cssFile, mostrarSidebar);
        } else {
            System.err.println("❌ ERROR: MainController es null.");
        }
    }
}
