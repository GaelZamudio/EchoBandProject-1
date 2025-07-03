package com.echo.echoband.controller;

import com.echo.echoband.Configuration;
import com.echo.echoband.Statistics;
import com.echo.echoband.connection.Connector;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import java.util.prefs.Preferences;
import java.util.regex.Pattern;

import static io.github.palexdev.materialfx.utils.StringUtils.containsAny;

public class ConfigurationController implements Initializable {

    private static final PseudoClass pseudoclaseValidacion = PseudoClass.getPseudoClass("invalido");
    private static final String[] mayusculas = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");
    private static final String[] minusculas = "a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" ");
    private static final String[] numeros = "0 1 2 3 4 5 6 7 8 9".split(" ");
    private static final String[] especiales = "! @ # & ( ) – [ { } ]: ; ' , ? / * ~ $ ^ + = < > -".split(" ");

    @FXML private MFXTextField fieldusuario;
    @FXML private MFXPasswordField fieldcontrasena;
    @FXML private MFXTextField fieldcorreo;
    @FXML private Label labelusuario, labelcontrasena, labelcorreo, labelNomUsuario, labelNomReal;
    public int idDatos;
    public String nomUsuario;
    public String nomReal;
    public String apPat;
    public String apMat;
    public String correo;
    public String contrasena;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obtenerDatosGuardados();
        fieldusuario.setPromptText(nomUsuario);
        fieldcontrasena.setPromptText(contrasena);
        fieldcorreo.setPromptText(correo);
        labelNomReal.setText(nomReal + " " + apPat);
        labelNomUsuario.setText(nomUsuario);


        // Usuario
        Constraint longitudNomRestriccion = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("El nombre debe tener al menos 5 caracteres")
                .setCondition(fieldusuario.textProperty().length().greaterThanOrEqualTo(5))
                .get();

        Constraint noCaracterRestriccion = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("El nombre no puede contener caracteres especiales")
                .setCondition(Bindings.createBooleanBinding(
                        () -> !containsAny(fieldusuario.getText(), "", especiales),
                        fieldusuario.textProperty()))
                .get();

// Correo
        Constraint formatoRestriccion = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("El campo debe tener formato de correo electrónico")
                .setCondition(fieldcorreo.textProperty().isNotEmpty().and(Bindings.createBooleanBinding(() -> {
                    String email = fieldcorreo.getText();
                    return email.matches("^[\\w.%+-]+@[\\w.-]+\\.com$") && !email.matches(".*\\.com\\.com$");
                }, fieldcorreo.textProperty())))
                .get();

// Contraseña
        Constraint longitudRestriccion = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("La contraseña debe tener al menos 8 caracteres")
                .setCondition(fieldcontrasena.textProperty().length().greaterThanOrEqualTo(8))
                .get();

        Constraint numeroRestriccion = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("La contraseña debe contener al menos un dígito")
                .setCondition(Bindings.createBooleanBinding(
                        () -> containsAny(fieldcontrasena.getText(), "", numeros),
                        fieldcontrasena.textProperty()))
                .get();

        Constraint letraRestriccion = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("La contraseña debe contener al menos un carácter en minúscula y otro en mayúscula")
                .setCondition(Bindings.createBooleanBinding(
                        () -> containsAny(fieldcontrasena.getText(), "", mayusculas) &&
                                containsAny(fieldcontrasena.getText(), "", minusculas),
                        fieldcontrasena.textProperty()))
                .get();

        Constraint caracterRestriccion = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("La contraseña debe contener al menos un carácter especial")
                .setCondition(Bindings.createBooleanBinding(
                        () -> containsAny(fieldcontrasena.getText(), "", especiales),
                        fieldcontrasena.textProperty()))
                .get();

        fieldcontrasena.getValidator()
                .constraint(numeroRestriccion)
                .constraint(letraRestriccion)
                .constraint(caracterRestriccion)
                .constraint(longitudRestriccion);

        fieldcorreo.getValidator()
                .constraint(formatoRestriccion);

        fieldusuario.getValidator()
                .constraint(longitudNomRestriccion)
                .constraint(noCaracterRestriccion);


        fieldcontrasena.getValidator().validProperty().addListener((observable, anteriorValor, nuevoValor) -> {
            if (nuevoValor) {
                labelcontrasena.setVisible(false);
                fieldcontrasena.pseudoClassStateChanged(pseudoclaseValidacion, false);
            }});

        fieldcontrasena.delegateFocusedProperty().addListener((observable, anteriorValor, nuevoValor) -> {
            if (anteriorValor && !nuevoValor) {
                List<Constraint> restricciones = fieldcontrasena.validate();
                if (!restricciones.isEmpty()) {
                    fieldcontrasena.pseudoClassStateChanged(pseudoclaseValidacion, true);
                    labelcontrasena.setText(restricciones.get(0).getMessage());
                    labelcontrasena.setVisible(true);
                }}});

