package modelo;

import java.util.ArrayList;

public class CasillaNormal extends Casilla {
    public CasillaNormal(int posicion, ArrayList<Jugador> jugadoresActuales) {
        super(posicion, jugadoresActuales);
    }

    @Override
    public void realizarAccion() {
        // No fa res especial
    }
}