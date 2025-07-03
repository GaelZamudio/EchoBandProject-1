package com.echo.echoband;

import com.echo.echoband.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

import java.util.prefs.Preferences;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        Preferences prefs = Preferences.userRoot().node("com.echo.echoband");
        prefs.remove("id_datos");
        prefs.remove("nom_usuario");
        prefs.remove("nom_real");
        prefs.remove("ap_pat");
        prefs.remove("ap_mat");
        prefs.remove("correo");
        prefs.remove("contrasena");

        // Cargar la vista de la aplicación principal con la barra lateral
        FXMLLoader loader = new FXMLLoader(getClass().getResource("mainView.fxml"));
        Parent root = loader.load();
        MainController mainController = loader.getController();



        // Crear la escena
        Scene scene = new Scene(root);

        // Cargar la hoja de estilos CSS
        String css = getClass().getResource("logInStyle.css").toExternalForm();
        scene.getStylesheets().add(css);

        // Asignar la escena a la ventana principal
        primaryStage.setScene(scene);
        primaryStage.setTitle("EchoBand");
        primaryStage.show();
        primaryStage.setMinWidth(1500);
        primaryStage.setMinHeight(700);
        primaryStage.centerOnScreen();
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setMaximized(true);
        primaryStage.show();




    }

    public static void main(String[] args) {
        launch(args);
    }
}