        fieldcorreo.getValidator().validProperty().addListener((observable, anteriorValor, nuevoValor) -> {
            if (nuevoValor) {
                labelcorreo.setVisible(false);
                fieldcorreo.pseudoClassStateChanged(pseudoclaseValidacion, false);
            }});

        fieldcorreo.delegateFocusedProperty().addListener((observable, anteriorValor, nuevoValor) -> {
            if (anteriorValor && !nuevoValor) {
                List<Constraint> restricciones = fieldcorreo.validate();
                if (!restricciones.isEmpty()) {
                    fieldcorreo.pseudoClassStateChanged(pseudoclaseValidacion, true);
                    labelcorreo.setText(restricciones.get(0).getMessage());
                    labelcorreo.setVisible(true);
                }}});

        fieldusuario.getValidator().validProperty().addListener((observable, anteriorValor, nuevoValor) -> {
            if (nuevoValor) {
                labelusuario.setVisible(false);
                fieldusuario.pseudoClassStateChanged(pseudoclaseValidacion, false);
            }});

        fieldusuario.delegateFocusedProperty().addListener((observable, anteriorValor, nuevoValor) -> {
            if (anteriorValor && !nuevoValor) {
                List<Constraint> restricciones = fieldusuario.validate();
                if (!restricciones.isEmpty()) {
                    fieldusuario.pseudoClassStateChanged(pseudoclaseValidacion, true);
                    labelusuario.setText(restricciones.get(0).getMessage());
                    labelusuario.setVisible(true);
                }}});
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

    @FXML
    private void guardarCambios() {
        String nuevoUsuario = fieldusuario.getText().trim();
        String nuevaContrasena = fieldcontrasena.getText().trim();
        String nuevoCorreo = fieldcorreo.getText().trim();

        // Si no hay cambios, salir
        if (nuevoUsuario.isEmpty() && nuevaContrasena.isEmpty() && nuevoCorreo.isEmpty()) {
            System.out.println("⚠️ No hay cambios que guardar.");
            return;
        }

        try {
            Connector connector = new Connector();
            var cn = connector.conectar();

            StringBuilder query = new StringBuilder("UPDATE datos_perso SET ");
            boolean necesitaComa = false;

            if (!nuevoUsuario.isEmpty()) {
                query.append("nom_usuario = ?");
                necesitaComa = true;
            }

            if (!nuevoCorreo.isEmpty()) {
                if (necesitaComa) query.append(", ");
                query.append("correo = ?");
                necesitaComa = true;
            }

            if (!nuevaContrasena.isEmpty()) {
                if (necesitaComa) query.append(", ");
                query.append("contraseña = ?");
            }

            query.append(" WHERE id_datos = ?");

            var ps = cn.prepareStatement(query.toString());

            int index = 1;
            if (!nuevoUsuario.isEmpty()) ps.setString(index++, nuevoUsuario);
            if (!nuevoCorreo.isEmpty()) ps.setString(index++, nuevoCorreo);
            if (!nuevaContrasena.isEmpty()) ps.setString(index++, nuevaContrasena);
            ps.setInt(index, idDatos);

            int filasAfectadas = ps.executeUpdate();

            if (filasAfectadas > 0) {
                System.out.println("✅ Datos actualizados correctamente.");

                // Actualizar Preferences
                Preferences prefs = Preferences.userRoot().node("com.echo.echoband");
                if (!nuevoUsuario.isEmpty()) prefs.put("nom_usuario", nuevoUsuario);
                if (!nuevoCorreo.isEmpty()) prefs.put("correo", nuevoCorreo);
                if (!nuevaContrasena.isEmpty()) prefs.put("contrasena", nuevaContrasena);

                // Refrescar campos
                obtenerDatosGuardados();
                labelNomUsuario.setText(nomUsuario);
                labelNomReal.setText(nomReal + " " + apPat);

                if (!fieldusuario.getText().trim().isEmpty()){
                    fieldusuario.setPromptText(fieldusuario.getText().trim());
                    labelNomUsuario.setText(fieldusuario.getText().trim());
                    fieldusuario.clear();
                }
                if (!fieldcontrasena.getText().trim().isEmpty()){
                    fieldcontrasena.setPromptText(fieldcontrasena.getText().trim());
                    fieldcontrasena.clear();
                }
                if(!fieldcorreo.getText().trim().isEmpty()){
                    fieldcorreo.setPromptText(fieldcorreo.getText().trim());
                    fieldcorreo.clear();
                }

            } else {
                System.err.println("❌ No se pudo actualizar los datos.");
            }

            connector.cerrarConexion();
        } catch (Exception e) {
            System.err.println("❌ ERROR al actualizar datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
