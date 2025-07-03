module com.echo.echoband {
    requires javafx.controls;
    requires javafx.fxml;
    requires MaterialFX;
    requires java.sql;
    requires jdk.compiler;
    requires java.prefs;


    opens com.echo.echoband.controller to javafx.fxml;

    opens com.echo.echoband to javafx.fxml;
    exports com.echo.echoband.controller;

    exports com.echo.echoband;
}