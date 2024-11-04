package com.echo.echoband.controller;

import com.echo.echoband.SignUp;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXCheckbox;
import io.github.palexdev.materialfx.controls.MFXPasswordField;
import io.github.palexdev.materialfx.controls.MFXTextField;
import io.github.palexdev.materialfx.validation.Constraint;
import io.github.palexdev.materialfx.validation.Severity;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.css.PseudoClass;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import static io.github.palexdev.materialfx.utils.StringUtils.containsAny;
import javafx.scene.input.KeyEvent;

public class SignUpController implements Initializable{

    private static final PseudoClass pseudoclaseValidacion = PseudoClass.getPseudoClass("invalido");
    private static final String[] mayusculas = "A B C D E F G H I J K L M N O P Q R S T U V W X Y Z".split(" ");
    private static final String[] minusculas = "a b c d e f g h i j k l m n o p q r s t u v w x y z".split(" ");
    private static final String[] numeros = "0 1 2 3 4 5 6 7 8 9".split(" ");
    private static final String[] especiales = "! @ # & ( ) – [ { } ]: ; ' , ? / * ~ $ ^ + = < > -".split(" ");

    @FXML private Text txtiniciar;
    @FXML private MFXTextField fieldnombre;
    @FXML private MFXTextField fieldpat;
    @FXML private MFXTextField fieldmat;
    @FXML private MFXTextField fieldusuario;
    @FXML private MFXTextField fieldcorreo;
    @FXML private MFXPasswordField fieldcontrasena;
    @FXML private Label labelnombre;
    @FXML private Label labelpat;
    @FXML private Label labelmat;
    @FXML private Label labelusuario;
    @FXML private Label labelcorreo;
    @FXML private Label labelcontrasena;
    @FXML private MFXCheckbox checkterminos;
    @FXML private MFXButton botoncrear;

    @FXML
    public void irAMenu() {
        try {
            Stage stage = (Stage) botoncrear.getScene().getWindow();
            SignUp app = new SignUp();

            app.cambiarEscena(stage, "/com/echo/echoband/trainingView.fxml");

            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(SignUp.class.getResource("/com/echo/echoband/trainingStyle.css").toExternalForm());

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error al cargar trainingView.fxml");
        }
    }

    public void irALogIn() throws IOException {
        Stage stage = (Stage) txtiniciar.getScene().getWindow();
        SignUp app = new SignUp();
        app.cambiarEscena(stage, "logInView.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fieldnombre.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]")) {
                event.consume();
            }
        });

        fieldpat.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]")) {
                event.consume();
            }
        });

        fieldmat.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ]")) {
                event.consume();
            }
        });

        Constraint nombreNoVacio = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("El nombre no puede estar vacío")
                .setCondition(fieldnombre.textProperty().isNotEmpty())
                .get();

        Constraint apellidoPaternoNoVacio = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("El apellido paterno no puede estar vacío")
                .setCondition(fieldpat.textProperty().isNotEmpty())
                .get();

        Constraint apellidoMaternoNoVacio = Constraint.Builder.build()
                .setSeverity(Severity.ERROR)
                .setMessage("El apellido materno no puede estar vacío")
                .setCondition(fieldmat.textProperty().isNotEmpty())
                .get();

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

        BooleanBinding camposValidos = fieldnombre.getValidator().validProperty()
                .and(fieldpat.getValidator().validProperty())
                .and(fieldmat.getValidator().validProperty())
                .and(fieldusuario.getValidator().validProperty())
                .and(fieldcorreo.getValidator().validProperty())
                .and(fieldcontrasena.getValidator().validProperty())
                .and(fieldnombre.textProperty().isNotEmpty())
                .and(fieldpat.textProperty().isNotEmpty())
                .and(fieldmat.textProperty().isNotEmpty())
                .and(fieldusuario.textProperty().isNotEmpty())
                .and(fieldcorreo.textProperty().isNotEmpty())
                .and(fieldcontrasena.textProperty().isNotEmpty())
                .and(checkterminos.selectedProperty());

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

        fieldnombre.getValidator().constraint(nombreNoVacio);

        fieldpat.getValidator().constraint(apellidoPaternoNoVacio);

        fieldmat.getValidator().constraint(apellidoMaternoNoVacio);

        botoncrear.disableProperty().bind(camposValidos.not());

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

        fieldnombre.getValidator().validProperty().addListener((observable, anteriorValor, nuevoValor) -> {
            if (nuevoValor) {
                labelnombre.setVisible(false);
                fieldnombre.pseudoClassStateChanged(pseudoclaseValidacion, false);
            }});

        fieldnombre.delegateFocusedProperty().addListener((observable, anteriorValor, nuevoValor) -> {
            if (anteriorValor && !nuevoValor) {
                List<Constraint> restricciones = fieldnombre.validate();
                if (!restricciones.isEmpty()) {
                    fieldnombre.pseudoClassStateChanged(pseudoclaseValidacion, true);
                    labelnombre.setText(restricciones.get(0).getMessage());
                    labelnombre.setVisible(true);
                }}});

        fieldpat.getValidator().validProperty().addListener((observable, anteriorValor, nuevoValor) -> {
            if (nuevoValor) {
                labelpat.setVisible(false);
                fieldpat.pseudoClassStateChanged(pseudoclaseValidacion, false);
            }});

        fieldpat.delegateFocusedProperty().addListener((observable, anteriorValor, nuevoValor) -> {
            if (anteriorValor && !nuevoValor) {
                List<Constraint> restricciones = fieldpat.validate();
                if (!restricciones.isEmpty()) {
                    fieldpat.pseudoClassStateChanged(pseudoclaseValidacion, true);
                    labelpat.setText(restricciones.get(0).getMessage());
                    labelpat.setVisible(true);
                }}});

        fieldmat.getValidator().validProperty().addListener((observable, anteriorValor, nuevoValor) -> {
            if (nuevoValor) {
                labelmat.setVisible(false);
                fieldmat.pseudoClassStateChanged(pseudoclaseValidacion, false);
            }});

        fieldmat.delegateFocusedProperty().addListener((observable, anteriorValor, nuevoValor) -> {
            if (anteriorValor && !nuevoValor) {
                List<Constraint> restricciones = fieldmat.validate();
                if (!restricciones.isEmpty()) {
                    fieldmat.pseudoClassStateChanged(pseudoclaseValidacion, true);
                    labelmat.setText(restricciones.get(0).getMessage());
                    labelmat.setVisible(true);
                }}});
    }
}