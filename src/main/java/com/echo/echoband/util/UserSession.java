package com.echo.echoband.util;

import java.util.prefs.Preferences;

public class UserSession {
    private static final Preferences prefs = Preferences.userRoot().node("com.echo.echoband");

    public static void guardarSesion(int id, String usuario, String nombre, String apellido) {
        prefs.putInt("id_datos", id);
        prefs.put("nom_usuario", usuario);
        prefs.put("nom_real", nombre);
        prefs.put("ap_pat", apellido);
    }

    public static int getId() {
        return prefs.getInt("id_datos", -1);
    }

    public static String getUsuario() {
        return prefs.get("nom_usuario", "");
    }

    public static String getNombreCompleto() {
        return prefs.get("nom_real", "") + " " + prefs.get("ap_pat", "");
    }

    public static void cerrarSesion() {
        try {
            prefs.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
