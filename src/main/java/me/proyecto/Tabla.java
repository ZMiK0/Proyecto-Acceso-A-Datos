package me.proyecto;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public abstract class Tabla {
    protected Scanner sc;

    // Metodo abstracto para insertar
    public abstract void insertar() throws SQLException;

    // Metodo abstracto para actualizar
    public abstract void actualizar() throws SQLException;

    // Metodo abstracto para eliminar
    public abstract void eliminar() throws SQLException;

    // Metodo para obtener todos los registros de la tabla
    public abstract List<?> obtenerTodos() throws SQLException;

    private static final String CONFIG_FILE = "config.properties";
    // Conexión común a la base de datos
    protected Connection getConnection() throws SQLException {
        Properties properties = new Properties();

        try (InputStream input = Tabla.class.getClassLoader().getResourceAsStream(CONFIG_FILE)) {
            if (input == null) {
                throw new RuntimeException("No se encontró el archivo de configuración.");
            }
            properties.load(input);
        } catch (Exception e) {
            throw new RuntimeException("Error cargando configuración", e);
        }

        String url = properties.getProperty("db.url");
        String user = properties.getProperty("db.user");
        String password = properties.getProperty("db.password");

        return DriverManager.getConnection(url, user, password);
    }

    public void pressEnter() {
        System.out.print("║ ");
        System.console().readLine();
    }

    protected void closeScanner() {
        if (sc != null) {
            sc.close();
        }
    }
}
