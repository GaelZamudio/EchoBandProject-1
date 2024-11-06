package com.echo.echoband.connection;

import java.sql.*;

public class Connector {
    Connection conn;


    public Connection conectar() {
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://bbajbhu4gnjiftjk0ikh-mysql.services.clever-cloud.com/bbajbhu4gnjiftjk0ikh",
                    "u2krjwfzaqfkv12i",
                    "7SaE4UJYunulwZMbeO1J");
            System.out.println("Conectado con éxito");
        }catch (Exception e) {
            System.out.println("Error al conectar"+e);
        }
        return conn;
    }

    public void cerrarConexion() {
        if (conn != null) {
            try {
                conn.close();
                System.out.println("Conexión cerrada.");
            } catch (SQLException e) {
                System.err.println("Error al cerrar la conexión: " + e.getMessage());
            }
        }
    }

}
