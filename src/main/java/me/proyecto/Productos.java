package me.proyecto;

import java.sql.SQLException;
import java.util.Date;
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
                        insertar(); //PODEMOS HACER 2 COSAS, QUE LO DE DEBAJO DE AQUI ESTE DENTRO DE INSERTAR, O PASARLE LOS PARAMETROS A INSERTAR, 1 de las 2. Honestamente prefiero la primera opción
                        System.out.print("║ Id_proveedor: ");
                        int id_proveedor = sc.nextInt();
                        System.out.print("║ Nombre: ");
                        String nombre = sc.next();
                        System.out.print("║ Precio: ");
                        float precio = sc.nextFloat();
                        System.out.print("║ Stock: ");
                        int stock = sc.nextInt();
                        System.out.println("╠════════════════════════════════════╗");
                        System.out.println("║  Datos Introducidos Correctamente  ║");
                        System.out.println("╠════════════════════════════════════╝");
                        this.pressEnter();
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    break;
            }
        }

    }

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
