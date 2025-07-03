// LeagueController.java
package com.echo.echoband.controller;

import com.echo.echoband.connection.Connector;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;
import java.util.prefs.Preferences;

public class LeagueController {

    @FXML private VBox vboxTop1;
    @FXML private GridPane gridRanking;

    private final int META = 500;

    @FXML
    public void initialize() {
        Preferences prefs = Preferences.userRoot().node("com.echo.echoband");
        int idUsuario = prefs.getInt("id_datos", -1);

        List<Jugador> ranking = new ArrayList<>();

        try (Connection conn = new Connector().conectar()) {
            String sql = "SELECT d.id_datos, d.nom_usuario, d.puntos FROM amigo a JOIN datos_perso d ON a.id_usuario_2 = d.id_datos WHERE a.id_usuario_1 = ? UNION SELECT id_datos, nom_usuario, puntos FROM datos_perso WHERE id_datos = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ps.setInt(2, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id_datos");
                String nombre = rs.getString("nom_usuario");
                int puntos = rs.getInt("puntos");
                String nivel = obtenerNivel(puntos);
                double progreso = Math.min((double) puntos / META, 1.0);
                ranking.add(new Jugador(id, nombre, nivel, puntos, progreso));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        ranking.sort((a, b) -> Integer.compare(b.puntos, a.puntos));

        mostrarTop1(ranking.get(0));
        mostrarResto(ranking.subList(1, Math.min(ranking.size(), 10)));
    }

    private void mostrarTop1(Jugador jugador) {
        vboxTop1.getChildren().clear();

        HBox card = crearTarjetaJugador(jugador, "1°", true);
        card.getStyleClass().add("primer-lugar");
        vboxTop1.getChildren().add(card);
    }

    private void mostrarResto(List<Jugador> jugadores) {
        gridRanking.getChildren().clear();

        for (int i = 0; i < jugadores.size(); i++) {
            Jugador jugador = jugadores.get(i);
            String lugar = (i + 2) + "°";
            HBox card = crearTarjetaJugador(jugador, lugar, false);

            int col = i % 2;
            int row = i / 2;
            gridRanking.add(card, col, row);
        }
    }

    private HBox crearTarjetaJugador(Jugador jugador, String lugar, boolean esTop1) {
        Label lblLugar = new Label(lugar);
        lblLugar.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");

        ImageView avatar = new ImageView(new Image("/amigosBarraNav.png"));
        avatar.setFitWidth(60);
        avatar.setFitHeight(60);
        avatar.getStyleClass().add("profile-picture");

        VBox datos = new VBox(5,
                new Label(jugador.nombre),
                new Label(jugador.nivel),
                new Label(jugador.puntos + " Puntos")
        );
        datos.getChildren().forEach(node -> node.getStyleClass().add("sub-datos"));
        datos.getChildren().get(0).getStyleClass().add("name-label");
        datos.getChildren().get(2).getStyleClass().add("points-label");

        ProgressBar barra = new ProgressBar(jugador.progreso);
        barra.setStyle("-fx-accent: #28A745;");
        barra.getStyleClass().add(esTop1 ? "barra-winner" : "progress-bar");

        VBox barraYpuntos = new VBox(5, barra);
        barraYpuntos.setMinWidth(150);

        HBox card = new HBox(15, lblLugar, avatar, datos, barraYpuntos);
        card.getStyleClass().add("ranking-item");

        return card;
    }

    private String obtenerNivel(int puntos) {
        if (puntos < 50) return "Novato";
        if (puntos < 100) return "Principiante";
        if (puntos < 150) return "Intermedio";
        if (puntos < 200) return "Avanzado";
        if (puntos < 300) return "Maestro";
        if (puntos < 400) return "Profesional";
        if (puntos < 500) return "Gran Maestro";
        return "Experto";
    }

    private static class Jugador {
        int id;
        String nombre;
        String nivel;
        int puntos;
        double progreso;

        Jugador(int id, String nombre, String nivel, int puntos, double progreso) {
            this.id = id;
            this.nombre = nombre;
            this.nivel = nivel;
            this.puntos = puntos;
            this.progreso = progreso;
        }
    }
}
