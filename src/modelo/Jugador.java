package modelo;

import java.io.Serializable;

public abstract class Jugador implements Serializable {
    private static final long serialVersionUID = 1L;

    private int posicion;
    private String nombre;
    private String color;
    private Tablero tablero;

    private Inventario inventario = new Inventario();

    public Jugador(int posicion, String nombre, String color) {
        this.posicion = posicion;
        this.nombre = nombre;
        this.color = color;
    }

    public Inventario getInv() {
        return inventario;
    }

    public void setInv(Inventario inventario) {
        this.inventario = inventario;
    }

    public int getPosicion() {
        return posicion;
    }

    public void setPosicion(int posicion) {
        this.posicion = posicion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public void tirarDado(int maximoDado) {
        // Implementación específica
    }

    public void moverPosicion(int p) {
        // Implementación específica
    }
}
