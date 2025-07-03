package com.echo.echoband.controller;

import com.echo.echoband.connection.Connector;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.prefs.Preferences;


public class UserProfileController {

    @FXML private Label labelNomUsuario, labelNomReal1, labelNomReal2, labelNivel, labelCantidadLogros;
    @FXML private GridPane gridLogros;
    public int idDatos;
    public String nomUsuario;
    public String nomReal;
    public String apPat;
    public String apMat;
    public String correo;
    public String contrasena;

    @FXML
    public void initialize() {
        obtenerDatosGuardados();
        labelNomReal2.setText(nomUsuario);
        int puntos = obtenerPuntosDesdeBD(idDatos);
        labelNomReal1.setText( puntos+" puntos");
        labelNomUsuario.setText(nomUsuario);
        if (puntos < 50){
            labelNivel.setText("Novato");
        } else if (puntos < 100){
            labelNivel.setText("Principiante");
        } else if (puntos < 150){
            labelNivel.setText("Intermedio");
        } else if (puntos < 200){
            labelNivel.setText("Avanzado");
        } else if (puntos < 300){
            labelNivel.setText("Maestro");
        } else if (puntos < 400) {
            labelNivel.setText("Profesional");
        } else if (puntos < 500){
            labelNivel.setText("Gran Maestro");
        } else if (puntos < 600){
            labelNivel.setText("Experto");
        }
        colorearLogrosDesbloqueados();
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

    public int obtenerPuntosDesdeBD(int id) {
        int puntos = 0;
        try {
            Connector connector = new Connector();
            Connection cn = connector.conectar();

            String consulta = "SELECT puntos FROM datos_perso WHERE id_datos = ?";
            PreparedStatement ps = cn.prepareStatement(consulta);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                puntos = rs.getInt("puntos");
            }

            connector.cerrarConexion();
        } catch (Exception e) {
            System.err.println("❌ Error al obtener puntos: " + e.getMessage());
        }
        return puntos;
    }

    private void colorearLogrosDesbloqueados() {
        try {
            Connector connector = new Connector();
            Connection conn = connector.conectar();

            PreparedStatement stmt = conn.prepareStatement("SELECT titulo FROM logro WHERE id_datos = ?");
            stmt.setInt(1, idDatos);
            ResultSet rs = stmt.executeQuery();

            List<String> logrosUsuario = new ArrayList<>();
            while (rs.next()) {
                logrosUsuario.add(rs.getString("titulo").trim());
            }
            int cantidadLogros = logrosUsuario.size();
            labelCantidadLogros.setText(cantidadLogros + "/9 LOGROS");

            // Buscar todos los labels dentro del GridPane
            gridLogros.getChildren().forEach(node -> {
                if (node instanceof HBox hbox && !hbox.getChildren().isEmpty() && hbox.getChildren().get(0) instanceof Label label) {
                    String titulo = label.getText().trim();
                    if (logrosUsuario.contains(titulo)) {
                        hbox.setStyle("-fx-background-color: #FFFACD; -fx-background-radius: 10;");
                        label.setStyle(label.getStyle() + "; -fx-text-fill: black;");
                    }
                }
            });

            connector.cerrarConexion();
        } catch (Exception e) {
            System.err.println("❌ Error al cargar logros: " + e.getMessage());
        }
    }
}
