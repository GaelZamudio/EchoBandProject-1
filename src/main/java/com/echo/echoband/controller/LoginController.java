package com.echo.echoband.controller;

import com.echo.echoband.LogIn;
import com.echo.echoband.connection.Connector;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.beans.binding.BooleanBinding;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

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

        if (txtcrear != null) {
            txtcrear.setOnMouseClicked(e -> cambiarVista("/com/echo/echoband/signUpView.fxml", "/com/echo/echoband/signUpStyle.css", false));
        }
        if (btnIniciar != null) {
            btnIniciar.setOnAction(e -> cambiarVista("/com/echo/echoband/trainingView.fxml", "/com/echo/echoband/trainingStyle.css", true));
        }

        BooleanBinding camposValidos = fieldusuario.textProperty().isNotEmpty()
                .and(fieldcontrasena.textProperty().isNotEmpty());

        btnIniciar.disableProperty().bind(camposValidos.not());
    }

    //Métodos para la BD
    @FXML
    public void ingresar() {
        String usuario = fieldusuario.getText();
        String contrasena = fieldcontrasena.getText();

        if (!usuario.isEmpty() && !contrasena.isEmpty()) {
            try {
                connector = new Connector();
                cn = connector.conectar();

                // Usamos PreparedStatement para evitar inyección SQL
                String consulta = "SELECT nom_real, ap_pat, nom_usuario, id_datos " +
                        "FROM datos_perso " +
                        "WHERE nom_usuario = ? AND contraseña = ?;";
                PreparedStatement ps = cn.prepareStatement(consulta);
                ps.setString(1, usuario);
                ps.setString(2, contrasena);

                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    String nomReal = rs.getString("nom_real");
                    String apPat = rs.getString("ap_pat");
                    String nomUsuario = rs.getString("nom_usuario");
                    int idDatos = rs.getInt("id_datos");

                    System.out.println("Hola de nuevo " + nomUsuario);
                    System.out.println("Perfil de " + nomReal + " " + apPat);
                    System.out.println("ID del usuario: " + idDatos);

                    if (btnIniciar != null) {
                        btnIniciar.setOnAction(e -> cambiarVista("/com/echo/echoband/trainingView.fxml", "/com/echo/echoband/trainingStyle.css", true));
                    } // Si el login es exitoso, ir al menú
                } else {
                    JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al iniciar sesión: " + e.getMessage());
                e.printStackTrace();
            } finally {
                connector.cerrarConexion();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Debe completar los campos.");
        }
    }
}
