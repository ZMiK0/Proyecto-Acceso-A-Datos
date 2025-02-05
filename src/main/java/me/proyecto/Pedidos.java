package me.proyecto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Pedidos extends Tabla {
    @Override
    public void insertar() throws SQLException {
        //EJEMPLO DE COMO SE HACE LO DE SQL
        String sentencia = "INSERT INTO Productos (Id_proveedor, Nombre, Categoria, Precio, Stock, Fecha_Cad) VALUES (?,?,?,?,?,?);";
        try(Connection c = getConnection()) {
            PreparedStatement ps = c.prepareStatement(sentencia);
            ps.setInt(1, 6);
            ps.executeQuery();
        } catch (SQLException e) {
            System.out.println("Weh weh");
        }


    }

    @Override
    public void actualizar() throws SQLException {

    }

    @Override
    public void eliminar() throws SQLException {

    }

    @Override
    public List<?> obtenerTodos() throws SQLException {
        return List.of();
    }
}
