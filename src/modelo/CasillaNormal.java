package modelo;

import java.util.ArrayList;

public class CasillaNormal extends Casilla {
    public CasillaNormal(int posicion, ArrayList<Jugador> jugadores) {
        super(posicion, jugadores);
    }

    @Override
    public String realizarAccion(Jugador jugador) {
        // No ocurre nada especial en una casilla normal
        return "Casilla normal.";
    }
}