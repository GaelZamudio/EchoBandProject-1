package com.echo.echoband.controller;

import com.echo.echoband.Configuration;
import com.echo.echoband.Statistics;
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
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static io.github.palexdev.materialfx.utils.StringUtils.containsAny;

public class ConfigurationController implements Initializable {

    private static final PseudoClass pseudoclaseValidacion = PseudoClass.getPseudoClass("invalido");
    private static final String[] mayusculas = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");
    private static final String[] minusculas = "a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" ");
    private static final String[] numeros = "0 1 2 3 4 5 6 7 8 9".split(" ");
    private static final String[] especiales = "! @ # & ( ) – [ { } ]: ; ' , ? / * ~ $ ^ + = < > -".split(" ");

    @FXML private MFXButton entrenar;
    @FXML private MFXButton estadisticas;
    @FXML private MFXButton amigos;
    @FXML private MFXButton perfil;
    @FXML private MFXButton liga;
    @FXML private MFXButton config;
    @FXML private MFXButton cerrar;

    @FXML private MFXTextField fieldusuario;
    @FXML private MFXPasswordField fieldcontrasena;
    @FXML private MFXTextField fieldcorreo;
    @FXML private Label labelusuario;
    @FXML private Label labelcontrasena;
    @FXML private Label labelcorreo;

    public void irAConfig() throws IOException {
        Stage stage = (Stage) config.getScene().getWindow();
        Configuration app = new Configuration();
        app.cambiarEscena(stage, "configurationView.fxml");
        stage.setTitle("Configuración");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Configuration.class.getResource("/com/echo/echoband/configurationStyle.css").toExternalForm());
    }

    public void irAAmigos() throws IOException {
        Stage stage = (Stage) amigos.getScene().getWindow();
        Configuration app = new Configuration();
        app.cambiarEscena(stage, "friendsView.fxml");
        stage.setTitle("Amigos");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Configuration.class.getResource("/com/echo/echoband/friendsStyle.css").toExternalForm());
    }

    public void irALiga() throws IOException {
        Stage stage = (Stage) liga.getScene().getWindow();
        Configuration app = new Configuration();
        app.cambiarEscena(stage, "leagueView.fxml");
        stage.setTitle("Liga");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Configuration.class.getResource("/com/echo/echoband/leagueStyle.css").toExternalForm());
    }

    public void irAEntrenar() throws IOException {
        Stage stage = (Stage) entrenar.getScene().getWindow();
        Configuration app = new Configuration();
        app.cambiarEscena(stage, "trainingView.fxml");
        stage.setTitle("Entrenamiento");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Configuration.class.getResource("/com/echo/echoband/trainingStyle.css").toExternalForm());
    }

    public void irAEstadisticas() throws IOException {
        Stage stage = (Stage) estadisticas.getScene().getWindow();
        Configuration app = new Configuration();
        app.cambiarEscena(stage, "statisticsView.fxml");
        stage.setTitle("Estadísticas");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Configuration.class.getResource("/com/echo/echoband/staticsStyle.css").toExternalForm());
    }

    public void irAPerfil() throws IOException {
        Stage stage = (Stage) perfil.getScene().getWindow();
        Configuration app = new Configuration();
        app.cambiarEscena(stage, "userProfileView.fxml");
        stage.setTitle("Perfil de Usuario");
        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(Configuration.class.getResource("/com/echo/echoband/userProfileStyle.css").toExternalForm());
    }

    public void irALogOut() throws IOException {
        Platform.exit();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

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

        Constraint formatoRestriccion = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("El campo debe tener formato de correo electrónico")
                .setCondition(fieldcorreo.textProperty().isNotEmpty().and(Bindings.createBooleanBinding(() -> {
                    String email = fieldcorreo.getText();
                    return email.contains("@") && email.endsWith(".com");
                }, fieldcorreo.textProperty())))
                .get();

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
                        () -> containsAny(fieldcontrasena.getText(), "", mayusculas) && containsAny(fieldcontrasena.getText(), "", minusculas),
                        fieldcontrasena.textProperty()))
                .get();

        Constraint caracterRestriccion = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("La contraseña debe contener al menos un carácter especial")
                .setCondition(Bindings.createBooleanBinding(
                        () -> containsAny(fieldcontrasena.getText(), "", especiales),
                        fieldcontrasena.textProperty()))
                .get();

        BooleanBinding camposValidos = (fieldusuario.getValidator().validProperty())
                .and(fieldcorreo.getValidator().validProperty())
                .and(fieldcontrasena.getValidator().validProperty())
                .and(fieldusuario.textProperty().isNotEmpty())
                .and(fieldcorreo.textProperty().isNotEmpty())
                .and(fieldcontrasena.textProperty().isNotEmpty());

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
}
