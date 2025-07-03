// FriendsController.java
package com.echo.echoband.controller;

import com.echo.echoband.connection.Connector;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.sql.*;
import java.util.prefs.Preferences;

public class FriendsController {
    @FXML private MFXTextField fieldBuscarUsuario;
    @FXML private MFXButton btnBuscarUsuario;
    @FXML private VBox vboxResultadosBusqueda;
    @FXML private VBox vboxSolicitudes;
    @FXML private VBox vboxAmigos;
    @FXML private Label labelNoSolicitudes;
    @FXML private Label labelNoAmigos;

    private int idUsuario;

    @FXML
    public void initialize() {
        Preferences prefs = Preferences.userRoot().node("com.echo.echoband");
        idUsuario = prefs.getInt("id_datos", -1);

        btnBuscarUsuario.setOnAction(e -> buscarUsuarios());
        cargarSolicitudesPendientes();
        cargarAmigos();
    }

    private void buscarUsuarios() {
        String buscar = fieldBuscarUsuario.getText().trim();
        vboxResultadosBusqueda.getChildren().clear();

        try (Connection conn = new Connector().conectar()) {
            String query = "SELECT id_datos, nom_usuario FROM datos_perso WHERE nom_usuario LIKE ? AND id_datos != ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "%" + buscar + "%");
            ps.setInt(2, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int idEncontrado = rs.getInt("id_datos");
                String nombre = rs.getString("nom_usuario");

                Label lbl = new Label(nombre);
                HBox hbox = new HBox(10);
                hbox.getChildren().add(lbl);

                if (yaEsAmigo(conn, idEncontrado)) {
                    Label msg = new Label("Ya es tu amigo!");
                    msg.setStyle("-fx-text-fill: #999999; -fx-font-style: italic;");
                    hbox.getChildren().add(msg);
                } else if (yaMeEnvioSolicitud(conn, idEncontrado)) {
                    Label msg = new Label("Ya te envió una solicitud");
                    msg.setStyle("-fx-text-fill: #999999; -fx-font-style: italic;");
                    hbox.getChildren().add(msg);
                } else if (yaLeEnvieSolicitud(conn, idEncontrado)) {
                    Label msg = new Label("Ya le enviaste una solicitud");
                    msg.setStyle("-fx-text-fill: #999999; -fx-font-style: italic;");
                    hbox.getChildren().add(msg);
                } else {
                    MFXButton btn = new MFXButton("Enviar solicitud");
                    btn.setStyle("-fx-background-color: #76B5CF; -fx-text-fill: white;");
                    btn.setOnAction(e -> enviarSolicitud(idEncontrado));
                    hbox.getChildren().add(btn);
                }

                vboxResultadosBusqueda.getChildren().add(hbox);
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void enviarSolicitud(int idReceptor) {
        try (Connection conn = new Connector().conectar()) {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO solicitud (id_emisor, id_receptor) VALUES (?, ?)");
            ps.setInt(1, idUsuario);
            ps.setInt(2, idReceptor);
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void cargarSolicitudesPendientes() {
        vboxSolicitudes.getChildren().clear();
        labelNoSolicitudes.setVisible(true);

        try (Connection conn = new Connector().conectar()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT s.id_emisor, d.nom_usuario, d.puntos FROM solicitud s JOIN datos_perso d ON s.id_emisor = d.id_datos WHERE s.id_receptor = ? AND s.estado = 'pendiente'");
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                labelNoSolicitudes.setVisible(false);
                int idEmisor = rs.getInt("id_emisor");
                String nombre = rs.getString("nom_usuario");
                int puntos = rs.getInt("puntos");
                String nivel = obtenerNivel(puntos);

                Label lbl = new Label(nombre + " - " + nivel + " - " + puntos + " pts");
                MFXButton btn = new MFXButton("Aceptar");
                btn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                btn.setOnAction(e -> aceptarSolicitud(idEmisor));

                HBox hbox = new HBox(10, lbl, btn);
                vboxSolicitudes.getChildren().add(hbox);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void aceptarSolicitud(int idEmisor) {
        try (Connection conn = new Connector().conectar()) {
            PreparedStatement ps1 = conn.prepareStatement("UPDATE solicitud SET estado = 'aceptada' WHERE id_emisor = ? AND id_receptor = ?");
            ps1.setInt(1, idEmisor);
            ps1.setInt(2, idUsuario);
            ps1.executeUpdate();

            PreparedStatement ps2 = conn.prepareStatement("INSERT INTO amigo (id_usuario_1, id_usuario_2) VALUES (?, ?), (?, ?)");
            ps2.setInt(1, idUsuario);
            ps2.setInt(2, idEmisor);
            ps2.setInt(3, idEmisor);
            ps2.setInt(4, idUsuario);
            ps2.executeUpdate();

            cargarSolicitudesPendientes();
            cargarAmigos();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarAmigos() {
        vboxAmigos.getChildren().clear();
        labelNoAmigos.setVisible(true);

        try (Connection conn = new Connector().conectar()) {
            PreparedStatement ps = conn.prepareStatement(
                    "SELECT d.nom_usuario, d.puntos FROM amigo a JOIN datos_perso d ON a.id_usuario_2 = d.id_datos WHERE a.id_usuario_1 = ?");
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            int misPuntos = obtenerPuntos(idUsuario);

            while (rs.next()) {
                labelNoAmigos.setVisible(false);
                String nombre = rs.getString("nom_usuario");
                int puntosAmigo = rs.getInt("puntos");
                String nivel = obtenerNivel(puntosAmigo);
                int diferencia = puntosAmigo - misPuntos;

                String comparacion;
                if (diferencia > 0) {
                    comparacion = diferencia + " puntos por encima de ti";
                } else if (diferencia < 0) {
                    comparacion = (-diferencia) + " puntos por debajo de ti";
                } else {
                    comparacion = "Tienen los mismos puntos";
                }

                Label lbl = new Label(nombre + " - " + nivel + " - " + puntosAmigo + " pts - " + comparacion);
                vboxAmigos.getChildren().add(lbl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int obtenerPuntos(int id) {
        try (Connection conn = new Connector().conectar()) {
            PreparedStatement ps = conn.prepareStatement("SELECT puntos FROM datos_perso WHERE id_datos = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("puntos");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
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

    private boolean yaHaySolicitud(Connection conn, int idOtroUsuario) throws SQLException {
        String sql = """
        SELECT COUNT(*) FROM solicitud 
        WHERE ((id_emisor = ? AND id_receptor = ?) OR (id_emisor = ? AND id_receptor = ?))
        AND estado = 'pendiente'
        """;
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idOtroUsuario);
            stmt.setInt(3, idOtroUsuario);
            stmt.setInt(4, idUsuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private boolean yaEsAmigo(Connection conn, int idOtroUsuario) throws SQLException {
        String sql = "SELECT COUNT(*) FROM amigo WHERE id_usuario_1 = ? AND id_usuario_2 = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idOtroUsuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private boolean yaLeEnvieSolicitud(Connection conn, int idOtro) throws SQLException {
        String sql = "SELECT COUNT(*) FROM solicitud WHERE id_emisor = ? AND id_receptor = ? AND estado = 'pendiente'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            stmt.setInt(2, idOtro);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }

    private boolean yaMeEnvioSolicitud(Connection conn, int idOtro) throws SQLException {
        String sql = "SELECT COUNT(*) FROM solicitud WHERE id_emisor = ? AND id_receptor = ? AND estado = 'pendiente'";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, idOtro);
            stmt.setInt(2, idUsuario);
            ResultSet rs = stmt.executeQuery();
            return rs.next() && rs.getInt(1) > 0;
        }
    }


}