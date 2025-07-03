package com.echo.echoband.controller;

import com.echo.echoband.connection.Connector;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;

import javax.swing.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoginController implements Initializable {

    private Connector connector;
    private Connection cn;

    @FXML private Text txtcrear;
    @FXML private MFXTextField fieldusuario;
    @FXML private MFXPasswordField fieldcontrasena;
    @FXML private Label labelusuario;
    @FXML private Label labelcontrasena;
    @FXML private MFXButton btnIniciar;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    private void cambiarVista(String fxmlFile, String cssFile, boolean mostrarSidebar) {
        if (mainController != null) {
            mainController.showScreen(fxmlFile, cssFile, mostrarSidebar);
        } else {
            System.err.println("❌ ERROR: MainController es null.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Activar botón solo si hay texto
        BooleanBinding camposValidos = fieldusuario.textProperty().isNotEmpty()
                .and(fieldcontrasena.textProperty().isNotEmpty());
        btnIniciar.disableProperty().bind(camposValidos.not());

        // Acción para crear cuenta
        if (txtcrear != null) {
            txtcrear.setOnMouseClicked(e -> cambiarVista(
                    "/com/echo/echoband/signUpView.fxml",
                    "/com/echo/echoband/signUpStyle.css",
                    false
            ));
        }

        // Acción de inicio de sesión
        btnIniciar.setOnAction(e -> ingresar());
    }

    @FXML
    public void ingresar() {
        String usuario = fieldusuario.getText();
        String contrasena = fieldcontrasena.getText();

        if (usuario.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Debe completar los campos.");
            return;
        }

        try {
            connector = new Connector();
            cn = connector.conectar();

            String consulta = "SELECT nom_real, ap_pat, ap_mat, nom_usuario, id_datos, correo, contraseña " +
                    "FROM datos_perso WHERE nom_usuario = ? AND contraseña = ?;";
            PreparedStatement ps = cn.prepareStatement(consulta);
            ps.setString(1, usuario);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String nomReal = rs.getString("nom_real");
                String apPat = rs.getString("ap_pat");
                String apMat = rs.getString("ap_mat");
                String nomUsuario = rs.getString("nom_usuario");
                String correo = rs.getString("correo");
                int idDatos = rs.getInt("id_datos");

                System.out.println("Hola de nuevo " + nomUsuario);
                System.out.println("Perfil de " + nomReal + " " + apPat + " " + apMat);
                System.out.println("ID del usuario: " + idDatos);
                System.out.println("Correo: " + correo);
                System.out.println("Contraseña " + contrasena);

                Preferences prefs = Preferences.userRoot().node("com.echo.echoband");
                prefs.putInt("id_datos", idDatos);
                prefs.put("nom_usuario", nomUsuario);
                prefs.put("nom_real", nomReal);
                prefs.put("ap_pat", apPat);
                prefs.put("ap_mat", apMat);
                prefs.put("correo", correo);
                prefs.put("contrasena", contrasena);

                // Justo después de cambiar la vista
                cambiarVista("/com/echo/echoband/trainingView.fxml", "/com/echo/echoband/trainingStyle.css", true);


                // Luego de cambiar vista, fuerza actualización del sidebar
                if (mainController != null && mainController.getSidebarController() != null) {
                    mainController.getSidebarController().actualizarDatosVisuales();
                }
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (connector != null) {
                connector.cerrarConexion();
            }
        }
    }
}
