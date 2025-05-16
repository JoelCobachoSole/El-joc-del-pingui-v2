package modelo;

import java.io.Serializable;

public class JugadorDB extends Jugador implements Serializable {
    private static final long serialVersionUID = 1L;

    private String password;
    private int nivel;
    private String partida;

    public JugadorDB(int posicion, String nombre, String color, String password, int nivel, String partida) {
        super(posicion, nombre, color);
        this.password = password;
        this.nivel = nivel;
        this.partida = partida;
    }

    public String getPassword() {
        return password;
    }

    public int getNivel() {
        return nivel;
    }

    public String getPartida() {
        return partida;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public void setPartida(String partida) {
        this.partida = partida;
    }
}
