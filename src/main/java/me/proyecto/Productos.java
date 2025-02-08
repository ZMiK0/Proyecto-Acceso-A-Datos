package me.proyecto;

import java.sql.*;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Productos extends Tabla {

    public Productos() {
        this.sc = new Scanner(System.in);
    }

    /**
     * Inserta un producto
     * @throws SQLException
     */
    @Override
    public void insertar() throws SQLException {
        System.out.print("║ Id_proveedor: ");
        int id_proveedor = sc.nextInt();
        System.out.print("║ Nombre: ");
        String nombre = sc.next();
        System.out.print("║ Precio: ");
        float precio = sc.nextFloat();
        System.out.print("║ Stock: ");
        int stock = sc.nextInt();
        String sql = "INSERT INTO PRODUCTOS (id_proveedor, nombre, precio, stock) VALUES (?, ?, ?, ?)";
        try (Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, id_proveedor);
            ps.setString(2, nombre);
            ps.setFloat(3, precio);
            ps.setInt(4, stock);
            ps.executeUpdate();
        }
    }

    /**
     * Actualiza un producto
     * @throws SQLException
     */
    @Override
    public void actualizar() throws SQLException {
        System.out.print("║ Id_producto a modificar: ");
        int id = sc.nextInt();
        System.out.print("║ Id_proveedor: ");
        int id_proveedor = sc.nextInt();
        System.out.print("║ Nombre: ");
        String nombre = sc.next();
        System.out.print("║ Precio: ");
        Float precio = sc.nextFloat();
        System.out.print("║ Stock: ");
        int stock = sc.nextInt();
        String sql = "UPDATE PRODUCTOS SET id_proveedor = ?, nombre = ?, precio = ?, stock = ? WHERE id_producto = ?";
        try (Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, id_proveedor);
            ps.setString(2, nombre);
            ps.setFloat(3, precio);
            ps.setInt(4, stock);
            ps.setInt(5, id);
            ps.executeUpdate();
        }
    }

    /**
     * Elimina un producto
     * @throws SQLException
     */
    @Override
    public void eliminar() throws SQLException {
        System.out.print("║ ID del producto a eliminar: ");
        int id = sc.nextInt();
        String sql = "DELETE FROM PRODUCTOS WHERE id_producto = ?";
        try (Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
                ps.setInt(1, id);
                ps.executeUpdate();
            }
    }

    /**
     * Obtiene todos los productos
     * @return Lista de productos
     * @throws SQLException
     */
    @Override
    public List<String> obtenerTodos() throws SQLException {
        List<String> productos = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTOS";
        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
          while(rs.next()){
            String producto = "║ ID[" + rs.getInt("id_producto") + "] ID_Proveedor[" + rs.getInt("id_proveedor") + "] Nombre[" + rs.getString("nombre") + "] Precio[" + rs.getFloat("precio") + "€] Stock[" + rs.getInt("stock") + "]";
            productos.add(producto);
          }
        }
        return productos;
    }

    /**
     * Menu productos
     */
    public void menu() {
        boolean terminado = false;
        while (!terminado) {
            System.out.println("╠════════════════════════════════════╗\n" +
                    "║          Menu Productos            ║\n" +
                    "╠════════════════════════════════════╣\n" +
                    "║ 1. Insertar Nueva Entrada          ║\n" +
                    "║ 2. Modificar Entrada               ║\n" +
                    "║ 3. Eliminar Entrada                ║\n" +
                    "║ 4. Obtener Productos               ║\n" +
                    "║ 0. SALIR                           ║\n" +
                    "╠════════════════════════════════════╝");
            System.out.print("║ Seleccione una opción (0-4): ");
            String opcion = sc.next();
            switch(opcion) {
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
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║      Proveedor No Existente        ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    } catch (InputMismatchException e) {
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║         Entrada Equivocada         ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    }
                    break;
                case "2":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║        Modificar Producto          ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        actualizar();
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║  Producto Modificado Correctamente ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    }catch (SQLException e){
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║      Proveedor No Existente        ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    } catch (InputMismatchException e) {
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║         Entrada Equivocada         ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    }

                    break;
                case "3":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║        Eliminar Producto           ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        eliminar();
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║  Producto Eliminado Correctamente  ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║        Lista de Productos          ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        List<String> productos = obtenerTodos();
                        for (String producto : productos) {
                            System.out.println(producto);
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
