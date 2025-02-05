package me.proyecto;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Engine engine = new Engine();
        
        boolean terminado = false;
        while(!terminado) {
            terminado = engine.start();
        }

    }
}