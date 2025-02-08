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

    /**
     * Inserta en una tabla
     * @throws SQLException
     * @throws CantidadInsuficienteException
     */
    public abstract void insertar() throws SQLException, CantidadInsuficienteException;

    /**
     * Actualiza un campo de una tabla
     * @throws SQLException
     */
    public abstract void actualizar() throws SQLException;

    /**
     * Elimina un campo de una tabla
     * @throws SQLException
     */
    public abstract void eliminar() throws SQLException;

    /**
     * Obtiene todos los campos de una tabla
     * @return Lista de campos de una tabla
     * @throws SQLException
     */
    public abstract List<?> obtenerTodos() throws SQLException;

    private static final String CONFIG_FILE = "config.properties";

    /**
     * Hace una conexción a la base de datos
      * @return Un Driver Manager
     * @throws SQLException
     */
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

    /**
     * Pequeña pausa para leer la consola
     */
    public void pressEnter() {
        System.out.print("║ ");
        System.console().readLine();
    }

    /**
     * Cierra los scanneres de una tabla
     */
    protected void closeScanner() {
        if (sc != null) {
            sc.close();
        }
    }
}
