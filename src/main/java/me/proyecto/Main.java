package me.proyecto;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();
        
        boolean terminado = false;
        while(!terminado) {
            terminado = engine.start();
        }

    }
}