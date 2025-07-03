package com.echo.echoband.controller;

import com.echo.echoband.connection.Connector;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.prefs.Preferences;

public class GameWonController {

    @FXML private MFXButton btnInicio;
    @FXML private MFXButton btnJugar;
    @FXML private Label labelConcentracionMax, labelConcentracionMin, labelPuntos, labelConcentracionPromedio, labelEstrellas;
    public String ultimoEjercicio;
    public float reaccion;

    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void initialize() {
        try {
            simulateAndSaveData();
        } catch (SQLException e) {
            System.err.println("❌ Error en simulateAndSaveData(): " + e.getMessage());
            e.printStackTrace();
        }

        if (btnInicio != null) {
            btnInicio.setOnAction(e -> cambiarVista("/com/echo/echoband/statisticsView.fxml", "/com/echo/echoband/statisticsStyle.css", true));
        }
        if (btnJugar != null) {
            btnJugar.setOnAction(e -> cambiarVista("/com/echo/echoband/trainingView.fxml", "/com/echo/echoband/trainingStyle.css", true));
        }
    }

    private void cambiarVista(String fxmlFile, String cssFile, boolean mostrarSidebar) {
        if (mainController != null) {
            mainController.showScreen(fxmlFile, cssFile, mostrarSidebar);
        } else {
            System.err.println("❌ ERROR: MainController es null.");
        }
    }
    private void simulateAndSaveData() throws SQLException {
        List<Integer> concentraciones = new ArrayList<>();
        Random random = new Random();

        // Rango mínimo fijo desde 43 hasta 73
        int minBase = 43 + random.nextInt(31); // 43 a 73

        // Máximo hasta 93, mínimo +10 de diferencia
        int maxBase = Math.min(minBase + 10 + random.nextInt(11), 93); // min+10 a min+20, máximo 93
        while (maxBase < minBase){
            maxBase = Math.min(minBase + 10 + random.nextInt(11), 93); // min+10 a min+20, máximo 93
        }

        for (int i = 0; i < 60; i++) {
            concentraciones.add(minBase + random.nextInt(maxBase - minBase + 1));
        }



        int promedio = (int) concentraciones.stream().mapToInt(Integer::intValue).average().orElse(0);

        Preferences prefs = Preferences.userRoot().node("com.echo.echoband");
        int idDatos = prefs.getInt("id_datos", -1);
        if (idDatos == -1) {
            System.err.println("⚠️ No se encontró el ID del usuario en Preferences.");
            return;
        }

        Connector connector = new Connector();
        Connection conn = connector.conectar();

        if (conn != null) {
            try {
                ultimoEjercicio = prefs.get("ultimo_ejercicio", "");
                reaccion = prefs.getFloat("reaccion", 0f);

                // Guardar entrenamiento
                String sqlEntrenamiento = "INSERT INTO entrenamiento (id_datos, fecha, concentracion_promedio, tipo_ejercicio) VALUES (?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(sqlEntrenamiento)) {
                    stmt.setInt(1, idDatos);
                    stmt.setDate(2, java.sql.Date.valueOf(LocalDate.now()));
                    stmt.setInt(3, promedio);
                    stmt.setString(4, ultimoEjercicio);
                    stmt.executeUpdate();
                }

                // Reglas de logros
                List<String> logrosAInsertar = new ArrayList<>();
                if (ultimoEjercicio.equals("Memorización")) {
                    logrosAInsertar.add("Memorizando...");
                    logrosAInsertar.add("Primera Vez");
                } else if (ultimoEjercicio.equals("Rapidez")) {
                    logrosAInsertar.add("Listo para F1");
                    if (reaccion < 0.4f) {
                        logrosAInsertar.add("¿Verstappen?");
                    }
                } else if (ultimoEjercicio.equals("Retención")) {
                    logrosAInsertar.add("Mejorando...");
                }

                for (String logro : logrosAInsertar) {
                    if (!logroYaRegistrado(conn, idDatos, logro)) {
                        try (PreparedStatement stmt2 = conn.prepareStatement("INSERT INTO logro (id_datos, titulo) VALUES (?, ?)")) {
                            stmt2.setInt(1, idDatos);
                            stmt2.setString(2, logro);
                            stmt2.executeUpdate();
                            System.out.println("🏆 Logro registrado: " + logro);
                        }
                    } else {
                        System.out.println("🔁 Logro ya existente, no se insertó: " + logro);
                    }
                }

            } catch (SQLException e) {
                System.err.println("❌ Error al insertar datos: " + e.getMessage());
            }
        }

        // Calcular puntos base
        int puntosGanados = 0;
        String estrellas = "";

        if (promedio < 50) {
            puntosGanados = 10;
            estrellas = "2 estrella";
        } else if (promedio < 70) {
            puntosGanados = 20;
            estrellas = "3 estrellas";
        } else if (promedio < 85) {
            puntosGanados = 30;
            estrellas = "4 estrellas";
        } else {
            puntosGanados = 40;
            estrellas = "5 estrellas";
        }

        // Reacción especial para "Verstappen"
        if (ultimoEjercicio.equals("Rapidez") && reaccion < 0.4f) {
            puntosGanados += 20;
        }

        // Actualizar puntos en la BD
        try (PreparedStatement ps = conn.prepareStatement("UPDATE datos_perso SET puntos = puntos + ? WHERE id_datos = ?")) {
            ps.setInt(1, puntosGanados);
            ps.setInt(2, idDatos);
            ps.executeUpdate();

        }
        connector.cerrarConexion();

        // Mostrar en interfaz
        labelConcentracionMin.setText("- Nivel máximo de concentración: " + Collections.max(concentraciones));
        labelConcentracionMax.setText("- Nivel mínimo de concentración:" + Collections.min(concentraciones));
        labelConcentracionPromedio.setText("Concentración promedio: " + promedio);
        labelPuntos.setText("¡+" + puntosGanados + " puntos a tu perfil!");
        labelEstrellas.setText(estrellas);

    }

    private boolean logroYaRegistrado(Connection conn, int idUsuario, String titulo) {
        String sql = "SELECT COUNT(*) FROM logro WHERE id_datos = ? AND titulo = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setString(2, titulo);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.err.println("❌ Error verificando logro: " + e.getMessage());
        }
        return false;
    }


}
