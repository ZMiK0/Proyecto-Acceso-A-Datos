package me.proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Pedidos extends Tabla {

    public Pedidos(){
        this.sc = new Scanner(System.in);
    }
    @Override
    public void insertar() throws SQLException {
        System.out.print("║ ID Producto: ");
        int id_proveedor = sc.nextInt();
        System.out.print("║ Fecha Pedido (YYYY-MM-DD): ");
        String fecha_pedido = sc.next();
        System.out.print("║ Cantidad Total: ");
        int cantidad_total = sc.nextInt();

        String sql =  "INSERT INTO Pedidos(Id_producto, Fecha_pedido, Cantidad_total) VALUES (?, ?, ?);";

        try(Connection c = getConnection()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id_proveedor);
            ps.setString(2, fecha_pedido);
            ps.setInt(3, cantidad_total);
            ps.executeUpdate();
        }
    }

    @Override
    public void actualizar() throws SQLException {
        System.out.print("║ ID Pedido: ");
        int id_pedido = sc.nextInt();
        System.out.print("║ ID Producto: ");
        int id_proveedor = sc.nextInt();
        System.out.print("║ Fecha Pedido (YYYY-MM-DD): ");
        String fecha_pedido = sc.next();
        System.out.print("║ Cantidad Total: ");
        int cantidad_total = sc.nextInt();
        
        String sql = "UPDATE PEDIDOS SET Id_Producto = ?, Fecha_pedido = ?, Cantidad_total = ? WHERE Id_pedidos = ?";
        
        try (Connection c = getConnection()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id_proveedor);
            ps.setString(2, fecha_pedido);
            ps.setInt(3, cantidad_total);
            ps.setInt(4, id_pedido);
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminar() throws SQLException {
        System.out.print("║ ID Pedido: ");
        int id_pedido = sc.nextInt();

        String sql = "DELETE FROM PEDIDOS WHERE Id_Pedidos = ?";

        try (Connection c = getConnection()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id_pedido);
            ps.executeUpdate();
        }
    }

    @Override
    public List<String> obtenerTodos() throws SQLException {
        List<String> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM PEDIDOS";
        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String pedido = "║ ID[" + rs.getInt("id_pedidos") + "] ID_Producto[" + rs.getInt("id_producto") + "] Fecha_Pedido[" + rs.getDate("Fecha_pedido") + "] Cantidad_total[" + rs.getInt("Cantidad_total") + "]";
                pedidos.add(pedido);
            }
        }
        return pedidos;
    }

    public void menu() {
        boolean terminado = false;
        while(!terminado){
            System.out.println("╠════════════════════════════════════╗\n" +
                               "║          Menu Pedidos              ║\n" +
                               "╠════════════════════════════════════╣\n" +
                               "║ 1. Insertar Nueva Entrada          ║\n" +
                               "║ 2. Modificar Entrada               ║\n" +
                               "║ 3. Eliminar Entrada                ║\n" +
                               "║ 4. Obtener Pedidos                 ║\n" +
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
                    System.out.println("║          Modificar Pedido          ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        actualizar();
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║   Pedido Modificado Correctamente  ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║          Eliminar Pedido           ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        eliminar();
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║   Pedido Eliminada Correctamente   ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║         Lista de Pedidos           ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        List<String> pedidos = obtenerTodos();
                        for (String pedido : pedidos) {
                            System.out.println(pedido);
                        }
                        this.pressEnter();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                System.out.println("Opción inválida");
                    break;
            }
        }
    }
}
