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
    @FXML private MFXButton botoniniciar;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BooleanBinding camposValidos = fieldusuario.textProperty().isNotEmpty()
                .and(fieldcontrasena.textProperty().isNotEmpty());

        botoniniciar.disableProperty().bind(camposValidos.not());
    }

    @FXML
    public void irAMenu() {
        try {
            Stage stage = (Stage) botoniniciar.getScene().getWindow();
            LogIn app = new LogIn();
            stage.setTitle("Entrenamiento");

            app.cambiarEscena(stage, "/com/echo/echoband/trainingView.fxml");

            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(LogIn.class.getResource("/com/echo/echoband/trainingStyle.css").toExternalForm());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar trainingView.fxml");
        }
    }

    public void irASignUp() throws IOException {
        Stage stage = (Stage) txtcrear.getScene().getWindow();
        LogIn app = new LogIn();
        app.cambiarEscena(stage, "signUpView.fxml");
        stage.setTitle("Sign Up");
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

                    irAMenu(); // Si el login es exitoso, ir al menú
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
