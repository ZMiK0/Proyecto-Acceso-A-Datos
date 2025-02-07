package me.proyecto;

public class Producto {
    private int id;
    private int id_proveedor;
    private String nombre;
    private float precio;
    private int stock; 

    public Producto(int id, int id_proveedor, String nombre, float precio, int stock){
        this.id = id;
        this.id_proveedor = id_proveedor;
        this.nombre = nombre;
        this.precio = precio;
        this.stock = stock;
    }

    @Override
    public String toString(){
        return "Producto{" +
        "id=" + id +
        ", id_proveedor=" + id_proveedor +
        ", nombre= '" + nombre + '\'' +
        ", precio= " + precio +
        ", stock= " + stock +
        '}';
    }
}
