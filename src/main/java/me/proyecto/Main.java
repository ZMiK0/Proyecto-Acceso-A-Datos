package me.proyecto;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();
        Scanner sc = new Scanner(System.in);
        System.out.print("Usuario: ");
        String usuario = sc.next();
        System.out.print("Contraseña: ");
        String contrasena = sc.next();
        boolean isAdmin = (usuario.equals("admin")) && (contrasena.equals("root1234"));
        
        boolean terminado = false;
        while(!terminado) {
            terminado = engine.start(isAdmin);
        }

    }
}