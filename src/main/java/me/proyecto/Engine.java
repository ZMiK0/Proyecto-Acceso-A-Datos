package me.proyecto;

import java.util.Scanner;

public class Engine {
    private Productos productos;
    private Pedidos pedidos;
    private Proveedores proveedores;
    private Ventas ventas;

    private Scanner sc;

    public void cleanScreen() {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
        System.out.flush();
    }

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

    public boolean start() {
        cleanScreen();
        System.out.println("╔════════════════════════════════════╗\n" +
                           "║           Menu Principal           ║\n" +
                           "╠════════════════════════════════════╣\n" +
                           "║ 1. Productos                       ║\n" +
                           "║ 2. Proveedores                     ║\n" +
                           "║ 3. Pedidos                         ║\n" +
                           "║ 4. Ventas                          ║\n" +
                           "║ 0. SALIR DEL PROGRAMA              ║\n" +
                           "╚════════════════════════════════════╝");
        System.out.print("Seleccione una opción (0-4): ");
        String opcion = sc.nextLine();
        switch(opcion) {
            case "0":
                System.out.println("╔═══════════════════════╗");
                System.out.println("║ Saliendo del programa ║");
                System.out.println("╚═══════════════════════╝");
                return true;
            case "1":
                return false;
            default:
                System.out.println("╔═══════════════════════╗");
                System.out.println("║  Entrada equivocada   ║");
                System.out.println("╚═══════════════════════╝");
                pressEnter();
                return false;

        }
    }
}
