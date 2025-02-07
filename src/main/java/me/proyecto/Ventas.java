package me.proyecto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ventas extends Tabla {
    
    public Ventas(){
        sc = new Scanner(System.in);
    }
    @Override
    public void insertar() throws SQLException {
        System.out.println("║ Id_producto: ");
        int Id_producto = sc.nextInt();
        System.out.println("║ Fecha_venta (YYYY-MM-DD): ");
        String fecha_venta = sc.next();
        System.out.println("║ Cantidad_producto: ");
        int Cantidad_producto = sc.nextInt();
        System.out.println("║ Monto_venta: ");
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
        System.out.println("║ Id_venta: ");
        int Id_venta = sc.nextInt();
        System.out.println("║ Id_producto: ");
        int Id_producto = sc.nextInt();
        System.out.println("║ Fecha_venta (YYYY-MM-DD): ");
        String fecha_venta = sc.next();
        System.out.println("║ Cantidad_producto: ");
        int Cantidad_producto = sc.nextInt();
        System.out.println("║ Monto_venta: ");
        float Monto_venta = sc.nextFloat();

        String sql = "UPDATE VENTAS SET Id_Producto = ?, Fecha_venta = ?, Cantidad_Producto = ?, Monto_venta = ?" +
       " WHERE Id_venta = ?";

       try (Connection c = getConnection()){
        PreparedStatement ps = c.prepareStatement(sql);
        ps.setInt(1, Id_producto);
        ps.setString(2, fecha_venta);
        ps.setInt(3, Cantidad_producto);
        ps.setFloat(4, Monto_venta);
        ps.setInt(5, Id_venta);
        ps.executeUpdate();
        System.out.println("║ Ventas actualizadas correctamente");
       }
    }

    @Override
    public void eliminar() throws SQLException {
        System.out.println("║ Id_venta: ");
        int Id_venta = sc.nextInt();

        String sql = "DELETE FROM VENTAS WHERE Id_venta = ?";
        
        try(Connection c = getConnection()){
            PreparedStatement ps = c.prepareStatement(sql);
            ps.setInt(1, Id_venta);
            ps.executeQuery();
            System.out.println("║ Ventas eliminadas correctamente");
        }
    }

    @Override
    public List<?> obtenerTodos() throws SQLException {
        List<?> ventas = new ArrayList<>();
        String sql = "SELECT * FROM VENTAS";

        try (Connection c = getConnection();
        PreparedStatement ps = c.prepareStatement(sql)){
            ResultSet rs = ps.executeQuery();
        while(rs.next()){
            ? ventas = new Venta(
                rs.getInt("Id_venta"),
                rs.getInt("Id__producto"),
                rs.getString("Fecha_venta"),
                rs.getInt("Cantidad_producto"),
                rs.getFloat("Monto_venta")

            );

        }

        }
        return ventas;
    }

    public void menu(){
        boolean terminado = false;
        while(!terminado){
            System.out.println("╠════════════════════════════════════╗\n" +
                               "║          Menu Ventas              ║\n" +
                               "╠════════════════════════════════════╣\n" +
                               "║ 1. Insertar Nueva Entrada          ║\n" +
                               "║ 2. Modificar Entrada               ║\n" +
                               "║ 3. Eliminar Entrada                ║\n" +
                               "║ 4. Obtener Pedidos                 ║\n" +
                               "║ 0. SALIR                           ║\n" +
                               "╠════════════════════════════════════╝");
            System.out.print("║ Seleccione una opción (0-4): ");
            String opcion = sc.next();

            switch(opcion){
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
                        List<?> ventas = obtenerTodos();
                        for ( ? v: ventas){
                            System.out.println(v);
                        }
                    }catch(SQLException e){
                        System.out.println(e.getMessage());
                    }
                    break;
                default:
                    System.out.println("║ Opción no válida.");
                    break;
            }
        }
    }
}
