package com.echo.echoband.controller;

import com.echo.echoband.LogIn;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.Bindings;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import static io.github.palexdev.materialfx.utils.StringUtils.containsAny;

public class LoginController /*implements Initializable*/{

    /*private static final PseudoClass pseudoclaseValidacion = PseudoClass.getPseudoClass("invalido");
    private static final String[] mayusculas = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");
    private static final String[] minusculas = "a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" ");
    private static final String[] numeros = "0 1 2 3 4 5 6 7 8 9".split(" ");
    private static final String[] especiales = "! @ # & ( ) – [ { } ]: ; ' , ? / * ~ $ ^ + = < > -".split(" ");*/

    @FXML
    private Text txtcrear;
    @FXML private MFXTextField fieldusuario;
    @FXML private MFXPasswordField fieldcontrasena;
    @FXML private Label labelusuario;
    @FXML private Label labelcontrasena;
    @FXML private MFXButton botoniniciar;

    public void irASignUp() throws IOException {
        Stage stage = (Stage) txtcrear.getScene().getWindow();
        LogIn app = new LogIn();
        app.cambiarEscena(stage, "signUpView.fxml");
    }

    /*@Override
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

        fieldcontrasena.getValidator()
                .constraint(numeroRestriccion)
                .constraint(letraRestriccion)
                .constraint(caracterRestriccion)
                .constraint(longitudRestriccion);

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
    }*/
}