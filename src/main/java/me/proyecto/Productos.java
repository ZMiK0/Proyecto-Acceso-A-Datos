package me.proyecto;

import java.sql.SQLException;
import java.util.List;

public class Productos extends Tabla {
    //CADA CLASE TIENE QUE TENER UN SCANNER QUE SE CIERRA CON EL PROGRAMA CON EL METODO scannerClose(){this.sc.close()}

    @Override
    public void insertar() throws SQLException {

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
