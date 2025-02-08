package me.proyecto;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Ventas extends Tabla {

    public Ventas(){
        sc = new Scanner(System.in);
    }

    /**
     * Inserta una venta
     * @throws SQLException
     * @throws CantidadInsuficienteException
     */
    @Override
    public void insertar() throws SQLException, CantidadInsuficienteException {
        List<String> productos = new ArrayList<>();
        String sql = "SELECT * FROM PRODUCTOS";
        try (Connection c = getConnection();
             PreparedStatement ps = c.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String producto = "║ ID[" + rs.getInt("id_producto") + "] Nombre[" + rs.getString("nombre") + "] Precio[" + rs.getFloat("precio") + "€] Stock[" + rs.getInt("stock") + "]";
                productos.add(producto);
            }
        }
        for (String producto : productos) {
            System.out.println(producto);
        }

        System.out.print("║ ID Producto: ");
        int Id_producto = sc.nextInt();
        System.out.print("║ Fecha Venta (YYYY-MM-DD): ");
        String fecha_venta = sc.next();
        System.out.print("║ Cantidad Producto: ");
        int Cantidad_producto = sc.nextInt();
        float Monto_venta = 0;

        try (Connection c = getConnection()) {
                String sql1 = "SELECT Precio, Stock FROM Productos WHERE Id_producto = ?";
                try (PreparedStatement ps1 = c.prepareStatement(sql1)) {
                    ps1.setInt(1, Id_producto);
                    try (ResultSet rs1 = ps1.executeQuery()) {
                        if (rs1.next()) {
                            float precio = rs1.getFloat("Precio");
                            int stockActual = rs1.getInt("Stock");

                            if (stockActual >= Cantidad_producto) {

                                Monto_venta = precio * Cantidad_producto;
                            } else {
                                throw new CantidadInsuficienteException("Cantidad Insuficiente");
                            }
                        } else {
                            throw new SQLException("Producto no encontrado");
                        }
                    }
                }

                String sql2 = "INSERT INTO VENTAS (Id_producto, Fecha_venta, Cantidad_producto, Monto_venta) VALUES (?, ?, ?, ?)";
                try (PreparedStatement ps2 = c.prepareStatement(sql2)) {
                    ps2.setInt(1, Id_producto);
                    ps2.setString(2, fecha_venta);
                    ps2.setInt(3, Cantidad_producto);
                    ps2.setFloat(4, Monto_venta);
                    ps2.executeUpdate();
                }

                String sql3 = "UPDATE Productos SET Stock = Stock - ? WHERE Id_producto = ?";
                try (PreparedStatement ps3 = c.prepareStatement(sql3)) {
                    ps3.setInt(1, Cantidad_producto);
                    ps3.setInt(2, Id_producto);
                    ps3.executeUpdate();

                String sqlE = "SELECT Stock FROM Productos WHERE Id_producto = ?";
                try (PreparedStatement psE = c.prepareStatement(sqlE)) {
                    psE.setInt(1, Id_producto);
                    try (ResultSet rsE = psE.executeQuery()) {
                        if (rsE.next()) {
                            int stockActual = rsE.getInt("Stock");
                            if (stockActual < 20) {
                                System.out.println("╠════════════════════════════════════╗");
                                System.out.println("║  ADVERTENCIA: POCO STOCK RESTANTE  ║");
                                System.out.println("╠════════════════════════════════════╝");
                                this.pressEnter();
                            }
                        } else {
                            throw new SQLException("Producto no encontrado");
                        }
                    }

               }
                }
        }
    }

    /**
     * Actualiza una venta (EN DESUSO)
     * @throws SQLException
     */
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
       }
    }

    /**
     * Elimina una venta
     * @throws SQLException
     */
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

    /**
     * Otiene todas las ventas
     * @return Lista de ventas
     * @throws SQLException
     */
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

    public String getStats() throws SQLException {
        float totalVendido = 0;
        String productoMasVendido = "No hay ventas registradas.";
        String productoMenosVendido = "No hay ventas registradas.";

        String sqlTotalVendido = "SELECT SUM(Monto_venta) AS total_vendido FROM VENTAS";
        String sqlMasVendido = "SELECT P.Nombre, COUNT(*) AS total_ventas " +
                "FROM VENTAS V " +
                "JOIN Productos P ON V.Id_producto = P.Id_producto " +
                "GROUP BY V.Id_producto " +
                "ORDER BY total_ventas DESC " +
                "LIMIT 1";
        String sqlMenosVendido = "SELECT P.Nombre, COUNT(*) AS total_ventas " +
                "FROM VENTAS V " +
                "JOIN Productos P ON V.Id_producto = P.Id_producto " +
                "GROUP BY V.Id_producto " +
                "ORDER BY total_ventas ASC " +
                "LIMIT 1";

        try (Connection c = getConnection()) {

            try (PreparedStatement psTotal = c.prepareStatement(sqlTotalVendido);
                 ResultSet rsTotal = psTotal.executeQuery()) {
                if (rsTotal.next()) {
                    totalVendido = rsTotal.getFloat("total_vendido");
                }
            }

            try (PreparedStatement psMasVendido = c.prepareStatement(sqlMasVendido);
                 ResultSet rsMasVendido = psMasVendido.executeQuery()) {
                if (rsMasVendido.next()) {
                    String nombreMasVendido = rsMasVendido.getString("Nombre");
                    int ventasMasVendido = rsMasVendido.getInt("total_ventas");
                    productoMasVendido = nombreMasVendido + " (" + ventasMasVendido + " ventas)";
                }
            }

            try (PreparedStatement psMenosVendido = c.prepareStatement(sqlMenosVendido);
                 ResultSet rsMenosVendido = psMenosVendido.executeQuery()) {
                if (rsMenosVendido.next()) {
                    String nombreMenosVendido = rsMenosVendido.getString("Nombre");
                    int ventasMenosVendido = rsMenosVendido.getInt("total_ventas");
                    productoMenosVendido = nombreMenosVendido + " (" + ventasMenosVendido + " ventas)";
                }
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            throw e;
        }

        String stats = "║ Total vendido: " + totalVendido + "€\n" +
                "║ Producto más vendido: " + productoMasVendido + "\n" +
                "║ Producto menos vendido: " + productoMenosVendido;

        return stats;
    }

    /**
     * Menu de Ventas
     */
    public void menu() {
        boolean terminado = false;
        while(!terminado){
            System.out.println("╠════════════════════════════════════╗\n" +
                               "║            Menu Ventas             ║\n" +
                               "╠════════════════════════════════════╣\n" +
                               "║ 1. Insertar Nueva Entrada          ║\n" +
                               "║ 2. Eliminar Entrada                ║\n" +
                               "║ 3. Obtener Ventas                  ║\n" +
                               "║ 4. Ver Estadísticas                ║\n" +
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
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║ Producto Inexistente/Fecha Errónea ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    } catch (InputMismatchException e) {
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║         Entrada Equivocada         ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    } catch (CantidadInsuficienteException e) {
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║       Cantidad Insuficiente        ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    }
                    break;
                case "2":
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
                case "3":
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
                case "4":
                    System.out.println("╠════════════════════════════════════╗");
                    System.out.println("║            Estadísticas            ║");
                    System.out.println("╠════════════════════════════════════╝");
                    try {
                        System.out.println(getStats());
                    } catch (SQLException e) {
                        System.out.println("║ ERROR");
                    }
                    this.pressEnter();
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
