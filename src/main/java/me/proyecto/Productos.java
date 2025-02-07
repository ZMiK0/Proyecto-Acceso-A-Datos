package me.proyecto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Productos extends Tabla {

    public Productos() {
        this.sc = new Scanner(System.in);
    }

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
                        //PODEMOS HACER 2 COSAS, QUE LO DE DEBAJO DE AQUI ESTE DENTRO DE INSERTAR, O PASARLE LOS PARAMETROS A INSERTAR, 1 de las 2. Honestamente prefiero la primera opción
                        System.out.print("║ Id_proveedor: ");
                        int id_proveedor = sc.nextInt();
                        System.out.print("║ Nombre: ");
                        String nombre = sc.nextLine();
                        System.out.print("║ Precio: ");
                        float precio = sc.nextFloat();
                        System.out.print("║ Stock: ");
                        int stock = sc.nextInt();
                        insertar(id_proveedor, nombre, precio, stock);
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
                    System.out.println("║        Modificar Producto          ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        System.out.println("║ Id_producto a modificar: ");
                        int id = sc.nextInt();
                        System.out.println("║ Id_proveedor: ");
                        int id_proveedor = sc.nextInt();
                        System.out.println("║ Nombre: ");
                        String nombre = sc.next();
                        System.out.println("║ Precio: ");
                        Float precio = sc.nextFloat();
                        System.out.println("║ Stock: ");
                        int stock = sc.nextInt();
                        actualizar(id, id_proveedor, nombre, precio, stock);
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║  Producto Modificado Correctamente ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║        Eliminar Producto           ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        System.out.println("Id_producto a eliminar: ");
                        int id = sc.nextInt();
                        eliminar(id);
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
                    List<Producto> productos = (List<Producto>) obtenerTodos();
                        for (Producto producto : productos) {
                            System.out.println(producto);
                        }
                        this.pressEnter();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║  Opción no válida. Intente de nuevo ║");
                    System.out.println("╠════════════════════════════════════╝");
                    break;
            }
        }
    }

    @Override
    public void insertar(int id_proveedor, String nombre, float precio, int stock) throws SQLException {
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

    @Override
    public void actualizar(int id, int id_proveedor, String nombre, float precio, int stock) throws SQLException {
        String sql = "UPDATE PRODUCTOS SET id_proveedor = ?, nombre = ?, precio = ?, stock = ? WHERE id = ?";
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

    @Override
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM PRODUCTOS WHERE id = ?";
        try (Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
                ps.setInt(1, id);
                ps.executeUpdate();
            }

    }

    @Override
    public List<Producto> obtenerTodos() throws SQLException {
        List<Producto> productos = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTOS";
        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
          while(rs.next()){
            Producto producto = new Producto(
                rs.getInt("id"),
                rs.getInt("id_proveedor"),
                rs.getString("nombre"),
                rs.getFloat("precio"),
                rs.getInt("stock")
            );
            productos.add(producto);
          }
        }
        return productos;
    }
}
