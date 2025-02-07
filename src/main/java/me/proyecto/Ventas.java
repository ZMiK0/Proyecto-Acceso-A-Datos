package me.proyecto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ventas extends Tabla {
    private float totalVendido;
    
    public Ventas(){
        sc = new Scanner(System.in);
    }
    @Override
    public void insertar() throws SQLException {
        System.out.print("║ ID Producto: ");
        int Id_producto = sc.nextInt();
        System.out.print("║ Fecha Venta (YYYY-MM-DD): ");
        String fecha_venta = sc.next();
        System.out.print("║ Cantidad Producto: ");
        int Cantidad_producto = sc.nextInt();
        System.out.print("║ Monto Venta: ");
        float Monto_venta = sc.nextFloat();

        String sql = "INSERT INTO VENTAS (Id_producto, Fecha_venta, Cantidad_producto, Monto_venta) VALUES (?, ?, ?, ?)";

        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
                ps.setInt(1, Id_producto);
                ps.setString(2, fecha_venta);
                ps.setInt(3, Cantidad_producto);
                ps.setFloat(4, Monto_venta);
                ps.executeUpdate();
                System.out.println("║ Ventas insertadas correctamente");

            }
        }

    @Override
    public void actualizar() throws SQLException {
        System.out.print("║ ID Venta: ");
        int Id_venta = sc.nextInt();
        System.out.print("║ Fecha Venta (YYYY-MM-DD): ");
        String fecha_venta = sc.next();
        System.out.print("║ ID Producto: ");
        int Id_producto = sc.nextInt();
        System.out.print("║ Cantidad Producto: ");
        int Cantidad_producto = sc.nextInt();
        System.out.print("║ Monto Venta: ");
        float Monto_venta = sc.nextFloat();

        String sql = "UPDATE VENTAS SET Fecha_venta = ?, Id_Producto = ?, Cantidad_Producto = ?, Monto_venta = ?" +
       " WHERE Id_venta = ?";

       try (Connection c = getConnection()){
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setString(1, fecha_venta);
        ps.setInt(2, Id_producto);
        ps.setInt(3, Cantidad_producto);
        ps.setFloat(4, Monto_venta);
        ps.setInt(5, Id_venta);
        ps.executeUpdate();
        System.out.println("║ Ventas actualizadas correctamente");
       }
    }

    @Override
    public void eliminar() throws SQLException {
        System.out.print("║ ID de la venta a eliminar: ");
        int id = sc.nextInt();
        String sql = "DELETE FROM VENTAS WHERE Id_venta = ?";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)){
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public List<String> obtenerTodos() throws SQLException {
        List<String> ventas = new ArrayList<>();
        String sql = "SELECT * FROM VENTAS";
        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                String venta = "║ ID[" + rs.getInt("Id_venta") + "] Fecha_Venta[" + rs.getDate("Fecha_venta") + "] ID_Producto[" + rs.getInt("Id_producto") + "] Cantidad_Producto[" + rs.getInt("Cantidad_producto") + "] Monto_Venta[" + rs.getFloat("Monto_venta") + "€]";
                ventas.add(venta);
            }
        }
        return ventas;
    }

    public float getTotalVendido() throws SQLException {
        this.totalVendido = 0;
        String sql = "SELECT * FROM VENTAS";
        try(Connection c = getConnection();
            PreparedStatement ps = c.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                totalVendido+=rs.getFloat("Monto_venta");
            }

        }
        return this.totalVendido;
    }

    public void menu() {
        boolean terminado = false;
        while(!terminado){
            System.out.println("╠════════════════════════════════════╗\n" +
                               "║           Menu Ventas              ║\n" +
                               "╠════════════════════════════════════╣\n" +
                               "║ 1. Insertar Nueva Entrada          ║\n" +
                               "║ 2. Modificar Entrada               ║\n" +
                               "║ 3. Eliminar Entrada                ║\n" +
                               "║ 4. Obtener Ventas                  ║\n" +
                               "║ 5. Ver Total Vendido               ║\n" +
                               "║ 0. SALIR                           ║\n" +
                               "╠════════════════════════════════════╝");
            System.out.print("║ Seleccione una opción (0-5): ");
            String opcion = sc.next();

            switch(opcion){
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
                    System.out.println("║           Modificar Venta          ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        actualizar();
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║   Venta Modificada Correctamente   ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    }catch (SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "3":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║           Eliminar Venta           ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        eliminar();
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║    Venta Eliminada Correctamente   ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                case "4":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║          Lista de Ventas           ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try{
                        List<String> ventas = obtenerTodos();
                        for (String venta : ventas) {
                            System.out.println(venta);
                        }
                        this.pressEnter();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case "5":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║           Total Vendido            ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try {
                        System.out.println("║ " + getTotalVendido() + "€ Totales Vendidos");
                    } catch (SQLException e) {
                        System.out.println("║ ERROR");
                    }
                    this.pressEnter();
                default:
                    System.out.println("║ Opción no válida. Intente de nuevo.");
                    break;
            }
        }
    }
}
