package com.echo.echoband.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

import java.util.prefs.Preferences;
import javafx.scene.control.Label;
import com.echo.echoband.connection.Connector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class StatisticsController {

    @FXML private LineChart<Number, Number> lineChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxisBar;
    @FXML private Label labelNomReal, labelNomUsuario;
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
        labelNomReal.setText(nomReal + " " + apPat);
        labelNomUsuario.setText(nomUsuario);

        List<Integer> concentraciones = new ArrayList<>();
        Map<String, Integer> conteoEjercicios = new HashMap<>();

        try {
            Connector connector = new Connector();
            Connection conn = connector.conectar();

            PreparedStatement ps = conn.prepareStatement("SELECT tipo_ejercicio, concentracion_promedio FROM entrenamiento WHERE id_datos = ?");
            ps.setInt(1, idDatos);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int conc = rs.getInt("concentracion_promedio");
                String tipo = rs.getString("tipo_ejercicio");

                concentraciones.add(conc);
                conteoEjercicios.put(tipo, conteoEjercicios.getOrDefault(tipo, 0) + 1);
            }

            connector.cerrarConexion();
        } catch (SQLException e) {
            System.err.println("❌ Error cargando entrenamientos: " + e.getMessage());
            return;
        }

        if (concentraciones.isEmpty()) {
            System.out.println("⚠️ Sin entrenamientos registrados.");
            return;
        }

        double promedio = concentraciones.stream().mapToInt(Integer::intValue).average().orElse(0);

        // CONFIGURAR EJE X CON ENTEROS Y LÍMITE FIJO
        xAxis.setLabel("Entrenamiento #");
        yAxis.setLabel("Nivel de Concentración");
        xAxis.setAutoRanging(false);
        xAxis.setTickUnit(1);
        xAxis.setLowerBound(1);
        xAxis.setUpperBound(concentraciones.size());

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Concentración");

        for (int i = 0; i < concentraciones.size(); i++) {
            series.getData().add(new XYChart.Data<>(i + 1, concentraciones.get(i)));
        }

        XYChart.Series<Number, Number> promedioSeries = new XYChart.Series<>();
        promedioSeries.setName("Promedio");

        for (int i = 1; i <= concentraciones.size(); i++) {
            promedioSeries.getData().add(new XYChart.Data<>(i, promedio));
        }

        lineChart.getData().addAll(series, promedioSeries);

        // GRÁFICA DE BARRAS - CÁLCULO DE PORCENTAJES
        barChart.getData().clear();
        int totalEjercicios = conteoEjercicios.values().stream().mapToInt(Integer::intValue).sum();

        for (Map.Entry<String, Integer> entry : conteoEjercicios.entrySet()) {
            String tipo = entry.getKey();
            int cantidad = entry.getValue();
            double porcentaje = (cantidad * 100.0) / totalEjercicios;

            XYChart.Series<String, Number> barSeries = new XYChart.Series<>();
            barSeries.setName(tipo);
            barSeries.getData().add(new XYChart.Data<>(tipo, porcentaje));
            barChart.getData().add(barSeries);
        }

        Platform.runLater(() -> {
            barChart.getScene().getStylesheets().add(getClass().getResource("statisticsStyle.css").toExternalForm());
        });

        barChart.setBarGap(-80);
        barChart.setCategoryGap(0);
        barChart.setMinWidth(675);
        barChart.setMaxWidth(675);
    }



    private double calcularPromedio(int[] concentracion) {
        double suma = 0;
        for (int valor : concentracion) {
            suma += valor;
        }
        return suma / concentracion.length;
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
}
