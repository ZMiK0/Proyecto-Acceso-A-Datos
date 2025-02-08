package me.proyecto;

import java.util.Scanner;

public class Engine {
    private Productos productos;
    private Pedidos pedidos;
    private Proveedores proveedores;
    private Ventas ventas;

    private Scanner sc;

    /**
     * Limpia la pantalla
     */
    public void cleanScreen() {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.flush();
    }

    /**
     * Pequeña pausa para que se pueda leer la interfaz
     */
    public void pressEnter() {
        System.console().readLine();
    }

    public Engine() {
        this.productos = new Productos();
        this.pedidos = new Pedidos();
        this.proveedores = new Proveedores();
        this.ventas = new Ventas();

        this.sc = new Scanner(System.in);
    }

    /**
     * Ejecuta 1 secuencia del programa
     * @param isAdmin Comprueba si se es administrador del sistema
     * @return True o False dependiendo si el programa termina o no
     */
    public boolean start(boolean isAdmin) {
        cleanScreen();
        if (isAdmin) {
            System.out.println("╔════════════════════════════════════╗\n" +
                    "║           Menu Principal           ║\n" +
                    "╠════════════════════════════════════╣\n" +
                    "║ 1. Pedidos                         ║\n" +
                    "║ 2. Ventas                          ║\n" +
                    "║ 3. Productos                       ║\n" +
                    "║ 4. Proveedores                     ║\n" +
                    "║ 0. SALIR DEL PROGRAMA              ║\n" +
                    "╠════════════════════════════════════╝");
        } else {
            System.out.println("╔════════════════════════════════════╗\n" +
                    "║           Menu Principal           ║\n" +
                    "╠════════════════════════════════════╣\n" +
                    "║ 1. Pedidos                         ║\n" +
                    "║ 2. Ventas                          ║\n" +
                    "║ 0. SALIR DEL PROGRAMA              ║\n" +
                    "╠════════════════════════════════════╝");
        }
        System.out.print("║ Seleccione una opción (0-4): ");
        String opcion = sc.next();
        switch(opcion) {
            case "0":
                System.out.println("╠════════════════════════════════════╗");
                System.out.println("║       Saliendo Del Programa        ║");
                System.out.println("╚════════════════════════════════════╝");
                this.productos.closeScanner();
                return true;
            case "1":
                this.pedidos.menu();
                return false;
            case "2":
                this.ventas.menu();
                return false;
            case "3":
                if(isAdmin) this.productos.menu(); else System.out.println("╠════════════════════════════════════╗\n║          Permiso Denegado          ║\n╚════════════════════════════════════╝");
                pressEnter();
                return false;
            case "4":
                if(isAdmin) this.proveedores.menu(); else System.out.println("╠════════════════════════════════════╗\n║          Permiso Denegado          ║\n╚════════════════════════════════════╝");
                pressEnter();
                return false;
            default:
                System.out.println("╠════════════════════════════════════╗");
                System.out.println("║          Entrada Erronea           ║");
                System.out.println("╚════════════════════════════════════╝");
                pressEnter();
                return false;

        }
    }
}
