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

    @Override
    public void insertar() throws SQLException {
        System.out.println(("║ Id: "));
        int id_proveedor = sc.nextInt();
        System.out.print("║ Id_producto: ");
        int id_producto = sc.nextInt();
        System.out.print("║ Nombre: ");
        String nombre = sc.nextLine();
        System.out.print("║ Contacto: ");
        String contacto = sc.nextLine();
        System.out.print("║ Dirección: ");
        String direccion = sc.nextLine();

        String sql = "INSERT INTO Proveedores (Nombre, Contacto, Direccion) VALUES (?, ?, ?)";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id_proveedor);
            ps.setInt(2, id_producto);
            ps.setString(3, nombre);
            ps.setString(4, contacto);
            ps.setString(5, direccion);
            ps.executeUpdate();
            System.out.println("║ Proveedor insertado correctamente.");
        }
    }

    @Override
    public void actualizar() throws SQLException {
        System.out.print("║ Id_proveedor: ");
        int id = sc.nextInt();
        System.out.print("║ Id_producto: ");
        int id_producto = sc.nextInt();
        System.out.print("║ Nombre: ");
        String nombre = sc.next();
        System.out.print("║ Contacto: ");
        String contacto = sc.next();
        System.out.print("║ Dirección: ");
        String direccion = sc.next();

        String sql = "UPDATE Proveedores SET Nombre = ?, Contacto = ?, Direccion = ? WHERE Id_proveedor = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id_producto);
            ps.setString(2, nombre);
            ps.setString(3, contacto);
            ps.setString(4, direccion);
            ps.setInt(5, id);
            ps.executeUpdate();
            System.out.println("║ Proveedor actualizado correctamente.");
        }
    }

    @Override
    public void eliminar() throws SQLException {
        System.out.print("║ Id_proveedor: ");
        int id = sc.nextInt();

        String sql = "DELETE FROM Proveedores WHERE Id_proveedor = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
            System.out.println("║ Proveedor eliminado correctamente.");
        }
    }

    @Override
    public List<?> obtenerTodos() throws SQLException {
        List<?> proveedores = new ArrayList<>();
        String sql = "SELECT * FROM Proveedores";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                proveedores.add(new ?(
                    rs.getInt("Id_proveedor"),
                    rs.getString("Nombre"),
                    rs.getString("Contacto"),
                    rs.getString("Direccion")
                ));
            }
        }
        return proveedores;
    }

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
                    System.out.println("║ Cerrando menú...");
                    terminado = true;
                    break;
                case "1":
                    try {
                        insertar();
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "2":
                    try {
                        actualizar();
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "3":
                    try {
                        eliminar();
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                case "4":
                    try {
                        List<?> proveedores = obtenerTodos();
                        for (? p : proveedores) {
                            System.out.println(p);
                        }
                    } catch (SQLException e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                    break;
                default:
                    System.out.println("║ Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }
}
