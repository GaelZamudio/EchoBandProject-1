package com.echo.echoband.controller;

import com.echo.echoband.SignUp;
import com.echo.echoband.connection.Connector;
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
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import static io.github.palexdev.materialfx.utils.StringUtils.containsAny;

public class SignUpController implements Initializable{

    private Connector connector;
    private Connection cn;

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
    @FXML private MFXButton btnCrear;
    private MainController mainController;

    public void setMainController(MainController mainController) {
        this.mainController = mainController;
    }

    @FXML
    public void irAMenu() {
        Stage stage = (Stage) btnCrear.getScene().getWindow();
        SignUp app = new SignUp();
        stage.setTitle("Entrenamiento");



        Scene scene = stage.getScene();
        scene.getStylesheets().clear();
        scene.getStylesheets().add(SignUp.class.getResource("/com/echo/echoband/trainingStyle.css").toExternalForm());

    }

    private void cambiarVista(String fxmlFile, String cssFile, boolean mostrarSidebar) {
        if (mainController != null) {
            mainController.showScreen(fxmlFile, cssFile, mostrarSidebar);
        } else {
            System.err.println("❌ ERROR: MainController es null.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnCrear.setOnAction(e -> registrar());

        if (txtiniciar != null) {
            txtiniciar.setOnMouseClicked(e -> cambiarVista("/com/echo/echoband/logInView.fxml", "/com/echo/echoband/logInStyle.css", false));
        }

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
                    return email.matches("^[\\w.%+-]+@[\\w.-]+\\.com$") && !email.matches(".*\\.com\\.com$");
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

        btnCrear.disableProperty().bind(camposValidos.not());

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

    //Métodos para la BD
    @FXML
    public void registrar(){

        String nomReal = fieldnombre.getText();
        String apPat = fieldpat.getText();
        String apMat = fieldmat.getText();
        String nomUsuario = fieldusuario.getText();
        String correo = fieldcorreo.getText();
        String contrasena = fieldcontrasena.getText();
        Boolean existe;



        if(nomReal.isEmpty() || apPat.isEmpty() || apMat.isEmpty() || nomUsuario.isEmpty() || correo.isEmpty() || contrasena.isEmpty()){
            JOptionPane.showMessageDialog(null, "Debe completar los datos");
        } else {
            try {
                connector = new Connector();
                cn = connector.conectar();

                existe = existe(cn);
                if(existe){
                    JOptionPane.showMessageDialog(null, "El usuario o correo ya existen");
                } else {
                    String consulta = "INSERT INTO datos_perso(nom_real, nom_usuario, ap_pat, ap_mat, contraseña, correo)" +
                            "VALUES ('"+nomReal+"', '"+nomUsuario+"', '"+apPat+"', '"+apMat+"', '"+contrasena+"', '"+correo+"');";
                    PreparedStatement ps = cn.prepareStatement(consulta);
                    ps.executeUpdate();
                    //JOptionPane.showMessageDialog(null, "Datos correctamente guardadossss");



                    // Obtener el ID del usuario recién creado
                    String obtID = "SELECT id_datos FROM datos_perso WHERE nom_usuario = ?;";
                    PreparedStatement ps2 = cn.prepareStatement(obtID);
                    ps2.setString(1, nomUsuario);  // Utilizamos PreparedStatement para evitar inyecciones SQL
                    ResultSet rs = ps2.executeQuery();

                    if (rs.next()) {  // Asegurarse de que haya un resultado antes de acceder a él
                        int idDatos = rs.getInt("id_datos");
                        System.out.println("ID: " + idDatos);
                        Preferences prefs = Preferences.userRoot().node("com.echo.echoband");
                        prefs.putInt("id_datos", idDatos);
                        prefs.put("nom_usuario", nomUsuario);
                        prefs.put("nom_real", nomReal);
                        prefs.put("ap_pat", apPat);
                        prefs.put("ap_mat", apMat);
                        prefs.put("correo", correo);
                        prefs.put("contrasena", contrasena);

                        System.out.println("Hola de nuevo " + nomUsuario);
                        System.out.println("Perfil de " + nomReal + " " + apPat + " " + apMat);
                        System.out.println("ID del usuario: " + idDatos);
                        System.out.println("Correo: " + correo);
                        System.out.println("Contraseña "+ contrasena);

                        // Justo después de cambiar la vista
                        cambiarVista("/com/echo/echoband/trainingView.fxml", "/com/echo/echoband/trainingStyle.css", true);


                        // Luego de cambiar vista, fuerza actualización del sidebar
                        if (mainController != null && mainController.getSidebarController() != null) {
                            mainController.getSidebarController().actualizarDatosVisuales();
                        }

                    } else {
                        System.out.println("No se encontró el ID del usuario.");
                    }

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "No se pudo guardar el usuario: "+e.getMessage());
            }
            connector.cerrarConexion();


        }
    }

    public Boolean existe(Connection cn){
        String usuario = fieldusuario.getText();
        String correo = fieldcorreo.getText();

        try {
            String validacion = "SELECT COUNT(*) > 0 AS existe\n" +
                    "FROM datos_perso\n" +
                    "WHERE correo = '"+correo+"' OR nom_usuario = '"+usuario+"';";
            PreparedStatement ps = cn.prepareStatement(validacion);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("existe");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo guardar usuario"+e);
        }
        return false;
    }
}
