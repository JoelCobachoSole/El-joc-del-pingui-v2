package modelo;

import java.util.ArrayList;

public class CasillaNormal extends Casilla {
    public CasillaNormal(int posicion, ArrayList<Jugador> jugadores) {
        super(posicion, jugadores);
    }

    @Override
    public void realizarAccion() {
        // No ocurre nada especial en una casilla normal
    }
}