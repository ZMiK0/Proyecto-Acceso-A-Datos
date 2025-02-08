package me.proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Proveedores extends Tabla {

    public Proveedores() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Inserta un proveedor
     * @throws SQLException
     */
    @Override
    public void insertar() throws SQLException {
        System.out.print("║ Nombre: ");
        String nombre = sc.next();
        System.out.print("║ Contacto: ");
        String contacto = sc.next();
        System.out.print("║ Dirección: ");
        String direccion = sc.next();

        String sql = "INSERT INTO Proveedores (Nombre, Contacto, Direccion) VALUES (?, ?, ?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, contacto);
            ps.setString(3, direccion);
            ps.executeUpdate();
        }
    }

    /**
     * Actualiza un proveedor
     * @throws SQLException
     */
    @Override
    public void actualizar() throws SQLException {
        System.out.print("║ ID Proveedor: ");
        int id = sc.nextInt();
        System.out.print("║ Nombre: ");
        String nombre = sc.next();
        System.out.print("║ Contacto: ");
        String contacto = sc.next();
        System.out.print("║ Dirección: ");
        String direccion = sc.next();

        String sql = "UPDATE Proveedores SET Nombre = ?, Contacto = ?, Direccion = ? WHERE Id_proveedor = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setString(1, nombre);
            ps.setString(2, contacto);
            ps.setString(3, direccion);
            ps.setInt(4, id);
            ps.executeUpdate();
        }
    }

    /**
     * Elimina un proveedor
     * @throws SQLException
     */
    @Override
    public void eliminar() throws SQLException {
        System.out.print("║ ID Proveedor: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM Proveedores WHERE Id_proveedor = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Obtiene todos los proveedores
     * @return Lista de proveedores
     * @throws SQLException
     */
    @Override
    public List<String> obtenerTodos() throws SQLException {
        List<String> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM PROVEEDORES";
        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String proveedor = "║ ID[" + rs.getInt("id_proveedor") + "] Nombre[" + rs.getString("Nombre") + "] Contacto[" + rs.getString("Contacto") + "€] Dirección[" + rs.getString("Direccion") + "]";
                proveedores.add(proveedor);
            }
        }
        return proveedores;
    }

    /**
     * Menu de proveedores
     */
    public void menu() {
        boolean terminado = false;
        while (!terminado) {
            System.out.println("╠════════════════════════════════════╗\n" +
                               "║          Menu Proveedores          ║\n" +
                               "╠════════════════════════════════════╣\n" +
                               "║ 1. Insertar Nuevo Proveedor        ║\n" +
                               "║ 2. Modificar Proveedor             ║\n" +
                               "║ 3. Eliminar Proveedor              ║\n" +
                               "║ 4. Obtener Todos los Proveedores   ║\n" +
                               "║ 0. SALIR                           ║\n" +
                               "╠════════════════════════════════════╝");
            System.out.print("║ Seleccione una opción (0-4): ");
            String opcion = sc.next();

            switch (opcion) {
                case "0":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║           Cerrando Menu            ║");
                    System.out.println("╠════════════════════════════════════╝");
                    terminado = true;
                    break;
                case "1":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║        Introduzca los datos        ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try {
                        insertar();
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║  Datos Introducidos Correctamente  ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "2":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║        Modificar Proveedor         ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        actualizar();
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║ Proveedor Modificado Correctamente ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║         Eliminar Proveedor         ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        eliminar();
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║ Proveedor Eliminado Correctamente  ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║        Lista de Proveedores        ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        List<String> proveedores = obtenerTodos();
                        for (String proveedor : proveedores) {
                            System.out.println(proveedor);
                        }
                        this.pressEnter();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║ Opción no válida. Intente de nuevo ║");
                    System.out.println("╠════════════════════════════════════╝");
                    break;
            }
        }
    }
}
