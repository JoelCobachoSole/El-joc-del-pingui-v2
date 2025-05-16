package modelo;

import java.io.Serializable;

public class EstadoJuego implements Serializable {
    private static final long serialVersionUID = 1L;

    private JugadorDB jugador;
    private Tablero tablero;
    private int turnoActual;

    public EstadoJuego(JugadorDB jugador, Tablero tablero, int turnoActual) {
        this.jugador = jugador;
        this.tablero = tablero;
        this.turnoActual = turnoActual;
    }

    public JugadorDB getJugador() {
        return jugador;
    }

    public void setJugador(JugadorDB jugador) {
        this.jugador = jugador;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public void setTablero(Tablero tablero) {
        this.tablero = tablero;
    }

    public int getTurnoActual() {
        return turnoActual;
    }

    public void setTurnoActual(int turnoActual) {
        this.turnoActual = turnoActual;
    }
}
