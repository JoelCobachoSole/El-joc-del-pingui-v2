package modelo;

import java.util.ArrayList;

public class SueloQuebradizo extends Casilla {

    public SueloQuebradizo(int posicion, ArrayList<Jugador> jugadoresActuales) {
        super(posicion, jugadoresActuales);
    }

    @Override
    public String realizarAccion(Jugador jugador) {
        // Implementa la lógica que quieras aquí
        return "Has pisado un suelo quebradizo.";
    }
}
