package com.echo.echoband.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.chart.*;

public class StatisticsController {

    @FXML private LineChart<Number, Number> lineChart;
    @FXML private NumberAxis xAxis;
    @FXML private NumberAxis yAxis;
    @FXML private BarChart<String, Number> barChart;
    @FXML private CategoryAxis xAxisBar;

    @FXML
    public void initialize() {
        int[] concentracion = {40, 24, 68, 53, 43, 68, 80, 34, 46, 46, 76, 68, 98, 68, 68, 67, 87, 68};
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
        seriesBar1.getData().add(new XYChart.Data<>("Focalización Visual", 50.0));

        XYChart.Series<String, Number> seriesBar2 = new XYChart.Series<>();
        seriesBar2.setName("Desafíos con amigos");
        seriesBar2.getData().add(new XYChart.Data<>("Desafíos con amigos", 0.0));

        XYChart.Series<String, Number> seriesBar3 = new XYChart.Series<>();
        seriesBar3.setName("Focalización general");
        seriesBar3.getData().add(new XYChart.Data<>("Focalización general", 30.0));

        XYChart.Series<String, Number> seriesBar4 = new XYChart.Series<>();
        seriesBar4.setName("Concentración real");
        seriesBar4.getData().add(new XYChart.Data<>("Concentración real", 67.0));

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
