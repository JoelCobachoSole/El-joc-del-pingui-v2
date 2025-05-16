package modelo;

import java.util.ArrayList;

public class Trineo extends Casilla {

	public Trineo(int posicion, ArrayList<Jugador> jugadoresActuales) {
		super(posicion, jugadoresActuales);
	}

	@Override
	public String realizarAccion(Jugador jugador) {
		int posActual = jugador.getPosicion();
		if (this.tablero != null) {
			for (int i = posActual + 1; i < tablero.getCasillas().size(); i++) {
				if (tablero.getCasillas().get(i) instanceof Trineo) {
					jugador.setPosicion(i);
					return "¡Has encontrado un Trineo! Avanzas al siguiente trineo.";
				}
			}
		}
		return "¡Has encontrado un Trineo! Pero no hay más trineos adelante.";
	}

}
