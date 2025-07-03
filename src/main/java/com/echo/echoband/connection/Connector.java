package com.echo.echoband.connection;

import java.sql.*;

public class Connector {
    Connection conn;


    public Connection conectar() {
        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://bx7hzfuwbyiwi7gw6nei-mysql.services.clever-cloud.com/bx7hzfuwbyiwi7gw6nei",
                    "u5jlbrlyb16rb6g3",
                    "B7Q7vVbrWOg9Im1L0Dvv");
            System.out.println("Conectado con éxito");
        }catch (Exception e) {
            System.out.println("Error al conectar: "+e);
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
