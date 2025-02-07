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
        System.out.println("║ Id_proveedor: ");
        int id_proveedor = sc.nextInt();
        System.out.println("║ Fecha_Pedido (YYYY-MM-DD)");
        String fecha_pedido = sc.next();
        System.out.println("║ Cantidad_total: ");
        int cantidad_total = sc.nextInt();

        String sql =  "INSERT INTO Pedidos(Id_proveedor, Fecha_pedido, Cantidad_total) VALUES (?, ?, ?);";

        try(Connection c = getConnection()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id_proveedor);
            ps.setString(2, fecha_pedido);
            ps.setInt(3, cantidad_total);
            ps.executeUpdate();
            System.out.println("Pedido insertado correctamente");
        }
    }

    @Override
    public void actualizar() throws SQLException {
        System.out.println("║ Id_pedidos: ");
        int id_pedido = sc.nextInt();
        System.out.println("║ Id_proveedor: ");
        int id_proveedor = sc.nextInt();
        System.out.println("║ Fecha_Pedido (YYYY-MM-DD)");
        String fecha_pedido = sc.next();
        System.out.println("║ Cantidad_total: ");
        int cantidad_total = sc.nextInt();
        
        String sql = "UPDATE PEDIDOS SET Id_Proveedor = ?, Fecha_pedido = ?, Cantidad_total = ? WHERE Id-pedidos = ?";
        
        try (Connection c = getConnection()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id_proveedor);
            ps.setString(2, fecha_pedido);
            ps.setInt(3, cantidad_total);
            ps.setInt(4, id_pedido);
            ps.executeUpdate();
            System.out.println("║ Pedido actualizado correctamente.");
        }
    }

    @Override
    public void eliminar() throws SQLException {
        System.out.println("║ Id_pedidos: ");
        int id_pedido = sc.nextInt();

        String sql = "DELETE FROM PEDIDOS WHERE Id_Proveedor = ?";

        try (Connection c = getConnection()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, id_pedido);
            ps.executeUpdate();
            System.out.println("Pedido eliminado correctamente");
        }
    }

    @Override
    public List<?> obtenerTodos() throws SQLException {
        List<?> pedidos = new ArrayList<>();
        String sql = "SELECT * FROM PEDIDOS";
        try (Connection c = getConnection()){
            PreparedStatement ps = c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
          while (rs.next()) {
            ? pedidos = new Pedido(
                rs.getInt("Id_pedidos"),
                rs.getInt("Id_proveedor"),
                rs.getTimestamp("Fecha_pedido"),
                rs.getInt("Cantidad_ total")
            );
            
          }
        }
        return pedidos;
    }

    public void menu(){
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
                    terminado = true;
                    break;
                case "1":
                    insertar();
                    break;
                case "2":
                    actualizar();
                    break;
                case "3":
                    eliminar();
                    break;
                case "4":
                try{
                    List<?> pedidos = obtenerTodos();
                    for(? p: pedidos){
                        System.out.println(p);

                    }
                }catch(SQLException e){
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
