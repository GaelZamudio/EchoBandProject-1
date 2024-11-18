package com.echo.echoband.controller;

import com.echo.echoband.Friends;
import com.echo.echoband.Statistics;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.stage.Stage;

import java.io.IOException;

public class StatisticsController {

    @FXML private MFXButton entrenar;
    @FXML private MFXButton estadisticas;
    @FXML private MFXButton amigos;
    @FXML private MFXButton perfil;
    @FXML private MFXButton liga;
    @FXML private MFXButton config;
    @FXML private MFXButton cerrar;

    @FXML private LineChart<Number, Number> lineChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxisBar;

    public void irAConfig() throws IOException {
        Stage stage = (Stage) config.getScene().getWindow();
        Statistics app = new Statistics();
        app.cambiarEscena(stage, "configurationView.fxml");
        stage.setTitle("Configuración");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Statistics.class.getResource("/com/echo/echoband/configurationStyle.css").toExternalForm());
    }

    public void irAAmigos() throws IOException {
        Stage stage = (Stage) amigos.getScene().getWindow();
        Statistics app = new Statistics();
        app.cambiarEscena(stage, "friendsView.fxml");
        stage.setTitle("Amigos");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Statistics.class.getResource("/com/echo/echoband/friendsStyle.css").toExternalForm());
    }

    public void irALiga() throws IOException {
        Stage stage = (Stage) liga.getScene().getWindow();
        Statistics app = new Statistics();
        app.cambiarEscena(stage, "leagueView.fxml");
        stage.setTitle("Liga");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Statistics.class.getResource("/com/echo/echoband/leagueStyle.css").toExternalForm());
    }

    public void irAEntrenar() throws IOException {
        Stage stage = (Stage) entrenar.getScene().getWindow();
        Statistics app = new Statistics();
        app.cambiarEscena(stage, "trainingView.fxml");
        stage.setTitle("Entrenamiento");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Statistics.class.getResource("/com/echo/echoband/trainingStyle.css").toExternalForm());
    }

    public void irAEstadisticas() throws IOException {
        Stage stage = (Stage) estadisticas.getScene().getWindow();
        Statistics app = new Statistics();
        app.cambiarEscena(stage, "statisticsView.fxml");
        stage.setTitle("Estadísticas");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Statistics.class.getResource("/com/echo/echoband/staticsStyle.css").toExternalForm());
    }

    public void irAPerfil() throws IOException {
        Stage stage = (Stage) perfil.getScene().getWindow();
        Statistics app = new Statistics();
        app.cambiarEscena(stage, "userProfileView.fxml");
        stage.setTitle("Perfil de Usuario");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Statistics.class.getResource("/com/echo/echoband/userProfileStyle.css").toExternalForm());
    }

    public void irALogOut() throws IOException {
        Platform.exit();
    }

    @FXML
    public void initialize() {
        int[] concentracion = {40, 24, 70, 53, 43, 23, 80, 34, 46, 46, 76, 68, 98, 100, 32, 67, 87, 89};
        double promedio = calcularPromedio(concentracion);

        xAxis.setLabel("Tiempo (segundos)");
        yAxis.setLabel("Nivel de Concentración");

        XYChart.Series<Number, Number> series = new XYChart.Series<>();
        series.setName("Concentración");

        for (int i = 0; i < concentracion.length; i++) {
            series.getData().add(new XYChart.Data<>(i + 1, concentracion[i]));
        }

        lineChart.getData().add(series);

        XYChart.Series<Number, Number> promedioSeries = new XYChart.Series<>();
        promedioSeries.setName("Promedio");

        for (int i = 1; i <= concentracion.length; i++) {
            promedioSeries.getData().add(new XYChart.Data<>(i, promedio));
        }

        lineChart.getData().add(promedioSeries);

        XYChart.Series<String, Number> seriesBar1 = new XYChart.Series<>();
        seriesBar1.setName("Focalización Visual");
        seriesBar1.getData().add(new XYChart.Data<>("Focalización Visual", 81.57));

        XYChart.Series<String, Number> seriesBar2 = new XYChart.Series<>();
        seriesBar2.setName("Desafíos con amigos");
        seriesBar2.getData().add(new XYChart.Data<>("Desafíos con amigos", 63.25));

        XYChart.Series<String, Number> seriesBar3 = new XYChart.Series<>();
        seriesBar3.setName("Focalización general");
        seriesBar3.getData().add(new XYChart.Data<>("Focalización general", 52.95));

        XYChart.Series<String, Number> seriesBar4 = new XYChart.Series<>();
        seriesBar4.setName("Concentración real");
        seriesBar4.getData().add(new XYChart.Data<>("Concentración real", 47.29));

        barChart.getData().addAll(seriesBar1, seriesBar2, seriesBar3, seriesBar4);

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
}
